package br.com.pedidodemedicamentos.relatorios;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import br.com.bean.PedidoMedicamentosBean;
import br.com.controller.Data;
import br.com.controller.ItextControl;
import br.com.controller.Save;
import br.com.controller.Config;
import br.com.dao.InternoDAO;
import br.com.dao.PedidoMedicamentosDAO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Esta classe gera um relatório de pedidos de medicamentos de um interno
 * específico entre as datas fornecidas e a situação financeira delas.
 * 
 * @author Luiz Alberto
 * 
 */
public class RelatorioIndividual extends ItextControl {

	private double soma;
	private byte statusPagamento;
	private String cpf;
	private String dataInicial;
	private String dataFinal;

	private String nomeInterno;

	private Document doc;
	private PdfPTable tabela;
	private float coluna[];
	private DecimalFormat df;

	/**
	 * Este método gera o relatório de um interno especificado por seu CPF.
	 * 
	 * @param statusPagamento
	 * @param cpf
	 * @param dataInicial
	 * @param dataFinal
	 */
	public void cpf(byte statusPagamento, String cpf, String dataInicial,
			String dataFinal) {

		this.statusPagamento = statusPagamento;
		this.cpf = cpf;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.nomeInterno = getNomeInterno();

		df = new DecimalFormat();
		df.applyPattern("#0.00");

		StringBuilder sb = new StringBuilder();
		sb.append("Solicitou o relatório de pedido de medicamento entre as datas ");
		sb.append(this.dataInicial);
		sb.append(" e ");
		sb.append(this.dataFinal);
		sb.append(" do interno ");
		sb.append(nomeInterno);
		Save.log(Config.usuarioLogado, sb.toString());

		try {
			doc = new Document(PageSize.A4, 72, 72, 20, 20);
			PdfWriter.getInstance(doc, new FileOutputStream(
					"C:/CERNA/Itext/pedidoindividual.pdf"));

			doc.open();
			pdf();
			doc.close();

			try {
				String command = "rundll32 SHELL32.DLL, ShellExec_RunDLL \"C:\\CERNA\\Itext\\pedidoindividual.pdf\"";
				Runtime.getRuntime().exec(command);
			} catch (Exception excecao) {
				excecao.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Este método invoca todos os outros que compõem a estrutura geral do
	 * arquivo, desde o título, os dados e os resultados.
	 */
	private void pdf() {
		try {
			tituloDocumento();
			estruturaTabela();
			addItem();
			addSum();
			doc.add(tabela);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método gera o título do documento.
	 */
	private void tituloDocumento() {
		StringBuilder periodo = new StringBuilder();
		periodo.append("Período: ");
		periodo.append(dataInicial);
		periodo.append(" a ");
		periodo.append(dataFinal);

		StringBuilder emissao = new StringBuilder();
		emissao.append("Emissão: ");
		emissao.append(Data.showDate());
		try {
			doc.add(titulo14("CERNA - Relatório individual"));
			doc.add(negrito12(emissao.toString()));
			doc.add(negrito12(periodo.toString()));
			doc.add(espaco(15));
			doc.add(titulo14("Interno: " + nomeInterno));
			doc.add(espaco(15));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método monta a estrutura da tabela.
	 */
	private void estruturaTabela() {
		try {
			coluna = new float[] { 50f, 250f, 70f, 70f, 50f };
			tabela = new PdfPTable(coluna.length);
			tabela.setTotalWidth(coluna);
			tabela.setLockedWidth(true);
			tabela.setHorizontalAlignment(Element.ALIGN_CENTER);

			textoEsquerdaTop(tabela, "Cód.", negrito8);
			textoEsquerdaTop(tabela, "Medicamento", negrito8);
			textoEsquerdaTop(tabela, "Data do pedido", negrito8);
			textoEsquerdaTop(tabela, "Preço", negrito8);
			textoEsquerdaTop(tabela, "Pago", negrito8);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método adiciona os pedidos de medicamentos que estão guardados na
	 * base de dados para o relatório de acordo com o CPF e as data passadas
	 * como parâmetros.
	 */
	private void addItem() {
		try {
			addLinha();
			PedidoMedicamentosDAO dao = new PedidoMedicamentosDAO();

			List<PedidoMedicamentosBean> lista = dao.getListPedido(
					statusPagamento, cpf, dataInicial, dataFinal);

			for (PedidoMedicamentosBean pedido : lista) {
				tabelaNoBorder(tabela, pedido.getId() + "", normal8);
				tabelaNoBorder(tabela, pedido.getMedicamento(), normal8);
				tabelaNoBorder(tabela, pedido.getDataPedido().getDate(),
						normal8);
				tabelaNoBorder(tabela, "R$ " + df.format(pedido.getPreco()),
						normal8);
				tabelaNoBorder(tabela, pedido.getPago(), normal8);
				soma = soma + pedido.getPreco();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método adiciona a soma de todas as pendências ao final do relatório.
	 */
	private void addSum() {
		try {
			textoEsquerdaTop(tabela, "", negrito8);
			textoEsquerdaTop(tabela, "", negrito8);
			textoEsquerdaTop(tabela, "Total de pedidos:", negrito8);
			textoEsquerdaTop(tabela, "R$ " + df.format(soma), negrito8);
			textoEsquerdaTop(tabela, "", negrito8);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método adiciona uma linha ao documento através do método
	 * cell.setBorder(Rectangle.TOP) da classe PdfPCell.
	 */
	private void addLinha() {
		for (int i = 0; i < coluna.length; i++) {
			textoEsquerdaTop(tabela, "", normal);
		}
	}

	/**
	 * Através do CPF informado, este método retorna o nome do interno
	 * selecionado para vinculação do pedido de medicamento.
	 * 
	 * @return String
	 */
	private String getNomeInterno() {
		InternoDAO dao = new InternoDAO();
		String nomeInterno = dao.returnNome(cpf);
		return nomeInterno;
	}
}
