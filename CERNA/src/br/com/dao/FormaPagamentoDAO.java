package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

public class FormaPagamentoDAO {
	private Connection connection;

	public FormaPagamentoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método retorna uma lista de Strings com os convênios cadastrados.
	 * 
	 * @return List<String>
	 */
	public List<String> getItem() {
		String query = "SELECT * FROM VIEW_FORMA_PAGAMENTO";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<String> lista = new ArrayList<>();
			while (resultSet.next()) {
				lista.add(resultSet.getString("FORMA"));
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
	 * Este método remove a forma de pagamento base de dados.
	 * 
	 * @param string
	 */
	public void delete(String string) {
		String query = "DELETE FROM FORMA_PAGTO WHERE FORMA = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, string);
			statement.execute();

			statement.close();
			connection.close();

			Show.informacao("Forma de pagamento removida com sucesso.");
			Save.log(Config.usuarioLogado, "Removeu a forma de pagamento "
					+ string);
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("falha ao remover forma de pagamento.\n" + e.getMessage());
			Save.log(Config.usuarioLogado,
					"Falhou ao tentar remover a forma de pagamento " + string);
		}
	}
}
