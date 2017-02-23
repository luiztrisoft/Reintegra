package br.com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import br.com.bean.InternoBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe realiza as operações de persistência e recuperação de dados do
 * interno.
 * 
 * @author Luiz Alberto
 * 
 */
public class InternoDAO {

	private Connection connection;

	public InternoDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	// ::::::::::::atributos para navegação::::::::::::
	public static Statement st = null;
	public static ResultSet rs = null;
	public static Connection c = null;

	/**
	 * Este método grava os dados do interno na base de dados e retorna 'true'
	 * em caso de sucesso e 'false' caso contrário.
	 * 
	 * @param interno
	 * @return boolean
	 */
	public boolean insert(InternoBean interno) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO INTERNOS");
		query.append("(");
		query.append("ID_STATUS,");
		query.append("ID_CONTRIBUICAO,");
		query.append("ID_CONVENIO,");
		query.append("NOME,");
		query.append("DATA_NASCIMENTO,");
		query.append("CPF ,");
		query.append("RG ,");
		query.append("TELEFONE ,");
		query.append("PAI ,");
		query.append("MAE ,");
		query.append("ESTADO_CIVIL ,");
		query.append("CONJUGE ,");
		query.append("PROFISSAO ,");
		query.append("ESCOLARIDADE ,");
		query.append("NATURALIDADE,");
		query.append("UF_NASCIMENTO,");
		query.append("NACIONALIDADE,");
		query.append("ENDERECO,");
		query.append("BAIRRO,");
		query.append("CIDADE,");
		query.append("UF,");
		query.append("FOTO ,");
		query.append("DATA_ADMISSAO ,");
		query.append("DATA_SAIDA ,");
		query.append("TIPO ,");
		query.append("MOTIVO ,");
		query.append("PROCESSO_CRIMINAL ,");
		query.append("LOCAL_PROCESSO ,");
		query.append("SOFRE_AMEACA ,");
		query.append("ID_DEPENDENCIA ,");
		query.append("TEMPO_USO ,");
		query.append("MOTIVO_USO ,");
		query.append("DOENCA ,");
		query.append("DIABETICO ,");
		query.append("MEDICACAO ,");
		query.append("RECOMENDACAO_MEDICA ,");
		query.append("DOCUMENTOS ,");
		query.append("OBSERVACOES");
		query.append(")");
		query.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());
			statement.setInt(1, interno.getStatus());
			statement.setInt(2, interno.getContribuicao());
			statement.setInt(3, interno.getConvenio());
			statement.setString(4, interno.getNome());
			statement.setDate(5, new Date(interno.getNascimento().getCalendar()
					.getTimeInMillis()));
			statement.setString(6, interno.getCpf());
			statement.setString(7, interno.getRg());
			statement.setString(8, interno.getTelefone());
			statement.setString(9, interno.getPai());
			statement.setString(10, interno.getMae());
			statement.setString(11, interno.getEstadoCivil());
			statement.setString(12, interno.getConjuge());
			statement.setString(13, interno.getProfissao());
			statement.setString(14, interno.getEscolaridade());
			statement.setString(15, interno.getNaturalidade());
			statement.setString(16, interno.getUfNascimento());
			statement.setString(17, interno.getNacionalidade());
			statement.setString(18, interno.getEndereco());
			statement.setString(19, interno.getBairro());
			statement.setString(20, interno.getCidade());
			statement.setString(21, interno.getUf());
			statement.setString(22, interno.getFoto());
			statement.setDate(23, new Date(interno.getDataAdmissao()
					.getCalendar().getTimeInMillis()));
			statement.setDate(24, new Date(interno.getDataSaida().getCalendar()
					.getTimeInMillis()));
			statement.setString(25, interno.getTipoSaida());
			statement.setString(26, interno.getMotivoSaida());
			statement.setString(27, interno.getProcessoCriminal());
			statement.setString(28, interno.getLocal());
			statement.setString(29, interno.getSofreAmeaca());
			statement.setInt(30, interno.getdependencia());
			statement.setString(31, interno.getTempodeUso());
			statement.setString(32, interno.getMotivodeUso());
			statement.setString(33, interno.getContraiuDoenca());
			statement.setString(34, interno.getDiabetico());
			statement.setString(35, interno.getUsaMedicacao());
			statement.setString(36, interno.getRecomendacaoMedica());
			statement.setString(37, interno.getDocumentosEntregues());
			statement.setString(38, interno.getObservacao());

			statement.execute();
			statement.close();
			connection.close();
			Save.log(Config.usuarioLogado, "Cadastrou o(a) interno(a) "
					+ interno.getNome());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro(e.getMessage());
			Save.log(Config.usuarioLogado, "Tentou cadastrar interno.");
			return false;
		}
	}

	/**
	 * Este método serve para alterar os dados do cadastro de internos
	 * existentes na base de dados.
	 * 
	 * @param interno
	 * @return boolean
	 */
	public boolean update(InternoBean interno) {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE INTERNOS SET ");
		query.append("ID_STATUS=?,");
		query.append("ID_CONTRIBUICAO=?,");
		query.append("ID_CONVENIO=?,");
		query.append("NOME=?,");
		query.append("DATA_NASCIMENTO=?,");
		query.append("RG=?,");
		query.append("TELEFONE=?,");
		query.append("PAI=?,");
		query.append("MAE=?,");
		query.append("ESTADO_CIVIL=?,");
		query.append("CONJUGE=?,");
		query.append("PROFISSAO=?,");
		query.append("ESCOLARIDADE=?,");
		query.append("NATURALIDADE=?,");
		query.append("UF_NASCIMENTO=?,");
		query.append("NACIONALIDADE=?,");
		query.append("ENDERECO=?,");
		query.append("BAIRRO=?,");
		query.append("CIDADE=?,");
		query.append("UF=?,");
		query.append("FOTO=?,");
		query.append("DATA_ADMISSAO=?,");
		query.append("DATA_SAIDA=?,");
		query.append("TIPO=?,");
		query.append("MOTIVO=?,");
		query.append("PROCESSO_CRIMINAL=?,");
		query.append("LOCAL_PROCESSO=?,");
		query.append("SOFRE_AMEACA=?,");
		query.append("ID_DEPENDENCIA=?,");
		query.append("TEMPO_USO=?,");
		query.append("MOTIVO_USO=?,");
		query.append("DOENCA=?,");
		query.append("DIABETICO=?,");
		query.append("MEDICACAO=?,");
		query.append("RECOMENDACAO_MEDICA=?,");
		query.append("DOCUMENTOS=?,");
		query.append("OBSERVACOES=? ");
		query.append("WHERE CPF=?");

		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());

			statement.setInt(1, interno.getStatus());
			statement.setInt(2, interno.getContribuicao());
			statement.setInt(3, interno.getConvenio());
			statement.setString(4, interno.getNome());
			statement.setDate(5, new Date(interno.getNascimento().getCalendar()
					.getTimeInMillis()));
			statement.setString(6, interno.getRg());
			statement.setString(7, interno.getTelefone());
			statement.setString(8, interno.getPai());
			statement.setString(9, interno.getMae());

			statement.setString(10, interno.getEstadoCivil());
			statement.setString(11, interno.getConjuge());
			statement.setString(12, interno.getProfissao());
			statement.setString(13, interno.getEscolaridade());
			statement.setString(14, interno.getNaturalidade());
			statement.setString(15, interno.getUfNascimento());
			statement.setString(16, interno.getNacionalidade());
			statement.setString(17, interno.getEndereco());
			statement.setString(18, interno.getBairro());
			statement.setString(19, interno.getCidade());
			statement.setString(20, interno.getUf());
			statement.setString(21, interno.getFoto());
			statement.setDate(22, new Date(interno.getDataAdmissao()
					.getCalendar().getTimeInMillis()));
			statement.setDate(23, new Date(interno.getDataSaida().getCalendar()
					.getTimeInMillis()));
			statement.setString(24, interno.getTipoSaida());
			statement.setString(25, interno.getMotivoSaida());
			statement.setString(26, interno.getProcessoCriminal());
			statement.setString(27, interno.getLocal());
			statement.setString(28, interno.getSofreAmeaca());
			statement.setInt(29, interno.getdependencia());
			statement.setString(30, interno.getTempodeUso());
			statement.setString(31, interno.getMotivodeUso());
			statement.setString(32, interno.getContraiuDoenca());
			statement.setString(33, interno.getDiabetico());
			statement.setString(34, interno.getUsaMedicacao());
			statement.setString(35, interno.getRecomendacaoMedica());
			statement.setString(36, interno.getDocumentosEntregues());
			statement.setString(37, interno.getObservacao());
			statement.setString(38, interno.getCpf());

			statement.execute();
			statement.close();

			connection.close();

			Save.log(Config.usuarioLogado, "Alterou os dados do interno(a) "
					+ interno.getNome());
			Show.informacao(interno.getNome() + " alterado com sucesso.");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			Save.log(Config.usuarioLogado, "Falhou ao alterar o interno(a) "
					+ interno.getNome());
			Show.erro("Erro ao alterar.\n" + e.getMessage());
			return false;
		}
	}

	/**
	 * Método que remove o funcionário da base de dados através do CPF passado
	 * como parâmetro.
	 * 
	 * @param interno
	 */
	public void delete(InternoBean interno) {
		String query = "DELETE FROM INTERNOS WHERE CPF=?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, interno.getCpf());
			statement.execute();
			statement.close();
			connection.close();
			Save.log(Config.usuarioLogado,
					"deletou o interno(a) " + interno.getNome());
			Show.alerta("Interno(a) deletado com sucesso.");
		} catch (Exception e) {
			Show.erro("Falha ao deletar interno(a).");
			e.printStackTrace();
		}
	}

	/**
	 * Este método retorna um InternoBean preenchido com os dados do banco. É
	 * necessário informar o nome ou parte dele para realizar o filtro no banco.
	 * 
	 * @param cpf
	 * @return InternoBean
	 */
	public InternoBean getInterno(String cpf) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM INTERNOS WHERE CPF ='" + cpf + "'");

		InternoBean interno = new InternoBean();

		try {
			PreparedStatement statement;
			statement = connection.prepareStatement(query.toString());
			ResultSet rs = statement.executeQuery();
			rs.first();

			interno.setId(rs.getInt("ID"));
			interno.setStatus(rs.getInt("ID_STATUS"));
			interno.setContribuicao(rs.getInt("ID_CONTRIBUICAO"));
			interno.setConvenio(rs.getInt("ID_CONVENIO"));
			interno.setNome(rs.getString("NOME"));
			interno.getNascimento().setDate(rs.getDate("DATA_NASCIMENTO"));
			interno.setCpf(rs.getString("CPF"));
			interno.setRg(rs.getString("RG"));
			interno.setTelefone(rs.getString("TELEFONE"));
			interno.setEstadoCivil(rs.getString("ESTADO_CIVIL"));
			interno.setPai(rs.getString("PAI"));
			interno.setMae(rs.getString("MAE"));
			interno.setConjuge(rs.getString("CONJUGE"));
			interno.setProfissao(rs.getString("PROFISSAO"));
			interno.setEscolaridade(rs.getString("ESCOLARIDADE"));
			interno.setNaturalidade(rs.getString("NATURALIDADE"));
			interno.setUfNascimento(rs.getString("UF_NASCIMENTO"));
			interno.setNacionalidade(rs.getString("NACIONALIDADE"));
			interno.setEndereco(rs.getString("ENDERECO"));
			interno.setBairro(rs.getString("BAIRRO"));
			interno.setCidade(rs.getString("CIDADE"));
			interno.setUf(rs.getString("UF"));
			interno.setFoto(rs.getString("FOTO"));
			interno.getDataAdmissao().setDate(rs.getDate("DATA_ADMISSAO"));
			interno.getDataSaida().setDate(rs.getDate("DATA_SAIDA"));
			interno.setTipoSaida(rs.getString("TIPO"));
			interno.setMotivoSaida(rs.getString("MOTIVO"));
			interno.setProcessoCriminal(rs.getString("PROCESSO_CRIMINAL"));
			interno.setLocal(rs.getString("LOCAL_PROCESSO"));
			interno.setSofreAmeaca(rs.getString("SOFRE_AMEACA"));
			interno.setDependencia(rs.getInt("ID_DEPENDENCIA"));
			interno.setTempodeUso(rs.getString("TEMPO_USO"));
			interno.setMotivodeUso(rs.getString("MOTIVO_USO"));
			interno.setContraiuDoenca(rs.getString("DOENCA"));
			interno.setDiabetico(rs.getString("DIABETICO"));
			interno.setUsaMedicacao(rs.getString("MEDICACAO"));
			interno.setRecomendacaoMedica(rs.getString("RECOMENDACAO_MEDICA"));
			interno.setDocumentosEntregues(rs.getString("DOCUMENTOS"));
			interno.setObservacao(rs.getString("OBSERVACOES"));

		} catch (SQLException e) {
			Show.erro("Interno não encontrado, provavelmente\n"
					+ "não há registro no banco de dados.");
			e.printStackTrace();
		}
		return interno;
	}

	/**
	 * Método que retorna os dados do interno em uma lista
	 * 
	 * @return List<InternoBean>
	 */
	public List<InternoBean> listaInterno() {
		String query = "SELECT ID, NOME FROM INTERNOS ORDER BY NOME";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<InternoBean> internoList = new ArrayList<>();
			while (resultSet.next()) {
				InternoBean interno = new InternoBean();
				interno.setId(resultSet.getInt("ID"));
				interno.setNome(resultSet.getString("NOME"));
				internoList.add(interno);
			}
			resultSet.close();
			statement.close();
			return internoList;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método retorna uma DefaultTableModel preenchida com os cpfs e nomes
	 * dos internos cadastrados.
	 * 
	 * @param coluna
	 * @return DefaultTableModel
	 */
	public DefaultTableModel preencherTabela(DefaultTableModel coluna) {
		String query = "SELECT CPF,NOME FROM INTERNOS ORDER BY NOME";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				coluna.addRow(new Object[] { resultSet.getString("CPF"),
						resultSet.getString("NOME") });
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.usuarioLogado, "Não foi possível retornar coluna\n"
					+ e.getMessage());
			Show.erro("Não foi possível retornar coluna\n" + e.getMessage());
		}
		return coluna;
	}

	/**
	 * Este método serve para verificar se o número do CPF informado está
	 * cadastrado na base de dados.
	 * 
	 * @param cpf
	 * @return boolean
	 */
	public boolean verifyCPF(String cpf) {
		boolean retorno = false;
		String query = "SELECT CPF FROM INTERNOS WHERE CPF= ?";
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
	 * Este método retorna o número do id correspondente ao CPF informado no
	 * parâmentro.
	 * 
	 * @param cpf
	 * @return long
	 */
	public long returnId(String cpf) {

		String query = "SELECT ID FROM INTERNOS WHERE CPF= ?";
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
			Save.log(Config.system, e.getMessage());
			Show.erro("Não foi possível recuperar o interno	 \n"
					+ e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método retorna o nome do interno correspondente ao CPF informado no
	 * parâmentro.
	 * 
	 * @param cpf
	 * @return String
	 */
	public String returnNome(String cpf) {
		String query = "SELECT NOME FROM INTERNOS WHERE CPF= ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, cpf);

			ResultSet resultSet = statement.executeQuery();

			String nome = null;
			while (resultSet.next()) {
				nome = resultSet.getString("NOME");
			}
			resultSet.close();
			statement.close();
			return nome;
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			Show.erro("Não foi possível recuperar o interno	 \n"
					+ e.getMessage());
			throw new RuntimeException();
		}
	}

	/**
	 * Este método retorna a quantidade de um tipo de saída passado como
	 * parâmetro de internos que sejam passivos.
	 * 
	 * @param arg
	 * @return int
	 */
	public int countTipoSaida(String arg) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT NOME FROM INTERNOS WHERE TIPO = '");
		query.append(arg);
		query.append("' AND ID_STATUS = 2");
		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count++;
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}

	/**
	 * Este método retorna a quantidade de admissões adquiridas no mês passado
	 * como parâmetro.
	 * 
	 * @param mes
	 * @return int
	 */
	public int countMesAdmissao(int mes) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT NOME FROM INTERNOS WHERE MONTH(DATA_ADMISSAO) = ");
		query.append(mes);

		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count++;
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}

	/**
	 * Este método retorna a quantidade de altas de internos passivos no mês
	 * passado como parâmetro.
	 * 
	 * @param mes
	 * @return int
	 */
	public int countMesAlta(int mes) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT NOME FROM INTERNOS WHERE MONTH(DATA_SAIDA) = ");
		query.append(mes);
		query.append(" AND ID_STATUS = 2");

		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query
					.toString());

			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count++;
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}

	/**
	 * Método que recupera o número de internos pertencentes a uma determinada
	 * faixa etária informada pelo usuário cadastrada na base de dados.
	 * 
	 * @param idadeMin
	 * @param idadeMax
	 * @return int
	 */
	public int idadeEntre(int idadeMin, int idadeMax) {
		String query = "SELECT YEAR(CURDATE()) - YEAR(DATA_NASCIMENTO) IDADE FROM INTERNOS";
		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getInt("IDADE") >= idadeMin
						&& resultSet.getInt("IDADE") <= idadeMax)
					count++;
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}

	/**
	 * Método que recupera o número de internos pertencentes a uma determinada
	 * faixa etária igual ou maior ao valor informado.
	 * 
	 * @return int
	 */
	public int idadeAcima(int idade) {
		String query = "SELECT YEAR(CURDATE()) - YEAR(DATA_NASCIMENTO) IDADE FROM INTERNOS";
		int count = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getInt("IDADE") > idade)
					count++;
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return count;
		}
	}

	/**
	 * Este método é usado para recuperar o ID do interno através do CPF passado
	 * como parâmetro.
	 * 
	 * @param cpf
	 * @return int
	 */
	public int getId(String cpf) {
		String query = "SELECT ID, CPF FROM INTERNOS WHERE CPF=?";
		int id = 0;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, cpf);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("ID");
			}
			statement.close();
			connection.close();
			resultSet.close();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Este método serve para realizar a navegação entre os registros do
	 * interno. Ele serve para ir para o próximo registro e também voltar ao
	 * anterior.
	 * 
	 */
	// public static void navigate() {
	// try {
	// c = new ConnectionFactory().getConnection();
	// st = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	// ResultSet.CONCUR_UPDATABLE);
	// rs = st.executeQuery("SELECT * FROM INTERNOS ORDER BY NOME");
	// } catch (SQLException erro) {
	// erro.printStackTrace();
	// }
	// }
}
