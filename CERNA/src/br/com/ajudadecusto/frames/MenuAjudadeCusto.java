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
 * Esta classe gera o menu de ajuda de custo que dá acesso a todas as outras
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
	 * COnstrutor padrão da classe.
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

		String txtButtons[] = { "<html><center>Controle<br>e relatórios",
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

		// ::::::::adiciona os botões::::::::
		for (int i = 0; i < qtdBotoes; i++) {
			URL url = resources.Recursos.class.getResource(pathImages[i]);
			botao[i] = new JButton(txtButtons[i], new ImageIcon(url));
			c.add(botao[i]);
			botao[i].setHorizontalTextPosition(SwingConstants.CENTER);
			botao[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			botao[i].setContentAreaFilled(false);
		}

		// ::::::::controle e relatórios::::::::
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
	 * Este método abre a tela de seleção de parâmetros para abrir a tela de
	 * controle de internos e também seus relatórios.
	 */
	private void telaFiltro() {
		dispose();
		TelaFiltroControle tfac = new TelaFiltroControle(null,
				Config.tituloJanela, false);
		tfac.setVisible(true);
	}

	/**
	 * Este método abre a verificação de CPF do interno. Caso exista uma ajuda
	 * de custo na base de dados, o usuário será direcionado para a tela de
	 * cadastro de ajuda de custo, caso contrário, uma mensagem informará que a
	 * ajuda de custo não existe.
	 */
	private void cadastroAjudadeCusto() {
		ControleAjudadeCusto controle = new ControleAjudadeCusto();
		controle.cadastroAjudadeCusto();
	}

	/**
	 * Este método abre a tela para digitar o CPF do interno. Caso exista uma
	 * ajuda de custo na base de dados, o usuário é direcionado a tela de
	 * alteração da ajuda de custo, caso contrário, uma mensagem informará que a
	 * ajuda de custo não existe.
	 */
	private void alteracaoAjudadeCusto() {
		ControleAjudadeCusto controle = new ControleAjudadeCusto();
		controle.alteracaoAjudadeCusto();
	}

	/**
	 * Este método abre a tela para digitar o CPF do interno. Caso exista uma
	 * ajuda de custo na base de dados, o usuário é direcionado a tela de
	 * quitação da ajuda de custo, caso contrário, uma mensagem informará que a
	 * ajuda de custo não existe.
	 */
	private void quitacaoAjudadeCusto() {
		ControleAjudadeCusto controle = new ControleAjudadeCusto();
		controle.quitacaoAjudadeCusto();
	}

	/**
	 * Este método abre a tela para digitar o CPF do interno. Caso exista uma
	 * ajuda de custo na base de dados, o usuário poderá remover a ajuda de
	 * custo que estiver selecionada, caso contrário, uma mensagem informará que
	 * a ajuda de custo não existe.
	 */
	private void remocaoAjudadeCusto() {
		ControleAjudadeCusto controle = new ControleAjudadeCusto();
		controle.removerAjudadeCusto();
	}
}
