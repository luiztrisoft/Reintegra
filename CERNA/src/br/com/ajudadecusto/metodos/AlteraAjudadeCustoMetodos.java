package br.com.ajudadecusto.metodos;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.AjudadeCustoBean;
import br.com.bean.ParcelasAjudadeCustoBean;
import br.com.controller.Data;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.AjudadeCustoDAO;
import br.com.metodosgenericos.MetodosGenericosAjudadeCusto;

/**
 * Esta classe pega os dados do formulário e envia para a classe AjudadeCustoDAO
 * para alteração dos dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class AlteraAjudadeCustoMetodos {

	/**
	 * Método utilizado para alterar os dados da ajuda de custo do interno.
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
	public void alterar(int idAjudadeCusto, JTextField campoVencimento[],
			JTextField campoValorPago[], JTextField campoObservacoes[],
			JTextField campoValordasParcelas, JComboBox<String> combo,
			JRadioButton radio[], JTextArea area) {

		try {

			AjudadeCustoDAO dao = new AjudadeCustoDAO();
			MetodosGenericosAjudadeCusto funcao = new MetodosGenericosAjudadeCusto();

			/*
			 * ==============================================
			 * 
			 * AJUDA DE CUSTO
			 * 
			 * Aqui será tratada a alteração dos dados da ajuda de custo
			 * pertencente a determinado interno.
			 * 
			 * ==============================================
			 */

			// ::::::::Define os ID's e demais campos::::::::
			byte idCategoria = 1;
			int idResponsavelPagamento = 0;
			int idFormaPagamento = funcao.indiceFormaPagto(combo
					.getItemAt(combo.getSelectedIndex()));
			int idInterno = funcao.getIdInterno(idAjudadeCusto);
			int idResponsavel = funcao.getResponsavel(idInterno);
			int idConvenio = funcao.getConvenio(idInterno);
			double valorParcelas = Double.parseDouble(campoValordasParcelas
					.getText().replaceAll(",", "."));
			String observacoes = area.getText();

			// ::::::::Define o responsável pelo pagamento::::::::
			if (radio[0].isSelected() == true) {
				idCategoria = 1;
				idResponsavelPagamento = idConvenio;
			} else if (radio[1].isSelected() == true) {
				idCategoria = 2;
				idResponsavelPagamento = idResponsavel;
			} else if (radio[2].isSelected() == true) {
				idCategoria = 3;
				idResponsavelPagamento = idInterno;
			}

			// ::::::::new AjudadeCustoBean::::::::
			AjudadeCustoBean ajuda = funcao.getAjudaBean(idAjudadeCusto,
					idInterno, idResponsavelPagamento, idCategoria,
					idFormaPagamento, valorParcelas, observacoes);

			/*
			 * ==============================================
			 * 
			 * PARCELAS DA AJUDA DE CUSTO
			 * 
			 * Aqui serão alteradas as parcelas pertencentes a ajuda de custo
			 * determinada acima.
			 * 
			 * ==============================================
			 */

			double valorPago[] = new double[Config.QTD_PARCELAS];
			String dataVencimento[] = new String[Config.QTD_PARCELAS];
			String observacaoIndividual[] = new String[Config.QTD_PARCELAS];

			for (int i = 0; i < Config.QTD_PARCELAS; i++) {
				valorPago[i] = Double.parseDouble(campoValorPago[i].getText()
						.replaceAll(",", "."));
				dataVencimento[i] = campoVencimento[i].getText();
				observacaoIndividual[i] = campoObservacoes[i].getText();
			}

			// ::::::::new ParcelasAjudadeCustoBean::::::::
			ParcelasAjudadeCustoBean parcela[] = new ParcelasAjudadeCustoBean[Config.QTD_PARCELAS];

			// parcela.setId(nao definido);

			for (int i = 0; i < Config.QTD_PARCELAS; i++) {
				parcela[i] = new ParcelasAjudadeCustoBean();
				parcela[i].setAjudadeCusto(idAjudadeCusto);
				parcela[i].getVencimento().setCalendar(
						Data.stringToDate(null, dataVencimento[i]));
				parcela[i].setValorPago(valorPago[i]);
				parcela[i].setObservacoes(observacaoIndividual[i]);
				parcela[i].setMes(i + 1);
			}

			// ::::::::alterar a ajuda de custo::::::::
			try {
				boolean bool = dao.updateAjudadeCusto(ajuda);
				if (bool == true) {
					for (int i = 0; i < Config.QTD_PARCELAS; i++) {
						dao.updateParcelaAjudadeCusto(parcela[i]);
					}
					cadastroOk(idAjudadeCusto);
				}
			} catch (Exception e) {
				cadastroFail(idAjudadeCusto, e);
			}

			// ::::::::Fecha esta conexão::::::::
			dao.closeConnection();

		} catch (Exception e) {
			e.printStackTrace();
			Show.alerta(e.getMessage());
		}
	}

	/**
	 * Este método mostra a mensagem informando que a alteração de ajuda de
	 * custo e suas parcelas foram bem sucedidas e salva o log no sistema.
	 * 
	 * @param idAjudadeCusto
	 */
	private void cadastroOk(int idAjudadeCusto) {
		Show.informacao("Ajuda de custo alterada com sucesso");
		Save.log(Config.usuarioLogado, new StringBuilder(
				"Alterou a ajuda de custo n° ").append(idAjudadeCusto)
				.toString());
		
	}

	/**
	 * Este método informa que a alteração de ajuda de custo e suas parcelas não
	 * foram bem sucedidas, além de salvar o log e imprimir o erro através do
	 * método printStackTrace.
	 * 
	 * @param idAjudadeCusto
	 * @param e
	 */
	private void cadastroFail(int idAjudadeCusto, Exception e) {
		e.printStackTrace();
		Save.log(Config.usuarioLogado,
				new StringBuilder(
						"Falhou ao tentar alterar a ajuda de custo n° ")
						.append(idAjudadeCusto).toString());
		Show.erro(new StringBuilder("Falhao ao alterar ajuda de custo.\n")
				.append(e.getMessage()).toString());
	}

	// private void imprimir(ParcelasAjudadeCustoBean parcela) {
	// System.out.println("vencimento "
	// + parcela.getVencimento().getCalendar().getTimeInMillis());
	// System.out.println("valor pago " + parcela.getValorPago());
	// System.out.println("observações " + parcela.getObservacoes());
	// System.out.println("mês " + parcela.getMes());
	// System.out.println("=================================");
	//
	// }
}
