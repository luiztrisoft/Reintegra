package br.com.ajudadecusto.relatorios;

import java.io.FileOutputStream;
import java.util.List;

import br.com.bean.ControleBean;
import br.com.controller.ItextControl;
import br.com.dao.ControleDAO;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Esta classe utiliza o itext para geração do relatório de controle de ajuda de
 * custo.
 * 
 * @author Luiz Alberto
 * 
 */
public class RelatoriodeControle extends ItextControl {

	/**
	 * Este método gera o PDF do relatório de controle de ajuda de custo.
	 * 
	 * 
	 * @param todaAjudadeCusto
	 * @param pagamentoAjudadeCusto
	 * @param status
	 * @param contribuicao
	 * @param convenio
	 */
	public void createPDF(boolean todaAjudadeCusto, byte pagamentoAjudadeCusto,
			byte status, byte contribuicao, String convenio) {
		try {
			Document doc = new Document(PageSize.A4.rotate());
			PdfWriter.getInstance(doc, new FileOutputStream(
					"C:/CERNA/Itext/controle.pdf"));

			// ::::::::imagem do topo::::::::
			Image topoImg = Image
					.getInstance("C:/CERNA/CernaIcon/logoFicha.png");
			topoImg.setAlignment(Element.ALIGN_CENTER);
			topoImg.scaleAbsolute(500, 65);

			// ::::::::mensagem::::::::
			Paragraph mensagem = new Paragraph(
					"Controle geral de ajudas de custo mensais");
			mensagem.setAlignment(Element.ALIGN_CENTER);

			// ::::::::estrutura da tabela::::::::
			float coluna[] = new float[] { 20f, 150f, 50f, 50f, 50f, 50f, 50f,
					50f, 50f, 60f, 100f, 100f };
			PdfPTable tabela = new PdfPTable(coluna.length);
			tabela.setTotalWidth(coluna);
			tabela.setLockedWidth(true);
			tabela.setHorizontalAlignment(Element.ALIGN_CENTER);

			textoCentralizadoTop(tabela, "Id", negrito);
			textoCentralizadoTop(tabela, "Nome", negrito);
			textoCentralizadoTop(tabela, "Entrada", negrito);
			textoCentralizadoTop(tabela, "1º venc.", negrito);
			textoCentralizadoTop(tabela, "2º venc.", negrito);
			textoCentralizadoTop(tabela, "3º venc.", negrito);
			textoCentralizadoTop(tabela, "4º venc.", negrito);
			textoCentralizadoTop(tabela, "5º venc.", negrito);
			textoCentralizadoTop(tabela, "6º venc.", negrito);
			textoCentralizadoTop(tabela, "Forma de pagamento", negrito);
			textoCentralizadoTop(tabela, "Observação", negrito);
			textoCentralizadoTop(tabela, "Responsável pelo pagamento", negrito);

			ControleDAO dao = new ControleDAO();
			List<ControleBean> lista = dao.getControle(todaAjudadeCusto,
					pagamentoAjudadeCusto, status, contribuicao, convenio);

			for (ControleBean controle : lista) {
				tabelaBorder(tabela, controle.getIdAjudadeCusto() + "", normal);
				tabelaBorder(tabela, controle.getNomeInterno(), normal);
				tabelaBorder(tabela, controle.getEntrada().getDate(), normal);
				tabelaBorder(tabela, controle.getV1().getDate(), normal);
				tabelaBorder(tabela, controle.getV2().getDate(), normal);
				tabelaBorder(tabela, controle.getV3().getDate(), normal);
				tabelaBorder(tabela, controle.getV4().getDate(), normal);
				tabelaBorder(tabela, controle.getV5().getDate(), normal);
				tabelaBorder(tabela, controle.getV6().getDate(), normal);
				tabelaBorder(tabela, controle.getFormadePagamento(), normal);
				tabelaBorder(tabela, controle.getObservacaoGeral(), normal);
				tabelaBorder(tabela, controle.getResponsavelPagto(), normal);
			}

			doc.open();
			doc.add(topoImg);
			doc.add(mensagem);
			doc.add(espaco(15));
			doc.add(tabela);
			doc.close();

			try {
				String command = "rundll32 SHELL32.DLL, ShellExec_RunDLL \"C:\\CERNA\\Itext\\controle.pdf\"";
				Runtime.getRuntime().exec(command);
			} catch (Exception excecao) {
				excecao.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
