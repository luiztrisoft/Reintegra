package br.com.internos.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta classe serve para o usuário escolher o interno ao qual ele irá atribuir
 * um responsável. Após realizar o cadastro do interno haverá uma opção de gerar
 * ou não um responsável a ele, então após selecionar o interno, abre-se a
 * telaCadastroResponsavel para realizar seu registro.
 * 
 * Outra forma é abrindo pelo próprio menu de responsáveis através do também
 * menu de internos.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class SelectInternos extends JDialog {

	private Container c;
	private DefaultTableModel coluna;
	private JButton button;
	private JScrollPane scroll;
	private JTable tabela;
	private MetodosGenericosInterno metodosInterno;

	public SelectInternos(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(570, 530);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		metodosInterno = new MetodosGenericosInterno();
		tabela();
		botao();
	}

	/**
	 * Cria uma tabela com os nomes dos internos para seleção.
	 */
	private void tabela() {
		// :::::::::::: define a tabela::::::::::::
		coluna = new DefaultTableModel();
		coluna.addColumn("CPF/Código");
		coluna.addColumn("Nome do interno");
		tabela = new JTable(coluna);
		tabela.setPreferredScrollableViewportSize(new Dimension(100, 100));
		tabela.getColumnModel().getColumn(0).setPreferredWidth(120);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(445);

		scroll = new JScrollPane(tabela);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		c.add(scroll);
		scroll.setBounds(0, 0, 565, 400);

		metodosInterno.modelarTabela(tabela, 1);
		metodosInterno.tabelaInternos(coluna);
	}

	/**
	 * Cria o botão de OK e atribui uma função a ele.
	 */
	private void botao() {
		// :::::::::::: botao OK ::::::::::::
		button = new JButton("OK");
		c.add(button);
		button.setFont(new Font("Arial", Font.BOLD, 13));
		button.setBounds(225, 450, 100, 30);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
