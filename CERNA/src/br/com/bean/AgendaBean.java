package br.com.bean;

import br.com.controller.Data;

/**
 * Esta classe modela e representa a entidade agenda.
 * 
 * @author Luiz Alberto
 * 
 */
public class AgendaBean {

	private int id;
	private String ativo;
	private Data dia = new Data();
	private String evento;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public Data getDia() {
		return dia;
	}

}
