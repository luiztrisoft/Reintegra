package br.com.ajudadecusto.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import br.com.ajudadecusto.metodos.BuscaAjudadeCustoMetodos;
import br.com.ajudadecusto.metodos.QuitaAjudadeCustoMetodos;
import br.com.controller.Show;
import br.com.controller.Config;

/**
 * Esta classe apresenta a view de quita��o das parcelas da ajuda de custo de um
 * interno pr�-selecionado. Neste ambiente o usu�rio pode marcar a parcela a ser
 * quitada, o valor pago e uma observa��o individual.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaQuitacaoAjudadeCusto extends JDialog {

	private short nParcelas;
	private int idAjudadeCusto;
	private Container c;
	private JButton botao[];
	private JCheckBox boxQuitar[];
	private JLabel label[], campoVencimento[];
	private JTextField campoValorPago[], campoObservacoes[];

	/**
	 * Contrutor padr�o da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 * @param idAjudadeCusto
	 */
	public TelaQuitacaoAjudadeCusto(JFrame jf, String s, boolean b,
			int idAjudadeCusto) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(660, 330);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		this.idAjudadeCusto = idAjudadeCusto;
		nParcelas = Config.QTD_PARCELAS;

		criarTitulo();
		instanciarElementos();
	}
	/**
	 * Este m�todo insere um t�tulo no topo da view.
	 */
	private void criarTitulo() {
		JLabel title = new JLabel("Quitar parcela(s) da ajuda de custo");
		title.setFont(new Font("Arial", Font.BOLD, 14));
		c.add(title);
		title.setBounds(10, 05, 400, 30);
	}

	/**
	 * Este m�todo instancia os elementos visuais do formul�rio como os JLabels,
	 * JTextFields etc.
	 */
	private void instanciarElementos() {

		/*
		 * =============================================
		 * 
		 * INSTANCIAR ELEMENTOS
		 * 
		 * Aqui todos os elementos desta view s�o instanciados e adicionado ao
		 * Container.
		 * 
		 * =============================================
		 */

		// ::::::::Labels::::::::
		String txtLabel[] = { "Quitar", "Vencimento", "Valor pago",
				"Observa��es", };
		label = new JLabel[4];
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(txtLabel[i]);
			c.add(label[i]);
		}

		// ::::::::caixa de quita��o(checkbox)::::::::
		boxQuitar = new JCheckBox[nParcelas];
		for (int i = 0; i < nParcelas; i++) {
			boxQuitar[i] = new JCheckBox();
			c.add(boxQuitar[i]);
			boxQuitar[i].setBackground(new Color(Config.RED, Config.GREEN,
					Config.BLUE));
		}

		// ::::::::campo vencimento::::::::
		campoVencimento = new JLabel[nParcelas];
		for (int i = 0; i < nParcelas; i++) {
			campoVencimento[i] = new JLabel("");
			c.add(campoVencimento[i]);
		}

		// ::::::::campo valor pago::::::::
		campoValorPago = new JTextField[nParcelas];
		for (int i = 0; i < nParcelas; i++) {
			campoValorPago[i] = new JTextField();
			c.add(campoValorPago[i]);
			campoValorPago[i].setHorizontalAlignment(SwingConstants.RIGHT);
		}

		// ::::::::campo observa��es::::::::
		campoObservacoes = new JTextField[nParcelas];
		for (int i = 0; i < nParcelas; i++) {
			campoObservacoes[i] = new JTextField();
			c.add(campoObservacoes[i]);
		}

		// ::::::::bot�es::::::::
		String txtBotao[] = { "Quitar parcela(s)", "Cancelar" };
		String imagePath[] = { "telaajudadecusto/accept.png",
				"telaajudadecusto/cancel.png", "telaajudadecusto/accept.png" };
		botao = new JButton[2];
		for (int i = 0; i < botao.length; i++) {
			URL url = resources.Recursos.class.getResource(imagePath[i]);
			botao[i] = new JButton(txtBotao[i], new ImageIcon(url));
			c.add(botao[i]);
		}

		/*
		 * =============================================
		 * 
		 * POSICIONAMENTO E FUN��ES
		 * 
		 * Nesta parte ser�o inseridas o posicionamento dos elementos e suas
		 * fun��es.
		 * 
		 * =============================================
		 */

		int height[] = { 75, 100, 125, 150, 175, 200 };

		// ::::::::box quitar::::::::
		label[0].setBounds(10, 50, 80, 20);
		for (int i = 0; i < nParcelas; i++) {
			boxQuitar[i].setBounds(15, height[i], 20, 20);
		}

		// ::::::::vencimentos::::::::
		label[1].setBounds(50, 50, 80, 20);
		for (int i = 0; i < nParcelas; i++) {
			campoVencimento[i].setBounds(50, height[i], 80, 20);
		}

		// ::::::::valor pago::::::::
		label[2].setBounds(120, 50, 80, 20);
		for (int i = 0; i < nParcelas; i++) {
			campoValorPago[i].setBounds(120, height[i], 60, 20);
		}

		// ::::::::observacoes individuais::::::::
		label[3].setBounds(185, 50, 80, 20);
		for (int i = 0; i < nParcelas; i++) {
			campoObservacoes[i].setBounds(185, height[i], 450, 20);
		}

		// ::::::::bot�es::::::::
		botao[0].setBounds(350, 250, 150, 30);
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmarQuitacao();
			}
		});

		botao[1].setBounds(510, 250, 120, 30);
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		getDados();

	}

	/**
	 * Este m�todo � respons�vel por instanciar a classe
	 * 'BuscaAjudadeCustoMetodos' que possui o m�todo 'getAjudadeCusto' que
	 * serve para apresentar os dados na view.
	 */
	private void getDados() {
		BuscaAjudadeCustoMetodos busca = new BuscaAjudadeCustoMetodos();
		busca.getAjudadeCusto(idAjudadeCusto, campoVencimento, boxQuitar,
				campoValorPago, campoObservacoes);
	}

	/**
	 * Este m�todo verifica se o usu�rio quer realmente quitar a parcela ou
	 * parcelas selecionadas. Caso a resposta seja positiva, as parcelas
	 * selecionadas ser�o marcadas como pagas na base de dados.
	 */
	private void confirmarQuitacao() {
		boolean quitar = Show
				.caixaConfirmacao("<html><h3>Deseja quitar a(s) parcela(s) selecionada(s)?");
		if (quitar == true) {
			quitar();
		}
	}

	/**
	 * Este m�todo invoca a classe QuitaAjudadeCustoMetodos para registrar as
	 * parcelas que foram quitadas.
	 */
	private void quitar() {
		QuitaAjudadeCustoMetodos funcao = new QuitaAjudadeCustoMetodos();
		funcao.quitar(idAjudadeCusto, boxQuitar, campoValorPago,
				campoObservacoes);
		dispose();
	}
}
