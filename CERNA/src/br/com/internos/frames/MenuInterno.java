package br.com.internos.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import br.com.controller.Fonte;
import br.com.controller.Config;
import br.com.responsavel.frames.MenuResponsavel;

@SuppressWarnings("serial")
public class MenuInterno extends JDialog {

	private Container c;

	private JButton[] button;
	private final int QUANTIDADE_BOTOES = 5;

	public MenuInterno(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setBounds(20, 50, 370, 350);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		descricaoMenu();
		botoes();

	}

	/**
	 * Adiciona o nome desta entidade no menu.
	 */
	private void descricaoMenu() {
		JLabel labelDescricao = new JLabel("Internos");
		c.add(labelDescricao);
		labelDescricao.setBounds(10, 05, 250, 40);
		Fonte.bold(labelDescricao, 24);
	}

	/**
	 * Método que adiciona os botões no menu de internos.
	 */
	private void botoes() {

		final int HEIGHT = 50, HEIGHT2 = 180;
		final int LARGURA_BOTAO = 120, ALTURA_BOTAO = 120;
		String[] imagens = { "telainterno/users_add.png",
				"telainterno/users_edit.png", "telainterno/responsavel.png",
				"telainterno/relatorio.png", "telainterno/chart.png" };
		String txtButtons[] = { "Cadastro", "Consulta", "Responsável",
				"Relatórios", "Gráficos" };

		button = new JButton[QUANTIDADE_BOTOES];

		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			URL url = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(txtButtons[i], new ImageIcon(url));
			c.add(button[i]);
			button[i].setHorizontalTextPosition(SwingConstants.CENTER);
			button[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			button[i].setContentAreaFilled(false);
		}

		// ::::::::::::cadastro de internos::::::::::::
		button[0].setBounds(20, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telaCadastro();
			}
		});

		// ::::::::::::consulta de internos::::::::::::
		button[1].setBounds(130, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaConsulta();
			}
		});

		// ::::::::::::menu de responsáveis::::::::::::
		button[2].setBounds(240, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuResponsavel();
			}
		});

		// ::::::::::::relatorio geral de internos::::::::::::
		button[3].setBounds(20, HEIGHT2, LARGURA_BOTAO, ALTURA_BOTAO);
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuRelatorios();
			}
		});

		// ::::::::::::Gráfico de internos::::::::::::
		button[4].setBounds(130, HEIGHT2, LARGURA_BOTAO, ALTURA_BOTAO);
		button[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MenuGraficos();
			}
		});
	}

	/**
	 * Método que abre a tela de consulta do interno sendo possível verificar e
	 * alterar seus dados.
	 */
	private void telaConsulta() {
		TelaConsultaInterno consultaInterno = new TelaConsultaInterno(null,
				Config.tituloJanela, true);
		consultaInterno.setVisible(true);
	}

	/**
	 * Método que abre a tela de cadastro para novo registro de internos.
	 */
	private void telaCadastro() {
		TelaCadastroInterno cadastroInterno = new TelaCadastroInterno(null,
				Config.tituloJanela, true);
		cadastroInterno.setVisible(true);
	}

	/**
	 * Este método abre o menu de opções do responsável de internos.
	 */
	private void menuResponsavel() {
		MenuResponsavel responsavel = new MenuResponsavel(null,
				Config.tituloJanela, true);
		responsavel.setVisible(true);
	}

	/**
	 * Este método abre o menu que disponibiliza os relatórios de internos
	 */
	private void menuRelatorios() {
		MenuRelatorios relatorios = new MenuRelatorios(null,
				Config.tituloJanela, true);
		relatorios.setVisible(true);
	}

}
