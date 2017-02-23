package br.com.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import br.com.agenda.frame.TelaAgenda;
import br.com.ajudadecusto.frames.MenuAjudadeCusto;
import br.com.controller.Data;
import br.com.controller.Fonte;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.FuncionarioDAO;
import br.com.dao.UsuarioDAO;
import br.com.funcionarios.frames.MenuFuncionario;
import br.com.internos.frames.MenuInterno;
import br.com.itens.frames.TelaSelecaoItem;
import br.com.pedidodemedicamentos.frames.TelaParametros;
import br.com.usuarios.frames.TelaCadastroUsuario;
import br.com.usuarios.frames.TelaDeletaUsuario;
import br.com.usuarios.frames.TelaRedefinirSenha;

/**
 * Esta � a tela principal do sistema que d� acesso a todas as funcionalidades
 * existentes.
 * 
 * @author Luiz Alberto
 * 
 */
public class TelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	private Container c;

	private final int QUANTIDADE_BOTOES = 7;
	private JButton button[];
	private URL urlButton[];

	/**
	 * M�todo construtor padr�o da classe.
	 */
	public TelaPrincipal() {
		super(Config.tituloJanela);
		c = getContentPane();
		c.setLayout(null);
		setSize(Config.WIDTH + 55, Config.HEIGHT);
		setLocationRelativeTo(null);
		setExtendedState(MAXIMIZED_BOTH);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setIconImage(Config.icone.getImage());
		Save.log(Config.usuarioLogado, "Entrou no sistema");

		barraMenu();
		botoes();
		logo();

	}

	/**
	 * Gera os bot�es principais do sistema utilizando arrays. Dentro do la�o o
	 * JButton recebe urlBotoes contendo imagens da String imagens.
	 */
	private void botoes() {
		final int HEIGHT = 0;
		final int LARGURA_BOTAO = 120;
		final int ALTURA_BOTAO = 120;

		button = new JButton[QUANTIDADE_BOTOES];
		urlButton = new URL[QUANTIDADE_BOTOES];

		String txtButton[] = { "Internos", "Funcion�rios", "Livro caixa",
				"Ajuda de Custo", "Medicamentos", "Agenda", "Backup" };

		String[] imagens = { "buttonicon/users.png",
				"buttonicon/business_users.png", "buttonicon/book_edit.png",
				"buttonicon/report.png", "buttonicon/icone_farmacia.png",
				"buttonicon/agenda.png", "buttonicon/backup.png" };

		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			urlButton[i] = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(txtButton[i], new ImageIcon(urlButton[i]));
			c.add(button[i]);
			button[i].setHorizontalTextPosition(SwingConstants.CENTER);
			button[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			button[i].setContentAreaFilled(false);
		}

		// ::::::::internos::::::::
		button[0].setToolTipText("Cadastro de internos");
		button[0].setBounds(0, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menuInteno();
			}
		});

		// ::::::::funcion�rios::::::::
		button[1].setToolTipText("Cadastro de funcion�rios");
		button[1].setBounds(LARGURA_BOTAO * 1, HEIGHT, LARGURA_BOTAO,
				ALTURA_BOTAO);
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menuFuncionario();
			}
		});

		// :::::::: livro caixa::::::::
		button[2].setToolTipText("Registro de entradas e sa�das di�rias");
		button[2].setBounds(LARGURA_BOTAO * 2, HEIGHT, LARGURA_BOTAO,
				ALTURA_BOTAO);
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telaLivroCaixa();
			}
		});

		// ::::::::ajuda de custo::::::::
		button[3].setToolTipText("Controle da ajuda de custo");
		button[3].setBounds(LARGURA_BOTAO * 3, HEIGHT, LARGURA_BOTAO,
				ALTURA_BOTAO);
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				menuAjudaCusto();
			}
		});

		// ::::::::medicamentos::::::::
		button[4].setToolTipText("Pedido de medicamentos");
		button[4].setBounds(LARGURA_BOTAO * 4, HEIGHT, LARGURA_BOTAO,
				ALTURA_BOTAO);
		button[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pedidoMedicamentos();
			}
		});

		// ::::::::agenda::::::::
		button[5].setToolTipText("Registro de lembretes");
		button[5].setBounds(LARGURA_BOTAO * 5, HEIGHT, LARGURA_BOTAO,
				ALTURA_BOTAO);
		button[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				agenda();
			}
		});

		// ::::::::backup::::::::
		button[6].setToolTipText("Backup dos dados do sistema");
		button[6].setBounds(LARGURA_BOTAO * 6, HEIGHT, LARGURA_BOTAO,
				ALTURA_BOTAO);
		button[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backup();
			}
		});

	}

	/**
	 * M�todo que insere o logo do cerna na tela principal.
	 */
	private void logo() {

		JLabel usuario = new JLabel("Usu�rio: " + Config.usuarioLogado + " - "
				+ Data.dateTextFull());
		c.add(usuario);
		usuario.setBounds(10, 120, 1000, 50);
		Fonte.bold(usuario, 16);
		usuario.setBackground(Color.YELLOW);
		usuario.setForeground(Color.GRAY);

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();

		URL url = resources.Recursos.class.getResource("logo/timbre.png");
		JLabel labelLogo = new JLabel(new ImageIcon(url));
		c.add(labelLogo);
		labelLogo.setBounds((d.width / 2) - 260, 50, 500, 600);
	}

	/**
	 * Abre o menu de internos
	 */
	private void menuInteno() {
		MenuInterno menuInterno = new MenuInterno(null, Config.tituloJanela,
				true);
		menuInterno.setVisible(true);
	}

	/**
	 * Abre o menu de funcion�rios.
	 */

	private void menuFuncionario() {
		MenuFuncionario menuFuncionario = new MenuFuncionario(null,
				Config.tituloJanela, true);
		menuFuncionario.setVisible(true);
	}

	/**
	 * Abre a tela de senha do m�dulo de livro caixa. O sistema informa o valor
	 * 1 como par�metro do m�todo. Ele direciona para a tela principal do m�dulo
	 * de livro caixa.
	 */
	private void telaLivroCaixa() {
		byte frame = 1;
		TelaSenha tSenha = new TelaSenha(null, Config.tituloJanela, true, frame);
		tSenha.setVisible(true);
	}

	/**
	 * Abre o menu de ajuda de custo.
	 */
	private void menuAjudaCusto() {
		MenuAjudadeCusto mac = new MenuAjudadeCusto(null, Config.tituloJanela,
				true);
		mac.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela de sele��o de par�metros do pedido de
	 * medicamentos.
	 */
	private void pedidoMedicamentos() {
		TelaParametros tela = new TelaParametros(null, Config.tituloJanela,
				true);
		tela.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela de lembretes do sistema no qual o usu�rio pode
	 * inserir a data e o recado.
	 */
	private void agenda() {
		TelaAgenda tela = new TelaAgenda(null, Config.tituloJanela, true);
		tela.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela de inser��o de senha para entrar no m�dulo de
	 * backup e restaura��o dos dados do sistema.
	 */
	private void backup() {
		byte frame = 3;
		TelaSenha tSenha = new TelaSenha(null, Config.tituloJanela, true, frame);
		tSenha.setVisible(true);
	}

	/**
	 * Este m�todo cria a barra de menus no topo da tela principal do sistema.
	 */
	private void barraMenu() {
		JMenuBar barra = new JMenuBar();
		setJMenuBar(barra);

		final int QTD_ITENS = 4;

		// ::::::::menu::::::::
		JMenu[] menu = new JMenu[QTD_ITENS];
		menu[0] = new JMenu("Configura��es");
		barra.add(menu[0]);

		// ::::::::items do menu::::::::
		String[] txtMenuItem = { "Cadastrar usu�rio", "Deletar Usu�rio",
				"Alterar sua senha", "Remover itens" };
		JMenuItem[] menuItem = new JMenuItem[QTD_ITENS];

		// ::::::::adiciona itens ao menu::::::::
		for (int i = 0; i < QTD_ITENS; i++) {
			menuItem[i] = new JMenuItem(txtMenuItem[i]);
			menu[0].add(menuItem[i]);
		}

		// ::::::::Fun��es::::::::
		// ::::::::cadastro de usu�rios::::::::
		menuItem[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaCadastroUSuario();
			}
		});
		// ::::::::remo��o de usu�rios::::::::
		menuItem[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaDeletaUsuario();
			}
		});

		// ::::::::alterar sua senha::::::::
		menuItem[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redefinirSenha();
			}
		});

		// ::::::::sele��o de itens para remo��o::::::::
		menuItem[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaItens();
			}
		});

	}

	/*
	 * ====================================================
	 * 
	 * FUN��ES DA BARRA DE MENUS
	 * 
	 * Estes m�todos realizam as fun��es da barra de menu como cadastrar e
	 * remover um usu�rio, realizar um backup da base de dados ou remover algum
	 * dos seguintes �tens:
	 * 
	 * categoria_lancamento, convenios, departamento, dependencia e forma_pagto;
	 * 
	 * ====================================================
	 */

	/**
	 * Este m�todo abre a tela cadastro de usu�rios. Primeiro ele transforma o
	 * formato do CPF de 11 digitos no tradicional de 14 com seus pontos(.) e o
	 * tra�o(-).Em seguida verifica se possui 14 dig�tos e por �ltimo verifica
	 * sua exist�ncia no banco de dados.
	 */
	private void telaCadastroUSuario() {
		String cpf = Show.caixaTexto("Digite o CPF do funcion�rio");
		cpf = Config.cpfFormat(cpf);
		FuncionarioDAO dao = new FuncionarioDAO();

		if (cpf.length() == 14) {
			if (dao.existenciaCPF(cpf) == true) {
				int idFuncionario = dao.getId(cpf);
				TelaCadastroUsuario tela = new TelaCadastroUsuario(null,
						"Cadastro de usu�rio", true, idFuncionario);
				tela.setVisible(true);
			} else {
				Show.erro("CPF inv�lido!");
			}
		}
	}

	/**
	 * Este m�todo abre a tela de remo��o de usu�rios
	 */
	private void telaDeletaUsuario() {
		String cpf = Show.caixaTexto("Digite o CPF do funcion�rio");
		cpf = Config.cpfFormat(cpf);
		UsuarioDAO dao = new UsuarioDAO();

		if (cpf.length() == 14) {
			if (dao.existenciaCPF(cpf) == true) {
				int idFuncionario = dao.getId(cpf);
				if (dao.existenciaId(idFuncionario) == true) {
					TelaDeletaUsuario tela = new TelaDeletaUsuario(null,
							"Deletar usu�rio", true, idFuncionario);
					tela.setVisible(true);
				} else {
					Show.alerta("N�o existe usu�rio com este CPF.");
				}
			} else {
				Show.erro("CPF inv�lido!");
			}
		}
	}

	/**
	 * Este m�todo � utilizado para a altera��o da senha de usu�rios.
	 */
	private void redefinirSenha() {
		TelaRedefinirSenha tela = new TelaRedefinirSenha(null, Config.tituloJanela, true);
		tela.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela de sele��o de entidade que o usu�rio quer que
	 * tenha seu item removido. As op��es s�o as seguintes:
	 * 
	 * | 1 - categoria_lancamento | 2 - convenios | 3 - departamento | 4 -
	 * dependencia | 5 - forma_pagto |
	 * 
	 */
	private void telaItens() {
		TelaSelecaoItem screen = new TelaSelecaoItem(null, Config.tituloJanela,
				true);
		screen.setVisible(true);
	}

}
