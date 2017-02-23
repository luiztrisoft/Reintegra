package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.ControleBean;
import br.com.connectionfactory.ConnectionFactory;

public class ControleDAO {

	private Connection connection;

	public ControleDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método utiliza os demais métodos como o nomeResponsavel e os métodos
	 * que retornam instruções SQL para preencher a coluna de controle de ajuda
	 * de custo.
	 * 
	 * @param todaAjudadeCusto
	 * @param pagamentoAjudadeCusto
	 * @param status
	 * @param contribuicao
	 * @param convenio
	 * @return List<ControleBean>
	 */
	public List<ControleBean> getControle(boolean todaAjudadeCusto,
			byte pagamentoAjudadeCusto, byte status, byte contribuicao,
			String convenio) {
		String query = null;

		if (todaAjudadeCusto == true) {
			query = todaAjudadeCusto();
		} else {
			if (pagamentoAjudadeCusto == 2) {
				query = ajudadeCustoRegulares(status, contribuicao, convenio);
			} else if (pagamentoAjudadeCusto == 1) {
				query = ajudadeCustoSituacaoPagamento(pagamentoAjudadeCusto,
						status, contribuicao, convenio);
			} else if (pagamentoAjudadeCusto == 0) {
				query = ajudadeCustoSituacaoPagamento(pagamentoAjudadeCusto,
						status, contribuicao, convenio);
			}
		}

		List<ControleBean> lista = new ArrayList<>();

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				ControleBean controle = new ControleBean();
				controle.setIdAjudadeCusto(resultSet.getInt(1));
				controle.setNomeInterno(resultSet.getString(2));
				controle.getEntrada().setDate(resultSet.getDate(3));
				controle.getV1().setDate(resultSet.getDate(4));
				controle.getV2().setDate(resultSet.getDate(5));
				controle.getV3().setDate(resultSet.getDate(6));
				controle.getV4().setDate(resultSet.getDate(7));
				controle.getV5().setDate(resultSet.getDate(8));
				controle.getV6().setDate(resultSet.getDate(9));
				controle.setFormadePagamento(resultSet.getString(10));
				controle.setObservacaoGeral(resultSet.getString(11));

				int responsavel = resultSet.getInt(12);
				int categoria = resultSet.getInt(13);
				controle.setResponsavelPagto(nomeResponsavel(categoria,
						responsavel));

				controle.setOb1(resultSet.getString(14));
				controle.setOb2(resultSet.getString(15));
				controle.setOb3(resultSet.getString(16));
				controle.setOb4(resultSet.getString(17));
				controle.setOb5(resultSet.getString(18));
				controle.setOb6(resultSet.getString(19));

				lista.add(controle);
			}

			statement.close();
			resultSet.close();
			connection.close();
			return lista;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return lista;
		}
	}

	/**
	 * Este método serve para retornar o nome da categoria do responsável pelo
	 * pagamento da ajuda de custo que pode ser "Convênios", "Responsável" e
	 * "Internos".
	 * 
	 * @param categoria
	 * @param responsavel
	 * @return
	 */
	private String nomeResponsavel(int categoria, int responsavel) {
		String query = "";
		String nome = "";
		if (categoria == 1) {
			query = "SELECT NOME FROM CONVENIOS WHERE ID = ?";
		} else if (categoria == 2) {
			query = "SELECT NOME FROM RESPONSAVEL WHERE ID = ?";
		} else if (categoria == 3) {
			query = "SELECT NOME FROM INTERNOS WHERE ID = ?";
		}

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, responsavel);

			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				nome = rs.getString("NOME");
			}
			statement.close();
			rs.close();
			return nome;
		} catch (Exception e) {
			e.printStackTrace();
			return nome;
		}
	}

	/**
	 * Este método retorna uma instrução SQL com todos os registros da ajuda de
	 * custo
	 * 
	 * 
	 * @return String
	 */
	private String todaAjudadeCusto() {
		StringBuilder builder = new StringBuilder();

		builder.append("SELECT AJUDA_CUSTO.ID, INTERNOS.NOME,INTERNOS.DATA_ADMISSAO ,");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 1 AND VENCIMENTO ),");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append("WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append("AND PARCELAS_AJUDA_CUSTO.MES = 2),");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 3),");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 4),");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");

		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO  ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 5), ");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO  ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 6), ");

		builder.append("(SELECT FORMA_PAGTO.FORMA FROM FORMA_PAGTO ");
		builder.append(" WHERE FORMA_PAGTO.ID = AJUDA_CUSTO.ID_FORMA_PAGTO ),");

		builder.append(" AJUDA_CUSTO.OBS ");

		builder.append(", AJUDA_CUSTO.ID_RESPONSAVEL_PAGTO, ");
		builder.append(" AJUDA_CUSTO.ID_CATEGORIA ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 1) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 2) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 3) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 4) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 5) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 6) ");

		builder.append(" FROM INTERNOS, AJUDA_CUSTO, PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE  INTERNOS.ID = AJUDA_CUSTO.ID_INTERNO  ");
		builder.append(" AND AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND MES = 1 ");

		builder.append(" ORDER BY INTERNOS.NOME");

		return builder.toString();
	}

	/**
	 * Este método retorna uma instrução SQL com as ajudas de custo que estão
	 * regulares no sistema, além da escolha de parâmetros que informa se o
	 * interno é ativo/passivo e contribuinte/isento.
	 * 
	 * @param status
	 * @param contribuicao
	 * @param convenio
	 * @return String
	 */
	private String ajudadeCustoRegulares(byte status, byte contribuicao,
			String convenio) {
		StringBuilder builder = new StringBuilder();

		builder.append("SELECT AJUDA_CUSTO.ID, INTERNOS.NOME,INTERNOS.DATA_ADMISSAO ,");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 1 ),");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 2),");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 3),");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 4),");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");

		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO  ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 5), ");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO  ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 6), ");

		builder.append("(SELECT FORMA_PAGTO.FORMA FROM FORMA_PAGTO ");
		builder.append(" WHERE FORMA_PAGTO.ID = AJUDA_CUSTO.ID_FORMA_PAGTO ),");

		builder.append(" AJUDA_CUSTO.OBS ");

		builder.append(", AJUDA_CUSTO.ID_RESPONSAVEL_PAGTO, ");
		builder.append(" AJUDA_CUSTO.ID_CATEGORIA ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 1 ) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 2) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 3) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 4) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 5) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 6) ");

		builder.append(" FROM INTERNOS, AJUDA_CUSTO, PARCELAS_AJUDA_CUSTO, CONVENIOS ");
		builder.append(" WHERE  INTERNOS.ID = AJUDA_CUSTO.ID_INTERNO  ");
		builder.append(" AND AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND MES = 1 AND INTERNOS.ID_STATUS = ");
		builder.append(status);
		builder.append(" AND INTERNOS.ID_CONTRIBUICAO = ");
		builder.append(contribuicao);

		builder.append(" AND INTERNOS.ID_CONVENIO = CONVENIOS.ID ");
		builder.append(" AND  CONVENIOS.NOME='");
		builder.append(convenio);
		builder.append("' ");

		builder.append(" ORDER BY INTERNOS.NOME");

		return builder.toString();
	}

	/**
	 * Este método retorna uma instrução SQL com a escolha de parâmetros que
	 * informa se o interno é ativo/passivo e contribuinte/isento e também o que
	 * deverá retornar são os pendentes ou regulares através do parâmetro
	 * 'pagamentoAjudadeCusto'.
	 * 
	 * @param pagamentoAjudadeCusto
	 * @param status
	 * @param contribuicao
	 * @param convenio
	 * @return String
	 */
	private String ajudadeCustoSituacaoPagamento(byte pagamentoAjudadeCusto,
			byte status, byte contribuicao, String convenio) {
		StringBuilder builder = new StringBuilder();

		builder.append("SELECT AJUDA_CUSTO.ID, INTERNOS.NOME,INTERNOS.DATA_ADMISSAO ,");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 1  AND VENCIMENTO < CURRENT_DATE() AND PARCELAS_AJUDA_CUSTO.PAGO =");
		builder.append(pagamentoAjudadeCusto);
		builder.append("), ");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append("WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append("AND PARCELAS_AJUDA_CUSTO.MES = 2 AND VENCIMENTO < CURRENT_DATE() AND PARCELAS_AJUDA_CUSTO.PAGO =");
		builder.append(pagamentoAjudadeCusto);
		builder.append("), ");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 3 AND VENCIMENTO < CURRENT_DATE() AND PARCELAS_AJUDA_CUSTO.PAGO =");
		builder.append(pagamentoAjudadeCusto);
		builder.append("), ");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 4 AND VENCIMENTO < CURRENT_DATE() AND PARCELAS_AJUDA_CUSTO.PAGO =");
		builder.append(pagamentoAjudadeCusto);
		builder.append("), ");

		builder.append(" (SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO  ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 5 AND VENCIMENTO < CURRENT_DATE() AND PARCELAS_AJUDA_CUSTO.PAGO =");
		builder.append(pagamentoAjudadeCusto);
		builder.append("), ");

		builder.append("(SELECT PARCELAS_AJUDA_CUSTO.VENCIMENTO FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO  ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 6 AND VENCIMENTO < CURRENT_DATE() AND PARCELAS_AJUDA_CUSTO.PAGO =");
		builder.append(pagamentoAjudadeCusto);
		builder.append("), ");

		builder.append("(SELECT FORMA_PAGTO.FORMA FROM FORMA_PAGTO ");
		builder.append(" WHERE FORMA_PAGTO.ID = AJUDA_CUSTO.ID_FORMA_PAGTO ),");

		builder.append(" AJUDA_CUSTO.OBS ");

		builder.append(", AJUDA_CUSTO.ID_RESPONSAVEL_PAGTO, ");
		builder.append(" AJUDA_CUSTO.ID_CATEGORIA ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 1) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 2) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 3) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 4) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 5) ");

		builder.append(" ,(SELECT OBS FROM PARCELAS_AJUDA_CUSTO ");
		builder.append(" WHERE AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND PARCELAS_AJUDA_CUSTO.MES = 6) ");

		builder.append(" FROM INTERNOS, AJUDA_CUSTO, PARCELAS_AJUDA_CUSTO, CONVENIOS ");
		builder.append(" WHERE  INTERNOS.ID = AJUDA_CUSTO.ID_INTERNO  ");
		builder.append(" AND AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO ");
		builder.append(" AND MES = 1 AND INTERNOS.ID_STATUS = ");
		builder.append(status);
		builder.append(" AND INTERNOS.ID_CONTRIBUICAO = ");
		builder.append(contribuicao);

		builder.append(" AND INTERNOS.ID_CONVENIO = CONVENIOS.ID ");
		builder.append(" AND  CONVENIOS.NOME='");
		builder.append(convenio);
		builder.append("' ");

		builder.append(" ORDER BY INTERNOS.NOME");

		return builder.toString();
	}

}
