package br.com.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.FuncionarioBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe realiza as operações de CRUD relacionadas a entidade Funcionario.
 * 
 * @author Luiz Alberto
 * 
 */
public class FuncionarioDAO {

	private Connection con;

	public FuncionarioDAO() {
		this.con = new ConnectionFactory().getConnection();
	}

	/**
	 * Método que salva um novo funcionário no banco de dados.
	 * 
	 * @param funcionario
	 */
	public void inserirFuncionario(FuncionarioBean funcionario) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO FUNCIONARIOS(");
		query.append("NOME_FUNCIONARIO,");
		query.append("CPF_FUNCIONARIO,");
		query.append("TELEFONE_FUNCIONARIO,");
		query.append("NASCIMENTO_FUNCIONARIO,");
		query.append("EMAIL_FUNCIONARIO,");
		query.append("ENDERECO_FUNCIONARIO,");
		query.append("BAIRRO_FUNCIONARIO,");
		query.append("CIDADE_FUNCIONARIO,");
		query.append("FILIACAO_FUNCIONARIO,");
		query.append("CARGO_FUNCIONARIO,");
		query.append("STATUS_FUNCIONARIO,");
		query.append("FOTO_FUNCIONARIO,");
		query.append("OBSERVACAO_FUNCIONARIO");
		query.append(")");
		query.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

		try {
			PreparedStatement statement = con
					.prepareStatement(query.toString());

			statement.setString(1, funcionario.getNome());
			statement.setString(2, funcionario.getCpf());
			statement.setString(3, funcionario.getTelefone());
			statement.setDate(4, new Date(funcionario.getNascimento()
					.getCalendar().getTimeInMillis()));
			statement.setString(5, funcionario.getEmail());
			statement.setString(6, funcionario.getEndereco());
			statement.setString(7, funcionario.getBairro());
			statement.setString(8, funcionario.getCidade());
			statement.setString(9, funcionario.getFiliacao());
			statement.setString(10, funcionario.getCargo());
			statement.setString(11, funcionario.getStatus());
			statement.setString(12, funcionario.getFoto());
			statement.setString(13, funcionario.getObservacao());

			statement.execute();
			statement.close();

			Save.log(Config.usuarioLogado, "Cadastrou o funcionário "
					+ funcionario.getNome());
			Show.informacao(funcionario.getNome() + " cadastrado com sucesso.");
		} catch (Exception e) {
			Show.erro("Erro ao salvar.\n" + e.getMessage());
		}
	}

	/**
	 * Este método busca funcionários através do nome passado como parâmetro da
	 * classe BuscaFuncionarioMetodos. O parâmetro pode ser o nome completo ou
	 * parte dele.
	 * 
	 * @param nome
	 * @return os dados do funcionário.
	 */
	public FuncionarioBean buscarFuncionario(String nome) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM FUNCIONARIOS WHERE NOME_FUNCIONARIO LIKE '%");
		query.append(nome);
		query.append("%' ORDER BY NOME_FUNCIONARIO");

		FuncionarioBean funcionario = new FuncionarioBean();

		try {
			PreparedStatement statement = con
					.prepareStatement(query.toString());
			ResultSet resultSet = statement.executeQuery();
			resultSet.first();
			funcionario.setNome(resultSet.getString("NOME_FUNCIONARIO"));
			funcionario.setCpf(resultSet.getString("CPF_FUNCIONARIO"));
			funcionario
					.setTelefone(resultSet.getString("TELEFONE_FUNCIONARIO"));
			funcionario.getNascimento().setDate(
					resultSet.getDate("NASCIMENTO_FUNCIONARIO"));
			funcionario.setEmail(resultSet.getString("EMAIL_FUNCIONARIO"));
			funcionario
					.setEndereco(resultSet.getString("ENDERECO_FUNCIONARIO"));
			funcionario.setBairro(resultSet.getString("BAIRRO_FUNCIONARIO"));
			funcionario.setCidade(resultSet.getString("CIDADE_FUNCIONARIO"));
			funcionario
					.setFiliacao(resultSet.getString("FILIACAO_FUNCIONARIO"));
			funcionario.setCargo(resultSet.getString("CARGO_FUNCIONARIO"));
			funcionario.setStatus(resultSet.getString("STATUS_FUNCIONARIO"));
			funcionario.setFoto(resultSet.getString("FOTO_FUNCIONARIO"));
			funcionario.setObservacao(resultSet
					.getString("OBSERVACAO_FUNCIONARIO"));
		} catch (SQLException e) {
			Show.erro("Funcionário não encontrado, provavelmente\n"
					+ "não há registro no banco de dados.");
			e.printStackTrace();
		}

		return funcionario;
	}

	/**
	 * Método que altera os dados do funcionário de acordo com o CPF informado.
	 * 
	 * @param funcionario
	 */
	public void alterarFuncionario(FuncionarioBean funcionario) {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE FUNCIONARIOS SET ");
		query.append("NOME_FUNCIONARIO=?,");
		query.append("TELEFONE_FUNCIONARIO=?,");
		query.append("NASCIMENTO_FUNCIONARIO=?,");
		query.append("EMAIL_FUNCIONARIO=?,");
		query.append("ENDERECO_FUNCIONARIO=?,");
		query.append("BAIRRO_FUNCIONARIO=?,");
		query.append("CIDADE_FUNCIONARIO=?,");
		query.append("FILIACAO_FUNCIONARIO=?,");
		query.append("CARGO_FUNCIONARIO=?,");
		query.append("STATUS_FUNCIONARIO=?,");
		query.append("FOTO_FUNCIONARIO=?,");
		query.append("OBSERVACAO_FUNCIONARIO=? ");
		query.append("WHERE CPF_FUNCIONARIO=?");

		try {
			PreparedStatement statement = con
					.prepareStatement(query.toString());

			statement.setString(1, funcionario.getNome());
			statement.setString(2, funcionario.getTelefone());
			statement.setDate(3, new Date(funcionario.getNascimento()
					.getCalendar().getTimeInMillis()));
			statement.setString(4, funcionario.getEmail());
			statement.setString(5, funcionario.getEndereco());
			statement.setString(6, funcionario.getBairro());
			statement.setString(7, funcionario.getCidade());
			statement.setString(8, funcionario.getFiliacao());
			statement.setString(9, funcionario.getCargo());
			statement.setString(10, funcionario.getStatus());
			statement.setString(11, funcionario.getFoto());
			statement.setString(12, funcionario.getObservacao());
			statement.setString(13, funcionario.getCpf());

			statement.execute();
			statement.close();

			Save.log(
					Config.usuarioLogado,
					"Alterou os dados do funcionário(a) "
							+ funcionario.getNome());
			Show.informacao(funcionario.getNome() + " alterado com sucesso.");
		} catch (SQLException e) {
			e.printStackTrace();
			Show.erro("Erro ao alterar.\n" + e.getMessage());
		}
	}

	/**
	 * Método que remove o funcionário da base de dados através do CPF passado
	 * como parâmetro.
	 * 
	 * @param funcionario
	 */
	public void deletarFuncionario(FuncionarioBean funcionario) {
		String query = "DELETE FROM FUNCIONARIOS WHERE CPF_FUNCIONARIO=?";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, funcionario.getCpf());
			statement.execute();
			statement.close();

			Save.log(Config.usuarioLogado, "deletou o funcionário(a) "
					+ funcionario.getNome());
			Show.alerta("Funcionário(a) deletado com sucesso.");
		} catch (Exception e) {
			Show.erro("Falha ao deletar funcionário(a).");
			e.printStackTrace();
		}
	}

	/**
	 * Este método faz uma verificação na base de dados para ver se o CPF
	 * passado como parâmetro está cadastrado retornando 'true' se sim e 'false'
	 * caso contrário.
	 * 
	 * @param cpf
	 * @return boolean
	 */
	public boolean existenciaCPF(String cpf) {
		boolean retorno = false;
		String query = "SELECT * FROM FUNCIONARIOS WHERE CPF_FUNCIONARIO=?";

		try {
			PreparedStatement statement = con
					.prepareStatement(query.toString());
			statement.setString(1, cpf);
			ResultSet resultSet = statement.executeQuery();
			retorno = resultSet.first();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * Retorna uma lista de funcionários.
	 * 
	 * @return List<String>
	 */
	public List<String> listaFuncionarios() {
		String query = "SELECT NOME_FUNCIONARIO FROM FUNCIONARIOS ORDER BY NOME_FUNCIONARIO";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<String> funcionarios = new ArrayList<>();
			while (resultSet.next()) {
				funcionarios.add(resultSet.getString("NOME_FUNCIONARIO"));
			}
			resultSet.close();
			statement.close();
			return funcionarios;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Método usado para buscar o nome do coordenador da comunidade.
	 * 
	 * @return String
	 */
	public String buscaCoordenador() {
		String coordenador = "Não há coordenador cadastrado na base de dados";
		String query = "SELECT NOME_FUNCIONARIO,CARGO_FUNCIONARIO FROM FUNCIONARIOS WHERE CARGO_FUNCIONARIO  = 'Coordenador'";

		try {
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			resultSet.first();
			coordenador = resultSet.getString("NOME_FUNCIONARIO");

			return coordenador;

		} catch (Exception e) {
			e.printStackTrace();
			return coordenador;
		}
	}

	/**
	 * Este método retorna o ID de um funcionário de acordo com o CPF informado
	 * com parâmetro.
	 * 
	 * @param cpf
	 * @return int
	 */
	public int getId(String cpf) {
		String query = "SELECT ID_FUNCIONARIO FROM FUNCIONARIOS WHERE CPF_FUNCIONARIO = ?";
		int id = 0;
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, cpf);
			statement.execute();
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt("ID_FUNCIONARIO");
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return id;
		}
	}

	/**
	 * Este método retorna o nome do funcionário de acordo com o ID informado
	 * como parâmetro
	 * 
	 * @param id
	 * @return String
	 */
	public String getNome(int id) {
		String query = "SELECT NOME_FUNCIONARIO FROM FUNCIONARIOS WHERE ID_FUNCIONARIO = ?";
		String nome = null;
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, id);
			statement.execute();
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				nome = resultSet.getString("NOME_FUNCIONARIO");
			}
			return nome;
		} catch (Exception e) {
			e.printStackTrace();
			return nome;
		}
	}
}
