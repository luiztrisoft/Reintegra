package br.com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.LivroCaixaBean;
import br.com.bean.LivroCaixaBeanTabela;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Data;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe � respons�vel pela persist�ncia e manipula��o das informa��es de
 * livro caixa no banco de dados.
 * 
 * @author Luiz Alberto
 * 
 */

public class LivroCaixaDAO {
	private Connection connection;

	public LivroCaixaDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este m�todo realiza a persist�ncia do registro de entrada e sa�da na
	 * tabela de livro caixa do banco de dados. Alguns dados s�o enviados de
	 * forma autom�tica como a data e a hora de registro e o status ativo ('A').
	 * 
	 * @param livroCaixa
	 * @param mensagem
	 */
	public void insert(LivroCaixaBean livroCaixa, String mensagem) {
		StringBuilder query = new StringBuilder();
		query.append(" INSERT INTO LIVRO_CAIXA ");
		query.append(" (DATA_SISTEMA,HORA_SISTEMA,TIPO,SITUACAO,DATA_REGISTRO, ");
		query.append(" ID_CATEGORIA,OBSERVACAO, VALOR) ");
		query.append(" VALUES(CURDATE(), CURTIME(),?,'A',?,?,?,?)");

		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			statement.setString(1, livroCaixa.getTipo());
			statement.setDate(2, new Date(livroCaixa.getDataRegistro()
					.getCalendar().getTimeInMillis()));
			statement.setInt(3, livroCaixa.getIdCategoria());
			statement.setString(4, livroCaixa.getObservacao());
			statement.setDouble(5, livroCaixa.getValor());

			statement.execute();
			statement.close();
			connection.close();

			Show.simples(mensagem);
			if (livroCaixa.getTipo().equalsIgnoreCase("E")) {
				Save.log(Config.usuarioLogado,
						"Registrou uma entrada no livro caixa");
			} else {
				Save.log(Config.usuarioLogado,
						"Registrou uma sa�da no livro caixa");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("Falha ao efetuar o registro em livro caixa.\n"
					+ e.getMessage());
			Save.log(Config.usuarioLogado,
					"Falhou ao registrar uma entrada no livro caixa");
		}
	}

	/**
	 * Este m�todo faz a altera��o da situa��o do registro do livro caixa,
	 * deixando-o 'passivo' para que o usu�rio n�o o veja nas consultas depois
	 * de extorn�-lo.
	 * 
	 * @param idLivroCaixa
	 */
	public void delete(int idLivroCaixa) {
		String query = "UPDATE LIVRO_CAIXA SET SITUACAO='P' WHERE ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idLivroCaixa);
			statement.execute();
			statement.close();
			connection.close();

			Show.simples("<html>O registro <b>N� " + idLivroCaixa
					+ "</b> foi exclu�do do livro caixa.");
			Save.log(Config.usuarioLogado, "Excluiu o registro N� "
					+ idLivroCaixa + " do livro caixa");
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("<html>Falha ao deletar o registro <b>N� " + idLivroCaixa
					+ "</b> do livro caixa.\n" + e.getMessage());
			Save.log(Config.usuarioLogado, "Falhou ao excluir o registro "
					+ idLivroCaixa + " do livro caixa");
		}
	}

	/**
	 * Este m�todo retorna uma lista com todos os registros do livro caixa.
	 * 
	 * @param dataMin
	 * @param dataMax
	 * @return List<LivroCaixaBeanTabela>
	 */
	public List<LivroCaixaBeanTabela> getListLivroCaixa(String dataMin,
			String dataMax) {

		String dataMinima = Data.traditionalFormat(dataMin);
		String dataMaxima = Data.traditionalFormat(dataMax);

		StringBuilder query = new StringBuilder();
		query.append(" SELECT LIVRO_CAIXA.ID, DATA_REGISTRO, CATEGORIA_LANCAMENTO.TIPO, OBSERVACAO, VALOR, '' ");
		query.append(" FROM LIVRO_CAIXA, CATEGORIA_LANCAMENTO ");
		query.append(" WHERE CATEGORIA_LANCAMENTO.ID = LIVRO_CAIXA.ID_CATEGORIA AND LIVRO_CAIXA.TIPO='E' ");
		query.append(" AND SITUACAO = 'A' ");
		query.append(" AND DATA_REGISTRO BETWEEN '");
		query.append(dataMinima);
		query.append("' AND '");
		query.append(dataMaxima);
		query.append("' ");
		query.append(" UNION ");
		query.append(" SELECT LIVRO_CAIXA.ID, DATA_REGISTRO, CATEGORIA_LANCAMENTO.TIPO, OBSERVACAO,'',  VALOR ");
		query.append(" FROM LIVRO_CAIXA, CATEGORIA_LANCAMENTO ");
		query.append(" WHERE CATEGORIA_LANCAMENTO.ID = LIVRO_CAIXA.ID_CATEGORIA AND LIVRO_CAIXA.TIPO='S' ");
		query.append(" AND SITUACAO = 'A' ");
		query.append(" AND DATA_REGISTRO BETWEEN '");
		query.append(dataMinima);
		query.append("' AND '");
		query.append(dataMaxima);
		query.append("' ");
		query.append(" ORDER BY DATA_REGISTRO ");

		List<LivroCaixaBeanTabela> lista = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				LivroCaixaBeanTabela registro = new LivroCaixaBeanTabela();
				registro.setId(resultSet.getInt(1));
				registro.getDataRegistro().setDate(resultSet.getDate(2));
				registro.setTipo(resultSet.getString(3));
				registro.setObservacao(resultSet.getString(4));
				registro.setEntrada(resultSet.getDouble(5));
				registro.setSaida(resultSet.getDouble(6));

				lista.add(registro);
			}
			resultSet.close();
			statement.close();
			connection.close();

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.usuarioLogado, "N�o foi poss�vel retornar coluna\n"
					+ e.getMessage());
			Show.erro("N�o foi poss�vel retornar coluna\n" + e.getMessage());
			return lista;
		}
	}

	/*
	 * =================================================
	 * 
	 * BALAN�O GERAL
	 * 
	 * Estes m�todos recuperam a entrada, a sa�da e o saldo geral do livro
	 * caixa.
	 * 
	 * =================================================
	 */
	/**
	 * M�todo que retorna a soma de todas as entradas no livro caixa.
	 * 
	 * @return double
	 */
	public double getEntradaGeral() {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT SUM(VALOR)'VALOR' FROM LIVRO_CAIXA ");
		query.append(" WHERE TIPO='E' AND SITUACAO='A' ");

		double valor = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				valor = resultSet.getDouble("VALOR");
			}
			resultSet.close();
			statement.close();
			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return valor;
		}

	}

	/**
	 * M�todo que retorna a soma de todas as sa�das no livro caixa.
	 * 
	 * @return double
	 */
	public double getSaidaGeral() {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT SUM(VALOR)'VALOR' FROM LIVRO_CAIXA ");
		query.append(" WHERE TIPO='S' AND SITUACAO='A' ");

		double valor = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				valor = resultSet.getDouble("VALOR");
			}
			resultSet.close();
			statement.close();
			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return valor;
		}
	}

	/*
	 * =================================================
	 * 
	 * BALAN�O PARCIAL
	 * 
	 * Estes m�todos recuperam a entrada, a sa�da e o saldo parcial do livro
	 * caixa atrav�s das datas m�nima e m�xima passadas como par�metro.
	 * 
	 * =================================================
	 */
	/**
	 * M�todo que retorna a soma de todas as entradas no livro caixa entre as
	 * datas informadas como par�metro.
	 * 
	 * @param dataMin
	 * @param dataMax
	 * @return double
	 */
	public double getEntradaParcial(String dataMin, String dataMax) {

		String dataMinima = Data.traditionalFormat(dataMin);
		String dataMaxima = Data.traditionalFormat(dataMax);

		StringBuilder query = new StringBuilder();
		query.append(" SELECT SUM(VALOR)'VALOR' FROM LIVRO_CAIXA ");
		query.append(" WHERE TIPO='E' AND SITUACAO='A' ");
		query.append(" AND DATA_REGISTRO BETWEEN '");
		query.append(dataMinima);
		query.append("' AND '");
		query.append(dataMaxima);
		query.append("' ");

		double valor = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				valor = resultSet.getDouble("VALOR");
			}
			resultSet.close();
			statement.close();
			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return valor;
		}

	}

	/**
	 * M�todo que retorna a soma de todas as sa�das no livro caixa entre as
	 * datas informadas como par�metro.
	 * 
	 * @param dataMin
	 * @param dataMax
	 * @return double
	 */
	public double getSaidaParcial(String dataMin, String dataMax) {

		String dataMinima = Data.traditionalFormat(dataMin);
		String dataMaxima = Data.traditionalFormat(dataMax);

		StringBuilder query = new StringBuilder();
		query.append(" SELECT SUM(VALOR)'VALOR' FROM LIVRO_CAIXA ");
		query.append(" WHERE TIPO='S' AND SITUACAO='A' ");
		query.append(" AND DATA_REGISTRO BETWEEN '");
		query.append(dataMinima);
		query.append("' AND '");
		query.append(dataMaxima);
		query.append("' ");

		double valor = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				valor = resultSet.getDouble("VALOR");
			}
			resultSet.close();
			statement.close();
			return valor;
		} catch (Exception e) {
			e.printStackTrace();
			return valor;
		}
	}

	/*
	 * =================================================
	 * 
	 * CLOSE CONNECTION
	 * 
	 * A view de livro caixa requer m�ltiplas consultas ao banco de dados
	 * simult�neamente, ent�o � necess�rio um m�todo espec�fico para encerrar a
	 * conex�o ap�s todas as opera��es tiverem sido efetuadas.
	 * 
	 * =================================================
	 */

	/**
	 * M�todo que encerra a conex�o com o banco de dados. deve ser utilizada
	 * ap�s encerramento das transa��es.
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}
}
