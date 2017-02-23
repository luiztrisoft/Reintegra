package br.com.pedidodemedicamentos.metodos;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.PedidoMedicamentosBean;
import br.com.controller.Data;
import br.com.dao.PedidoMedicamentosDAO;

/**
 * Esta classe se comunica com a classe {@link PedidoMedicamentosDAO} para
 * realizar a alteração dos dados do pedido de medicamento.
 * 
 * @author Luiz Alberto
 * 
 */
public class AlteraPedidoMetodos {

	/**
	 * Este método envia os dados para a classe {@link PedidoMedicamentosDAO}
	 * para que as informações sejam alteradas.
	 * 
	 * @param idPedido
	 * @param campo
	 * @param radio
	 * @param area
	 */
	public void update(int idPedido, JTextField[] campo, JRadioButton[] radio,
			JTextArea area) {
		// ::::::::definição dos atributos::::::::
		String medicamento = campo[0].getText();
		double preco = Double.parseDouble(campo[1].getText().replaceAll(",",
				"."));
		String dataPedido = campo[2].getText();
		String dataPagamento = campo[3].getText();
		String observacoes = area.getText();
		String pago = null;
		if (radio[0].isSelected() == true) {
			pago = "N";
		} else if (radio[1].isSelected() == true) {
			pago = "S";
		}

		// ::::::::pedido de medicamento::::::::
		PedidoMedicamentosBean pedido = new PedidoMedicamentosBean();
		pedido.setId(idPedido);
		pedido.setMedicamento(medicamento);
		pedido.setPreco(preco);
		pedido.getDataPedido().setCalendar(Data.stringToDate(null, dataPedido));
		pedido.setPago(pago);
		pedido.setObservacoes(observacoes);
		/*
		 * Se o campo possuir o formato de data d2/d2/d4, a data do pagamento
		 * salva neste formato. Caso contrário salva a data como null.
		 */
		if (dataPagamento.matches("\\d{2}/\\d{2}/\\d{4}")) {
			pedido.getDataPagamento().setCalendar(
					Data.stringToDate(null, dataPagamento));
		} else {
			pedido.setDataPagamento(null);
		}

		// ::::::::update DAO::::::::
		PedidoMedicamentosDAO dao = new PedidoMedicamentosDAO();
		dao.update(pedido);

	}
}
