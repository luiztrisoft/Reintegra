package br.com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.PedidoMedicamentosBean;
import br.com.bean.PedidoMedicamentosBeanTabela;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Data;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe é responsável por realizar a persistência das informações na base
 * de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class PedidoMedicamentosDAO {

	private Connection connection;

	/**
	 * Método construtor padrão da classe.
	 */
	public PedidoMedicamentosDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método persiste um novo pedido de medicamento na base de dados.
	 * 
	 * @param pedido
	 */
	public void insert(PedidoMedicamentosBean pedido) {
		String query = "INSERT INTO PEDIDO_MEDICAMENTO(ID_INTERNO, MEDICAMENTO, DATA_PEDIDO, PRECO, OBSERVACOES, PAGO, DATA_PAGAMENTO) VALUES(?,?,?,?,?,?,?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, pedido.getIdInterno());
			statement.setString(2, pedido.getMedicamento());
			statement.setDate(3, new Date(pedido.getDataPedido().getCalendar()
					.getTimeInMillis()));
			statement.setDouble(4, pedido.getPreco());
			statement.setString(5, pedido.getObservacoes());
			statement.setString(6, pedido.getPago());

			if (pedido.getDataPagamento() != null) {
				statement.setDate(7, new Date(pedido.getDataPagamento()
						.getCalendar().getTimeInMillis()));
			} else {
				statement.setNull(7, Types.DATE);
			}
			statement.execute();
			statement.close();
			connection.close();
			Show.informacao("Pedido cadastrado com sucesso.");
			Save.log(Config.usuarioLogado, "Cadastrou um pedido de medicamento");
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("Falha ao cadastrar pedido.");
			Save.log(Config.usuarioLogado, "Falhou ao cadastrar um novo pedido");
		}
	}

	/**
	 * Este método é utilizado para realizar a alteração dos dados de um pedido
	 * de medicamento especificado pelo seu ID.
	 * 
	 * @param pedido
	 */
	public void update(PedidoMedicamentosBean pedido) {
		String query = "UPDATE PEDIDO_MEDICAMENTO SET MEDICAMENTO=?, DATA_PEDIDO=?, PRECO=?, OBSERVACOES=?, PAGO=?, DATA_PAGAMENTO=? WHERE ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, pedido.getMedicamento());
			statement.setDate(2, new Date(pedido.getDataPedido().getCalendar()
					.getTimeInMillis()));
			statement.setDouble(3, pedido.getPreco());
			statement.setString(4, pedido.getObservacoes());
			statement.setString(5, pedido.getPago());
			if (pedido.getDataPagamento() != null) {
				statement.setDate(6, new Date(pedido.getDataPagamento()
						.getCalendar().getTimeInMillis()));
			} else {
				statement.setNull(6, Types.DATE);
			}
			statement.setInt(7, pedido.getId());

			statement.execute();
			statement.close();
			connection.close();

			Save.log(
					Config.usuarioLogado,
					"Alterou os dados do pedido de medicamento nº "
							+ pedido.getId());
			Show.informacao("Os dados deste pedido foram alterados com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			Save.log(
					Config.usuarioLogado,
					"Falhou ao alterar o pedido de medicamento nº "
							+ pedido.getId());
			Show.erro("Erro ao alterar.\n" + e.getMessage());
		}
	}

	/**
	 * Este método é utilizado para remover um pedido de medicamentos
	 * especificado por seu ID da base de dados.
	 * 
	 * @param idPedido
	 */
	public void delete(int idPedido) {
		String query = "DELETE FROM PEDIDO_MEDICAMENTO WHERE ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idPedido);
			statement.execute();
			statement.close();
			connection.close();

			Save.log(Config.usuarioLogado,
					"Deletou o pedido de medicamento nº " + idPedido);
			Show.informacao("Os dados deste pedido foram deletados com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.usuarioLogado,
					"Falhou ao deletar o pedido de medicamento nº " + idPedido);
			Show.erro("Erro ao deletar.\n" + e.getMessage());
		}
	}

	/**
	 * Este método retorna uma lista com os pedidos de medicamentos de acordo
	 * com os parâmetros fornecidos.
	 * 
	 * @param statusPagamento
	 * 
	 * @param dataMin
	 * @param dataMax
	 * @return {@link PedidoMedicamentosBeanTabela}
	 */
	public List<PedidoMedicamentosBeanTabela> getListPedido(
			byte statusPagamento, String dataMin, String dataMax) {

		String query = null;
		if (statusPagamento == 0) {
			query = sqlGeral(dataMin, dataMax);
		} else if (statusPagamento >= 1) {
			query = sqlFiltro(statusPagamento, dataMin, dataMax);
		}

		List<PedidoMedicamentosBeanTabela> lista = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet r = statement.executeQuery();

			while (r.next()) {
				PedidoMedicamentosBeanTabela pedido = new PedidoMedicamentosBeanTabela();
				pedido.setId(r.getInt("ID"));
				pedido.setNomeInterno(r.getString("NOME"));
				pedido.setMedicamento(r.getString("MEDICAMENTO"));
				pedido.getDataPedido().setDate(r.getDate("DATA_PEDIDO"));
				pedido.setPreco(r.getDouble("PRECO"));
				pedido.setPago(r.getString("PAGO"));

				lista.add(pedido);
			}
			r.close();
			statement.close();
			connection.close();

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.usuarioLogado, "Não foi possível retornar pedido\n"
					+ e.getMessage());
			Show.erro("Não foi possível retornar pedido\n" + e.getMessage());
			return null;
		}

	}

	/**
	 * Este método retorna uma lista com os pedidos de medicamentos específicos
	 * de um interno de acordo com o CPF fornecido.
	 * 
	 * @param statusPagamento
	 * @param cpf
	 * @param dataMin
	 * @param dataMax
	 * @return {@link PedidoMedicamentosBeanTabela}
	 */
	public List<PedidoMedicamentosBean> getListPedido(byte statusPagamento,
			String cpf, String dataMin, String dataMax) {

		String query = null;
		if (statusPagamento == 0) {
			query = sqlInternoGeral(cpf, dataMin, dataMax);
		} else if (statusPagamento >= 1) {
			query = sqlInternoFiltro(statusPagamento, cpf, dataMin, dataMax);
		}

		List<PedidoMedicamentosBean> lista = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet r = statement.executeQuery();

			while (r.next()) {
				PedidoMedicamentosBean pedido = new PedidoMedicamentosBean();
				pedido.setId(r.getInt("ID"));
				pedido.setMedicamento(r.getString("MEDICAMENTO"));
				pedido.getDataPedido().setDate(r.getDate("DATA_PEDIDO"));
				pedido.setPreco(r.getDouble("PRECO"));
				pedido.setPago(r.getString("PAGO"));

				lista.add(pedido);
			}
			r.close();
			statement.close();
			connection.close();

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.usuarioLogado, "Não foi possível retornar pedido\n"
					+ e.getMessage());
			Show.erro("Não foi possível retornar pedido\n" + e.getMessage());
			return null;
		}

	}

	/**
	 * Este método retorna um pedido de medicamento específico através do ID
	 * informado como parâmetro.
	 * 
	 * @param idPedido
	 * @return PedidoMedicamentosBean
	 */
	public PedidoMedicamentosBean getPedido(int idPedido) {
		String query = "SELECT * FROM PEDIDO_MEDICAMENTO WHERE ID=?";
		PedidoMedicamentosBean pedido = new PedidoMedicamentosBean();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idPedido);
			ResultSet r = statement.executeQuery();
			r.first();

			pedido.setMedicamento(r.getString("MEDICAMENTO"));
			pedido.getDataPedido().setDate(r.getDate("DATA_PEDIDO"));
			pedido.setPreco(r.getDouble("PRECO"));
			pedido.setPago(r.getString("PAGO"));
			pedido.getDataPagamento().setDate(r.getDate("DATA_PAGAMENTO"));
			pedido.setObservacoes(r.getString("OBSERVACOES"));

		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("Falha ao buscar pedido.\n" + e.getMessage());
		}
		return pedido;
	}

	/**
	 * Este método preenche e retorna uma coluna com os dados dos pedidos de
	 * medicamentos cadastrados na base de dados.
	 * 
	 * @param statusPagamento
	 * @param dataInicial
	 * @param dataFinal
	 * @param coluna
	 * @return DefaultTableModel
	 */
	/*
	 * public DefaultTableModel getPedidos(byte statusPagamento, String
	 * dataInicial, String dataFinal, DefaultTableModel coluna) {
	 * 
	 * String query = null; if (statusPagamento == 0) { query =
	 * sqlGeral(dataInicial, dataFinal); } else if (statusPagamento >= 1) {
	 * query = sqlFiltro(statusPagamento, dataInicial, dataFinal); }
	 * 
	 * try { PreparedStatement statement = connection.prepareStatement(query);
	 * ResultSet r = statement.executeQuery(); String pagto = ""; while
	 * (r.next()) { if (r.getString("PAGO").equals("N")) { pagto = "Não"; } else
	 * if (r.getString("PAGO").equals("S")) { pagto = "Sim"; } coluna.addRow(new
	 * Object[] { r.getString("PEDIDO_MEDICAMENTO.ID"), r.getString("NOME"),
	 * r.getString("MEDICAMENTO"), Data.dateFormat(r.getDate("DATA_PEDIDO")),
	 * r.getDouble("PRECO"), pagto }); } statement.close(); r.close();
	 * connection.close(); } catch (Exception e) { e.printStackTrace(); } return
	 * coluna; }
	 */

	/**
	 * Este método retorna uma lista que serve para preencher a tabela de pedido
	 * de medicamentos informando o código, nome do interno, medicamento, data
	 * do pedido, preço e se está pago ou não.
	 * 
	 * @param statusPagamento
	 * @param dataInicial
	 * @param dataFinal
	 * @return List<PedidoMedicamentoBeanTabela>
	 */
	public List<PedidoMedicamentosBeanTabela> getList(byte statusPagamento,
			String dataInicial, String dataFinal) {

		String query = null;
		if (statusPagamento == 0) {
			query = sqlGeral(dataInicial, dataFinal);
		} else if (statusPagamento >= 1) {
			query = sqlFiltro(statusPagamento, dataInicial, dataFinal);
		}

		List<PedidoMedicamentosBeanTabela> list = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet r = statement.executeQuery();
			while (r.next()) {

				PedidoMedicamentosBeanTabela bean = new PedidoMedicamentosBeanTabela();
				bean.setId(r.getInt("PEDIDO_MEDICAMENTO.ID"));
				bean.setNomeInterno(r.getString("NOME"));
				bean.setMedicamento(r.getString("MEDICAMENTO"));
				bean.getDataPedido().setDate(r.getDate("DATA_PEDIDO"));
				bean.setPreco(r.getDouble("PRECO"));
				bean.setPago(r.getString("PAGO"));

				list.add(bean);
			}
			statement.close();
			r.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Este método retorna um valor inteiro contendo a quantidade de registros
	 * de pedidos de medicamentos que estão pagos ou que estão pendentes para
	 * serem apresentados no gráfico visual.
	 * 
	 * @param pago
	 * @param dataMinima
	 * @param dataMaxima
	 * @return int
	 */
	public int getCountPago(String pago, String dataMinima, String dataMaxima) {

		String min = Data.traditionalFormat(dataMinima);
		String max = Data.traditionalFormat(dataMaxima);

		StringBuilder s = new StringBuilder();
		s.append("SELECT PAGO FROM PEDIDO_MEDICAMENTO WHERE PAGO='");
		s.append(pago);
		s.append("' AND DATA_PEDIDO BETWEEN '");
		s.append(min);
		s.append("' AND '");
		s.append(max);
		s.append("'");
		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(s
					.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getString("PAGO").equals(pago)) {
					count++;
				}
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}

	}

	/**
	 * Este método gera um script sql que retorna os valores de pedido de
	 * medicamentos filtrados somente pelo intervalo de datas.
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return String
	 */
	private String sqlGeral(String dataInicial, String dataFinal) {

		String dataInicio = Data.traditionalFormat(dataInicial);
		String dataFim = Data.traditionalFormat(dataFinal);

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT PEDIDO_MEDICAMENTO.ID, ");
		sb.append(" NOME, ");
		sb.append(" MEDICAMENTO, ");
		sb.append(" DATA_PEDIDO, ");
		sb.append(" PRECO, ");
		sb.append(" PAGO");
		sb.append(" FROM ");
		sb.append(" PEDIDO_MEDICAMENTO, INTERNOS ");
		sb.append(" WHERE DATA_PEDIDO BETWEEN '");
		sb.append(dataInicio);
		sb.append("' AND '");
		sb.append(dataFim);
		sb.append("' AND PEDIDO_MEDICAMENTO.ID_INTERNO = INTERNOS.ID ");
		sb.append(" ORDER BY DATA_PEDIDO");

		return sb.toString();
	}

	/**
	 * Este método gera um script sql que retorna os valores de pedido de
	 * medicamentos filtrados pelo intervalo de datas e pelo status de
	 * pagamento.
	 * 
	 * @param statusPagamento
	 * @param dataInicial
	 * @param dataFinal
	 * @return String
	 */
	private String sqlFiltro(byte statusPagamento, String dataInicial,
			String dataFinal) {

		String dataInicio = Data.traditionalFormat(dataInicial);
		String dataFim = Data.traditionalFormat(dataFinal);
		String pago = null;

		if (statusPagamento == 1) {
			pago = "S";
		} else if (statusPagamento == 2) {
			pago = "N";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT PEDIDO_MEDICAMENTO.ID, ");
		sb.append(" INTERNOS.NOME, ");
		sb.append(" MEDICAMENTO, ");
		sb.append(" DATA_PEDIDO, ");
		sb.append(" PRECO, ");
		sb.append(" PAGO");
		sb.append(" FROM ");
		sb.append(" PEDIDO_MEDICAMENTO, INTERNOS ");
		sb.append(" WHERE DATA_PEDIDO BETWEEN '");
		sb.append(dataInicio);
		sb.append("' AND '");
		sb.append(dataFim);
		sb.append("' AND PAGO='");
		sb.append(pago);
		sb.append("' AND PEDIDO_MEDICAMENTO.ID_INTERNO = INTERNOS.ID ");
		sb.append(" ORDER BY DATA_PEDIDO");

		return sb.toString();
	}

	/**
	 * Este método gera um script sql que retorna os valores de pedido de
	 * medicamentos filtrados pelo CPF do interno e pelo intervalo de datas.
	 * 
	 * @param cpf
	 * @param dataInicial
	 * @param dataFinal
	 * @return
	 */
	private String sqlInternoGeral(String cpf, String dataInicial,
			String dataFinal) {
		String dataInicio = Data.traditionalFormat(dataInicial);
		String dataFim = Data.traditionalFormat(dataFinal);

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT PEDIDO_MEDICAMENTO.ID, ");
		// sb.append(" NOME, ");
		sb.append(" MEDICAMENTO, ");
		sb.append(" DATA_PEDIDO, ");
		sb.append(" PRECO, ");
		sb.append(" PAGO");
		sb.append(" FROM ");
		sb.append(" PEDIDO_MEDICAMENTO, INTERNOS ");
		sb.append(" WHERE DATA_PEDIDO BETWEEN '");
		sb.append(dataInicio);
		sb.append("' AND '");
		sb.append(dataFim);
		sb.append("' AND PEDIDO_MEDICAMENTO.ID_INTERNO = INTERNOS.ID ");
		sb.append(" AND INTERNOS.CPF = '");
		sb.append(cpf);
		sb.append("' ");
		sb.append(" ORDER BY DATA_PEDIDO");

		return sb.toString();
	}

	/**
	 * Este método gera um script sql que retorna os valores de pedido de
	 * medicamentos filtrados pelo CPF do interno, intervalo de datas e pelo
	 * status de pagamento.
	 * 
	 * @param statusPagamento
	 * @param cpf
	 * @param dataInicial
	 * @param dataFinal
	 * @return String
	 */
	private String sqlInternoFiltro(byte statusPagamento, String cpf,
			String dataInicial, String dataFinal) {

		String dataInicio = Data.traditionalFormat(dataInicial);
		String dataFim = Data.traditionalFormat(dataFinal);
		String pago = null;

		if (statusPagamento == 1) {
			pago = "S";
		} else if (statusPagamento == 2) {
			pago = "N";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT PEDIDO_MEDICAMENTO.ID, ");
		// sb.append(" INTERNOS.NOME, ");
		sb.append(" MEDICAMENTO, ");
		sb.append(" DATA_PEDIDO, ");
		sb.append(" PRECO, ");
		sb.append(" PAGO");
		sb.append(" FROM ");
		sb.append(" PEDIDO_MEDICAMENTO, INTERNOS ");
		sb.append(" WHERE DATA_PEDIDO BETWEEN '");
		sb.append(dataInicio);
		sb.append("' AND '");
		sb.append(dataFim);
		sb.append("' AND PAGO='");
		sb.append(pago);
		sb.append("' AND PEDIDO_MEDICAMENTO.ID_INTERNO = INTERNOS.ID ");
		sb.append(" AND INTERNOS.CPF = '");
		sb.append(cpf);
		sb.append("' ");
		sb.append(" ORDER BY DATA_PEDIDO");

		return sb.toString();
	}
}
