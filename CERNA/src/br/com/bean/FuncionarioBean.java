package br.com.bean;


public class FuncionarioBean extends PessoaFisica {

	private String cargo;
	private String status;
	
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
}