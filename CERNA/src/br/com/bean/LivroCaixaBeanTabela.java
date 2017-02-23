package br.com.bean;

/**
 * Esta classe é utilizada para preencher a tabela de registros de livro caixa.
 * Ela estende da classe {@link LivroCaixaBean}.
 * 
 * @author Luiz Alberto
 * 
 */
public class LivroCaixaBeanTabela extends LivroCaixaBean {

	private double entrada;
	private double saida;

	public double getEntrada() {
		return entrada;
	}

	public void setEntrada(double entrada) {
		this.entrada = entrada;
	}

	public double getSaida() {
		return saida;
	}

	public void setSaida(double saida) {
		this.saida = saida;
	}

}
/*
 * private int id; private Data data = new Data(); private String tipo; private
 * String observacao; private double entrada; private double saida;
 */