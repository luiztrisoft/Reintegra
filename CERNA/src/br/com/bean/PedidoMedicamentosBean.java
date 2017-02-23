package br.com.bean;

import br.com.controller.Data;

public class PedidoMedicamentosBean {

	private int id;
	private int idInterno;
	private String medicamento;
	private double preco;
	private String pago;
	private String observacoes;
	Data dataPedido = new Data();
	Data dataPagamento = new Data();

	public int getId() {
		return id;
	}

	public void setDataPagamento(Data dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdInterno() {
		return idInterno;
	}

	public void setIdInterno(int idInterno) {
		this.idInterno = idInterno;
	}

	public String getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getPago() {
		return pago;
	}

	public void setPago(String pago) {
		this.pago = pago;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Data getDataPedido() {
		return dataPedido;
	}

	public Data getDataPagamento() {
		return dataPagamento;
	}

}
