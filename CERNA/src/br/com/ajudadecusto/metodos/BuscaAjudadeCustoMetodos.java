package br.com.ajudadecusto.metodos;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.AjudadeCustoBean;
import br.com.bean.ParcelasAjudadeCustoBean;
import br.com.controller.Config;
import br.com.dao.AjudadeCustoDAO;

/**
 * Esta classe é responsável por pegar os dados da ajuda de custo do interno na
 * base de dados e apresentá-las na view.
 * 
 * @author Luiz Alberto
 * 
 */
public class BuscaAjudadeCustoMetodos {

	/**
	 * Este método recupera os dados da ajuda de custo do interno que estão
	 * inseridas na base de dados do sistema e mostra na view
	 * TelaAlteracaoAjudadeCusto.
	 * 
	 * @param idAjudadeCusto
	 * @param campoVencimento
	 * @param campoValorPago
	 * @param campoObservacoes
	 * @param campoValordasParcelas
	 * @param combo
	 * @param radio
	 * @param area
	 */
	public void getAjudadeCusto(int idAjudadeCusto,
			JTextField campoVencimento[], JTextField campoValorPago[],
			JTextField campoObservacoes[], JTextField campoValordasParcelas,
			JComboBox<String> combo, JRadioButton radio[], JTextArea area) {
		try {
			AjudadeCustoDAO dao = new AjudadeCustoDAO();

			/*
			 * ==============================================
			 * 
			 * AJUDA DE CUSTO
			 * 
			 * Aqui será tratada a recuperação dos dados da ajuda de custo
			 * pertencente a determinado interno.
			 * 
			 * ==============================================
			 */

			AjudadeCustoBean ajuda = dao.getAjudadeCusto(idAjudadeCusto);

			// ::::::::valor das parcelas::::::::
			campoValordasParcelas.setText("" + ajuda.getValorParcelas());

			// ::::::::forma de pagamento::::::::
			combo.setSelectedItem(dao.getNomeFormaPagto(ajuda
					.getFormaPagamento()));
			// ::::::::responsavel pelo pagamento::::::::
			if (ajuda.getCategoria() == 1) {
				radio[0].setSelected(true);
			} else if (ajuda.getCategoria() == 2) {
				radio[1].setSelected(true);
			} else if (ajuda.getCategoria() == 3) {
				radio[2].setSelected(true);
			}

			// ::::::::observacoes gerais::::::::
			area.setText(ajuda.getObservacoes());

			/*
			 * ==============================================
			 * 
			 * PARCELAS DA AJUDA DE CUSTO
			 * 
			 * Aqui serão tratadas as parcelas pertencentes a ajuda de custo de
			 * um determinado interno recuperada acima.
			 * 
			 * ==============================================
			 */

			int idAjuda = ajuda.getId();

			ParcelasAjudadeCustoBean parcela[] = new ParcelasAjudadeCustoBean[Config.QTD_PARCELAS];

			for (int i = 0; i < Config.QTD_PARCELAS; i++) {
				parcela[i] = dao.getParcelaAjudadeCusto(idAjuda, i + 1);
				campoVencimento[i]
						.setText(parcela[i].getVencimento().getDate());
				campoValorPago[i].setText("" + parcela[i].getValorPago());
				campoObservacoes[i].setText(parcela[i].getObservacoes());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Este método recupera os dados da ajuda de custo e mostra na view
	 * TelaQuitacaoAjudadeCusto para que possam ter suas parcelas quitadas.
	 * 
	 * @param idAjudadeCusto
	 * @param campoVencimento
	 * @param boxQuitar
	 * @param campoValorPago
	 * @param campoObservacoes
	 */
	public void getAjudadeCusto(int idAjudadeCusto, JLabel campoVencimento[],
			JCheckBox boxQuitar[], JTextField campoValorPago[],
			JTextField campoObservacoes[]) {

		try {
			AjudadeCustoDAO dao = new AjudadeCustoDAO();
			ParcelasAjudadeCustoBean parcela[] = new ParcelasAjudadeCustoBean[Config.QTD_PARCELAS];

			for (int i = 0; i < Config.QTD_PARCELAS; i++) {
				parcela[i] = dao.getParcelaAjudadeCusto(idAjudadeCusto, i + 1);
				if (parcela[i].getPago() == 1) {
					boxQuitar[i].setSelected(true);
					boxQuitar[i].setEnabled(false);
					boxQuitar[i].setVisible(false);
				}
				campoVencimento[i]
						.setText(parcela[i].getVencimento().getDate());
				campoValorPago[i].setText("" + parcela[i].getValorPago());
				campoObservacoes[i].setText(parcela[i].getObservacoes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
