package br.com.itens.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import br.com.controller.Show;
import br.com.controller.Config;
import br.com.itens.metodos.ItemManagement;

/**
 * Esta classe � respons�vel por apresentar a view que mostra os itens das
 * entidades que dever�o ser removidas. cada entidade � representada por um
 * valor byte definido no construtor padr�o que representa uma das entidades.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaItens extends JDialog {

	private byte itemId;
	private Container c;
	private ItemManagement funcao;

	private JLabel label;
	private JComboBox<String> combo;
	private JButton botao;

	/**
	 * Construtor padr�o da classe. Este m�todo recebe um valor byte que ir�
	 * definir qual a entidade que ser� mostrada nesta view. Cada valor a seguir
	 * representa sua respectiva entidade no banco de dados.
	 * 
	 * | 1 - categoria_lancamento | 2 - convenios | 3 - departamento | 4 -
	 * dependencia | 5 - forma_pagto |
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 * @param itemId
	 */
	public TelaItens(JFrame jf, String s, boolean b, byte itemId) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(420, 150);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		funcao = new ItemManagement();
		this.itemId = itemId;
		formulario();
	}

	/**
	 * Formul�rio de cadastro de usu�rios.
	 */
	private void formulario() {
		final int ALTURA = 25;
		label = new JLabel("Item");
		c.add(label);
		combo = new JComboBox<>();
		c.add(combo);
		URL url = resources.Recursos.class.getResource("telaitem/remove.png");
		botao = new JButton("Deletar", new ImageIcon(url));
		c.add(botao);

		// ::::::::fonte::::::::
		Font fonte = new Font("Tahoma", Font.PLAIN, 12);
		label.setFont(fonte);
		combo.setFont(fonte);
		botao.setFont(fonte);

		addItem();

		// ::::::::label item::::::::
		label.setBounds(20, 20, 65, ALTURA);

		// ::::::::combo box item::::::::
		combo.setBounds(85, 20, 300, ALTURA);

		// ::::::::bot�o remover::::::::
		botao.setBounds(85, 60, 150, 35);

		botao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeItem();
			}
		});
	}

	/**
	 * Este m�todo define qual tipo de item ser� adicionado ao comboBox desta
	 * classe.
	 */
	private void addItem() {
		if (itemId == 1) {
			funcao.addCategoriaLancamento(combo);
		} else if (itemId == 2) {
			funcao.addConvenios(combo);
		} else if (itemId == 3) {
			funcao.addDepartamento(combo);
		} else if (itemId == 4) {
			funcao.addDependencia(combo);
		} else if (itemId == 5) {
			funcao.addFormaPagto(combo);
		}
	}

	/**
	 * Este m�todo remove o item selecionado no combo box.
	 */
	private void removeItem() {
		boolean b = Show.caixaConfirmacao("Deseja remover o item selecionado?");
		if (b == true) {
			if (itemId == 1) {
				funcao.removecategoriaLancamento(combo);
			} else if (itemId == 2) {
				funcao.removeConvenios(combo);
			} else if (itemId == 3) {
				funcao.removeDepartamento(combo);
			} else if (itemId == 4) {
				funcao.removeDependencia(combo);
			} else if (itemId == 5) {
				funcao.removeFormaPagto(combo);
			}
			updateCombo();
		}
	}

	/**
	 * Este m�todo faz a fun��o de atualiza��o do combo box removendo todos os
	 * seus itens e em seguida invocando o m�todo addItem desta mesma classe.
	 */
	private void updateCombo() {
		combo.removeAllItems();
		addItem();
	}
}
