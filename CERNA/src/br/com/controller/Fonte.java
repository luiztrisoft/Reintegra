package br.com.controller;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * Esta classe define um formato de fonte Arial para um componente JLabel, com
 * especifica��o PLAIN, BOLD ou ITALIC e tamb�m seu tamanho.
 * 
 * @author Luiz Alberto
 * 
 */
public class Fonte {

	/**
	 * Define uma fonte plain. � necess�rio informar o tamanho da fonte pelo
	 * par�metro size.
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
	 * Define uma fonte bold. � necess�rio informar o tamanho da fonte pelo
	 * par�metro size.
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
	 * Define uma fonte italic. � necess�rio informar o tamanho da fonte pelo
	 * par�metro size.
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
