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
 * Esta classe possui m�todos que verificam se o CPF � cadastrado no banco de
 * dados. Se for ele direciona para as telas desejadas pelo usu�rio que s�o
 * cadastro, altera��o e quita��o de parcelas.
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
	 * Os m�todos a seguir s�o respons�veis por controlar e validar os dados da
	 * ajuda de custo do interno antes de serem enviados para cadastro a fim de
	 * evitar redund�ncias e prov�veis erros.
	 * 
	 * ========================================
	 */

	/**
	 * Este m�todo abre uma caixa de di�logo para inser��o de CPF. Ele verifica
	 * a exist�ncia do interno no sistema e as condi��es para cadastro de ajuda
	 * de custo. Esta condi��es verificam se ele � ativo e contribuinte. Ap�s
	 * isso ele � direcionado para a tela de cadastro. Existe tamb�m a
	 * verifica��o se o interno j� possui ajuda de custo cadastrada no sistema.
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
	 * Este m�todo verifica se o cadastro do interno � ativo e contribuinte para
	 * ent�o cadastrar a ajuda de custo no sistema.
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
	 * Este m�todo abre a tela de cadastro de ajuda de custo do interno.
	 * 
	 * @param cpf
	 */
	private void telaCadastro(String cpf) {
		TelaCadastroAjudadeCusto tcac = new TelaCadastroAjudadeCusto(null,
				Config.tituloJanela, true, cpf);
		tcac.setVisible(true);
	}

	/**
	 * Este m�todo mostra uma mensagem quando o interno(a) � isento de ajuda de
	 * custo.
	 * 
	 * @param nome
	 */
	private void isentoMessage(String nome) {
		StringBuilder msg = new StringBuilder();
		msg.append("<html><b>");
		msg.append(nome);
		msg.append("</b> � isento(a) de ajuda de custo.\nPara adicionar ajuda de custo, o interno deve ser 'Contribuinte'.");
		Show.informacao(msg.toString());
	}

	/**
	 * Este m�todo mostra uma mensagem quando o interno(a) possui o status
	 * 'Passivo'.
	 * 
	 * @param nome
	 */
	private void statusMessage(String nome) {
		StringBuilder msg = new StringBuilder();
		msg.append("<html>O status de <b>");
		msg.append(nome);
		msg.append("</b> � 'Passivo'.\nPara adicionar ajuda de custo, altere seu status para 'Ativo'");
		Show.informacao(msg.toString());
	}

	/**
	 * Este m�todo � invocado quando o interno j� possui uma ajuda de custo
	 * cadastrada no sistema e sua fun��o � exibir uma caixa de confirma��o
	 * perguntando se o usu�rio quer cadastrar outra ajuda ou n�o.
	 * 
	 * @param interno
	 * @return boolean
	 */
	private boolean verifyAjudaCusto(InternoBean interno) {
		StringBuilder msg = new StringBuilder();
		msg.append("<html><b>");
		msg.append(interno.getNome());
		msg.append("</b> j� possui uma ajuda de custo cadastrada.\nDeseja adicionar uma nova ajuda de custo?");
		boolean existeAjudaCadastrada = Show.caixaConfirmacao(msg.toString());
		return existeAjudaCadastrada;
	}

	/*
	 * ========================================
	 * 
	 * ALTERA��O DE AJUDA DE CUSTO
	 * 
	 * Os m�todos a seguir s�o respons�veis por controlar e validar os dados da
	 * ajuda de custo do interno antes de serem enviados para altera��o a fim de
	 * evitar redund�ncias e prov�veis erros.
	 * 
	 * ========================================
	 */

	/**
	 * Este m�todo faz algumas valida��es de interno antes de abrir a tela de
	 * altera��o de parcela.
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
	 * Este m�todo apresenta na tela as ajudas de custo que um interno tenha
	 * cadastrado na base de dados para, o usu�rio selecionar qual ser�
	 * alterada.
	 */
	private void tabelaAlterarAjudadeCustoIntenros(int id) {
		new SelecaoContaAlteracao(null, Config.tituloJanela, true, id)
				.setVisible(true);
	}

	/*
	 * ========================================
	 * 
	 * QUITA��O DE AJUDA DE CUSTO
	 * 
	 * Os m�todos a seguir s�o respons�veis por controlar e validar os dados da
	 * ajuda de custo do interno antes de serem enviados para quita��o a fim de
	 * evitar redund�ncias e prov�veis erros.
	 * 
	 * ========================================
	 */

	/**
	 * Este m�todo faz algumas valida��es de interno antes de abrir a tela de
	 * quita��o de parcela.
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
	 * Este m�todo abre a tela de sele��o de ajuda de custo a ter sua parcela
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
	 * REMO��O DE AJUDA DE CUSTO
	 * 
	 * Os m�todos a seguir s�o respons�veis pela remo��o da ajuda de custo
	 * selecionada na tabela.
	 * 
	 * ========================================
	 */
	/**
	 * Este m�todo faz algumas valida��es de interno antes de abrir a tela de
	 * remo��o de parcela.
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
	 * Este m�todo abre a view de sele��o de conta a ser removida.
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
	 * M�TODOS GEN�RICOS
	 * 
	 * Os m�todos a seguir s�o de uso gen�rico, podendo ser utilizados pela
	 * altera��o, quita��o, cadastro etc.
	 * 
	 * ========================================
	 */

	/**
	 * Este m�todo abre a caixa de di�logo para inser��o
	 * 
	 * @return String
	 */
	private String getCPF() {
		String cpf = Show.caixaTexto("Digite o CPF do interno");
		return cpf;
	}

	/**
	 * Este m�todo exibe uma mensagem dizendo que o interno informado n�o possui
	 * ajuda de custo cadastrada no sistema.
	 * 
	 * @param interno
	 */
	private void naoExisteAjudadeCusto(InternoBean interno) {
		StringBuilder msg = new StringBuilder();
		msg.append("<html>O interno(a) '");
		msg.append(interno.getNome());
		msg.append("' <b>N�O</b> possui\n ajuda de custo cadastrada no sistema");
		Show.informacao(msg.toString());
	}

}
