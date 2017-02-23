package br.com.ajudadecusto.metodos;

import javax.swing.JRadioButton;

import br.com.bean.AjudadeCustoBean;
import br.com.bean.ParcelasAjudadeCustoBean;
import br.com.controller.Data;
import br.com.controller.Show;
import br.com.dao.AjudadeCustoDAO;
import br.com.metodosgenericos.MetodosGenericosAjudadeCusto;

/**
 * Esta classe é responsável por pegar os dados da tela de cadastro de ajuda de
 * custo e enviar para a classe AjudadeCustoDAO para persistência de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class CadastraAjudadeCustoMetodos {

	private String primeiroVencimento;
	private MetodosGenericosAjudadeCusto metodoAjudadeCusto;
	private AjudadeCustoDAO ajudaDAO;

	/**
	 * Este método age como uma ponte entre as classes
	 * 'TelaCadastroAjudadeCusto' e 'AjudadeCustoDAO'.
	 * 
	 * @param cpf
	 * @param idFormaPagamento
	 * @param vlrParcela
	 * @param primeiroVencimento
	 * @param observacoes
	 * @param radio
	 */
	public void cadastrar(String cpf, int idFormaPagamento, double vlrParcela,
			String primeiroVencimento, String observacoes, JRadioButton radio[]) {

		metodoAjudadeCusto = new MetodosGenericosAjudadeCusto();
		ajudaDAO = new AjudadeCustoDAO();
		this.primeiroVencimento = primeiroVencimento;

		int idResponsavelPagamento = 0;

		byte idCategoria = 1;
		int idInterno = metodoAjudadeCusto.getIdInterno(cpf);
		int idResponsavel = metodoAjudadeCusto.getResponsavel(idInterno);
		int idConvenio = metodoAjudadeCusto.getConvenio(idInterno);

		// ::::::::convenio::::::::
		if (radio[0].isSelected()) {
			idCategoria = 1;
			idResponsavelPagamento = idConvenio;
		}
		// ::::::::responsavel::::::::
		else if (radio[1].isSelected()) {
			idCategoria = 2;
			idResponsavelPagamento = idResponsavel;
		}
		// ::::::::interno::::::::
		else if (radio[2].isSelected()) {
			idCategoria = 3;
			idResponsavelPagamento = idInterno;
		}

		AjudadeCustoBean ajudadeCusto = metodoAjudadeCusto.getAjudaBean(
				idInterno, idResponsavelPagamento, idCategoria,
				idFormaPagamento, vlrParcela, observacoes);

		// ::::::::cadastra a ajuda de custo e gera as parcelas::::::::
		boolean novaAjuda = ajudaDAO.insertAjudadeCusto(ajudadeCusto);
		if (novaAjuda == true) {
			gerarParcelas();
		}
	}

	/**
	 * Este método é utilizado para gerar as 6 parcelas da ajuda de custo do
	 * sistema. Ela só é chamada quando uma nova ajuda é cadastrada com sucesso.
	 * Assim estas parcelas serão vinculadas a nova ajuda de custo.
	 */
	private void gerarParcelas() {

		int ultimoRegistro = ajudaDAO.getMaxId();
		String dataVencimento[] = new String[6];

		dataVencimento[0] = primeiroVencimento;
		dataVencimento[1] = Data.showDate(primeiroVencimento, 0, 1, 0);
		dataVencimento[2] = Data.showDate(primeiroVencimento, 0, 2, 0);
		dataVencimento[3] = Data.showDate(primeiroVencimento, 0, 3, 0);
		dataVencimento[4] = Data.showDate(primeiroVencimento, 0, 4, 0);
		dataVencimento[5] = Data.showDate(primeiroVencimento, 0, 5, 0);

		for (int i = 0; i < dataVencimento.length; i++) {
			ParcelasAjudadeCustoBean parcelas = metodoAjudadeCusto
					.getParcelaBean(ultimoRegistro, dataVencimento[i], 0.00, 0,
							"", i + 1);
			ajudaDAO.gerarParcelas(parcelas);
		}

		ajudaDAO.closeConnection();
		Show.informacao("Ajuda de custo cadastrada com sucesso.");
	}
}
