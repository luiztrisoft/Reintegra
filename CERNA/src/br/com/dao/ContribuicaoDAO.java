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
 * Esta classe controla as opera��es de banco de dados da entidade contribui��o.
 * Por default existem duas situa��es cadastrados pr�-estabelecidas que s�o
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
	 * Este m�todo retorna uma lista com o tipo de contribuicao cadastrados.
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
	 * Este m�todo retorna o �ndice e a situa��o da entidade contribuicao
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
	 * Este m�todo recupera o tipo da contribui��o atrav�s do id passado como
	 * par�metro.
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
