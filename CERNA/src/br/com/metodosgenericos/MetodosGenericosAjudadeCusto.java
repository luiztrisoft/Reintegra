package br.com.metodosgenericos;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import br.com.bean.AjudadeCustoBean;
import br.com.bean.FormaPagamentoBean;
import br.com.bean.InternoBean;
import br.com.bean.ParcelasAjudadeCustoBean;
import br.com.bean.ResponsavelBean;
import br.com.controller.Data;
import br.com.controller.Show;
import br.com.dao.AjudadeCustoDAO;
import br.com.dao.ConvenioDAO;
import br.com.dao.InternoDAO;
import br.com.dao.ResponsavelDAO;

/**
 * Esta classe possui os m�todos principais da entidade ajuda de custo.
 * 
 * @author Luiz Alberto
 * 
 */
public class MetodosGenericosAjudadeCusto extends MetodosGenericos {

	/**
	 * Este m�todo preenche e retorna o combo box com os internos cadastrados na
	 * base de dados.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbInterno() {
		JComboBox<String> combo = new JComboBox<String>();
		InternoDAO dao = new InternoDAO();
		List<InternoBean> lista = dao.listaInterno();
		for (InternoBean interno : lista) {
			combo.addItem(interno.getNome());
		}
		return combo;
	}

	/**
	 * Este m�todo preenche e retorna o combo box com os respons�veis
	 * cadastrados na base de dados.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbResponsavel() {
		JComboBox<String> combo = new JComboBox<String>();
		ResponsavelDAO dao = new ResponsavelDAO();
		List<ResponsavelBean> lista = dao.listaResponsavel();
		for (ResponsavelBean responsavel : lista) {
			combo.addItem(responsavel.getNome());
		}
		return combo;
	}

	/**
	 * Este m�todo preenche e retorna o combo box com as formas de pagamento
	 * cadastrados na base de dados.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbFormadePagamento() {
		JComboBox<String> combo = new JComboBox<String>();
		AjudadeCustoDAO dao = new AjudadeCustoDAO();
		List<FormaPagamentoBean> lista = dao.listaFormasPagamento();
		for (FormaPagamentoBean bean : lista) {
			combo.addItem(bean.getForma());
		}
		return combo;
	}

	/**
	 * Este m�todo retorna o id do respons�vel vinculado ao interno.
	 * 
	 * @param id
	 * @return long
	 */
	public int getResponsavel(int id) {
		ResponsavelDAO respDAO = new ResponsavelDAO();
		ResponsavelBean responsavel = respDAO.getResponsavel(id);
		return responsavel.getId();
	}

	/**
	 * Este m�todo retorna o id do conv�nio vinculado ao interno.
	 * 
	 * @param id
	 * @return int
	 */
	public int getConvenio(long id) {
		ConvenioDAO dao = new ConvenioDAO();
		int idConvenio = dao.getConvenio(id);
		return idConvenio;
	}

	/**
	 * Este m�todo recebe os par�metros necess�rios de AjudadeCustoBean para
	 * preench�-los e retorn�-los prontos para persist�ncia na classe
	 * AjudadeCustoDAO.
	 * 
	 * @param idInterno
	 * @param idResponsavelPagamento
	 * @param idCategoria
	 * @param idFormaPagamento
	 * @param vlrParcelas
	 * @param observacoes
	 * @return AjudadeCustoBean
	 */
	public AjudadeCustoBean getAjudaBean(int idInterno,
			int idResponsavelPagamento, byte idCategoria, int idFormaPagamento,
			double vlrParcelas, String observacoes) {
		AjudadeCustoBean ajudadeCusto = new AjudadeCustoBean();
		ajudadeCusto.setInterno(idInterno);
		ajudadeCusto.setResponsavelPagamento(idResponsavelPagamento);
		ajudadeCusto.setCategoria(idCategoria);
		ajudadeCusto.setFormaPagamento(idFormaPagamento);
		ajudadeCusto.setValorParcelas(vlrParcelas);
		ajudadeCusto.setObservacoes(observacoes);
		return ajudadeCusto;
	}

	/**
	 * Este m�todo recebe os par�metros necess�rios de AjudadeCustoBean para
	 * preench�-los e retorn�-los prontos para persist�ncia na classe
	 * AjudadeCustoDAO.
	 * 
	 * Obs.: Este m�todo difere do anterior pois ele tamb�m guarda o ID da ajuda
	 * de custo.
	 * 
	 * @param id
	 * @param idInterno
	 * @param idResponsavelPagamento
	 * @param idCategoria
	 * @param idFormaPagamento
	 * @param vlrParcelas
	 * @param observacoes
	 * @return AjudadeCustoBean
	 */
	public AjudadeCustoBean getAjudaBean(int id, int idInterno,
			int idResponsavelPagamento, byte idCategoria, int idFormaPagamento,
			double vlrParcelas, String observacoes) {
		AjudadeCustoBean ajudadeCusto = new AjudadeCustoBean();
		ajudadeCusto.setId(id);
		ajudadeCusto.setInterno(idInterno);
		ajudadeCusto.setResponsavelPagamento(idResponsavelPagamento);
		ajudadeCusto.setCategoria(idCategoria);
		ajudadeCusto.setFormaPagamento(idFormaPagamento);
		ajudadeCusto.setValorParcelas(vlrParcelas);
		ajudadeCusto.setObservacoes(observacoes);
		return ajudadeCusto;
	}

	/**
	 * Este m�todo recebe os par�metros necess�rios de ParcelaAjudadeCustoBean
	 * para preench�-los e retorn�-los prontos para persist�ncia na classe
	 * AjudadeCustoDAO.
	 * 
	 * @param ajudadeCusto
	 * @param primeiroVencimento
	 * @param valor
	 * @param pago
	 * @param observacoes
	 * @param mes
	 * 
	 * @return ParcelasAjudadeCustoBean
	 */
	public ParcelasAjudadeCustoBean getParcelaBean(int ajudadeCusto,
			String primeiroVencimento, double valor, int pago,
			String observacoes, int mes) {
		ParcelasAjudadeCustoBean parcela = new ParcelasAjudadeCustoBean();
		parcela.setAjudadeCusto(ajudadeCusto);
		parcela.getVencimento().setCalendar(
				Data.stringToDate(null, primeiroVencimento));
		parcela.setValorPago(valor);
		parcela.setPago(pago);
		parcela.setObservacoes(observacoes);
		parcela.setMes(mes);
		return parcela;
	}

	/**
	 * Este m�todo serve para adicionar uma nova forma de pagamento a base de
	 * dados.
	 */
	public void adicionaFormadePagamento(JComboBox<String> combo) {
		AjudadeCustoDAO ajudaDAO = new AjudadeCustoDAO();
		boolean confirm = Show
				.caixaConfirmacao("Deseja cadastrar uma nova forma de pagamento?");
		if (confirm == true) {
			String forma = Show.caixaTexto("Digite a forma de pagamento");
			if (forma.length() < 2) {
				Show.alerta("A forma de pagamento deve ter pelo menos 2 caracteres.");
			} else {
				FormaPagamentoBean formaBean = new FormaPagamentoBean();
				formaBean.setForma(forma);
				boolean cadastro = ajudaDAO.insertFormadePagamento(formaBean);
				if (cadastro == true) {
					combo.addItem(forma);
				}
			}
		}
	}

	/**
	 * Este m�todo preenche uma DefaultTableModel contendo as ajudas de custo do
	 * interno informado.
	 * 
	 * @param coluna
	 * @param idInterno
	 */
	public void tabelaAjudadeCusto(DefaultTableModel coluna, int idInterno) {
		Timer timer = new Timer(0, null);
		timer.start();

		AjudadeCustoDAO dao = new AjudadeCustoDAO();
		dao.preencherTabela(coluna, idInterno);
		timer.stop();
	}

	/**
	 * Este m�todo retorna um valor int com o ID do interno na tabela de ajuda
	 * de custo.
	 * 
	 * @param idAjudadeCusto
	 * @return int
	 */
	public int getIdInterno(int idAjudadeCusto) {
		AjudadeCustoDAO dao = new AjudadeCustoDAO();
		int id = dao.getIdInterno(idAjudadeCusto);
		return id;
	}
}
