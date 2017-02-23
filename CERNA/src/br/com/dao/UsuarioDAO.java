package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.bean.UsuarioBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Criptografia;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosUsuario;

/**
 * Este método trata as operações pertinentes a persistência e recuperação de
 * dados do Usuário a base de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class UsuarioDAO extends FuncionarioDAO {

	private Connection con;

	public UsuarioDAO() {
		this.con = new ConnectionFactory().getConnection();
	}

	/**
	 * Método que salva um usuário na base de dados.
	 * 
	 * @param usuario
	 */
	public void inserirUsuario(UsuarioBean usuario) {
		String query = "INSERT INTO USUARIOS(LOGIN, SENHA, ID_FUNCIONARIO, ID_CRIADOR) VALUES(?,?,?,?)";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setInt(3, usuario.getIdFuncionario());
			statement.setInt(4, usuario.getIdUsuarioCriador());
			statement.execute();

			// ::::::::log do sistema:::::::;
			Save.log(Config.usuarioLogado, "Cadastrou o usuário "
					+ getFuncionario(usuario.getIdFuncionario()));
			Show.informacao("O usuário "
					+ getFuncionario(usuario.getIdFuncionario())
					+ " foi cadastrado com sucesso.");

			statement.close();
			con.close();
		} catch (SQLException e) {
			Show.erro("Falha ao cadastrar novo usuário.\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Este método é utilizado caso o usuário queira atualizar sua senha no
	 * sistema.
	 * 
	 * @param senhaAtual
	 * @param novaSenha
	 */
	public void atualizarSenha(String senhaAtual, String novaSenha) {
		String query = "UPDATE USUARIOS SET SENHA=? WHERE SENHA=? AND ID_FUNCIONARIO=?";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, novaSenha);
			statement.setString(2, senhaAtual);
			statement.setInt(3, Config.idUsuarioLogado);
			statement.execute();

			Show.informacao("Sua senha foi alterada com sucesso!");
			Save.log(Config.usuarioLogado, "Alterou sua senha no sistema");

			statement.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro("Falha ao atualizar a senha de usuário!\n"
					+ e.getMessage());
			Save.log(Config.usuarioLogado,
					"Falhou ao tentar alterar sua senha no sistema");
		}
	}

	public String getSenha() {
		String query = "SELECT SENHA FROM USUARIOS WHERE ID_FUNCIONARIO=?";
		String senhadoBanco = null;
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, Config.idUsuarioLogado);
			ResultSet r = statement.executeQuery();
			while (r.next()) {
				senhadoBanco = r.getString("SENHA");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return senhadoBanco;
	}

	/**
	 * Este método retorna o nome do funcionário de acordo com o id informado.
	 * 
	 * @param id
	 * @return String
	 */
	private String getFuncionario(int id) {
		String query = "SELECT NOME_FUNCIONARIO FROM FUNCIONARIOS WHERE ID_FUNCIONARIO=?";
		String nome = null;
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				nome = rs.getString("NOME_FUNCIONARIO");
			}
			statement.close();
			return nome;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Método utilizado para remover um usuário da base de dados.
	 * 
	 * @param usuario
	 */
	public void deletarUsuario(UsuarioBean usuario) {
		String query = "DELETE FROM USUARIOS WHERE ID_FUNCIONARIO=?";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, usuario.getIdFuncionario());
			statement.execute();

			Save.log(Config.usuarioLogado, "deletou o usuário "
					+ getFuncionario(usuario.getIdFuncionario()));
			Show.informacao("Usuário deletado com sucesso.");

			statement.close();
			con.close();

		} catch (SQLException e) {
			Show.erro("Erro ao deletar usuário.");
			e.printStackTrace();
		}
	}

	/**
	 * Método usado ao abrir o programa e realizar uma tentativa de login tendo
	 * um valor boolean como resposta.
	 * 
	 * @param login
	 * @param senha
	 * @return boolean
	 */
	public boolean logar(String login, String senha) {
		boolean retorno = false;
		String query = "SELECT * FROM USUARIOS WHERE LOGIN=? AND SENHA=?";
		MetodosGenericosUsuario metodoUsuario = new MetodosGenericosUsuario();
		if (metodoUsuario.validaLogin(login) == true
				&& metodoUsuario.validaSenha(senha) == true) {
			try {
				PreparedStatement statement = con.prepareStatement(query);
				statement.setString(1, login);
				statement.setString(2, Criptografia.criptografar(senha));
				ResultSet resultSet = statement.executeQuery();
				retorno = resultSet.first();
				usuarioLogado(login);
				idUsuarioLogado(login);

				resultSet.close();
				statement.close();
				con.close();
			} catch (SQLException e) {
				Show.erro(e.getMessage());
			}
		} else {
		}
		return retorno;
	}

	/**
	 * Este método atribui a uma variável estática da classe {@link Config}
	 * chamada idUsuarioLogado o ID do usuário que efetuou login no sistema.
	 * 
	 * @param login
	 * 
	 */
	private void idUsuarioLogado(String login) {
		String query = "SELECT FUNCIONARIOS.ID_FUNCIONARIO FROM FUNCIONARIOS, USUARIOS WHERE FUNCIONARIOS.ID_FUNCIONARIO=USUARIOS.ID_FUNCIONARIO AND USUARIOS.LOGIN=?";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("FUNCIONARIOS.ID_FUNCIONARIO");
				Config.idUsuarioLogado = id;
			}

			statement.close();
			resultSet.close();
			// con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método é invocado quando o usuário tenta efetuar o login no sistema.
	 * Ele serve para mostrar o nome do usuário que acabou de entrar. Ele é
	 * invocado pelo método logar desta mesma classe. A função principal é
	 * atribuir o nome do usuário e seu login ao sistema para identificação e
	 * persistência de logs.
	 * 
	 * @param login
	 */
	private void usuarioLogado(String login) {
		String query = "SELECT NOME_FUNCIONARIO FROM FUNCIONARIOS, USUARIOS WHERE FUNCIONARIOS.ID_FUNCIONARIO=USUARIOS.ID_FUNCIONARIO AND USUARIOS.LOGIN=?";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String nome = resultSet.getString("NOME_FUNCIONARIO");
				Config.usuarioLogado = nome;
				Config.loginUsuario = login;
			}
			statement.close();
			resultSet.close();
			// con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna uma lista de todos os funcionários.
	 * 
	 * @return List<string>
	 */
	/*
	 * public List<String> listaFuncionarios() { String query =
	 * "SELECT NOME_FUNCIONARIO FROM FUNCIONARIOS ORDER BY NOME_FUNCIONARIO";
	 * try { PreparedStatement statement = con.prepareStatement(query);
	 * ResultSet resultSet = statement.executeQuery();
	 * 
	 * List<String> lista = new ArrayList<>(); while (resultSet.next()) {
	 * lista.add(resultSet.getString("NOME_FUNCIONARIO")); } resultSet.close();
	 * statement.close(); return lista; } catch (Exception e) { throw new
	 * RuntimeException(); } }
	 */
	/**
	 * Retorna uma lista de todos os usuários.
	 * 
	 * @return List<string>
	 */
	public List<String> listaUsuarios() {
		String query = "SELECT NOME_FUNCIONARIO FROM USUARIOS WHERE NOME_FUNCIONARIO NOT LIKE '%"
				+ Config.usuarioLogado + "%' ORDER BY NOME_FUNCIONARIO";
		try {
			PreparedStatement statement = con.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			List<String> lista = new ArrayList<>();
			while (resultSet.next()) {
				lista.add(resultSet.getString("NOME_FUNCIONARIO"));
			}
			resultSet.close();
			statement.close();
			con.close();
			return lista;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Este método verifica a existência do ID_FUNCIONARIO na tabela de USUARIOS
	 * para realizar a remoção do mesmo.
	 * 
	 * @param id
	 * @return boolean
	 */
	public boolean existenciaId(int id) {
		boolean retorno = false;
		String query = "SELECT * FROM USUARIOS WHERE ID_FUNCIONARIO=?";

		try {
			PreparedStatement statement = con
					.prepareStatement(query.toString());
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			retorno = resultSet.first();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
}
