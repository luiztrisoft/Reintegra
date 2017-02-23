package br.com.bean;

import br.com.controller.Data;

public class LivroCaixaBean {

	private int id;
	private String tipo;
	private Data dataRegistro = new Data();
	private int idCategoria;
	private String observacao;
	private double valor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Data getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Data dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
