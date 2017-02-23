package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.ConvenioBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe realiza as operações pertinentes a base de dados do convênio.
 * 
 * @author Luiz Alberto
 * 
 */
public class ConvenioDAO {
	private Connection connection;

	public ConvenioDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método grava um novo registro de convênio no banco de dados.
	 * 
	 * @param nomeConvenio
	 */
	public void insert(String nomeConvenio) {
		String query = "INSERT INTO CONVENIOS (NOME) VALUES(?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, nomeConvenio);
			statement.execute();
			statement.close();
			connection.close();
			Show.informacao("Convênio adicionado com sucesso.");
			Save.log(Config.usuarioLogado, "Cadastrou o convênio "
					+ nomeConvenio);
		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.system, e.getMessage());
		}
	}

	/**
	 * Este método retorna uma lista com os convênios cadastrados.
	 * 
	 * @return List<ConvenioBean>
	 */
	public List<ConvenioBean> listaConvenio() {
		String query = "SELECT ID, NOME FROM CONVENIOS ORDER BY NOME";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<ConvenioBean> listaConvenio = new ArrayList<>();
			while (resultSet.next()) {
				ConvenioBean convenio = new ConvenioBean();
				convenio.setId(resultSet.getInt("ID"));
				convenio.setNome(resultSet.getString("NOME"));
				listaConvenio.add(convenio);
			}
			resultSet.close();
			statement.close();
			connection.close();
			return listaConvenio;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	// /**
	// * Este método retorna o índice e a situação da entidade contribuicao
	// * selecionada.
	// *
	// * @param s
	// * @return int
	// */
	// public int conveniod(String s) {
	// String query = "SELECT ID, NOME FROM CONVENIOS WHERE NOME='" + s + "'";
	// try {
	// PreparedStatement statement = connection.prepareStatement(query);
	// ResultSet resultSet = statement.executeQuery();
	// int id = 0;
	// while (resultSet.next()) {
	// id = resultSet.getInt("ID");
	// }
	// resultSet.close();
	// statement.close();
	// connection.close();
	// return id;
	// } catch (Exception e) {
	// Save.log(Static.system, e.getMessage());
	// throw new RuntimeException();
	// }
	// }

	/**
	 * Este método recupera o nome do convênio através do id passado como
	 * parâmetro.
	 * 
	 * @param id
	 * @return String
	 */
	public String getNome(int id) {
		String query = "SELECT NOME FROM CONVENIOS WHERE ID = " + id;
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
	 * Com este método é possível recuperar o código do convênio cadastrado
	 * através do id do interno passado como parâmetro.
	 * 
	 * @param id
	 * @return int
	 */
	public int getConvenio(long id) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT convenios.ID, internos.ID, internos.ID_CONVENIO FROM convenios,internos");
		query.append(" WHERE internos.ID = ");
		query.append(id);
		query.append(" AND convenios.ID = internos.ID_CONVENIO");
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet rs = statement.executeQuery();

			rs.first();
			int idConvenio = rs.getInt("ID");

			statement.close();
			rs.close();
			connection.close();

			return idConvenio;
		} catch (Exception e) {
			Show.erro(e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Este método retorna uma lista de Strings com os convênios cadastrados.
	 * 
	 * @return List<String>
	 */
	public List<String> getItem() {
		String query = "SELECT * FROM VIEW_CONVENIO";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<String> lista = new ArrayList<>();
			while (resultSet.next()) {
				lista.add(resultSet.getString("NOME"));
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
	 * Este método remove o convênio base de dados.
	 * 
	 * @param string
	 */
	public void delete(String string) {
		String query = "DELETE FROM CONVENIOS WHERE NOME = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, string);
			statement.execute();

			statement.close();
			connection.close();

			Show.informacao("Convênio removido com sucesso.");
			Save.log(Config.usuarioLogado, "Removeu o convênio " + string);
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("falha ao remover convênio.\n" + e.getMessage());
			Save.log(Config.usuarioLogado,
					"Falhou ao tentar remover o convênio " + string);
		}

	}

}
