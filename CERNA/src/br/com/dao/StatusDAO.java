package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.StatusBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Config;

/**
 * Esta classe controla a situação do interno, informando se ele é passivo ou
 * ativo.
 * 
 * @author Luiz Alberto
 * 
 */
public class StatusDAO {

	private Connection con;

	public StatusDAO() {
		this.con = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método retorna uma lista com os status de internos cadastrados.
	 * 
	 * @return List<StatusBean>
	 */
	public List<StatusBean> listaStatus() {
		String query = "SELECT * FROM STATUS_INTERNO ORDER BY ID";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<StatusBean> listaStatus = new ArrayList<>();
			while (resultSet.next()) {
				StatusBean status = new StatusBean();
				status.setId(resultSet.getInt("ID"));
				status.setSituacao(resultSet.getString("SITUACAO"));
				listaStatus.add(status);
			}
			resultSet.close();
			statement.close();
			return listaStatus;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método retorna o índice e a situação da entidade status selecionada.
	 * 
	 * @param s
	 * @return StatusBean
	 */
	public int status(String s) {
		String query = "SELECT * FROM STATUS_INTERNO WHERE SITUACAO='" + s
				+ "'";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			int id = 0;
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
			}
			resultSet.close();
			statement.close();
			return id;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método recupera o status através do id passado como
	 * parâmetro.
	 * 
	 * @param id
	 * @return String
	 */
	public String getNome(int id) {
		String query = "SELECT SITUACAO FROM STATUS_INTERNO WHERE ID = " + id;
		String nome = null;
		try {
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nome = resultSet.getString(1);
			}
			statement.close();
			con.close();
			return nome;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());

		}
		return nome;
	}

}
