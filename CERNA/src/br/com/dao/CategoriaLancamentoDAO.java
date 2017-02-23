package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.CategoriaLancamentoBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

public class CategoriaLancamentoDAO {

	private Connection connection;

	public CategoriaLancamentoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método retorna uma lista com as categorias de lançamentos ou
	 * registros de livro caixa cadastrados.
	 * 
	 * @return List<CategoriaLancamento>
	 */
	public List<CategoriaLancamentoBean> listaCategorias() {
		String query = "SELECT * FROM CATEGORIA_LANCAMENTO ORDER BY ID";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<CategoriaLancamentoBean> lista = new ArrayList<>();
			while (resultSet.next()) {
				CategoriaLancamentoBean categoriaLancamento = new CategoriaLancamentoBean();
				categoriaLancamento.setId(resultSet.getInt("ID"));
				categoriaLancamento.setTipo(resultSet.getString("TIPO"));
				lista.add(categoriaLancamento);
			}
			resultSet.close();
			statement.close();
			connection.close();// StatusDAO
			return lista;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método grava um novo tipo de categoria de registro de livro caixa no
	 * banco de dados.
	 * 
	 * @param novaCategoria
	 */
	public void insert(String novaCategoria) {
		String query = "INSERT INTO CATEGORIA_LANCAMENTO (TIPO) VALUES(?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, novaCategoria);
			statement.execute();
			statement.close();
			connection.close();
			Show.informacao("Categoria adicionada com sucesso.");
			Save.log(Config.usuarioLogado,
					"Cadastrou a categoria de livro caixa " + novaCategoria);
		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.system, e.getMessage());
		}
	}

	/**
	 * Este método retorna o índice da entidade categoria de registro de livro
	 * caixa selecionada.
	 * 
	 * @param tipo
	 * @return int
	 */
	public int getIdCategoria(String tipo) {
		String query = "SELECT ID,TIPO FROM CATEGORIA_LANCAMENTO WHERE TIPO=?";
		int id = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, tipo);
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
	 * Este método retorna uma lista de Strings com as categorias de lançamentos
	 * ou registros de livro caixa cadastrados.
	 * 
	 * @return List<CategoriaLancamento>
	 */
	public List<String> getItem() {
		String query = "SELECT * FROM VIEW_CATEGORIA_LANCAMENTO";
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
	 * Este método remove a categoria de lançamento da base de dados.
	 * 
	 * @param string
	 */
	public void delete(String string) {
		String query = "DELETE FROM CATEGORIA_LANCAMENTO WHERE TIPO = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, string);
			statement.execute();

			statement.close();
			connection.close();

			Show.informacao("Categoria removida com sucesso.");
			Save.log(Config.usuarioLogado, "Removeu a categoria " + string);
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("falha ao remover categoria.\n" + e.getMessage());
			Save.log(Config.usuarioLogado,
					"Falhou ao tentar remover a categoria " + string);
		}

	}
}
