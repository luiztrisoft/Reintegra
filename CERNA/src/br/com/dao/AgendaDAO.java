package br.com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.AgendaBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Data;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe é responsável pelos dados de persistência do sistema. Aqui são
 * feitas as inclusões e exclusões de eventos no banco de dados além de retornar
 * a lista com todos os eventos.
 * 
 * @author Luiz Alberto
 * 
 */
public class AgendaDAO {

	private Connection connection;

	/**
	 * Método construtor padrão da classe.
	 */
	public AgendaDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * Este método é responsável por realizar o registro na tabela de eventos do
	 * módulo de agenda do sistema. O usuário registra a data e o lembrete.
	 * 
	 * @param agenda
	 */
	public void insert(AgendaBean agenda) {
		String query = "INSERT INTO AGENDA(ATIVO, DIA, EVENTO) VALUES(?,?,?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, agenda.getAtivo());
			statement.setDate(2, new Date(agenda.getDia().getCalendar()
					.getTimeInMillis()));
			statement.setString(3, agenda.getEvento());

			statement.execute();
			statement.close();
			connection.close();

			Show.informacao("<html><h3>Registro efetuado com sucesso!");
			Save.log(Config.usuarioLogado, "Registrou um evento na agenda");
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("Falha ao cadastrar evento.");
			Save.log(Config.usuarioLogado,
					"Falhou ao registrar um evento na agenda.");
		}
	}

	/**
	 * Este método altera o atributo ativo S(sim) para N(não) não sendo mais
	 * visualizado pelo usuário na tabela de eventos.
	 * 
	 * @param idAgenda
	 */
	public void delete(int idAgenda) {
		String query = "UPDATE AGENDA SET ATIVO='N' WHERE ID=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idAgenda);
			statement.execute();
			statement.close();
			connection.close();

			Show.simples("<html>O evento <b>Nº " + idAgenda
					+ "</b> foi excluído da agenda.");
			Save.log(Config.usuarioLogado, "Excluiu o evento Nº " + idAgenda
					+ " da agenda");
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("<html>Falha ao deletar o evento <b>Nº " + idAgenda
					+ "</b> da agenda.\n" + e.getMessage());
			Save.log(Config.usuarioLogado, "Falhou ao excluir o evento "
					+ idAgenda + " da agenda");
		}
	}

	/**
	 * Este método é responsável por retornar uma lista com todos os eventos
	 * registrados na agenda
	 * 
	 * @param dataMin
	 * @param dataMax
	 * @return List<AgendaBean>
	 */
	public List<AgendaBean> agendaList(String dataMin, String dataMax) {
		String dataMinima = Data.traditionalFormat(dataMin);
		String dataMaxima = Data.traditionalFormat(dataMax);

		StringBuilder query = new StringBuilder();
		query.append(" SELECT ID, ATIVO, DIA, EVENTO FROM AGENDA ");
		query.append(" WHERE ATIVO='S' ");
		query.append(" AND DIA BETWEEN '");
		query.append(dataMinima);
		query.append("' AND '");
		query.append(dataMaxima);
		query.append("' ");
		query.append(" ORDER BY DIA");

		List<AgendaBean> lista = new ArrayList<>();

		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				AgendaBean agenda = new AgendaBean();
				agenda.setId(resultSet.getInt("ID"));
				agenda.setAtivo(resultSet.getString("ATIVO"));
				agenda.getDia().setDate(resultSet.getDate("DIA"));
				agenda.setEvento(resultSet.getString("EVENTO"));

				lista.add(agenda);
			}
			resultSet.close();
			statement.close();
			connection.close();

			return lista;

		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.usuarioLogado, "Não foi possível retornar coluna\n"
					+ e.getMessage());
			Show.erro("Não foi possível retornar coluna\n" + e.getMessage());
			return lista;
		}

	}
}
