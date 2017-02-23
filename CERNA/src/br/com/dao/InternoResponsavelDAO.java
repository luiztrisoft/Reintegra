package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.bean.InternoResponsavelBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Show;

public class InternoResponsavelDAO {

	private Connection connection;

	public InternoResponsavelDAO() {
		connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este m�todo salva o relacionamento entre interno e respons�veis na tabela
	 * interno_responsavel.
	 * 
	 * @param ir
	 */
	public void insert(InternoResponsavelBean ir) {
		String query = "INSERT INTO INTERNO_RESPONSAVEL(ID_INTERNO, ID_RESPONSAVEL) VALUES(?,?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, ir.getIdInterno());
			statement.setLong(2, ir.getIdResponsavel());

			statement.execute();
			statement.close();
			connection.close();

			Show.questao("O respons�vel foi vinculado ao interno.");
		} catch (Exception e) {
			Show.erro("O interno j� possui um respons�vel.\n" + e.getMessage());
			e.printStackTrace();
		}
	}
}
