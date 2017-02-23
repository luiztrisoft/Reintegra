package br.com.bean;

/**
 * Esta classe modela e representa a entidade ajuda de custo.
 * 
 * @author Luiz Alberto
 * 
 */
public class AjudadeCustoBean {
	private int id;
	private int interno;
	private int responsavelPagamento;
	private int categoria;
	private int formaPagamento;

	private double valorParcelas;
	private String observacoes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInterno() {
		return interno;
	}

	public void setInterno(int interno) {
		this.interno = interno;
	}

	public int getResponsavelPagamento() {
		return responsavelPagamento;
	}

	public void setResponsavelPagamento(int responsavelPagamento) {
		this.responsavelPagamento = responsavelPagamento;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public int getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(int formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public double getValorParcelas() {
		return valorParcelas;
	}

	public void setValorParcelas(double valorParcelas) {
		this.valorParcelas = valorParcelas;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

}
