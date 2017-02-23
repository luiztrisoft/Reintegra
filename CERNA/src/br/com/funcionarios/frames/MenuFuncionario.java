package br.com.funcionarios.frames;

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

import net.sf.jasperreports.engine.JRException;
import br.com.controller.Fonte;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.funcionarios.relatorios.ListaFuncionarioRelatorio;

/**
 * Esta classe abre uma tela contendo todas as funcionalidades de Funcionários
 * como a tela de cadastro e os relatórios.
 * 
 * @author Luiz Alberto
 * 
 */
public class MenuFuncionario extends JDialog {
	private static final long serialVersionUID = 1L;

	private Container c;

	private JButton[] button;
	private final int QUANTIDADE_BOTOES = 3;

	/**
	 * Contrutor padrão da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public MenuFuncionario(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setBounds(20, 50, 370, 220);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		descricaoMenu();
		botoes();
	}

	/**
	 * Adiciona o nome desta entidade no menu.
	 */
	private void descricaoMenu() {
		JLabel labelDescricao = new JLabel("Funcionários");
		c.add(labelDescricao);
		labelDescricao.setBounds(10, 05, 250, 40);
		Fonte.bold(labelDescricao, 24);
	}

	/**
	 * Método que adiciona os botões no menu de funcionários.
	 */
	private void botoes() {

		final int HEIGHT = 50;
		final int LARGURA_BOTAO = 100, ALTURA_BOTAO = 120;
		String[] imagens = { "telafuncionario/user add.png",
				"telafuncionario/user edit.png",
				"telafuncionario/relatorio.png" };
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

		// cadastro de funcionario
		button[0].setBounds(20, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telaCadastro();
			}
		});

		// consulta de funcionarios
		button[1].setBounds(130, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaConsulta();
			}
		});

		// relatorio de funcionarios
		button[2].setBounds(240, HEIGHT, LARGURA_BOTAO, ALTURA_BOTAO);
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioFuncionario();
			}
		});
	}

	/**
	 * Este método abre as outras tela de cadastro funcionários.
	 */
	private void telaCadastro() {
		TelaCadastroFuncionario tela = new TelaCadastroFuncionario(null,
				"Cadastro de funcionários", true);
		tela.setVisible(true);
	}

	/**
	 * Este método abre a tela de consulta de funcionários, onde pode-se
	 * procurar ou consultar, editar e remover.
	 */
	private void telaConsulta() {
		TelaConsultaFuncionario tela = new TelaConsultaFuncionario(null,
				"Consulta de funcionários", true);
		tela.setVisible(true);
	}

	/**
	 * Método que abre um relatório de todos os funcionários através da classe
	 * ListaFuncionarioRelatorio.
	 */
	private void relatorioFuncionario() {
		String opcoes[] = { "Todos", "Remunerados", "Voluntários", "Cedidos" };
		String caixa = Show.caixaOpcao("Selecione o tipo de funcionário",
				opcoes);

		String status = null;

		if (caixa.equals(opcoes[0])) {
			status = "";
		} else if (caixa.equals(opcoes[1])) {
			status = "Remu";
		} else if (caixa.equals(opcoes[2])) {
			status = "Volu";
		} else if (caixa.equals(opcoes[3])) {
			status = "Cedi";
		}

		try {
			ListaFuncionarioRelatorio listaFuncionarios = new ListaFuncionarioRelatorio();
			listaFuncionarios.gerar(status);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Show.erro(e.getMessage());
		} catch (JRException e) {
			Show.erro(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			Show.erro(e.getMessage());
			e.printStackTrace();
		}
	}
}
