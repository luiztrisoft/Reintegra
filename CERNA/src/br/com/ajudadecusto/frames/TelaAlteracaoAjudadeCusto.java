package br.com.ajudadecusto.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.com.ajudadecusto.metodos.AlteraAjudadeCustoMetodos;
import br.com.ajudadecusto.metodos.BuscaAjudadeCustoMetodos;
import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosAjudadeCusto;

/**
 * Esta classe cria a view de alteração de ajuda de custo. Nela o usuário pode
 * alterar os dados do cadastro como a data dos vencimentos, o valor pago, as
 * observações individuais e gerais, o valor das parcelas e a forma de
 * pagamento.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaAlteracaoAjudadeCusto extends JDialog {

	private short nParcelas;
	private int idAjudadeCusto;
	private ButtonGroup grupo;
	private Container c;
	private JButton botao[];
	private JComboBox<String> combo;
	private JLabel label[];
	private JRadioButton radio[];
	private JTextArea area;
	private JTextField campoValordasParcelas;
	private JTextField campoVencimento[], campoValorPago[], campoObservacoes[];
	private MetodosGenericosAjudadeCusto metodoAjudadeCusto;

	/**
	 * Construtor padrão da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 * @param idAjudadeCusto
	 */
	public TelaAlteracaoAjudadeCusto(JFrame jf, String s, boolean b,
			int idAjudadeCusto) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(660, 480);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		nParcelas = Config.QTD_PARCELAS;
		this.idAjudadeCusto = idAjudadeCusto;

		metodoAjudadeCusto = new MetodosGenericosAjudadeCusto();

		criarTitulo();
		instanciarElementos();
	}

	/**
	 * Este método insere um título no topo da view.
	 */
	private void criarTitulo() {
		JLabel title = new JLabel("Alterar ajuda de custo");
		title.setFont(new Font("Arial", Font.BOLD, 14));
		c.add(title);
		title.setBounds(10, 05, 400, 30);
	}

	/**
	 * Este método instancia os elementos visuais do formulário como os JLabels,
	 * JTextFields etc.
	 */
	private void instanciarElementos() {

		/*
		 * =============================================
		 * 
		 * INSTANCIAR ELEMENTOS
		 * 
		 * Aqui todos os elementos desta view são instanciados e adicionado ao
		 * Container.
		 * 
		 * =============================================
		 */

		// ::::::::Labels::::::::
		String txtLabel[] = { "Vencimento", "Valor pago", "Observações(80)",
				"Valor das parcelas", "Forma de pagamento",
				"Responsável pelo pagamento", "Observação geral(150)" };
		label = new JLabel[7];
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(txtLabel[i]);
			c.add(label[i]);
		}

		// ::::::::campo vencimento::::::::
		campoVencimento = new JTextField[nParcelas];
		for (int i = 0; i < nParcelas; i++) {
			try {
				campoVencimento[i] = new JFormattedTextField(new MaskFormatter(
						"##/##/####"));
				c.add(campoVencimento[i]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// ::::::::campo valor pago::::::::
		campoValorPago = new JTextField[nParcelas];
		for (int i = 0; i < nParcelas; i++) {
			campoValorPago[i] = new JTextField("0.00");
			c.add(campoValorPago[i]);
			campoValorPago[i].setHorizontalAlignment(SwingConstants.RIGHT);
		}

		// ::::::::campo observações::::::::
		campoObservacoes = new JTextField[nParcelas];
		for (int i = 0; i < nParcelas; i++) {
			campoObservacoes[i] = new JTextField();
			c.add(campoObservacoes[i]);
		}

		// ::::::::campo valor das parcelas::::::::
		campoValordasParcelas = new JTextField();
		c.add(campoValordasParcelas);
		campoValordasParcelas.setHorizontalAlignment(SwingConstants.RIGHT);

		// ::::::::forma de pagamento::::::::
		combo = new JComboBox<String>();
		combo = metodoAjudadeCusto.cbFormadePagamento();
		c.add(combo);

		// ::::::::area de observações:::::::::
		area = new JTextArea();
		c.add(area);
		area.setWrapStyleWord(true);
		area.setLineWrap(true);

		// ::::::::responsável pelo pagamento(radioButton)::::::::
		String txtRadio[] = { "Convênio", "Responsável", "Interno" };
		radio = new JRadioButton[3];
		for (int i = 0; i < radio.length; i++) {
			radio[i] = new JRadioButton(txtRadio[i]);
			c.add(radio[i]);
			radio[i].setBackground(new Color(Config.RED, 230, Config.BLUE));
		}

		// ::::::::grupo de radio buttons::::::::
		grupo = new ButtonGroup();
		for (int i = 0; i < radio.length; i++) {
			grupo.add(radio[i]);
		}

		// ::::::::botões::::::::
		String txtBotao[] = { "OK", "Cancelar" };
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
		 * POSICIONAMENTO E FUNÇÕES
		 * 
		 * Nesta parte serão inseridas o posicionamento dos elementos e suas
		 * funções.
		 * 
		 * =============================================
		 */

		int height[] = { 75, 100, 125, 150, 175, 200 };

		// ::::::::vencimentos::::::::
		label[0].setBounds(10, 50, 80, 20);
		for (int i = 0; i < nParcelas; i++) {
			campoVencimento[i].setBounds(10, height[i], 80, 20);
		}

		// ::::::::valor pago::::::::
		label[1].setBounds(100, 50, 80, 20);
		for (int i = 0; i < nParcelas; i++) {
			campoValorPago[i].setBounds(100, height[i], 80, 20);
		}

		// ::::::::observacoes individuais::::::::
		label[2].setBounds(190, 50, 120, 20);
		for (int i = 0; i < nParcelas; i++) {
			campoObservacoes[i].setBounds(190, height[i], 450, 20);
		}

		// ::::::::valor das parcelas::::::::
		label[3].setBounds(10, 240, 90, 20);
		campoValordasParcelas.setBounds(110, 240, 80, 20);

		// ::::::::forma de pagamento::::::::
		label[4].setBounds(220, 240, 110, 20);
		combo.setBounds(330, 240, 310, 20);

		// ::::::::responsável pelo pagamento::::::::
		label[5].setBounds(10, 280, 150, 20);
		radio[0].setBounds(10, 300, 150, 20);
		radio[1].setBounds(10, 320, 150, 20);
		radio[2].setBounds(10, 340, 150, 20);

		// ::::::::observações gerais::::::::
		label[6].setBounds(190, 280, 200, 20);
		area.setBounds(190, 300, 445, 70);

		// ::::::::botões::::::::
		botao[0].setBounds(10, 400, 120, 30);
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarAjudadeCusto();
			}
		});

		botao[1].setBounds(140, 400, 120, 30);
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		getDados();

	}

	/**
	 * Este método é responsável por instanciar a classe
	 * 'BuscaAjudadeCustoMetodos' que possui o método 'getAjudadeCusto' que
	 * serve para apresentar os dados na view.
	 */
	private void getDados() {
		BuscaAjudadeCustoMetodos busca = new BuscaAjudadeCustoMetodos();
		busca.getAjudadeCusto(idAjudadeCusto, campoVencimento, campoValorPago,
				campoObservacoes, campoValordasParcelas, combo, radio, area);
	}

	/**
	 * Este método instancia a classe AlteraAjudadeCustoMetodos para mandar os
	 * dados a serem alterados para a base de dados do sisteam.
	 */
	private void alterarAjudadeCusto() {
		AlteraAjudadeCustoMetodos ajudadeCusto = new AlteraAjudadeCustoMetodos();
		ajudadeCusto.alterar(idAjudadeCusto, campoVencimento, campoValorPago,
				campoObservacoes, campoValordasParcelas, combo, radio, area);
		dispose();
	}

}
