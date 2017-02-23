package br.com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import br.com.bean.AjudadeCustoBean;
import br.com.bean.FormaPagamentoBean;
import br.com.bean.ParcelasAjudadeCustoBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe é responsável pela persistência e manipulação das informações de
 * ajuda de custo no banco de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class AjudadeCustoDAO {

	private Connection connection;

	public AjudadeCustoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método cadastra uma nova ajuda de custo no banco de dados. Ele
	 * retorna um valor boolean para o sistema saber que a ajuda de custo foi
	 * gerada e então gerar as parcelas através de seu ID. Note que a conexão
	 * não será fechada enquanto não forem geradas as seis parcelas de ajuda de
	 * custo.
	 * 
	 * @param ajuda
	 * @return boolean
	 */
	public boolean insertAjudadeCusto(AjudadeCustoBean ajuda) {
		String query = "INSERT INTO AJUDA_CUSTO (ID_INTERNO, ID_RESPONSAVEL_PAGTO, ID_CATEGORIA, ID_FORMA_PAGTO, VALOR_PARCELAS, OBS) VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, ajuda.getInterno());
			statement.setLong(2, ajuda.getResponsavelPagamento());
			statement.setInt(3, ajuda.getCategoria());
			statement.setInt(4, ajuda.getFormaPagamento());
			statement.setDouble(5, ajuda.getValorParcelas());
			statement.setString(6, ajuda.getObservacoes());

			statement.execute();
			statement.close();

			Save.log(Config.usuarioLogado,
					"Cadastrou uma nova ajuda de custo no sistema");
			return true;
		} catch (Exception e) {
			Save.log(Config.usuarioLogado,
					"Falhou ao tentar cadastrar uma nova ajuda de custo no sistema");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Este método altera os dados da ajuda de custo de um determinado interno.
	 * Após alterar a ajuda de custo, ela deverá retornar um valor true. Caso
	 * isso ocorra, o sistema também fará alteração das parcelas desta ajuda de
	 * custo através do método 'updateParcelaAjudadeCusto' da classe
	 * {@link AjudadeCustoDAO}.
	 * 
	 * @param ajuda
	 */
	public boolean updateAjudadeCusto(AjudadeCustoBean ajuda) {
		String query = "UPDATE AJUDA_CUSTO SET ID_INTERNO=?, ID_RESPONSAVEL_PAGTO=?, ID_CATEGORIA=?,ID_FORMA_PAGTO=?, VALOR_PARCELAS=?, OBS=? WHERE ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, ajuda.getInterno());
			statement.setInt(2, ajuda.getResponsavelPagamento());
			statement.setInt(3, ajuda.getCategoria());
			statement.setInt(4, ajuda.getFormaPagamento());
			statement.setDouble(5, ajuda.getValorParcelas());
			statement.setString(6, ajuda.getObservacoes());
			statement.setInt(7, ajuda.getId());
			statement.execute();
			statement.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Após alterar a ajuda de custo, este método deve ser chamado para alterar
	 * as parcelas referentes a esta ajuda de custo.
	 * 
	 * @param parcela
	 */
	public void updateParcelaAjudadeCusto(ParcelasAjudadeCustoBean parcela) {
		String query = "UPDATE PARCELAS_AJUDA_CUSTO SET VENCIMENTO = ?, VALOR_PAGO = ?, OBS= ? WHERE ID_AJUDA_CUSTO = ? AND MES = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setDate(1, new Date(parcela.getVencimento().getCalendar()
					.getTimeInMillis()));
			statement.setDouble(2, parcela.getValorPago());
			statement.setString(3, parcela.getObservacoes());
			statement.setInt(4, parcela.getAjudadeCusto());
			statement.setInt(5, parcela.getMes());
			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método serve para remover a ajuda de custo com ID igual ao valor
	 * informado como parâmetro.
	 * 
	 * @param ajuda
	 */
	public void delete(AjudadeCustoBean ajuda) {
		String query = "DELETE FROM AJUDA_CUSTO WHERE ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, ajuda.getId());
			statement.execute();
			statement.close();
			connection.close();

			Save.log(Config.usuarioLogado, "deletou a ajuda de custo N° "
					+ ajuda.getId());
			Show.alerta("Ajuda de custo removida com sucesso.");
		} catch (Exception e) {
			Show.erro("Falha ao remover ajuda de custo");
			e.printStackTrace();
		}
	}

	/**
	 * Este método pega o último id registrado na base de dados e o retorna como
	 * inteiro. Note que a conexão não será fechada enquanto não forem geradas
	 * as seis parcelas de ajuda de custo.
	 * 
	 * @return int
	 */
	public int getMaxId() {
		String query = "SELECT MAX(ID)AS ID FROM AJUDA_CUSTO";
		int ultimoId = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ultimoId = resultSet.getInt("ID");
			}

			statement.close();
			resultSet.close();

			return ultimoId;

		} catch (Exception e) {
			Show.erro(e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Este método serve para gerar as parcelas da ajuda de custo. A classe que
	 * o invoca deve fazer o laço que é percorrido seis vezes chamando este
	 * método. Note que a conexão não será fechada enquanto não forem geradas as
	 * seis parcelas de ajuda de custo.
	 * 
	 * @param parcelas
	 */
	public void gerarParcelas(ParcelasAjudadeCustoBean parcelas) {
		String query = "INSERT INTO PARCELAS_AJUDA_CUSTO(ID_AJUDA_CUSTO,VENCIMENTO, VALOR_PAGO, PAGO, OBS, MES) VALUES(?,?,?,?,?,?) ";
		try {
			PreparedStatement statement = connection.prepareStatement(query);

			statement.setLong(1, parcelas.getAjudadeCusto());
			statement.setDate(2, new Date(parcelas.getVencimento()
					.getCalendar().getTimeInMillis()));
			statement.setDouble(3, parcelas.getValorPago());
			statement.setInt(4, parcelas.getPago());
			statement.setString(5, parcelas.getObservacoes());
			statement.setInt(6, parcelas.getMes());

			statement.execute();
			statement.close();

		} catch (Exception e) {
			Show.erro(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Este método serve para fechar a conexão atual. Ele foi implementado
	 * devido a geração de seis parcelas não poder ser fechada a cada iteração.
	 * Assim sendo, quando todas as seis parcelas forem geradas, este método
	 * deverá ser chamado em seguida para encerrar a conexão atual.
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método serve para adicionar uma nova forma de pagamento a base de
	 * dados do sistema.
	 * 
	 * @param forma
	 * @return boolean
	 */
	public boolean insertFormadePagamento(FormaPagamentoBean forma) {
		String query = "INSERT INTO FORMA_PAGTO (FORMA) VALUES(?);";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, forma.getForma());
			statement.execute();
			statement.close();
			connection.close();

			Show.informacao("Forma de pagamento '" + forma.getForma()
					+ "' foi adicionada a base de dados");
			Save.log(Config.usuarioLogado, "Cadastrou a forma de pagamento '"
					+ forma.getForma() + "'");
			return true;
		} catch (Exception e) {
			Save.log(Config.usuarioLogado,
					"Falhou ao tentar cadastrar uma nova forma de pagamento");
			Show.erro(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Método que retorna uma lista com as formas de pagamento cadastradas na
	 * base de dados.
	 * 
	 * @return FormaPagamentoBean
	 */
	public List<FormaPagamentoBean> listaFormasPagamento() {
		String query = "SELECT ID, FORMA FROM FORMA_PAGTO ORDER BY ID";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<FormaPagamentoBean> lista = new ArrayList<>();
			while (resultSet.next()) {
				FormaPagamentoBean forma = new FormaPagamentoBean();
				forma.setId(resultSet.getInt("ID"));
				forma.setForma(resultSet.getString("FORMA"));
				lista.add(forma);
			}
			resultSet.close();
			statement.close();
			return lista;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método retorna o índice da entidade forma de pagamento selecionada.
	 * 
	 * @param s
	 * @return int
	 */
	public int getIdFormaPagto(String s) {
		String query = "SELECT ID,FORMA FROM FORMA_PAGTO WHERE FORMA=?";
		int id = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, s);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
			}
			statement.close();
			// connection.close();
			resultSet.close();
			return id;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método retorna uma String contendo a forma de pagamento de acordo
	 * com o indice informado.
	 * 
	 * @param idFormadePagamento
	 * @return String
	 */
	public String getNomeFormaPagto(int idFormadePagamento) {
		String query = "SELECT FORMA FROM FORMA_PAGTO,AJUDA_CUSTO WHERE FORMA_PAGTO.ID =? AND FORMA_PAGTO.ID = AJUDA_CUSTO.ID_FORMA_PAGTO";
		String forma = "";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idFormadePagamento);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				forma = resultSet.getString("FORMA");
			}
			statement.close();
			// connection.close();
			resultSet.close();
			return forma;
		} catch (Exception e) {
			Show.erro(e.getMessage());
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método retorna o índice da entidade convênio selecionada.
	 * 
	 * @param nome
	 * @return int
	 */
	public int getIdConvenios(String nome) {
		String query = "SELECT ID,NOME FROM CONVENIOS WHERE NOME=?";
		int id = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, nome);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
			}
			statement.close();
			// connection.close();
			resultSet.close();
			return id;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método serve para verificar se o ID do interno está cadastrado na
	 * tabela ajuda_custo.
	 * 
	 * @param cpf
	 * @return boolean
	 */
	public boolean verifyIdInternos(String cpf) {
		boolean retorno = false;
		String query = "SELECT INTERNOS.ID, AJUDA_CUSTO.ID_INTERNO FROM INTERNOS,AJUDA_CUSTO WHERE CPF=?  AND INTERNOS.ID=AJUDA_CUSTO.ID_INTERNO";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, cpf);
			ResultSet resultSet = statement.executeQuery();
			retorno = resultSet.first();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * Este método retorna uma DefaultTableModel contendo todas as ajudas de
	 * custo do interno informado através de seu ID.
	 * 
	 * @param coluna
	 * @param idInterno
	 * @return DefaultTableModel
	 */
	public DefaultTableModel preencherTabela(DefaultTableModel coluna,
			int idInterno) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT AJUDA_CUSTO.ID, INTERNOS.NOME FROM AJUDA_CUSTO, INTERNOS WHERE INTERNOS.ID = ");
		query.append(idInterno);
		query.append(" AND INTERNOS.ID = AJUDA_CUSTO.ID_INTERNO");
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				coluna.addRow(new Object[] {
						resultSet.getString("AJUDA_CUSTO.ID"),
						resultSet.getString("INTERNOS.NOME") });
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.usuarioLogado, "Não foi possível retornar coluna\n"
					+ e.getMessage());
			Show.erro("Não foi possível retornar coluna\n" + e.getMessage());
		}
		return coluna;
	}

	/**
	 * Método que retorna uma ajuda de custo cadastrada no banco de acordo com
	 * seu ID informado como parâmetro.
	 * 
	 * @param idAjudadeCusto
	 * @return AjudadeCustoBean
	 */
	public AjudadeCustoBean getAjudadeCusto(int idAjudadeCusto) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT ");
		query.append("ID,");
		query.append("VALOR_PARCELAS,");
		query.append("ID_FORMA_PAGTO, ");
		query.append("ID_RESPONSAVEL_PAGTO,");
		query.append("ID_CATEGORIA,");
		query.append("OBS");
		query.append(" FROM AJUDA_CUSTO ");
		query.append(" WHERE ID =");
		query.append(idAjudadeCusto);
		AjudadeCustoBean ajuda = new AjudadeCustoBean();
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet rs = statement.executeQuery();
			rs.first();
			ajuda.setId(rs.getInt("ID"));
			ajuda.setValorParcelas(rs.getDouble("VALOR_PARCELAS"));
			ajuda.setFormaPagamento(rs.getInt("ID_FORMA_PAGTO"));
			ajuda.setResponsavelPagamento(rs.getInt("ID_RESPONSAVEL_PAGTO"));
			ajuda.setCategoria(rs.getInt("ID_CATEGORIA"));
			ajuda.setObservacoes(rs.getString("OBS"));

			statement.close();
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajuda;

	}

	/**
	 * Este método recupera as informações da tabela de parcela de ajuda de
	 * custo de acordo com o ID da ajuda de custo informado como parâmetro.
	 * 
	 * @param idAjuda
	 * @param mes
	 * @return ParcelasAjudadeCustoBean
	 */
	public ParcelasAjudadeCustoBean getParcelaAjudadeCusto(int idAjuda, int mes) {

		StringBuilder query = new StringBuilder();
		query.append("SELECT  PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO, VENCIMENTO, VALOR_PAGO, PARCELAS_AJUDA_CUSTO.PAGO, PARCELAS_AJUDA_CUSTO.OBS, MES FROM PARCELAS_AJUDA_CUSTO , AJUDA_CUSTO ");
		query.append("WHERE AJUDA_CUSTO.ID = ");
		query.append(idAjuda);
		query.append(" AND AJUDA_CUSTO.ID = PARCELAS_AJUDA_CUSTO.ID_AJUDA_CUSTO AND MES =");
		query.append(mes);

		ParcelasAjudadeCustoBean parcela = new ParcelasAjudadeCustoBean();

		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet rs = statement.executeQuery();
			rs.first();

			parcela.setAjudadeCusto(rs.getInt("ID_AJUDA_CUSTO"));
			parcela.getVencimento().setDate(rs.getDate("VENCIMENTO"));
			parcela.setValorPago(rs.getDouble("VALOR_PAGO"));
			parcela.setPago(rs.getInt("PAGO"));
			parcela.setObservacoes(rs.getString("OBS"));
			parcela.setMes(rs.getInt("MES"));

			statement.close();
			rs.close();
			// connection.close();

			return parcela;

		} catch (Exception e) {
			e.printStackTrace();
			return parcela;
		}
	}

	/**
	 * Este método é usado para recuperar o ID do interno através do ID da ajuda
	 * de custo passado como parâmetro.
	 * 
	 * @param idAjudadeCusto
	 * @return int
	 */
	public int getIdInterno(int idAjudadeCusto) {
		String query = "SELECT ID_INTERNO FROM AJUDA_CUSTO WHERE ID=?";
		int idInterno = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idAjudadeCusto);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				idInterno = resultSet.getInt("ID_INTERNO");
			}
			statement.close();
			// connection.close();
			resultSet.close();
			return idInterno;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Este método é invocado quando o usuário quita pelo menos uma parcela da
	 * ajuda de custo do interno, alterando seus parâmetros.
	 * 
	 * @param parcela
	 */
	public void quitarParcela(ParcelasAjudadeCustoBean parcela) {
		String query = "UPDATE PARCELAS_AJUDA_CUSTO SET VALOR_PAGO=?, PAGO=?, OBS=? WHERE ID_AJUDA_CUSTO=? AND MES=?;";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setDouble(1, parcela.getValorPago());
			statement.setInt(2, parcela.getPago());
			statement.setString(3, parcela.getObservacoes());
			statement.setInt(4, parcela.getAjudadeCusto());
			statement.setInt(5, parcela.getMes());

			statement.execute();
			statement.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
