package br.com.responsavel.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import br.com.controller.Fonte;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosResponsavel;
import br.com.responsavel.relatorios.RelatorioInternoResponsavel;

/**
 * Esta classe cria a visualização de menu do responsável pelo interno. Ela
 * garante acesso as outras views como cadastro e consulta.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class MenuResponsavel extends JDialog {

	private final int QUANTIDADE_BOTOES = 3;
	private Container c;
	private JButton[] button;
	private MetodosGenericosResponsavel metodoResponsavel;

	public MenuResponsavel(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setBounds(70, 100, 370, 220);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		metodoResponsavel = new MetodosGenericosResponsavel();

		descricaoMenu();
		botoes();

	}

	/**
	 * Adiciona o nome desta entidade no menu.
	 */
	private void descricaoMenu() {
		JLabel labelDescricao = new JLabel("Responsáveis");
		c.add(labelDescricao);
		labelDescricao.setBounds(10, 05, 250, 40);
		Fonte.bold(labelDescricao, 24);
	}

	/**
	 * Método que adiciona os botões no menu de responsáveis.
	 */
	private void botoes() {

		final int HEIGHT = 50;
		final int LARGURA_BOTAO = 100, ALTURA_BOTAO = 120;
		String[] imagens = { "telaresponsavel/users_add.png",
				"telaresponsavel/users_edit.png",
				"telaresponsavel/relatorio.png" };
		String txtButton[] = { "Cadastro", "Consulta",
				"<html><center>Lista<br>de nomes" };
		button = new JButton[QUANTIDADE_BOTOES];

		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			URL url = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(txtButton[i], new ImageIcon(url));
			c.add(button[i]);
			button[i].setHorizontalTextPosition(SwingConstants.CENTER);
			button[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			button[i].setContentAreaFilled(false);
		}

		// ::::::::::::cadastro de responsavel::::::::::::
		button[0].setBounds(20, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String cpf = Show.caixaTexto("Digite o CPF do interno.");
				if (metodoResponsavel.verifyCpf(cpf) == true) {
					long id = metodoResponsavel.returnIdInterno(cpf);
					cadastro(id);
				}
			}
		});

		// ::::::::::::consulta de responsavel::::::::::::
		button[1].setBounds(130, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cpf = Show.caixaTexto("Digite o CPF do interno.");
				if (metodoResponsavel.verifyCpf(cpf) == true) {
					long id = metodoResponsavel.returnIdInterno(cpf);
					consulta(id);
				}
			}
		});

		// ::::::::::::relatorio geral de responsavel::::::::::::
		button[2].setBounds(240, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorio();
			}
		});
	}

	/**
	 * Abre a tela de cadastro de responsáveis
	 */
	private void cadastro(long id) {
		TelaCadastroResponsavel cadastroResponsavel = new TelaCadastroResponsavel(
				null, Config.tituloJanela, true, id);
		cadastroResponsavel.setVisible(true);
	}

	/**
	 * Abre a tela de consulta de responsáveis
	 */
	private void consulta(long id) {
		TelaConsultaResponsavel consultaResponsavel = new TelaConsultaResponsavel(
				null, Config.tituloJanela, true, id);
		consultaResponsavel.setVisible(true);
	}

	/**
	 * Este método serve para gerar o relatório em PDF de internos e
	 * responsáveis.
	 */
	private void relatorio() {
		try {
			RelatorioInternoResponsavel relatorio = new RelatorioInternoResponsavel();
			relatorio.pdf();
		} catch (SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}
}
