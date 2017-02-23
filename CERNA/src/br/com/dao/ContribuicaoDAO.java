package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.ContribuicaoBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Config;

/**
 * Esta classe controla as operações de banco de dados da entidade contribuição.
 * Por default existem duas situações cadastrados pré-estabelecidas que são
 * contribuinte e isento.
 * 
 * @author Luiz Alberto
 * 
 */
public class ContribuicaoDAO {
	private Connection con;

	public ContribuicaoDAO() {
		this.con = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método retorna uma lista com o tipo de contribuicao cadastrados.
	 * 
	 * @return List<ContribuicaoBean>
	 */
	public List<ContribuicaoBean> listaContribuicao() {
		String query = "SELECT * FROM CONTRIBUICAO ORDER BY ID";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<ContribuicaoBean> listaContribuicao = new ArrayList<>();
			while (resultSet.next()) {
				ContribuicaoBean contribuicao = new ContribuicaoBean();
				contribuicao.setId(resultSet.getInt("ID"));
				contribuicao.setSituacao(resultSet.getString("SITUACAO"));
				listaContribuicao.add(contribuicao);
			}
			resultSet.close();
			statement.close();
			return listaContribuicao;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método retorna o índice e a situação da entidade contribuicao
	 * selecionada.
	 * 
	 * @param s
	 * @return int
	 */
	public int contribuicao(String s) {
		String query = "SELECT * FROM CONTRIBUICAO WHERE SITUACAO='" + s + "'";
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
	 * Este método recupera o tipo da contribuição através do id passado como
	 * parâmetro.
	 * 
	 * @param id
	 * @return String
	 */
	public String getNome(int id) {
		String query = "SELECT SITUACAO FROM CONTRIBUICAO WHERE ID = " + id;
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
			throw new RuntimeException();
		}
	}
}
