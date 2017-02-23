package br.com.pedidodemedicamentos.metodos;

import br.com.dao.PedidoMedicamentosDAO;

/**
 * Esta classe se comunica com a classe {@link PedidoMedicamentosDAO} para
 * realizar a exlusão do pedido de medicamento.
 * 
 * @author Luiz Alberto
 * 
 */
public class DeletaPedidoMetodos {

	/**
	 * Este método informa o ID do pedido a ser removido da base de dados para a
	 * classe {@link PedidoMedicamentosDAO}.
	 * 
	 * @param idPedido
	 */
	public void delete(int idPedido) {
		PedidoMedicamentosDAO dao = new PedidoMedicamentosDAO();
		dao.delete(idPedido);
	}
}
