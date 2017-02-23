package br.com.pedidodemedicamentos.metodos;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.PedidoMedicamentosBean;
import br.com.dao.PedidoMedicamentosDAO;

/**
 * Esta classe se comunica com a classe {@link PedidoMedicamentosDAO} para
 * recuperar um pedido de medicamento específico através do seu ID informado
 * como parâmetro.
 * 
 * @author Luiz Alberto
 * 
 */
public class BuscaPedidoMetodos {

	/**
	 * Este método é usado para preencher os campos da tela detalhe com os dados
	 * do pedido específicado pelo ID.
	 * 
	 * @param idPedido
	 * @param campo
	 * @param radio
	 * @param area
	 */
	public void getPedido(int idPedido, JTextField[] campo,
			JRadioButton[] radio, JTextArea area) {
		PedidoMedicamentosDAO dao = new PedidoMedicamentosDAO();
		PedidoMedicamentosBean pedido = dao.getPedido(idPedido);

		String medicamento = pedido.getMedicamento();
		String preco = "" + pedido.getPreco();
		String dataPedido = pedido.getDataPedido().getDate();
		String dataPagamento = pedido.getDataPagamento().getDate();
		String observacoes = pedido.getObservacoes();

		if (pedido.getPago().equals("N")) {
			radio[0].setSelected(true);
		} else if (pedido.getPago().equals("S")) {
			radio[1].setSelected(true);
		}

		campo[0].setText(medicamento);
		campo[1].setText(preco);
		campo[2].setText(dataPedido);
		campo[3].setText(dataPagamento);
		area.setText(observacoes);
	}
}
