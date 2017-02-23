package br.com.controller;

import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Classe que fornece atributis est�ticos necess�rios a aplica��o como t�tulo da
 * janela, altura e largura das janelas etc.
 * 
 * @author Tiko
 * 
 */
public class Config {

	public static String tituloJanela = "Reintegra";
	private static URL urlIcone = resources.Recursos.class
			.getResource("logo/icone.png");
	public static ImageIcon icone = new ImageIcon(urlIcone);

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static final int RED = 207;
	public static final int GREEN = 241;
	public static final int BLUE = 254;

	public static int fonteSimples = Font.PLAIN;
	public static int fonteNegrito = Font.BOLD;
	public static int fonteItalic = Font.ITALIC;

	public static final int tamanhoFonte = 13;

	public static int idUsuarioLogado;
	public static String usuarioLogado;
	public static String loginUsuario;

	public static String system = "System";

	public static long idInterno = 0;
	public static long idResponsavel = 0;

	// :::::::::tipo de sa�da:::::::::
	public static final String VOID = "";
	public static final String SOLICITADA = "Solicitada";
	public static final String ADMINISTRATIVA = "Administrativa";
	public static final String EVASAO = "Evas�o";
	public static final String TERAPEUTICO = "Terap�utico";

	// ::::::::quantidade de parcelas::::::::
	public final static short QTD_PARCELAS = 6;

	// ::::::::diret�rio default de backup::::::::
	private final static String DIRETORIO_IMAGENS = "C:\\CERNA\\imagens";

	public static String getDiretorio() {
		return DIRETORIO_IMAGENS;
	}

	// ::::::::data inicial para campos de data::::::::
	private final static String DATA_INICIAL = "01/01/2000";

	public static String dataInicial() {
		return DATA_INICIAL;
	}

	/*	*//**
	 * Este m�todo informa se a aplica��o funcionar� apenas localmente ou
	 * por rede e compartilhamento.
	 * 
	 * 1 - Localhost
	 * 
	 * 2 - Rede
	 * 
	 * @return byte
	 */
	/*
	 * public final static byte typeConnection() { byte tipo = 1; return tipo; }
	 *//**
	 * Retorna o IP da m�quina servidora do banco de dados.
	 * 
	 * @return String
	 */
	/*
	 * public final static String ipServer() { return "192.168.137.1"; }
	 *//**
	 * Retorna o endere�o da pasta compartilhada
	 * 
	 * @return String
	 */
	/*
	 * public final static String ipPasta() { StringBuilder s = new
	 * StringBuilder(); s.append("\\\\"); s.append(ipServer());
	 * s.append("\\imagens\\"); // return "\\\\" + ipServer() + "\\imagens\\";
	 * return s.toString(); }
	 */

	/**
	 * Este m�todo pega uma String de 11 dig�tos num�ricos e retorna no formato
	 * ddd.ddd.ddd-dd.
	 * 
	 * @param cpf
	 * @return String
	 */
	public static String cpfFormat(String cpf) {
		StringBuilder s = new StringBuilder();
		try {
			String p1 = cpf.substring(0, 3);
			String p2 = cpf.substring(3, 6);
			String p3 = cpf.substring(6, 9);
			String p4 = cpf.substring(9, 11);
			s.append(p1);
			s.append(".");
			s.append(p2);
			s.append(".");
			s.append(p3);
			s.append("-");
			s.append(p4);
			return s.toString();
		} catch (Exception e) {
			Show.erro("O cpf deve conter 11 dig�tos num�ricos.\n"
					+ e.getMessage());
			e.printStackTrace();
		}
		return s.toString();
	}

}
