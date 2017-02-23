package br.com.controller;

import javax.swing.JOptionPane;

/**
 * Esta classe exibe mensagens de confirmação, alerta e erro através do
 * JOptionPane.
 * 
 * @author Luiz Alberto
 * 
 */
public class Show {

	/**
	 * Mensagem simples.
	 * 
	 * @param msg
	 */
	public static void simples(String msg) {
		JOptionPane.showMessageDialog(null, msg, Config.tituloJanela,
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Mensagem de informação.
	 * 
	 * @param msg
	 */
	public static void informacao(String msg) {
		JOptionPane.showMessageDialog(null, msg, Config.tituloJanela,
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Mensagem de alerta.
	 * 
	 * @param msg
	 */
	public static void alerta(String msg) {
		JOptionPane.showMessageDialog(null, msg, Config.tituloJanela,
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Mensagem de erro.
	 * 
	 * @param msg
	 */
	public static void erro(String msg) {
		JOptionPane.showMessageDialog(null, msg, Config.tituloJanela,
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Mensagem de questionamento.
	 * 
	 * @param msg
	 */
	public static void questao(String msg) {
		JOptionPane.showMessageDialog(null, msg, Config.tituloJanela,
				JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Este método abre uma caixa para inserir dados, retornando uma String com
	 * o texto inserido na caixa.
	 * 
	 * @param msg
	 * @return String
	 */
	public static String caixaTexto(String msg) {
		String string = JOptionPane.showInputDialog(null, msg,
				Config.tituloJanela, JOptionPane.PLAIN_MESSAGE);
		return string;
	}

	/**
	 * Este método gera uma caixa de opções para o usuário escolher. Além da
	 * mensagem na caixa, é necessário fornecer um array de Strings como
	 * parâmetro, podendo assim obter uma String de retorno.
	 * 
	 * @param message
	 * @param opcoes
	 * @return String
	 */
	public static String caixaOpcao(String message, String[] opcoes) {
		Object opcao = JOptionPane.showInputDialog(null, message,
				Config.tituloJanela, JOptionPane.PLAIN_MESSAGE, null, opcoes,
				opcoes[0]);
		return opcao.toString();
	}

	/**
	 * Este método abre uma caixa de confirmação. Se o usuário clicar em sim ele
	 * retorna 'true', se clicar em não retorna 'false'.
	 * 
	 * @param msg
	 * @return boolean
	 */
	public static boolean caixaConfirmacao(String msg) {
		int opcao = JOptionPane.showConfirmDialog(null, msg,
				Config.tituloJanela, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (opcao == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}
}
