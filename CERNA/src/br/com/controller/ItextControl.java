package br.com.controller;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Esta classe reserva funcionalidades para a constru��o de relat�rios com a API
 * itext como defini��o de fontes, tamanho, tipo etc.
 * 
 * @author Luiz Alberto
 * 
 */
public class ItextControl {
	protected static Font normal8 = FontFactory.getFont(FontFactory.TIMES_ROMAN,
			8, Font.NORMAL);
	protected static Font normal = FontFactory.getFont(FontFactory.TIMES_ROMAN,
			10, Font.NORMAL);
	protected static Font italico = FontFactory.getFont(
			FontFactory.TIMES_ROMAN, 10, Font.ITALIC);
	protected static Font negrito8 = FontFactory.getFont(
			FontFactory.TIMES_ROMAN, 8, Font.BOLD);
	protected static Font negrito = FontFactory.getFont(
			FontFactory.TIMES_ROMAN, 10, Font.BOLD);
	protected static Font negrito12 = FontFactory.getFont(
			FontFactory.TIMES_ROMAN, 12, Font.BOLD);
	protected static Font negritoUnderline = FontFactory.getFont(
			FontFactory.TIMES_ROMAN, 10, Font.UNDERLINE + Font.BOLD);
	protected static Font titulo = FontFactory.getFont(FontFactory.TIMES_ROMAN,
			10, Font.BOLD);
	protected static Font titulo14 = FontFactory.getFont(
			FontFactory.TIMES_ROMAN, 14, Font.BOLD);

	/**
	 * Este m�todo serve para gerar um t�tulo no documento.
	 * 
	 * @param arg
	 * @return Paragraph
	 */
	protected static Paragraph titulo(String arg) {
		Paragraph p = new Paragraph(arg, titulo);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		return p;
	}

	/**
	 * Este m�todo gera um t�tulo do tipo Paragraph no tamanho 12, centralizado
	 * e estilo BOLD.
	 * 
	 * @param arg
	 * @return Paragraph
	 */
	protected static Paragraph negrito12(String arg) {
		Paragraph p = new Paragraph(arg, negrito12);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		return p;
	}

	/**
	 * Este m�todo gera um t�tulo do tipo Paragraph no tamanho 14, centralizado
	 * e estilo BOLD.
	 * 
	 * @param arg
	 * @return Paragraph
	 */
	protected static Paragraph titulo14(String arg) {
		Paragraph p = new Paragraph(arg, titulo14);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		return p;
	}

	/**
	 * Este m�todo gera um par�grafo no documento. A cada novo par�grafo ele
	 * quebra a linha diferente do m�todo frase.
	 * 
	 * @param arg
	 * @param font
	 * @return Paragraph
	 */
	protected static Paragraph paragrafo(String arg, Font font) {
		Paragraph p = new Paragraph(arg, font);
		return p;
	}

	/**
	 * Este m�todo cria um par�grafo igual ao m�todo paragrafo(String arg, Font
	 * font), por�m alinhado a direita do documento.
	 * 
	 * @param arg
	 * @param font
	 * @return Paragraph
	 */
	protected static Paragraph paragrafoDireita(String arg, Font font) {
		Paragraph p = new Paragraph(arg, font);
		p.setAlignment(Paragraph.ALIGN_RIGHT);
		return p;
	}

	/**
	 * Este m�todo cria um par�grafo igual ao m�todo paragrafo(String arg, Font
	 * font), por�m alinhado a esquerda do documento.
	 * 
	 * @param arg
	 * @param font
	 * @return Paragraph
	 */
	protected static Paragraph paragrafoEsquerda(String arg, Font font) {
		Paragraph p = new Paragraph(arg, font);
		p.setAlignment(Paragraph.ALIGN_LEFT);
		return p;
	}

	/**
	 * Este m�todo cria paragrafos justificados no documento.
	 * 
	 * @param arg
	 * @return Paragraph
	 */
	protected static Paragraph paragrafoJustificado(String arg) {
		Paragraph p = new Paragraph(arg, normal);
		p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		return p;
	}

	/**
	 * Este m�todo gera uma frase no documento utilizando a fonte simples.
	 * Diferente do m�todo par�grafo, ele n�o sofre quebra de linha.
	 * 
	 * @param arg
	 * @return Phrase
	 */
	protected static Phrase frase(String arg) {
		Phrase p = new Phrase(arg, normal);
		return p;
	}

	/**
	 * Este m�todo gera uma frase no documento com a op��o de escolher a fonte.
	 * Diferente do m�todo par�grafo, ele n�o sofre quebra de linha.
	 * 
	 * @param arg
	 * @param font
	 * @return Phrase
	 */
	protected static Phrase frase(String arg, Font font) {
		Phrase p = new Phrase(arg, font);
		return p;
	}

	/**
	 * Este m�todo cria um espa�o entre os par�grafos de acordo com o par�metro
	 * passado do tipo float.
	 * 
	 * @param f
	 * @return Paragraph
	 */
	protected static Paragraph espaco(float f) {
		Paragraph p = new Paragraph();
		p.setSpacingAfter(f);
		return p;
	}

	/**
	 * Retorna um espa�o vertical para ser usado nas frases.
	 * 
	 * @return Paragraph
	 */
	protected static Phrase espaco() {
		Phrase p = new Phrase("                         ");
		return p;
	}

	/**
	 * Este m�todo adiciona uma tabela simples ao documento para centraliz�-lo
	 * sem muitas dificuldades.
	 * 
	 * @param table
	 * @param arg
	 * @param font
	 */
	protected void textoCentralizado(PdfPTable table, String arg, Font font) {
		PdfPCell cell = new PdfPCell(new Paragraph(arg, font));
		cell.setBorder(Rectangle.UNDEFINED);
		cell.setNoWrap(false);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	}

	/**
	 * Este m�todo gera uma tabela simples sem nenhuma borda.
	 * 
	 * @param table
	 * @param arg
	 * @param font
	 */
	protected void tabelaNoBorder(PdfPTable table, String arg, Font font) {
		PdfPCell cell = new PdfPCell(new Paragraph(arg, font));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setNoWrap(false);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
	}

	/**
	 * Este m�todo adiciona uma tabela somente com a linha superior das bordas
	 * ao documento.
	 * 
	 * @param table
	 * @param arg
	 * @param font
	 */
	protected void textoCentralizadoTop(PdfPTable table, String arg, Font font) {
		PdfPCell cell = new PdfPCell(new Paragraph(arg, font));
		cell.setBorder(Rectangle.TOP);
		cell.setNoWrap(false);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	}

	/**
	 * Este m�todo adiciona uma tabela somente com a linha superior das bordas
	 * ao documento alinhado a esquerda.
	 * 
	 * @param table
	 * @param arg
	 * @param font
	 */
	protected void textoEsquerdaTop(PdfPTable table, String arg, Font font) {
		PdfPCell cell = new PdfPCell(new Paragraph(arg, font));
		cell.setBorder(Rectangle.TOP);
		cell.setNoWrap(false);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
	}

	/**
	 * Este m�todo gera uma tabela somente com a linha inferior das bordas ao
	 * documento.
	 * 
	 * @param table
	 * @param arg
	 * @param font
	 */
	protected void tabelaBottom(PdfPTable table, String arg, Font font) {
		PdfPCell cell = new PdfPCell(new Paragraph(arg, font));
		cell.setBorder(Rectangle.BOTTOM);
		cell.setNoWrap(false);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
	}

	/**
	 * Este m�todo gera uma tabela simples com todas as bordas.
	 * 
	 * @param table
	 * @param arg
	 * @param font
	 */
	protected void tabelaBorder(PdfPTable table, String arg, Font font) {
		PdfPCell cell = new PdfPCell(new Paragraph(arg, font));
		cell.setBorder(Rectangle.BOX);
		cell.setNoWrap(false);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
	}

}
