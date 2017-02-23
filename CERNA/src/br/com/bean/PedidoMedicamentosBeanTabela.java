package br.com.bean;

/**
 * Esta classe serve para recuperar os dados para preencher a tabela de pedido
 * de medicamentos. Ela estende de {@link PedidoMedicamentosBean} e acrescenta
 * apenas o nome do interno em sua estrutura.
 * 
 * @author Luiz Alberto
 * 
 */
public class PedidoMedicamentosBeanTabela extends PedidoMedicamentosBean {

	private String nomeInterno;

	public String getNomeInterno() {
		return nomeInterno;
	}

	public void setNomeInterno(String nomeInterno) {
		this.nomeInterno = nomeInterno;
	}

}
