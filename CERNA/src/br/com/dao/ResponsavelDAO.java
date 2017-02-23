package br.com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.ResponsavelBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe manipula os dados persistidos do respons�vel pelo interno caso
 * ele exita.
 * 
 * @author Luiz Alberto
 * 
 */
public class ResponsavelDAO {

	private Connection connection;

	public ResponsavelDAO() {
		connection = new ConnectionFactory().getConnection();
	}

	/**
	 * M�todo que cadastra um novo respons�vel por interno na base de dados.
	 * 
	 * @param responsavel
	 */
	public void insert(ResponsavelBean responsavel) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO RESPONSAVEL");
		query.append("(");
		query.append("NOME,");
		query.append("RG,");
		query.append("CPF,");
		query.append("DATA_NASCIMENTO,");
		query.append("ENDERECO,");
		query.append("BAIRRO,");
		query.append("CIDADE,");
		query.append("UF,");
		query.append("TELEFONE,");
		query.append("CELULAR,");
		query.append("EMAIL,");
		query.append("NATURALIDADE,");
		query.append("UF_NASCIMENTO,");
		query.append("NACIONALIDADE,");
		query.append("PROFISSAO,");
		query.append("PAI,");
		query.append("MAE");
		query.append(")");
		query.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			statement.setString(1, responsavel.getNome());
			statement.setString(2, responsavel.getRg());
			statement.setString(3, responsavel.getCpf());
			statement.setDate(4, new Date(responsavel.getNascimento()
					.getCalendar().getTimeInMillis()));
			statement.setString(5, responsavel.getEndereco());
			statement.setString(6, responsavel.getBairro());
			statement.setString(7, responsavel.getCidade());
			statement.setString(8, responsavel.getUf());
			statement.setString(9, responsavel.getTelefone());
			statement.setString(10, responsavel.getCelular());
			statement.setString(11, responsavel.getEmail());
			statement.setString(12, responsavel.getNaturalidade());
			statement.setString(13, responsavel.getUfNascimento());
			statement.setString(14, responsavel.getNacionalidade());
			statement.setString(15, responsavel.getProfissao());
			statement.setString(16, responsavel.getPai());
			statement.setString(17, responsavel.getMae());

			statement.execute();
			statement.close();

			Save.log(Config.usuarioLogado, "Cadastrou o(a) respons�vel "
					+ responsavel.getNome());

			Show.informacao("O respons�vel foi cadastrado(a) com sucesso.");

		} catch (Exception e) {
			e.printStackTrace();
			Show.alerta("O CPF '" + responsavel.getCpf()
					+ "' j� estava pr�-cadastrado no sistema.");
			Save.log(Config.usuarioLogado, "Tentou cadastrar respons�vel\n."
					+ e.getMessage());
		}
	}

	/**
	 * Este m�todo � respons�vel por alterar os dados do respons�vel atrav�s do
	 * cpf.
	 * 
	 * @param responsavel
	 */
	public void update(ResponsavelBean responsavel) {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE RESPONSAVEL SET ");
		query.append("NOME=?,");
		query.append("RG=?,");
		query.append("DATA_NASCIMENTO=?,");
		query.append("ENDERECO=?,");
		query.append("BAIRRO=?,");
		query.append("CIDADE=?,");
		query.append("UF=?,");
		query.append("TELEFONE=?,");
		query.append("CELULAR=?,");
		query.append("EMAIL=?,");
		query.append("NATURALIDADE=?,");
		query.append("UF_NASCIMENTO=?,");
		query.append("NACIONALIDADE=?,");
		query.append("PROFISSAO=?,");
		query.append("PAI=?,");
		query.append("MAE=? ");
		query.append("WHERE CPF = ?");

		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());

			statement.setString(1, responsavel.getNome());
			statement.setString(2, responsavel.getRg());
			statement.setDate(3, new Date(responsavel.getNascimento()
					.getCalendar().getTimeInMillis()));
			statement.setString(4, responsavel.getEndereco());
			statement.setString(5, responsavel.getBairro());
			statement.setString(6, responsavel.getCidade());
			statement.setString(7, responsavel.getUf());
			statement.setString(8, responsavel.getTelefone());
			statement.setString(9, responsavel.getCelular());
			statement.setString(10, responsavel.getEmail());
			statement.setString(11, responsavel.getNaturalidade());
			statement.setString(12, responsavel.getUfNascimento());
			statement.setString(13, responsavel.getNacionalidade());
			statement.setString(14, responsavel.getProfissao());
			statement.setString(15, responsavel.getPai());
			statement.setString(16, responsavel.getMae());
			statement.setString(17, responsavel.getCpf());

			statement.execute();
			statement.close();

			Save.log(
					Config.usuarioLogado,
					"Alterou os dados do(a) respons�vel "
							+ responsavel.getNome());
			Show.informacao(responsavel.getNome() + " alterado com sucesso.");
		} catch (SQLException e) {
			e.printStackTrace();
			Show.erro("Erro ao alterar.\n" + e.getMessage());
		}
	}

	/**
	 * M�todo que remove o respons�vel da base de dados atrav�s do CPF passado
	 * como par�metro.� preciso lembrar que ao deletar o respons�vel, todos os
	 * v�nculos com internos tamb�m s�o removidos da base de dados.
	 * 
	 * @param responsavel
	 */
	public void delete(ResponsavelBean responsavel) {
		String query = "DELETE FROM RESPONSAVEL WHERE CPF=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, responsavel.getCpf());
			statement.execute();
			statement.close();

			Save.log(Config.usuarioLogado, "deletou o funcion�rio "
					+ responsavel.getNome());
			Show.alerta("Respons�vel deletado com sucesso.");
		} catch (Exception e) {
			Show.erro("Falha ao deletar respons�vel.");
			e.printStackTrace();
		}
	}

	/**
	 * Este m�todo verifica se existe o registro de um interno vinculado a um
	 * respons�vel na tabela INTERNO_RESPONSAVEL para poder instanciar um
	 * respons�vel para inclu�-lo nos termos de compromisso do CERNA. Caso
	 * contr�rio o termo mostrar� apenas o nome do conv�nio como respons�vel
	 * pelo interno.
	 * 
	 * @param idInterno
	 * @return boolean
	 */
	public boolean verifyInternoResponsavel(int idInterno) {
		boolean retorno = false;
		String query = "SELECT ID_INTERNO FROM INTERNO_RESPONSAVEL WHERE ID_INTERNO = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, idInterno);
			ResultSet resultSet = statement.executeQuery();
			retorno = resultSet.first();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * Este m�todo serve para recuperar as informa��es de um determinado
	 * respons�vel de acordo com o id passado como par�metro.
	 * 
	 * @param id
	 * @return ResponsavelBean
	 */
	public ResponsavelBean getResponsavel(long id) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM responsavel ");
		query.append(" JOIN interno_responsavel ");
		query.append(" WHERE interno_responsavel.ID_INTERNO = ");
		query.append(id);
		query.append(" AND interno_responsavel.ID_RESPONSAVEL = responsavel.ID;");

		ResponsavelBean responsavel = new ResponsavelBean();

		try {
			PreparedStatement statement;
			statement = connection.prepareStatement(query.toString());
			ResultSet rs = statement.executeQuery();
			rs.first();

			responsavel.setId(rs.getInt("ID"));
			responsavel.setNome(rs.getString("NOME"));
			responsavel.setRg(rs.getString("RG"));
			responsavel.setCpf(rs.getString("CPF"));
			responsavel.getNascimento().setDate(rs.getDate("DATA_NASCIMENTO"));
			responsavel.setEndereco(rs.getString("ENDERECO"));
			responsavel.setBairro(rs.getString("BAIRRO"));
			responsavel.setCidade(rs.getString("CIDADE"));
			responsavel.setUf(rs.getString("UF"));
			responsavel.setTelefone(rs.getString("TELEFONE"));
			responsavel.setCelular(rs.getString("CELULAR"));
			responsavel.setEmail(rs.getString("EMAIL"));
			responsavel.setNaturalidade(rs.getString("NATURALIDADE"));
			responsavel.setUfNascimento(rs.getString("UF_NASCIMENTO"));
			responsavel.setNacionalidade(rs.getString("NACIONALIDADE"));
			responsavel.setProfissao(rs.getString("PROFISSAO"));
			responsavel.setPai(rs.getString("PAI"));
			responsavel.setMae(rs.getString("MAE"));

			statement.close();
			rs.close();
			connection.close();
			return responsavel;
		} catch (SQLException e) {
			// Show.informacao("N�o h� respons�vel vinculado a este interno.\nVoc� pode cadastrar um novo respons�vel.");
			e.printStackTrace();
			return responsavel;
		}

	}

	public long returnID(long id) {
		long idResponsavel = 0;
		StringBuilder query = new StringBuilder();
		query.append(" SELECT ID FROM responsavel ");
		query.append(" JOIN interno_responsavel ");
		query.append(" WHERE interno_responsavel.ID_INTERNO = ");
		query.append(id);
		query.append(" AND interno_responsavel.ID_RESPONSAVEL = responsavel.ID;");

		ResponsavelBean responsavel = new ResponsavelBean();

		try {
			PreparedStatement statement;
			statement = connection.prepareStatement(query.toString());
			ResultSet rs = statement.executeQuery();
			rs.first();

			responsavel.setId(rs.getInt("ID"));

			id = responsavel.getId();

			statement.close();
			rs.close();
			connection.close();

			return idResponsavel;

		} catch (SQLException e) {
			Show.informacao("N�o h� respons�vel vinculado a este interno.\nVoc� pode cadastrar um novo respons�vel.");
			e.printStackTrace();
			return id;
		}

	}

	/**
	 * Este m�todo verifica a exist�ncia de um CPF e retorna um valor boolean
	 * para informar.
	 * 
	 * @param cpf
	 * @return boolean
	 */
	public boolean verifyId(String cpf) {
		boolean retorno = false;
		String query = "SELECT ID FROM RESPONSAVEL WHERE CPF = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, cpf);
			ResultSet resultSet = statement.executeQuery();
			retorno = resultSet.first();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * Este m�todo retorna o id correspondente ao cpf passado como par�metro.
	 * 
	 * @param cpf
	 * @return long
	 */
	public long returnId(String cpf) {

		String query = "SELECT ID FROM RESPONSAVEL WHERE CPF= ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, cpf);

			ResultSet resultSet = statement.executeQuery();

			long id = 0;
			while (resultSet.next()) {
				id = resultSet.getLong("ID");
			}
			resultSet.close();
			statement.close();
			return id;
		} catch (Exception e) {
			Show.erro("N�o foi poss�vel recuperar o respons�vel \n"
					+ e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * M�todo que retorna uma lista com os nomes dos respons�veis cadastrados na
	 * base de dados.
	 * 
	 * @return ResponsavelBean
	 */
	public List<ResponsavelBean> listaResponsavel() {
		String query = "SELECT ID, NOME FROM RESPONSAVEL ORDER BY NOME";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<ResponsavelBean> responsavelList = new ArrayList<>();
			while (resultSet.next()) {
				ResponsavelBean responsavel = new ResponsavelBean();
				responsavel.setId(resultSet.getInt("ID"));
				responsavel.setNome(resultSet.getString("NOME"));
				responsavelList.add(responsavel);
			}
			resultSet.close();
			statement.close();
			return responsavelList;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	// *********************************************
	// ****************************************************

	// ::::::::::::atributos para navega��o::::::::::::
	// public static Statement st = null;
	// public static ResultSet rs = null;
	// public static Connection c = null;
	//
	// public static void navigate(long id) {
	// try {
	// c = new ConnectionFactory().getConnection();
	// st = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	// ResultSet.CONCUR_UPDATABLE);
	// StringBuilder query = new StringBuilder();
	// query.append(" SELECT * FROM responsavel ");
	// query.append(" JOIN interno_responsavel ");
	// query.append(" WHERE interno_responsavel.ID_INTERNO = ");
	// query.append(id);
	// query.append(" AND interno_responsavel.ID_RESPONSAVEL = responsavel.ID;");
	//
	// rs = st.executeQuery(query.toString());
	//
	// } catch (SQLException erro) {
	// erro.printStackTrace();
	// }
	// }
}
