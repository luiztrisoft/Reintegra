package br.com.bean;

import java.util.Calendar;

public class LogBean {

	private String usuario;
	private String evento;
	private Calendar data;
	private String hora;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String i) {
		this.hora = i;
	}
}
