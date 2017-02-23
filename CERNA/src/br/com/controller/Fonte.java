package br.com.controller;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * Esta classe define um formato de fonte Arial para um componente JLabel, com
 * especificação PLAIN, BOLD ou ITALIC e também seu tamanho.
 * 
 * @author Luiz Alberto
 * 
 */
public class Fonte {

	/**
	 * Define uma fonte plain. É necessário informar o tamanho da fonte pelo
	 * parâmetro size.
	 * 
	 * @param label
	 * @param size
	 * @return JLabel
	 */
	public static JLabel plain(JLabel label, int size) {
		label.setFont(new Font("Arial", Font.PLAIN, size));
		return label;
	}

	/**
	 * Define uma fonte bold. É necessário informar o tamanho da fonte pelo
	 * parâmetro size.
	 * 
	 * @param label
	 * @param size
	 * @return JLabel
	 */
	public static JLabel bold(JLabel label, int size) {
		label.setFont(new Font("Tahoma", Font.BOLD, size));
		return label;
	}

	/**
	 * Define uma fonte italic. É necessário informar o tamanho da fonte pelo
	 * parâmetro size.
	 * 
	 * @param label
	 * @param size
	 * @return JLabel
	 */
	public static JLabel italic(JLabel label, int size) {
		label.setFont(new Font("Arial", Font.ITALIC, size));
		return label;
	}

}
