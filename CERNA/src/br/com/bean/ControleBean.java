package br.com.bean;

import br.com.controller.Data;

public class ControleBean {

	private int idAjudadeCusto;
	private String nomeInterno;
	private Data entrada = new Data();

	// ::::::::vencimentos::::::::
	private Data v1 = new Data();
	private Data v2 = new Data();
	private Data v3 = new Data();
	private Data v4 = new Data();
	private Data v5 = new Data();
	private Data v6 = new Data();
	private String formadePagamento;
	private String observacaoGeral;
	private String responsavelPagto;

	private String ob1;
	private String ob2;
	private String ob3;
	private String ob4;
	private String ob5;
	private String ob6;

	
	public int getIdAjudadeCusto() {
		return idAjudadeCusto;
	}

	public void setIdAjudadeCusto(int idAjudadeCusto) {
		this.idAjudadeCusto = idAjudadeCusto;
	}

	public String getNomeInterno() {
		return nomeInterno;
	}

	public void setNomeInterno(String nomeInterno) {
		this.nomeInterno = nomeInterno;
	}

	public String getFormadePagamento() {
		return formadePagamento;
	}

	public void setFormadePagamento(String formadePagamento) {
		this.formadePagamento = formadePagamento;
	}

	public String getObservacaoGeral() {
		return observacaoGeral;
	}

	public void setObservacaoGeral(String observacaoGeral) {
		this.observacaoGeral = observacaoGeral;
	}

	public String getResponsavelPagto() {
		return responsavelPagto;
	}

	public void setResponsavelPagto(String responsavelPagto) {
		this.responsavelPagto = responsavelPagto;
	}

	public Data getEntrada() {
		return entrada;
	}

	public Data getV1() {
		return v1;
	}

	public Data getV2() {
		return v2;
	}

	public Data getV3() {
		return v3;
	}

	public Data getV4() {
		return v4;
	}

	public Data getV5() {
		return v5;
	}

	public Data getV6() {
		return v6;
	}

	public String getOb1() {
		return ob1;
	}

	public void setOb1(String ob1) {
		this.ob1 = ob1;
	}

	public String getOb2() {
		return ob2;
	}

	public void setOb2(String ob2) {
		this.ob2 = ob2;
	}

	public String getOb3() {
		return ob3;
	}

	public void setOb3(String ob3) {
		this.ob3 = ob3;
	}

	public String getOb4() {
		return ob4;
	}

	public void setOb4(String ob4) {
		this.ob4 = ob4;
	}

	public String getOb5() {
		return ob5;
	}

	public void setOb5(String ob5) {
		this.ob5 = ob5;
	}

	public String getOb6() {
		return ob6;
	}

	public void setOb6(String ob6) {
		this.ob6 = ob6;
	}
	
	

}
