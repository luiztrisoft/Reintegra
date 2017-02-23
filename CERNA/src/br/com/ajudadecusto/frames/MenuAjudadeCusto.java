package br.com.ajudadecusto.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import br.com.ajudadecusto.controle.ControleAjudadeCusto;
import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta classe gera o menu de ajuda de custo que d� acesso a todas as outras
 * funcionalidades de ajuda de custo.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class MenuAjudadeCusto extends JDialog {

	private Container c;
	private JButton botao[];

	/**
	 * COnstrutor padr�o da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public MenuAjudadeCusto(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setBounds(20, 50, 720, 250);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		menu();
	}

	private void menu() {
		final int HEIGHT = 50, LARGURA_BOTAO = 120, ALTURA_BOTAO = 120;
		byte qtdBotoes = 6;

		String txtButtons[] = { "<html><center>Controle<br>e relat�rios",
				"<html><center>Cadastrar ajuda<br>de custo",
				"<html><center>Alterar ajuda<br>de custo", "Quitar parcela",
				"<html><center>Remover ajuda<br>de custo",
				"<html><center>Consultar<br>CPF" };
		String pathImages[] = { "telaajudadecusto/folder_full.png",
				"telaajudadecusto/notes_add.png",
				"telaajudadecusto/notes_edit.png",
				"telaajudadecusto/register.png",
				"telaajudadecusto/notes_delete.png",
				"telaajudadecusto/info.png" };

		botao = new JButton[qtdBotoes];

		// ::::::::adiciona os bot�es::::::::
		for (int i = 0; i < qtdBotoes; i++) {
			URL url = resources.Recursos.class.getResource(pathImages[i]);
			botao[i] = new JButton(txtButtons[i], new ImageIcon(url));
			c.add(botao[i]);
			botao[i].setHorizontalTextPosition(SwingConstants.CENTER);
			botao[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			botao[i].setContentAreaFilled(false);
		}

		// ::::::::controle e relat�rios::::::::
		botao[0].setBounds(20, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// dispose();
				telaFiltro();
			}
		});

		// ::::::::cadastrar ajuda de custo::::::::
		botao[1].setBounds(130, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastroAjudadeCusto();
			}
		});

		// ::::::::alterar ajuda de custo::::::::
		botao[2].setBounds(240, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		botao[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alteracaoAjudadeCusto();
			}
		});

		// ::::::::quitar parcela::::::::
		botao[3].setBounds(350, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		botao[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitacaoAjudadeCusto();
			}
		});

		// ::::::::remover parcela::::::::
		botao[4].setBounds(460, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		botao[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remocaoAjudadeCusto();
			}
		});

		// ::::::::consultar CPF::::::::
		botao[5].setBounds(570, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		botao[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MetodosGenericosInterno metodoInterno = new MetodosGenericosInterno();
				metodoInterno.tabelaCPFs();
			}
		});
	}

	/**
	 * Este m�todo abre a tela de sele��o de par�metros para abrir a tela de
	 * controle de internos e tamb�m seus relat�rios.
	 */
	private void telaFiltro() {
		dispose();
		TelaFiltroControle tfac = new TelaFiltroControle(null,
				Config.tituloJanela, false);
		tfac.setVisible(true);
	}

	/**
	 * Este m�todo abre a verifica��o de CPF do interno. Caso exista uma ajuda
	 * de custo na base de dados, o usu�rio ser� direcionado para a tela de
	 * cadastro de ajuda de custo, caso contr�rio, uma mensagem informar� que a
	 * ajuda de custo n�o existe.
	 */
	private void cadastroAjudadeCusto() {
		ControleAjudadeCusto controle = new ControleAjudadeCusto();
		controle.cadastroAjudadeCusto();
	}

	/**
	 * Este m�todo abre a tela para digitar o CPF do interno. Caso exista uma
	 * ajuda de custo na base de dados, o usu�rio � direcionado a tela de
	 * altera��o da ajuda de custo, caso contr�rio, uma mensagem informar� que a
	 * ajuda de custo n�o existe.
	 */
	private void alteracaoAjudadeCusto() {
		ControleAjudadeCusto controle = new ControleAjudadeCusto();
		controle.alteracaoAjudadeCusto();
	}

	/**
	 * Este m�todo abre a tela para digitar o CPF do interno. Caso exista uma
	 * ajuda de custo na base de dados, o usu�rio � direcionado a tela de
	 * quita��o da ajuda de custo, caso contr�rio, uma mensagem informar� que a
	 * ajuda de custo n�o existe.
	 */
	private void quitacaoAjudadeCusto() {
		ControleAjudadeCusto controle = new ControleAjudadeCusto();
		controle.quitacaoAjudadeCusto();
	}

	/**
	 * Este m�todo abre a tela para digitar o CPF do interno. Caso exista uma
	 * ajuda de custo na base de dados, o usu�rio poder� remover a ajuda de
	 * custo que estiver selecionada, caso contr�rio, uma mensagem informar� que
	 * a ajuda de custo n�o existe.
	 */
	private void remocaoAjudadeCusto() {
		ControleAjudadeCusto controle = new ControleAjudadeCusto();
		controle.removerAjudadeCusto();
	}
}
