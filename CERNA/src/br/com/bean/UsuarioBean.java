package br.com.bean;

public class UsuarioBean {

	private int id;
	private String login;
	private String senha;
	// private String nomeFuncionario;
	private int idFuncionario;
	// private String nomeCriador;
	private int idUsuarioCriador;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public int getIdUsuarioCriador() {
		return idUsuarioCriador;
	}

	public void setIdUsuarioCriador(int idUsuarioCriador) {
		this.idUsuarioCriador = idUsuarioCriador;
	}

}