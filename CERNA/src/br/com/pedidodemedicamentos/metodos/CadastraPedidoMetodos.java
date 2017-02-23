package br.com.pedidodemedicamentos.metodos;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.PedidoMedicamentosBean;
import br.com.controller.Data;
import br.com.dao.InternoDAO;
import br.com.dao.PedidoMedicamentosDAO;

/**
 * Esta classe se comunica com a classe PedidoMedicamentoDAO para realizar o
 * cadastro do pedido de medicamento.
 * 
 * @author Luiz Alberto
 * 
 */
public class CadastraPedidoMetodos {

	/**
	 * Este método se comunica com a classe {@link PedidoMedicamentosDAO} para
	 * persistir os dados do pedido.
	 * 
	 * @param cpf
	 * @param campo
	 * @param area
	 * @param pago
	 */
	public void cadastrar(String cpf, JTextField[] campo, JTextArea area,
			byte pago) {
		PedidoMedicamentosBean pedido = new PedidoMedicamentosBean();

		InternoDAO internoDAO = new InternoDAO();
		int idInterno = internoDAO.getId(cpf);

		String medicamento = campo[0].getText();
		double preco = Double.parseDouble(campo[1].getText().replaceAll(",",
				"."));
		String observacoes = area.getText();
		String dataPedido = campo[2].getText();
		String dataPagamento = campo[3].getText();

		String pedidoPago = null;
		if (pago == 0) {
			pedidoPago = "N";
		} else if (pago == 1) {
			pedidoPago = "S";
		}

		// ::::::::seta os valores::::::::

		pedido.setIdInterno(idInterno);
		pedido.setMedicamento(medicamento);
		pedido.setPreco(preco);
		pedido.setPago(pedidoPago);
		pedido.setObservacoes(observacoes);
		pedido.getDataPedido().setCalendar(Data.stringToDate(null, dataPedido));
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

		// ::::::::persistência dos dados::::::::
		PedidoMedicamentosDAO dao = new PedidoMedicamentosDAO();
		dao.insert(pedido);

	}
}
