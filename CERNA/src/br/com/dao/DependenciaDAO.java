package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.DependenciaBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

public class DependenciaDAO {

	private Connection connection;

	public DependenciaDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este m�todo grava um novo tipo de depend�ncia no banco de dados.
	 * 
	 * @param tipoDependencia
	 */
	public void insert(String tipoDependencia) {
		String query = "INSERT INTO DEPENDENCIA (TIPO) VALUES(?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, tipoDependencia);
			statement.execute();
			statement.close();
			connection.close();
			Show.informacao("Depend�ncia adicionada com sucesso.");
			Save.log(Config.usuarioLogado, "Cadastrou a depend�ncia "
					+ tipoDependencia);
		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.system, e.getMessage());
		}
	}

	/**
	 * Este m�todo retorna uma lista com os tipos de depend�ncias cadastradas.
	 * 
	 * @return List<DependenciaBean>
	 */
	public List<DependenciaBean> listaDependencia() {
		String query = "SELECT * FROM DEPENDENCIA ORDER BY TIPO";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<DependenciaBean> lista = new ArrayList<>();
			while (resultSet.next()) {
				DependenciaBean dependencia = new DependenciaBean();
				dependencia.setId(resultSet.getInt("ID"));
				dependencia.setTipo(resultSet.getString("TIPO"));
				lista.add(dependencia);
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
	 * Este m�todo retorna o �ndice da entidade depend�ncia selecionada.
	 * 
	 * @param tipo
	 * @return int
	 */
	public int getIdConvenios(String tipo) {
		String query = "SELECT ID,TIPO FROM DEPENDENCIA WHERE TIPO=?";
		int id = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, tipo);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
			}
			statement.close();
			resultSet.close();
			return id;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este m�todo recupera o tipo da depend�ncia atrav�s do id passado como
	 * par�metro.
	 * 
	 * @param id
	 * @return String
	 */
	public String getTipo(int id) {
		String query = "SELECT TIPO FROM DEPENDENCIA WHERE ID = " + id;
		String nome = null;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nome = resultSet.getString(1);
			}
			statement.close();
			connection.close();
			return nome;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este m�todo retorna a quantidade de um tipo de depend�ncia passado como
	 * par�metro.
	 * 
	 * @param tipo
	 * @return int
	 */
	public int countTipoDependencia(String tipo) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT INTERNOS.ID_DEPENDENCIA ");
		query.append(" FROM INTERNOS, DEPENDENCIA ");
		query.append(" WHERE DEPENDENCIA.TIPO = '");
		query.append(tipo);
		query.append("' ");
		query.append(" AND INTERNOS.ID_DEPENDENCIA = DEPENDENCIA.ID");

		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count++;
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}

	public List<String> tiposdeDependencia() {
		String query = "SELECT TIPO FROM DEPENDENCIA ORDER BY ID";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<String> lista = new ArrayList<>();
			while (resultSet.next()) {
				lista.add(resultSet.getString("TIPO"));
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
	 * Este m�todo retorna uma lista de Strings com as dependencias cadastradas.
	 * 
	 * @return List<String>
	 */
	public List<String> getItem() {
		String query = "SELECT * FROM VIEW_DEPENDENCIA";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<String> lista = new ArrayList<>();
			while (resultSet.next()) {
				lista.add(resultSet.getString("TIPO"));
			}
			resultSet.close();
			statement.close();
			connection.close();
			return lista;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este m�todo remove a depend�ncia base de dados.
	 * 
	 * @param string
	 */
	public void delete(String string) {
		String query = "DELETE FROM DEPENDENCIA WHERE TIPO = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, string);
			statement.execute();

			statement.close();
			connection.close();

			Show.informacao("Depend�ncia removida com sucesso.");
			Save.log(Config.usuarioLogado, "Removeu a depend�ncia " + string);
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("falha ao remover depend�ncia.\n" + e.getMessage());
			Save.log(Config.usuarioLogado,
					"Falhou ao tentar remover a depend�ncia " + string);
		}

	}

}
