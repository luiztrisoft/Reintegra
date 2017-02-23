package br.com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import br.com.bean.LogBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Config;

/**
 * Esta classe é responsável por salvar registros de log de todas as ações do
 * usuário.
 * 
 * @author Luiz Alberto
 * 
 */
public class LogDAO {
	Connection con = new ConnectionFactory().getConnection();

	/**
	 * Método que salva um determinado evento na tabela de log.
	 * 
	 * @param log
	 */
	public void salvarLOG(LogBean log) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO LOG_ACESSO(");
		sql.append("USUARIO,");
		sql.append("EVENTO,");
		sql.append("DIA,");
		sql.append("HORA");
		sql.append(" ) ");
		sql.append("VALUES(?,?,?,?)");

		try {
			PreparedStatement statement = con.prepareStatement(sql.toString());
			statement.setString(1, log.getUsuario());
			statement.setString(2, log.getEvento());
			statement.setDate(3, new Date(log.getData().getTimeInMillis()));
			statement.setString(4, log.getHora());
			statement.execute();
			statement.close();
			con.close();
		} catch (Exception exception) {
			Save.log(Config.system, exception.getMessage());
			// Save.log(Config.system, "Falha na operação");
		}
	}
}
