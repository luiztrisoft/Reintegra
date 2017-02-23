package br.com.internos.relatorios;

import java.io.FileOutputStream;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.controller.CalculoIdade;
import br.com.controller.Data;
import br.com.controller.ItextControl;
import br.com.controller.Show;
import br.com.dao.FuncionarioDAO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FichadeInternacaoPDF extends ItextControl {

	/**
	 * Método que cria a estrutura do documento em PDF.
	 * 
	 * @param campo
	 * @param combo
	 * @param textArea
	 * @param idResponsavel
	 */
	public void createPDF(JTextField campo[], JComboBox<String> combo[],
			JTextArea textArea, int idResponsavel) {

		try {
			// ::::::::novo documento::::::::
			Document doc = new Document(PageSize.A4, 72, 72, 20, 20);
			PdfWriter.getInstance(doc, new FileOutputStream(
					"C:/CERNA/Itext/ficha.pdf"));
			doc.open();

			// ::::::::imagem do topo::::::::
			Image topoImg = Image
					.getInstance("C:/CERNA/CernaIcon/logoFicha.png");
			topoImg.setAlignment(Element.ALIGN_CENTER);
			topoImg.scaleAbsolute(500, 65);

			// ::::::::imagem do rodapé::::::::
			Image rodapeImg = Image
					.getInstance("C:/CERNA/CernaIcon/rodapeFicha.png");
			rodapeImg.setAlignment(Element.ALIGN_CENTER);
			rodapeImg.scaleAbsolute(500, 25);
			rodapeImg.setAbsolutePosition(50, 50);

			/*
			 * ===================================================
			 * 
			 * ESTRUTURA DO DOCUMENTO
			 * 
			 * Os métodos abaixo invocam as quatro fichas de admissão de
			 * internos
			 * 
			 * ===================================================
			 */
			declaracao(doc, campo, combo, topoImg, rodapeImg, idResponsavel);
			termodeResponsabilidade(doc, campo, topoImg, rodapeImg);
			dadosComplementares(doc, campo, combo, textArea, topoImg, rodapeImg);
			termodeCompromisso(doc, campo, combo, topoImg, rodapeImg,
					idResponsavel);

			// ::::::::encerra o documento::::::::
			doc.close();

			try {
				String command = "rundll32 SHELL32.DLL, ShellExec_RunDLL \"C:\\CERNA\\Itext\\ficha.pdf\"";
				Runtime.getRuntime().exec(command);
			} catch (Exception excecao) {
				excecao.printStackTrace();
			}

		} catch (Exception e) {
			Show.erro(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Este método gera a estrutura do termo de compromisso.
	 * 
	 * @param doc
	 * @param campo
	 * @param combo
	 * @param topoImg
	 * @param rodapeImg
	 * @param idResponsavel
	 * @throws DocumentException
	 */
	private void termodeCompromisso(Document doc, JTextField[] campo,
			JComboBox<String>[] combo, Image topoImg, Image rodapeImg,
			int idResponsavel) throws DocumentException {
		doc.add(topoImg);
		doc.add(espaco(15));
		doc.add(titulo("TERMO DE COMPROMISSO"));
		doc.add(espaco(10));

		// if
		// (combo[4].getSelectedItem().toString().equals("00 - Nao conveniado"))
		// {
		// if(combo[4].getSelectedIndex()==0){
		if (idResponsavel > 0) {
			doc.add(frase("Eu ", negrito));
			doc.add(frase(campo[33].getText()));
			doc.add(frase("\nFiliação: Pai: ", negrito));
			doc.add(frase(campo[48].getText()));
			doc.add(frase("\n               Mãe: ", negrito));
			doc.add(frase(campo[49].getText()));
			doc.add(frase("\nNacionalidade: ", negrito));
			doc.add(frase(campo[46].getText()));
			doc.add(frase("\nRG Nº: ", negrito));
			doc.add(frase(campo[34].getText()));
			doc.add(frase("\nCPF Nº: ", negrito));
			doc.add(frase(campo[35].getText()));
			doc.add(frase("\nData de Nascimento: ", negrito));
			doc.add(frase(campo[36].getText()));
			doc.add(frase("\nNatural de: ", negrito));
			doc.add(frase(campo[44].getText()));
			doc.add(espaco());
			doc.add(frase("Estado: ", negrito));
			doc.add(frase(combo[9].getSelectedItem().toString()));
			doc.add(frase("\nResidente À: ", negrito));
			doc.add(frase(campo[37].getText()));

			// ::::::::adicionado posteriormente
			doc.add(espaco());
			doc.add(frase("Bairro: ", negrito));
			doc.add(frase(campo[38].getText()));
			doc.add(frase("\nCidade: ", negrito));
			doc.add(frase(campo[39].getText()));
			doc.add(espaco());
			doc.add(frase("UF: ", negrito));
			doc.add(frase(combo[8].getSelectedItem().toString()));

			doc.add(frase("\nFone: ", negrito));
			doc.add(frase(campo[41].getText()));
			doc.add(espaco());
			doc.add(frase("Celular: ", negrito));
			doc.add(frase(campo[42].getText()));
			doc.add(frase("\nE-mail: ", negrito));
			doc.add(frase(campo[43].getText()));
			doc.add(espaco());
			doc.add(frase("Profissão: ", negrito));
			doc.add(frase(campo[47].getText()));

		} else if (combo[4].getSelectedItem().toString()
				.equals("00 - Nao conveniado")) {
			doc.add(frase("Eu ", negrito));
			doc.add(frase(campo[1].getText()));
			doc.add(frase("\nFiliação: Pai: ", negrito));
			doc.add(frase(campo[54].getText()));
			doc.add(frase("\n               Mãe: ", negrito));
			doc.add(frase(campo[55].getText()));
			doc.add(frase("\nNacionalidade: ", negrito));
			doc.add(frase(campo[12].getText()));
			doc.add(frase("\nRG Nº: ", negrito));
			doc.add(frase(campo[4].getText()));
			doc.add(frase("\nCPF Nº: ", negrito));
			doc.add(frase(campo[3].getText()));
			doc.add(frase("\nData de Nascimento: ", negrito));
			doc.add(frase(campo[2].getText()));
			doc.add(frase("\nNatural de: ", negrito));
			doc.add(frase(campo[10].getText()));
			doc.add(espaco());
			doc.add(frase("Estado: ", negrito));
			doc.add(frase(combo[1].getSelectedItem().toString()));
			doc.add(frase("\nResidente À: ", negrito));
			doc.add(frase(campo[13].getText()));
			doc.add(frase("\nFone ", negrito));
			doc.add(frase(campo[5].getText()));
			doc.add(espaco());
			doc.add(frase("Celular ", negrito));
			// interno sem campo celular
			doc.add(frase("\nE-mail: ", negrito));
			doc.add(frase(campo[43].getText()));
			doc.add(espaco());
			doc.add(frase("Profissão", negrito));
			doc.add(frase(""));

		} else {
			doc.add(frase("Eu ", negrito));
			doc.add(frase(combo[4].getSelectedItem().toString()));
			doc.add(frase("\nFiliação: Pai: ", negrito));
			doc.add(frase("\n               Mãe: ", negrito));
			doc.add(frase("\nNacionalidade: ", negrito));
			doc.add(frase("\nRG Nº: ", negrito));
			doc.add(frase("\nCPF Nº: ", negrito));
			doc.add(frase("\nData de Nascimento: ", negrito));
			doc.add(frase("\nNatural de: ", negrito));
			doc.add(espaco());
			doc.add(frase("Estado: ", negrito));
			doc.add(frase("\nResidente À: ", negrito));
			doc.add(frase("\nFone ", negrito));
			doc.add(espaco());
			doc.add(frase("Celular ", negrito));
			doc.add(frase("\nE-mail: ", negrito));
			doc.add(espaco());
			doc.add(frase("Profissão", negrito));
			doc.add(frase(""));
		}

		doc.add(espaco(15));
		doc.add(frase("  Comprometo-me a contribuir com a "));
		doc.add(frase("COMUNIDADE TERAPÊUTICA NOVA ALIANÇA ", negrito));
		doc.add(frase("para ajuda de custo do recuperando:...............................................................................................................................................\n..................................................................................................assumindo inteira responsabilidade."));
		doc.add(frase("\n  Comprometo-me a contribuir com a quantia de R$..........................................................\npor extenso(..............................................................................................................................................................) por mês, durante o período de internação do recuperando descrito na ficha de internação("));
		doc.add(frase("documento anexo", italico));
		doc.add(frase(")."));
		doc.add(frase(
				"\n  Comprometo-me a quitar com a ajuda de custo na data de vencimento da mesma, estando ciente que se até a data combinada não houver efetivado a contribuição haverá o acréscimo de: R$ 5,00 após 5 dias a contar do vencimento, R$ 15,00 após 10 dias a contar do vencimento e R$ 20,00 após 15 dias a contar do vencimento.",
				negrito));
		doc.add(frase("\n  Caso haja desistência da parte do recuperando, fica o "));
		doc.add(frase("CERNA ", negrito));
		doc.add(frase("descompromissado de devolver a quantia por mim efetuada."));
		doc.add(frase("\n   Estou ciente que esta Comunidade Terapêutica não tem poder de policia e não se responsabiliza por fuga, sendo certo que, neste local, só permanecerão as pessoas que por livre e espontânea vontade tem o desejo de se recuperarem."));
		doc.add(frase("\nContribuirei com a entidade, conforme o combinado, mesmo que não haja um resultado satisfatório, pois estou ciente que, a recuperação, depende em grande parte do interno."));
		doc.add(frase("\nContribuirei com a entidade, conforme o combinado, mesmo que não haja um resultado satisfatório, pois estou ciente que, a recuperação, depende em grande parte do interno."));
		doc.add(frase("\nContribuirei com a entidade, conforme o combinado, com a ajuda de custo sempre no dia..........................de cada mês."));
		doc.add(frase("\n\nPor estar ciente, assino o presente compromisso, para que surtam seus efeitos jurídicos e legais. "));
		doc.add(frase("\n\n"));
		doc.add(paragrafoDireita("Rolim de Moura, " + Data.dateTextLong(),
				normal));
		doc.add(frase("\n\n"));
		doc.add(paragrafoDireita("_________________________________", normal));
		doc.add(paragrafoDireita("ASSINATURA DO RESPONSAVEL", normal));
		doc.add(rodapeImg);

	}

	/**
	 * Este método adiciona os dados complementares ao documento.
	 * 
	 * @param doc
	 * @param campo
	 * @param combo
	 * @param textArea
	 * @param topoImg
	 * @param rodapeImg
	 * @throws DocumentException
	 */
	private void dadosComplementares(Document doc, JTextField[] campo,
			JComboBox<String>[] combo, JTextArea textArea, Image topoImg,
			Image rodapeImg) throws DocumentException {

		doc.add(topoImg);
		doc.add(espaco(50));
		doc.add(titulo("DADOS COMPLEMENTARES"));
		doc.add(espaco(30));
		doc.add(frase("Nome:   ", negrito));
		doc.add(frase(campo[1].getText()));
		doc.add(frase("\nFiliação: Pai: ", negrito));
		doc.add(frase(campo[54].getText()));
		doc.add(frase("\n               Mãe: ", negrito));
		doc.add(frase(campo[55].getText()));
		doc.add(frase("\nData de nasc: ", negrito));
		doc.add(frase(campo[2].getText()));
		doc.add(espaco());
		doc.add(frase("Local de nasc: ", negrito));
		doc.add(frase(campo[10].getText()));
		doc.add(espaco());
		doc.add(frase("UF ", negrito));
		doc.add(frase(combo[1].getSelectedItem().toString()));
		doc.add(frase("\nRG: ", negrito));
		doc.add(frase(campo[4].getText()));
		doc.add(espaco());
		doc.add(frase("CPF: ", negrito));
		doc.add(frase(campo[3].getText()));
		doc.add(frase("\nEndereço: ", negrito));
		doc.add(frase(campo[13].getText()));
		doc.add(frase("\nCidade: ", negrito));
		doc.add(frase(campo[15].getText()));
		doc.add(espaco());
		doc.add(frase("UF ", negrito));
		doc.add(frase(combo[2].getSelectedItem().toString()));
		doc.add(frase("\nFone residencial/celular: ", negrito));
		doc.add(frase(campo[5].getText()));
		doc.add(frase("\nEstado Civil: ", negrito));
		doc.add(frase(combo[10].getSelectedItem().toString()));
		doc.add(frase("\nNome do cônjuge: ", negrito));
		doc.add(frase(campo[7].getText()));
		doc.add(frase("\nEscolaridade: ", negrito));
		doc.add(frase(combo[0].getSelectedItem().toString()));
		doc.add(frase("\nProfissão: ", negrito));
		doc.add(frase(campo[8].getText()));
		doc.add(frase("\nResponde algum tipo de processo criminal: ", negrito));
		doc.add(frase(campo[21].getText()));
		doc.add(frase("\nOnde: ", negrito));
		doc.add(frase(campo[22].getText()));
		doc.add(frase("\nEstá sob ameaça: ", negrito));
		doc.add(frase(combo[6].getSelectedItem().toString()));
		doc.add(frase("\nTipo de vício: ", negrito));
		doc.add(frase(combo[12].getSelectedItem().toString()));
		doc.add(espaco());
		doc.add(frase("a quanto tempo: ", negrito));
		doc.add(frase(campo[25].getText()));
		doc.add(frase("\nMotivo do 1º uso: ", negrito));
		doc.add(frase(campo[26].getText()));
		doc.add(frase("\nContraiu algum tipo de doença: ", negrito));
		doc.add(frase(campo[28].getText()));
		doc.add(espaco());
		doc.add(frase("\nÉ diabético: ", negrito));
		doc.add(frase(combo[7].getSelectedItem().toString()));
		doc.add(frase("\nFaz uso de alguma medicação: ", negrito));
		doc.add(frase(campo[29].getText()));
		doc.add(frase("\nRecomendações médicas: ", negrito));
		doc.add(frase(campo[30].getText()));
		doc.add(frase("\nHistórico do interno: ", negrito));
		doc.add(frase(textArea.getText()));
		doc.add(frase("\nDocumentos entregues: ", negrito));
		doc.add(frase(campo[31].getText()));
		doc.add(espaco(120));
		doc.add(rodapeImg);
		doc.newPage();
	}

	/**
	 * Este método adiciona o termo de responsabilidade ao documento.
	 * 
	 * @param doc
	 * @param campo
	 * @param topoImg
	 * @param rodapeImg
	 * @throws DocumentException
	 */
	private void termodeResponsabilidade(Document doc, JTextField[] campo,
			Image topoImg, Image rodapeImg) throws DocumentException {
		FuncionarioDAO dao = new FuncionarioDAO();
		String nomeCoordenador = dao.buscaCoordenador();

		doc.add(topoImg);
		doc.add(paragrafo("", normal));
		doc.add(espaco(50));
		doc.add(titulo("TERMO DE RESPONSABILIDADE"));
		doc.add(espaco(50));
		doc.add(paragrafo("•  Prometo não usar bebidas alcoólicas", normal));
		doc.add(paragrafo(
				"•  Prometo não fumar cigarro, cachimbo ou outro tipo de drogas.",
				normal));
		doc.add(paragrafo(
				"•  Prometo não provocar brigas, desordens ou discriminar alguém.",
				normal));
		doc.add(paragrafo(
				"•  Prometo cuidar de meus pertences pessoais tais como: peças íntimas, sapatos, roupas etc.",
				normal));
		doc.add(paragrafo(
				"•  Prometo não deixar a casa sem informar a liderança.",
				normal));
		doc.add(paragrafo("  Prometo não furtar.", normal));
		doc.add(paragrafo(
				"•  Prometo não usar aparelhos sonoros, fotos ou objetos que venham lembrar o passado e gabar-se dele.",
				normal));
		doc.add(paragrafo(
				"•  Prometo entregar todos os meus documentos para serem arquivados no escritório.",
				normal));
		doc.add(paragrafo(
				"•  Prometo ser responsável diante de todos os membros da casa, por minha atitude e comportamento.",
				normal));
		doc.add(paragrafo(
				"•  Prometo participar de todas as atividades dispostas no programa da instituição, por exemplo: cultos, estudo, etc.",
				normal));
		doc.add(paragrafo(
				"•  Prometo não sair dos limites determinados pela entidade.",
				normal));
		doc.add(paragrafo(
				"•  Prometo não sair da Casa de Reabilitação, a não ser acompanhado de algum monitor.",
				normal));
		doc.add(paragrafo(
				"•  Estou ciente que, em caso de desistência, o CERNA não se responsabiliza por meus pertences deixados na entidade.",
				normal));
		doc.add(paragrafo("•  Prometo esforçar-me para ser recuperado.",
				normal));
		doc.add(paragrafo(
				"•  Prometo permitir revistarem todos os meus pertences.",
				normal));
		doc.add(espaco(15));
		doc.add(paragrafoEsquerda(
				"LENDO ESTE TERMO, IREI CUMPRI-LO FIELMENTE.", negrito));
		doc.add(espaco(30));
		doc.add(paragrafo("INTERNADO   " + campo[1].getText(), normal));
		doc.add(espaco(10));
		doc.add(paragrafo(
				"ASSINATURA........................................................................................................................................................",
				normal));
		doc.add(espaco(10));
		doc.add(paragrafo("COORDENADOR   " + nomeCoordenador, normal));
		doc.add(espaco(40));
		doc.add(paragrafoDireita("Rolim de Moura " + Data.dateTextLong(),
				normal));
		doc.add(espaco(70));
		doc.add(rodapeImg);
		doc.newPage();

	}

	/**
	 * Método que adiciona o termo 'DECLARAÇÃO' ao documento
	 * 
	 * @param doc
	 * @param campo
	 * @param combo
	 * @param topoImg
	 * @param rodapeImg
	 * @param idResponsavel
	 * @throws DocumentException
	 */
	private void declaracao(Document doc, JTextField[] campo,
			JComboBox<String>[] combo, Image topoImg, Image rodapeImg,
			int idResponsavel) throws DocumentException {

		// ::::::::estrutura do rodapé::::::::
		float coluna[] = new float[] { 200f, 200f };
		PdfPTable tabela = new PdfPTable(coluna.length);
		tabela.setTotalWidth(coluna);
		tabela.setLockedWidth(true);
		tabela.setHorizontalAlignment(Element.ALIGN_CENTER);

		textoCentralizado(tabela, "_______________________________", negrito);
		textoCentralizado(tabela, "_______________________________", negrito);
		textoCentralizado(tabela, "RESPONSAVEL", negrito);
		textoCentralizado(tabela, "INTERNADO", negrito);

		float coluna2[] = new float[] { 200f, 200f };
		PdfPTable tabela2 = new PdfPTable(coluna2.length);
		tabela2.setTotalWidth(coluna2);
		tabela2.setLockedWidth(true);
		tabela2.setHorizontalAlignment(Element.ALIGN_CENTER);
		textoCentralizado(tabela2, "_______________________________", negrito);
		textoCentralizado(tabela2, "_______________________________", negrito);
		textoCentralizado(tabela2, "COORDENADOR - CERNA", negrito);
		textoCentralizado(tabela2, "TESTEMUNHA", negrito);

		// ::::::::adiciona elementos ao documento::::::::
		doc.add(topoImg);
		doc.add(titulo("FICHA DE INTERNAÇÃO Nº.........................................................DATA "
				+ Data.showDate()));
		doc.add(espaco(15));
		doc.add(titulo("DECLARAÇÃO"));
		doc.add(espaco(10));

		// se interno for menor de 18, campos preenchidos com dados do
		// responsável no cabeçalho

		if (CalculoIdade.calculoIdade(campo[2].getText()) < 18) {
			doc.add(frase("Eu ", negrito));
			// se o menor não possuir responsável...
			if (idResponsavel > 0) {
				doc.add(frase(campo[33].getText()));
			} else {
				doc.add(frase(
						" :::::::: IMPORTANTE: O interno é menor e não possui responsável cadastrado ::::::::",
						negrito));
			}
			doc.add(frase("\nnatural de ", negrito));
			doc.add(frase(campo[44].getText()));
			doc.add(frase("\nData nasc.  ", negrito));
			doc.add(frase(campo[36].getText()));
			doc.add(espaco());
			doc.add(frase("RG ", negrito));
			doc.add(frase(campo[34].getText()));
			doc.add(espaco());
			doc.add(frase("CPF ", negrito));
			doc.add(frase(campo[35].getText()));
			doc.add(frase("\nNacionalidade ", negrito));
			doc.add(frase(campo[46].getText()));
			doc.add(espaco());
			doc.add(frase("Profissão ", negrito));
			doc.add(frase(campo[47].getText()));
			doc.add(frase("\nEndereço ", negrito));
			doc.add(frase(campo[37].getText()));
			doc.add(espaco());
			doc.add(frase("Bairro ", negrito));
			doc.add(frase(campo[38].getText()));
			doc.add(frase("\nCidade ", negrito));
			doc.add(frase(campo[39].getText()));
			doc.add(espaco());
			doc.add(espaco());
			doc.add(frase("UF ", negrito));
			doc.add(frase(combo[8].getSelectedItem().toString()));
			doc.add(frase("\nDECLARO ser responsável pelo menor: ", negrito));
			doc.add(frase("\nNome ", negrito));
			doc.add(frase(campo[1].getText()));
			doc.add(frase("\nnatural de ", negrito));
			doc.add(frase(campo[10].getText()));
			doc.add(espaco());
			doc.add(frase("UF  ", negrito));
			doc.add(frase(combo[1].getSelectedItem().toString()));
			doc.add(espaco());
			doc.add(frase("\nData nasc. ", negrito));
			doc.add(frase(campo[2].getText()));
			doc.add(frase("\nNacionalidade ", negrito));
			doc.add(frase(campo[12].getText()));
			doc.add(espaco());
			doc.add(frase("Profissão ", negrito));
			doc.add(frase(campo[8].getText()));

		}
		// se for maior de 18, Dados do interno no cabeçalho
		else {
			doc.add(frase("Eu ", negrito));
			doc.add(frase(campo[1].getText()));
			doc.add(frase("\nnatural de ", negrito));
			doc.add(frase(campo[10].getText()));
			doc.add(frase("\nData nasc.  ", negrito));
			doc.add(frase(campo[2].getText()));
			doc.add(espaco());
			doc.add(frase("RG ", negrito));
			doc.add(frase(campo[4].getText()));
			doc.add(espaco());
			doc.add(frase("CPF ", negrito));
			doc.add(frase(campo[3].getText()));
			doc.add(frase("\nNacionalidade ", negrito));
			doc.add(frase(campo[12].getText()));
			doc.add(espaco());
			doc.add(frase("Profissão ", negrito));
			doc.add(frase(campo[8].getText()));
			doc.add(frase("\nEndereço ", negrito));
			doc.add(frase(campo[13].getText()));
			doc.add(espaco());
			doc.add(frase("Bairro ", negrito));
			doc.add(frase(campo[14].getText()));
			doc.add(frase("\nCidade ", negrito));
			doc.add(frase(campo[15].getText()));
			doc.add(espaco());
			doc.add(espaco());
			doc.add(frase("UF ", negrito));
			doc.add(frase(combo[1].getSelectedItem().toString()));
			doc.add(frase("\nDECLARO ser responsável pelo menor: ", negrito));
			doc.add(frase("\nNome ", negrito));
			// doc.add(frase(campo[1].getText()));
			doc.add(frase("\nnatural de ", negrito));
			// doc.add(frase(campo[10].getText()));
			doc.add(espaco());
			doc.add(frase("UF  ", negrito));
			// doc.add(frase(combo[2].getSelectedItem().toString()));
			doc.add(espaco());
			doc.add(frase("\nData nasc. ", negrito));
			// doc.add(frase(campo[2].getText()));
			doc.add(frase("\nNacionalidade ", negrito));
			// doc.add(frase(campo[12].getText()));
			doc.add(espaco());
			doc.add(frase("Profissão ", negrito));
			// doc.add(frase(campo[8].getText()));
		}

		doc.add(frase("\nDECLARO", negrito));
		doc.add(frase(", sob as penas da Lei:"));
		doc.add(frase("\nI. Que recebi todas as informações e orientações quanto ao tratamento no "));
		doc.add(frase("CERNA - COMUNIDADE TERAPÊUTICA NOVA ALIANÇA - ", negrito));
		doc.add(frase("e nada há de minha parte que se oponha."));
		doc.add(frase("\nII.	Que concordo plenamente em participar do tratamento de recuperação do "));
		doc.add(frase("CERNA", negrito));
		doc.add(frase(", sendo que a permanência é totalmente livre e voluntária e em caso de fuga o desligamento será automático, tomando-se as devidas providências."));
		doc.add(frase("\nIII.	Que as atividades das quais participarei não gerarão vínculo empregatício, pois fazem parte do tratamento."));
		doc.add(frase("\nIV.	Que fica resguardado o direito de desistência sem qualquer tipo de constrangimento a minha pessoa, comprometendo-me a assinar o"));
		doc.add(frase(" TERMO DE DESISTÊNCIA ", negrito));
		doc.add(frase("assim como informar ao responsável pela casa com antecedência de 24 horas para que sejam tomadas as providências cabíveis."));
		doc.add(frase("\nV.	Que reconheço a possibilidade de interrupção do tratamento a qualquer momento, resguardados as exceções de risco imediato para terceiros, intoxicação por SPA (Substância Psicoativa), avaliada por médico responsável."));
		doc.add(frase("\nVI.	Que em caso de estar sendo encaminhado por meio de mandado judicial, submeto-me a todas as normas e regulamentos do "));
		doc.add(frase("CERNA.", negrito));
		doc.add(frase("\nVII.	Que estou ciente que o período de internação para o tratamento é de 180 dias (6 meses), podendo haver prorrogação, dependendo da avaliação e de minha concordância."));
		doc.add(frase("\nVIII.	Que após ler e aprovar, aceito voluntariamente todos os regulamentos e normas do "));
		doc.add(frase("CERNA.\n ", negrito));

		// doc.add(paragrafoJustificado("I.	Que recebi todas as informações e orientações quanto ao tratamento no CERNA - COMUNIDADE TERAPÊUTICA NOVA ALIANÇA - e nada há de minha parte que se oponha."));
		// doc.add(paragrafoJustificado("II.	Que concordo plenamente em participar do tratamento de recuperação do CERNA, sendo que a permanência é totalmente livre e voluntária e em caso de fuga o desligamento será automático, tomando-se as devidas providências."));
		// doc.add(paragrafoJustificado("III.	Que as atividades das quais participarei não gerarão vínculo empregatício, pois fazem parte do tratamento."));
		// doc.add(paragrafoJustificado("IV.	Que fica resguardado o direito de desistência sem qualquer tipo de constrangimento a minha pessoa, comprometendo-me a assinar o TERMO DE DESISTÊNCIA assim como informar ao responsável pela casa com antecedência de 24 horas para que sejam tomadas as providências cabíveis."));
		// doc.add(paragrafoJustificado("V.	Que reconheço a possibilidade de interrupção do tratamento a qualquer momento, resguardados as exceções de risco imediato para terceiros, intoxicação por SPA (Substância Psicoativa), avaliada por médico responsável."));
		// doc.add(paragrafoJustificado("VI.	Que em caso de estar sendo encaminhado por meio de mandado judicial, submeto-me a todas as normas e regulamentos do CERNA."));
		// doc.add(paragrafoJustificado("VII.	Que estou ciente que o período de internação para o tratamento é de 180 dias (6 meses), podendo haver prorrogação, dependendo da avaliação e de minha concordância."));
		// doc.add(paragrafoJustificado("VIII.	Que após ler e aprovar, aceito voluntariamente todos os regulamentos e normas do CERNA."));

		doc.add(frase(
				"Ciente do que li, assino juntamente com o recuperando, dando-lhe total e plena valia.",
				negritoUnderline));
		doc.add(espaco(10));
		doc.add(paragrafoDireita("Rolim de Moura " + Data.dateTextLong(),
				normal));
		doc.add(espaco(20));
		doc.add(tabela);
		doc.add(espaco(15));
		doc.add(tabela2);
		doc.add(espaco(15));
		doc.add(frase("Saída:____/____/________ Motivo:____________________________________________________\n"));
		doc.add(rodapeImg);
		doc.newPage();
	}

}
