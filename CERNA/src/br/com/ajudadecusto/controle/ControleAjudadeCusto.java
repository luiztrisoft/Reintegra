package br.com.ajudadecusto.controle;

import br.com.ajudadecusto.frames.SelecaoContaAlteracao;
import br.com.ajudadecusto.frames.SelecaoContaQuitacao;
import br.com.ajudadecusto.frames.SelecaoContaRemocao;
import br.com.ajudadecusto.frames.TelaCadastroAjudadeCusto;
import br.com.bean.InternoBean;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.AjudadeCustoDAO;
import br.com.dao.InternoDAO;

/**
 * Esta classe possui métodos que verificam se o CPF é cadastrado no banco de
 * dados. Se for ele direciona para as telas desejadas pelo usuário que são
 * cadastro, alteração e quitação de parcelas.
 * 
 * @author Luiz Alberto
 * 
 */
public class ControleAjudadeCusto {

	/*
	 * ========================================
	 * 
	 * CADASTRO DE AJUDA DE CUSTO
	 * 
	 * Os métodos a seguir são responsáveis por controlar e validar os dados da
	 * ajuda de custo do interno antes de serem enviados para cadastro a fim de
	 * evitar redundâncias e prováveis erros.
	 * 
	 * ========================================
	 */

	/**
	 * Este método abre uma caixa de diálogo para inserção de CPF. Ele verifica
	 * a existência do interno no sistema e as condições para cadastro de ajuda
	 * de custo. Esta condições verificam se ele é ativo e contribuinte. Após
	 * isso ele é direcionado para a tela de cadastro. Existe também a
	 * verificação se o interno já possui ajuda de custo cadastrada no sistema.
	 */
	public void cadastroAjudadeCusto() {
		String cpf = getCPF();

		InternoDAO internoDAO = new InternoDAO();
		AjudadeCustoDAO ajudaDAO = new AjudadeCustoDAO();
		InternoBean interno = internoDAO.getInterno(cpf);

		boolean novoCadastro = false;
		if (internoDAO.verifyCPF(cpf) == true) {
			if (ajudaDAO.verifyIdInternos(cpf) == true) {
				novoCadastro = verifyAjudaCusto(interno);
				if (novoCadastro == true) {
					validarInterno(interno);
				}
			} else {
				validarInterno(interno);
			}
		}
	}

	/**
	 * Este método verifica se o cadastro do interno é ativo e contribuinte para
	 * então cadastrar a ajuda de custo no sistema.
	 * 
	 * @param interno
	 */
	private void validarInterno(InternoBean interno) {
		if (interno.getStatus() == 1) {
			if (interno.getContribuicao() == 1) {
				telaCadastro(interno.getCpf());
			} else {
				isentoMessage(interno.getNome());
			}
		} else {
			statusMessage(interno.getNome());
		}
	}

	/**
	 * Este método abre a tela de cadastro de ajuda de custo do interno.
	 * 
	 * @param cpf
	 */
	private void telaCadastro(String cpf) {
		TelaCadastroAjudadeCusto tcac = new TelaCadastroAjudadeCusto(null,
				Config.tituloJanela, true, cpf);
		tcac.setVisible(true);
	}

	/**
	 * Este método mostra uma mensagem quando o interno(a) é isento de ajuda de
	 * custo.
	 * 
	 * @param nome
	 */
	private void isentoMessage(String nome) {
		StringBuilder msg = new StringBuilder();
		msg.append("<html><b>");
		msg.append(nome);
		msg.append("</b> é isento(a) de ajuda de custo.\nPara adicionar ajuda de custo, o interno deve ser 'Contribuinte'.");
		Show.informacao(msg.toString());
	}

	/**
	 * Este método mostra uma mensagem quando o interno(a) possui o status
	 * 'Passivo'.
	 * 
	 * @param nome
	 */
	private void statusMessage(String nome) {
		StringBuilder msg = new StringBuilder();
		msg.append("<html>O status de <b>");
		msg.append(nome);
		msg.append("</b> é 'Passivo'.\nPara adicionar ajuda de custo, altere seu status para 'Ativo'");
		Show.informacao(msg.toString());
	}

	/**
	 * Este método é invocado quando o interno já possui uma ajuda de custo
	 * cadastrada no sistema e sua função é exibir uma caixa de confirmação
	 * perguntando se o usuário quer cadastrar outra ajuda ou não.
	 * 
	 * @param interno
	 * @return boolean
	 */
	private boolean verifyAjudaCusto(InternoBean interno) {
		StringBuilder msg = new StringBuilder();
		msg.append("<html><b>");
		msg.append(interno.getNome());
		msg.append("</b> já possui uma ajuda de custo cadastrada.\nDeseja adicionar uma nova ajuda de custo?");
		boolean existeAjudaCadastrada = Show.caixaConfirmacao(msg.toString());
		return existeAjudaCadastrada;
	}

	/*
	 * ========================================
	 * 
	 * ALTERAÇÃO DE AJUDA DE CUSTO
	 * 
	 * Os métodos a seguir são responsáveis por controlar e validar os dados da
	 * ajuda de custo do interno antes de serem enviados para alteração a fim de
	 * evitar redundâncias e prováveis erros.
	 * 
	 * ========================================
	 */

	/**
	 * Este método faz algumas validações de interno antes de abrir a tela de
	 * alteração de parcela.
	 */
	public void alteracaoAjudadeCusto() {
		String cpf = getCPF();
		InternoDAO internoDAO = new InternoDAO();
		AjudadeCustoDAO ajudaDAO = new AjudadeCustoDAO();
		InternoBean interno = internoDAO.getInterno(cpf);

		if (internoDAO.verifyCPF(cpf) == true) {
			if (ajudaDAO.verifyIdInternos(cpf) == true) {
				tabelaAlterarAjudadeCustoIntenros(interno.getId());
			} else {
				naoExisteAjudadeCusto(interno);
			}
		}
	}

	/**
	 * Este método apresenta na tela as ajudas de custo que um interno tenha
	 * cadastrado na base de dados para, o usuário selecionar qual será
	 * alterada.
	 */
	private void tabelaAlterarAjudadeCustoIntenros(int id) {
		new SelecaoContaAlteracao(null, Config.tituloJanela, true, id)
				.setVisible(true);
	}

	/*
	 * ========================================
	 * 
	 * QUITAÇÃO DE AJUDA DE CUSTO
	 * 
	 * Os métodos a seguir são responsáveis por controlar e validar os dados da
	 * ajuda de custo do interno antes de serem enviados para quitação a fim de
	 * evitar redundâncias e prováveis erros.
	 * 
	 * ========================================
	 */

	/**
	 * Este método faz algumas validações de interno antes de abrir a tela de
	 * quitação de parcela.
	 */
	public void quitacaoAjudadeCusto() {
		String cpf = getCPF();
		InternoDAO internoDAO = new InternoDAO();
		AjudadeCustoDAO ajudaDAO = new AjudadeCustoDAO();
		InternoBean interno = internoDAO.getInterno(cpf);

		if (internoDAO.verifyCPF(cpf) == true) {
			if (ajudaDAO.verifyIdInternos(cpf) == true) {
				tabelaQuitarAjudadeCustoIntenros(interno.getId());
			} else {
				naoExisteAjudadeCusto(interno);
			}
		}
	}

	/**
	 * Este método abre a tela de seleção de ajuda de custo a ter sua parcela
	 * quitada.
	 * 
	 * @param id
	 */
	private void tabelaQuitarAjudadeCustoIntenros(int id) {
		new SelecaoContaQuitacao(null, Config.tituloJanela, true, id)
				.setVisible(true);
	}

	/*
	 * ========================================
	 * 
	 * REMOÇÃO DE AJUDA DE CUSTO
	 * 
	 * Os métodos a seguir são responsáveis pela remoção da ajuda de custo
	 * selecionada na tabela.
	 * 
	 * ========================================
	 */
	/**
	 * Este método faz algumas validações de interno antes de abrir a tela de
	 * remoção de parcela.
	 */
	public void removerAjudadeCusto() {
		String cpf = getCPF();
		InternoDAO internoDAO = new InternoDAO();
		AjudadeCustoDAO ajudaDAO = new AjudadeCustoDAO();
		InternoBean interno = internoDAO.getInterno(cpf);

		if (internoDAO.verifyCPF(cpf) == true) {
			if (ajudaDAO.verifyIdInternos(cpf) == true) {
				tabelaRemocaoAjudadeCusto(interno.getId());
			} else {
				naoExisteAjudadeCusto(interno);
			}
		}
	}

	/**
	 * Este método abre a view de seleção de conta a ser removida.
	 * 
	 * @param idInterno
	 */
	private void tabelaRemocaoAjudadeCusto(Integer idInterno) {
		new SelecaoContaRemocao(null, Config.tituloJanela, true, idInterno)
				.setVisible(true);
	}

	/*
	 * ========================================
	 * 
	 * MÉTODOS GENÉRICOS
	 * 
	 * Os métodos a seguir são de uso genérico, podendo ser utilizados pela
	 * alteração, quitação, cadastro etc.
	 * 
	 * ========================================
	 */

	/**
	 * Este método abre a caixa de diálogo para inserção
	 * 
	 * @return String
	 */
	private String getCPF() {
		String cpf = Show.caixaTexto("Digite o CPF do interno");
		return cpf;
	}

	/**
	 * Este método exibe uma mensagem dizendo que o interno informado não possui
	 * ajuda de custo cadastrada no sistema.
	 * 
	 * @param interno
	 */
	private void naoExisteAjudadeCusto(InternoBean interno) {
		StringBuilder msg = new StringBuilder();
		msg.append("<html>O interno(a) '");
		msg.append(interno.getNome());
		msg.append("' <b>NÃO</b> possui\n ajuda de custo cadastrada no sistema");
		Show.informacao(msg.toString());
	}

}
