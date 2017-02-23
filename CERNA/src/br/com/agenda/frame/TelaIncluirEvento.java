package br.com.agenda.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.agenda.metodos.CadastraAgendaMetodos;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.AgendaDAO;

/**
 * Esta classe gera a tela de inclus�o de registros ou eventos na agenda.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaIncluirEvento extends JDialog {

	private Container c;
	private JLabel label[];
	private JTextField campo[];
	private JButton botao;

	/**
	 * M�todo construtor padr�o da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public TelaIncluirEvento(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(800, 140);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		screen();

	}

	/**
	 * Este m�todo constr�i a tela de inclus�o de eventos do m�dulo de agenda.
	 * Aqui existem os campos onde ser�o adicionados a data, a descri��o do
	 * evento e o bot�o de confirma��o.
	 */
	private void screen() {
		final byte NUM_CAMPOS = 2;
		Font bold = new Font("Tahoma", Font.BOLD, 12);
		Font plain = new Font("Tahoma", Font.PLAIN, 12);

		// ::::::::Labels::::::::
		String txtLabels[] = { "Data", "Evento" };
		label = new JLabel[NUM_CAMPOS];
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(txtLabels[i]);
			c.add(label[i]);
			label[i].setFont(bold);
		}

		// ::::::::Campos::::::::
		campo = new JTextField[NUM_CAMPOS];
		try {
			campo[0] = new JFormattedTextField(new MaskFormatter("##/##/####"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		campo[1] = new JTextField();
		for (int i = 0; i < campo.length; i++) {
			c.add(campo[i]);
			campo[i].setFont(plain);
		}

		// ::::::::Bot�es::::::::
		URL url = resources.Recursos.class.getResource("telaagenda/accept.png");
		botao = new JButton("OK", new ImageIcon(url));
		c.add(botao);
		botao.setFont(plain);

		// ::::::::data::::::::
		label[0].setBounds(10, 10, 50, 20);
		campo[0].setBounds(70, 10, 80, 20);

		// ::::::::evento::::::::
		label[1].setBounds(10, 40, 50, 20);
		campo[1].setBounds(70, 40, 700, 20);

		// ::::::::bot�o OK::::::::
		botao.setBounds(70, 70, 80, 25);

		// ::::::::fun��o::::::::
		botao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluirEvento();
			}
		});

	}

	/**
	 * Este m�todo faz uma inst�ncia da classe {@link CadastraAgendaMetodos} que
	 * possui o m�todo cadastrar que envia os dados para a classe
	 * {@link AgendaDAO} para que as informa��es sejam persistidas.
	 */
	private void incluirEvento() {
		boolean confirm = Show
				.caixaConfirmacao("Deseja cadastrar este evento na agenda?");
		if (confirm == true) {
			dispose();
			CadastraAgendaMetodos funcao = new CadastraAgendaMetodos();
			funcao.cadastrar(campo);
		}
	}
}
