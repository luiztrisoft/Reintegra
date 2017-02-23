package br.com.livrocaixa.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.com.controller.Data;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.livrocaixa.metodos.CadastraRegistroLivroMetodos;
import br.com.metodosgenericos.MetodosGenericosLivroCaixa;

/**
 * Esta classe � repons�vel por construir a view de sa�da de receitas no livro
 * caixa.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaSaidaLivroCaixa extends JDialog {

	private Container c;
	private JButton botao[];
	private JComboBox<String> combo;
	private JLabel label[];
	private JTextField campo[];
	private MetodosGenericosLivroCaixa metodoLivro;

	/**
	 * Construtor padr�o da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public TelaSaidaLivroCaixa(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(670, 300);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		metodoLivro = new MetodosGenericosLivroCaixa();

		formulario();
	}

	private void formulario() {
		Font fonte = new Font("Tahoma", Font.PLAIN, 12);

		// ::::::::JLabel::::::::
		label = new JLabel[5];
		String txtLabel[] = { "Sa�da no Livro Caixa", "Data", "Categoria",
				"Observa��es", "Valor" };
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(txtLabel[i]);
			c.add(label[i]);
			label[i].setFont(fonte);
		}
		label[0].setFont(new Font("Tahoma", Font.BOLD, 30));

		// ::::::::JTextField::::::::
		campo = new JTextField[5];
		for (int i = 1; i < campo.length; i++) {
			campo[i] = new JTextField();
			c.add(campo[i]);
			campo[i].setFont(fonte);
		}
		campo[2].setText("0,00");
		campo[2].setHorizontalAlignment(SwingConstants.RIGHT);

		// ::::::::JFormattedTextField::::::::
		try {
			campo[0] = new JFormattedTextField(new MaskFormatter("##/##/####"));
			c.add(campo[0]);
			campo[0].setFont(fonte);
			campo[0].setText(Data.showDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// ::::::::JComboBox::::::::
		combo = metodoLivro.cbCategoria();
		c.add(combo);
		combo.setFont(fonte);

		// ::::::::JButton::::::::
		String txtButton[] = { "Adicionar categoria", "Registrar sa�da",
				"Cancelar" };
		String imagePath[] = { "telalivrocaixa/accept.png",
				"telalivrocaixa/accept.png", "telalivrocaixa/cancel.png" };
		botao = new JButton[3];
		for (int i = 0; i < botao.length; i++) {
			URL url = resources.Recursos.class.getResource(imagePath[i]);
			botao[i] = new JButton(txtButton[i], new ImageIcon(url));
			c.add(botao[i]);
			botao[i].setFont(fonte);
		}
		botao[0].setContentAreaFilled(false);

		/*
		 * ==================================================
		 * 
		 * SCRIPT DE POSICIONAMENTO
		 * 
		 * Estes c�digos definem o posicionamento na tela de todos os objetos
		 * instanciados da classe swing.
		 * 
		 * ==================================================
		 */

		// ::::::::Titulo::::::::
		label[0].setBounds(10, 10, 400, 50);

		// ::::::::data::::::::
		label[1].setBounds(10, 80, 70, 20);
		campo[0].setBounds(100, 80, 80, 20);

		// ::::::::categoria::::::::
		label[2].setBounds(10, 110, 70, 20);
		combo.setBounds(100, 110, 250, 20);
		botao[0].setBounds(355, 105, 160, 30);

		// ::::::::observa��es::::::::
		label[3].setBounds(10, 140, 70, 20);
		campo[1].setBounds(100, 140, 550, 20);

		// ::::::::valor::::::::
		label[4].setBounds(10, 170, 70, 20);
		campo[2].setBounds(100, 170, 80, 20);

		// ::::::::confirmar/cancelar::::::::
		botao[1].setBounds(100, 210, 150, 30);
		botao[2].setBounds(255, 210, 100, 30);

		/*
		 * ==================================================
		 * 
		 * SCRIPT DE FUN��ES
		 * 
		 * Estes c�digos definem as fun��es de cada bot�o da view.
		 * 
		 * ==================================================
		 */

		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCategoria();
			}
		});

		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputSaida();
				dispose();
			}
		});

		botao[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}

	/**
	 * Este m�todo serve para adicionar uma categoria de entrada de livro caixa
	 */
	private void addCategoria() {
		metodoLivro.addNovaCategoria(combo);
	}

	/**
	 * Este m�todo serve para registrar a entrada no livro caixa.
	 */
	private void inputSaida() {
		boolean b = Show
				.caixaConfirmacao("<html><h2>Deseja registrar a sa�da no livro caixa?");
		if (b == true) {
			CadastraRegistroLivroMetodos funcao = new CadastraRegistroLivroMetodos();
			funcao.saidaLivroCaixa(campo, combo);
			resetFields();
		}
	}

	/**
	 * Este m�todo volta os campos a seus estados originais.
	 */
	private void resetFields() {
		campo[0].setText(Data.showDate());
		combo.setSelectedIndex(0);
		campo[1].setText("");
		campo[2].setText("0,00");
	}

}
