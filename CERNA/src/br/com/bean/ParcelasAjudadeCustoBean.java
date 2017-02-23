package br.com.bean;

import br.com.controller.Data;

/**
 * Esta classe modela a entidade Parcelas da ajuda de custo. Este modelo trata
 * das parcelas geradas a partir da ajuda de custo.
 * 
 * @author Luiz Alberto
 * 
 */
public class ParcelasAjudadeCustoBean {

	private int id;
	private int ajudadeCusto;

	private Data vencimento = new Data();
	private double valorPago;
	private int pago;
	private String observacoes;
	private int mes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAjudadeCusto() {
		return ajudadeCusto;
	}

	public void setAjudadeCusto(int ajudadeCusto) {
		this.ajudadeCusto = ajudadeCusto;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public int getPago() {
		return pago;
	}

	public void setPago(int pago) {
		this.pago = pago;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Data getVencimento() {
		return vencimento;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

}
