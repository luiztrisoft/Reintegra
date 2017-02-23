package br.com.ajudadecusto.metodos;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import br.com.bean.ParcelasAjudadeCustoBean;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.AjudadeCustoDAO;
import br.com.frames.TelaSenha;

/**
 * Esta classe se comunica com a classe AjudadeCustoDAO para realizar a quitação
 * das parcelas pré-selecionadas pelo usuário na view TelaQuitacaoAjudadeCusto.
 * 
 * @author Luiz Alberto
 * 
 */
public class QuitaAjudadeCustoMetodos {

	public void quitar(int idAjudadeCusto, JCheckBox boxQuitar[],
			JTextField campoValorPago[], JTextField campoObservacoes[]) {

		double valorPago[] = new double[Config.QTD_PARCELAS];
		String observacaoIndividual[] = new String[Config.QTD_PARCELAS];

		// ::::::::array de parcelas de ajuda de custo::::::::
		ParcelasAjudadeCustoBean parcela[] = new ParcelasAjudadeCustoBean[Config.QTD_PARCELAS];

		for (int i = 0; i < Config.QTD_PARCELAS; i++) {
			valorPago[i] = Double.parseDouble(campoValorPago[i].getText()
					.replaceAll(",", "."));
			observacaoIndividual[i] = campoObservacoes[i].getText();
			short mes = 1;

			// ::::::::new ParcelasAjudadeCustoBean::::::::
			parcela[i] = new ParcelasAjudadeCustoBean();
			parcela[i].setAjudadeCusto(idAjudadeCusto);
			parcela[i].setValorPago(valorPago[i]);
			parcela[i].setObservacoes(observacaoIndividual[i]);
			parcela[i].setMes(mes + i);
			if (boxQuitar[i].isSelected() == true) {
				parcela[i].setPago(1);
			} else {
				parcela[i].setPago(0);
			}
		}

		AjudadeCustoDAO dao = new AjudadeCustoDAO();

		// ::::::::quitar parcela(s)::::::::
		try {
			for (int i = 0; i < Config.QTD_PARCELAS; i++) {
				dao.quitarParcela(parcela[i]);
				logConfirmacao(boxQuitar[i], parcela[i].getMes(),
						parcela[i].getAjudadeCusto());
			}
			dao.closeConnection();
			Show.informacao("Parcela quitada com sucesso.");

			registrarEntradaLivroCaixa();

		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("Erro ao quitar parcela.\n" + e.getMessage());
			Save.log(Config.usuarioLogado, "Falha ao tentar quitar a parcela ");
		}
	}

	/**
	 * Este método abre a tela de inserção no livro caixa, logo após realizar a
	 * quitação de parcelas de ajuda de custo. O sistema informa o valor 2 como
	 * parâmetro do método. Ele direciona para a tela de entrada de registro de
	 * livro caixa.
	 */
	private void registrarEntradaLivroCaixa() {
		boolean registrarEntrada = Show
				.caixaConfirmacao("Deseja registrar as parcelas quitadas no livro caixa?");
		if (registrarEntrada == true) {
			byte frame = 2;
			TelaSenha tSenha = new TelaSenha(null, Config.tituloJanela, true,
					frame);
			tSenha.setVisible(true);
		}
	}

	/**
	 * Este método grava nos logs do sistema o mês e a ajuda de custo que o
	 * usuário que está logado quitou.
	 * 
	 * @param box
	 * @param mes
	 * @param ajudadeCusto
	 */
	private void logConfirmacao(JCheckBox box, int mes, int ajudadeCusto) {
		if (box.isEnabled() == true && box.isSelected() == true) {
			Save.log(
					Config.usuarioLogado,
					new StringBuilder("Quitou a parcela N° ").append(mes)
							.append(" da ajuda de custo N° ")
							.append(ajudadeCusto).toString());
		}
	}
}
