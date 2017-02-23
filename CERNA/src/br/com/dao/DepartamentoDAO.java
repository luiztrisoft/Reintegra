package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.DepartamentoBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe controla as operações de CRUD relacionadas a Departamento.
 * 
 * @author Luiz Alberto
 * 
 */
public class DepartamentoDAO {

	private Connection con;

	public DepartamentoDAO() {
		this.con = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método retorna uma lista com os cargos cadastrados.
	 * 
	 * @return List<DepartamentoBean>
	 */
	public List<DepartamentoBean> listaCargos() {
		String query = "SELECT * FROM DEPARTAMENTO ORDER BY CARGO_DEPARTAMENTO";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<DepartamentoBean> cargos = new ArrayList<>();
			while (resultSet.next()) {
				DepartamentoBean funcao = new DepartamentoBean();
				funcao.setId(resultSet.getInt("ID_CARGO"));
				funcao.setCargo(resultSet.getString("CARGO_DEPARTAMENTO"));
				cargos.add(funcao);
			}
			resultSet.close();
			statement.close();
			return cargos;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Este método cadastra um novo cargo na tabela DEPARTAMENTO na base de
	 * dados.
	 * 
	 * @param departamento
	 */
	public void cadastrarFuncao(DepartamentoBean departamento) {
		String query = "INSERT INTO DEPARTAMENTO(CARGO_DEPARTAMENTO) VALUES (?)";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, departamento.getCargo());
			statement.execute();
			statement.close();
			con.close();
			Save.log(Config.usuarioLogado, "Cadastrou o cargo/função "
					+ departamento.getCargo());
			Show.informacao("Novo cargo cadastrado com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("Não foi possível cadastrar novo cargo\n"
					+ e.getMessage());
		}
	}

	/**
	 * Este método retorna uma lista de Strings com os departamentos
	 * cadastrados.
	 * 
	 * @return List<String>
	 */
	public List<String> getItem() {
		String query = "SELECT * FROM VIEW_DEPARTAMENTO";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<String> lista = new ArrayList<>();
			while (resultSet.next()) {
				lista.add(resultSet.getString("CARGO_DEPARTAMENTO"));
			}
			resultSet.close();
			statement.close();
			con.close();
			return lista;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método remove o departamento base de dados.
	 * 
	 * @param string
	 */
	public void delete(String string) {
		String query = "DELETE FROM DEPARTAMENTO WHERE CARGO_DEPARTAMENTO = ?";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, string);
			statement.execute();

			statement.close();
			con.close();

			Show.informacao("Departamento removido com sucesso.");
			Save.log(Config.usuarioLogado, "Removeu o departamento " + string);
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("falha ao remover departamento.\n" + e.getMessage());
			Save.log(Config.usuarioLogado,
					"Falhou ao tentar remover o departamento " + string);
		}

	}

}
