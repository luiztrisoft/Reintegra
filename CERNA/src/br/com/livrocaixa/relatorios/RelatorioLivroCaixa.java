package br.com.livrocaixa.relatorios;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import br.com.bean.LivroCaixaBeanTabela;
import br.com.controller.Data;
import br.com.controller.ItextControl;
import br.com.controller.Save;
import br.com.controller.Config;
import br.com.dao.LivroCaixaDAO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Esta classe � respons�vel por construir e apresentar os registros de livro
 * caixa de acordo com as datas informadas como par�metros em arquivo PDF.
 * 
 * @author Luiz Alberto
 * 
 */
public class RelatorioLivroCaixa extends ItextControl {

	private DecimalFormat df;
	private Document doc;
	private PdfPTable tabela;
	private String dataMinima;
	private String dataMaxima;
	private float coluna[];

	/**
	 * M�todo principal da classe. A partir dele se inicia a constru��o do
	 * arquivo em PDF que conter� os registros do livro caixa de acordo com as
	 * datas m�nima e m�xima informadas como par�metros.
	 * 
	 * @param dataMinima
	 * @param dataMaxima
	 */
	public void pdf(String dataMinima, String dataMaxima) {
		this.dataMinima = dataMinima;
		this.dataMaxima = dataMaxima;

		StringBuilder sb = new StringBuilder();
		sb.append("Solicitou o relat�rio de livro caixa entre as datas ");
		sb.append(this.dataMinima);
		sb.append(" e ");
		sb.append(this.dataMaxima);
		Save.log(Config.usuarioLogado, sb.toString());

		try {

			doc = new Document(PageSize.A4, 72, 72, 20, 20);
			PdfWriter.getInstance(doc, new FileOutputStream(
					"C:/CERNA/Itext/livrocaixa.pdf"));

			doc.open();
			pdf();
			doc.close();

			try {
				String command = "rundll32 SHELL32.DLL, ShellExec_RunDLL \"C:\\CERNA\\Itext\\livrocaixa.pdf\"";
				Runtime.getRuntime().exec(command);
			} catch (Exception excecao) {
				excecao.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este m�todo invoca todos os outros que comp�em a estrutura geral do
	 * arquivo, desde o t�tulo, os dados e os resultados.
	 */
	private void pdf() {
		tituloDocumento();
		estruturaTabela();
		addItem();
	}

	/**
	 * Este m�todo gera o t�tulo do documento.
	 */
	private void tituloDocumento() {
		StringBuilder periodo = new StringBuilder();
		periodo.append("Per�odo: ");
		periodo.append(dataMinima);
		periodo.append(" a ");
		periodo.append(dataMaxima);

		StringBuilder emissao = new StringBuilder();
		emissao.append("Emiss�o: ");
		emissao.append(Data.showDate());
		try {
			doc.add(titulo14("CERNA - LIVRO CAIXA"));
			doc.add(negrito12(emissao.toString()));
			doc.add(negrito12(periodo.toString()));
			doc.add(espaco(15));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este m�todo monta a estrutura da tabela.
	 */
	private void estruturaTabela() {
		try {
			coluna = new float[] { 50f, 110f, 240f, 50f, 50f, 50f, };
			tabela = new PdfPTable(coluna.length);
			tabela.setTotalWidth(coluna);
			tabela.setLockedWidth(true);
			tabela.setHorizontalAlignment(Element.ALIGN_CENTER);

			textoEsquerdaTop(tabela, "Data", negrito8);
			textoEsquerdaTop(tabela, "Categoria", negrito8);
			textoEsquerdaTop(tabela, "Observa��es", negrito8);
			textoEsquerdaTop(tabela, "Entrada", negrito8);
			textoEsquerdaTop(tabela, "Sa�da", negrito8);
			textoEsquerdaTop(tabela, "Saldo", negrito8);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este m�todo adiciona os registros do livro caixa que est�o guardados na
	 * base de dados para o relat�rio de acordo com as data passadas como
	 * par�metros.
	 */
	private void addItem() {
		try {
			addLinha();
			df = new DecimalFormat();
			df.applyPattern("#0.00");

			LivroCaixaDAO dao = new LivroCaixaDAO();

			List<LivroCaixaBeanTabela> lista = dao.getListLivroCaixa(
					dataMinima, dataMaxima);

			double soma = 0;
			double entrada = 0;
			double saida = 0;

			// ::::::::preenche o PDF::::::::
			for (LivroCaixaBeanTabela registro : lista) {
				soma = soma + registro.getEntrada() - registro.getSaida();
				entrada = entrada + registro.getEntrada();
				saida = saida + registro.getSaida();

				tabelaNoBorder(tabela, registro.getDataRegistro().getDate(), normal8);
				tabelaNoBorder(tabela, registro.getTipo(), normal8);
				tabelaNoBorder(tabela, registro.getObservacao(), normal8);
				tabelaNoBorder(tabela, df.format(registro.getEntrada()),
						normal8);
				tabelaNoBorder(tabela, df.format(registro.getSaida()), normal8);
				tabelaNoBorder(tabela, df.format(soma), normal8);

			}

			addLinha();

			textoEsquerdaTop(tabela, "", normal);
			textoEsquerdaTop(tabela, "", normal);
			textoEsquerdaTop(tabela, "", normal);
			textoEsquerdaTop(tabela, df.format(entrada), negrito8);
			textoEsquerdaTop(tabela, df.format(saida), negrito8);
			textoEsquerdaTop(tabela, df.format(soma), negrito8);

			doc.add(tabela);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este m�todo adiciona uma linha ao documento atrav�s do m�todo
	 * cell.setBorder(Rectangle.TOP) da classe PdfPCell.
	 */
	private void addLinha() {
		for (int i = 0; i < coluna.length; i++) {
			textoEsquerdaTop(tabela, "", normal);
		}
	}

}
