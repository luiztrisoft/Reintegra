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

import br.com.ajudadecusto.metodos.CadastraAjudadeCustoMetodos;
import br.com.controller.Data;
import br.com.controller.Config;
import br.com.dao.InternoDAO;
import br.com.metodosgenericos.MetodosGenericosAjudadeCusto;

/**
 * Esta classe gera a tela de cadastro de ajuda de custo dos internos. Nela nós
 * informamos o valor de parcela, a data do primeiro vencimento que define os
 * outros cinco sequentes, o responsável pelo pagamento e observações gerais.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaCadastroAjudadeCusto extends JDialog {

	private int idFormaPagamento;
	private ButtonGroup grupo;
	private Container c;
	private InternoDAO internoDAO;
	private JButton botao[];
	private JComboBox<String> combo;
	private JLabel label[];
	private JTextArea area;
	private JTextField campo[];
	private JRadioButton radio[];
	private MetodosGenericosAjudadeCusto metodoAjudadeCusto;
	private String cpf;

	public TelaCadastroAjudadeCusto(JFrame jf, String s, boolean b, String cpf) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(470, 430);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		this.cpf = cpf;

		internoDAO = new InternoDAO();
		metodoAjudadeCusto = new MetodosGenericosAjudadeCusto();

		idFormaPagamento = 1;

		nomeInterno();
		formularioCadastro();
	}

	/**
	 * Este método gera a tela de cadastro da ajuda de custo dos internos. Nesta
	 * tela será informado o valor das parcelas, data do primeiro vencimento, a
	 * forma de pagamento, o responsável pelo pagamento e as observações gerais.
	 */
	private void formularioCadastro() {

		String txtLabel[] = { "Valor das parcelas *", "primeiro vencimento *",
				"Responsável pelo pagamento", "Observações(150 caracteres)",
				"Forma de pagamento" };
		String txtRadio[] = { "Convênio", "Responsável", "Interno" };
		String txtBotao[] = { "Cadastrar", "Cancelar", "" };
		String imagePath[] = { "telaajudadecusto/accept.png",
				"telaajudadecusto/cancel.png", "telaajudadecusto/accept.png" };

		label = new JLabel[5];
		campo = new JTextField[2];
		radio = new JRadioButton[3];
		combo = new JComboBox<String>();
		grupo = new ButtonGroup();
		botao = new JButton[3];

		// ::::::::labels::::::::
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(txtLabel[i]);
			c.add(label[i]);
			label[i].setFont(new Font("Arial", Font.BOLD, 12));
		}

		// ::::::::campos::::::::
		for (int i = 0; i < campo.length; i++) {
			campo[i] = new JTextField();
			c.add(campo[i]);
		}

		// ::::::::radio buttons::::::::
		for (int i = 0; i < radio.length; i++) {
			radio[i] = new JRadioButton(txtRadio[i]);
			c.add(radio[i]);
			grupo.add(radio[i]);
			radio[i].setFont(new Font("Arial", Font.BOLD, 12));
			radio[i].setBackground(new Color(Config.RED, Config.GREEN,
					Config.BLUE));
		}
		radio[0].setSelected(true);

		// ::::::::combo boxes::::::::
		// combo[0] = metodoAjudadeCusto.cbConvenio();
		// combo[1] = metodoAjudadeCusto.cbResponsavel();
		// combo[2] = metodoAjudadeCusto.cbInterno();
		combo = metodoAjudadeCusto.cbFormadePagamento();

		// for (int i = 0; i < combo.length; i++) {
		combo.setFont(new Font("Arial", Font.BOLD, 12));
		c.add(combo);
		// }

		// ::::::::area de texto::::::::
		area = new JTextArea();
		c.add(area);
		area.setWrapStyleWord(true);
		area.setLineWrap(true);

		// ::::::::botões::::::::
		for (int i = 0; i < botao.length; i++) {
			URL url = resources.Recursos.class.getResource(imagePath[i]);
			botao[i] = new JButton(txtBotao[i], new ImageIcon(url));
			c.add(botao[i]);
		}

		// ::::::::mascaras e formatos::::::::
		campo[0].setHorizontalAlignment(SwingConstants.RIGHT);
		campo[0].setText("600,00");
		try {
			campo[1] = new JFormattedTextField(new MaskFormatter("##/##/####"));
			c.add(campo[1]);
			campo[1].setText(Data.showDate(0, 0, 0));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// ::::::::valor da parcela::::::::
		label[0].setBounds(10, 70, 120, 20);
		campo[0].setBounds(140, 70, 65, 20);

		// ::::::::primeiro vencimento::::::::
		label[1].setBounds(245, 70, 130, 20);
		campo[1].setBounds(385, 70, 65, 20);

		// ::::::::forma de pagamento::::::::
		label[4].setBounds(10, 110, 120, 20);
		combo.setBounds(140, 110, 285, 20);
		botao[2].setBounds(430, 110, 20, 20);

		// ::::::::responsavel pelo pagamento::::::::
		label[2].setBounds(10, 160, 250, 20);
		radio[0].setBounds(10, 190, 100, 20);
		radio[1].setBounds(180, 190, 100, 20);
		radio[2].setBounds(380, 190, 100, 20);
		// combo[0].setBounds(110, 150, 345, 20);
		// combo[1].setBounds(110, 180, 345, 20);
		// combo[2].setBounds(110, 210, 345, 20);

		// ::::::::observações::::::::
		label[3].setBounds(10, 240, 200, 20);
		area.setBounds(10, 260, 440, 70);

		// ::::::::botões::::::::
		botao[0].setBounds(10, 350, 130, 30);
		botao[1].setBounds(150, 350, 110, 30);

		// ::::::::funções dos botões::::::::

		// ::::::::botão cadastrar::::::::
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gravar();
			}
		});

		// ::::::::botão cancelar::::::::
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// ::::::::adicionar forma de pagamento::::::::
		botao[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoAjudadeCusto.adicionaFormadePagamento(combo);
			}
		});

		// ::::::::convênio::::::::
		radio[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// combo[0].setEnabled(true);
			}
		});

		// ::::::::responsável::::::::
		radio[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// combo[0].setEnabled(false);
			}
		});

		// ::::::::interno::::::::
		radio[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// combo[0].setEnabled(false);
			}
		});

		// ::::::::forma de pagamento::::::::
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idFormaPagamento = metodoAjudadeCusto.indiceFormaPagto(combo
						.getItemAt(combo.getSelectedIndex()));
			}
		});

	}

	/**
	 * Este método grava a ajuda de custo na base de dados através da classe
	 * ponte CadastraAjudadeCustoMetodos.
	 */
	private void gravar() {
		double vlrParcela = Double.parseDouble(campo[0].getText().replaceAll(
				",", "."));
		String primeiroVencimento = campo[1].getText();
		String observacoes = area.getText();

		new CadastraAjudadeCustoMetodos().cadastrar(cpf, idFormaPagamento,
				vlrParcela, primeiroVencimento, observacoes, radio);

		dispose();
	}

	/**
	 * Este método imprime o nome do internos na tela para informar ao usuário
	 * para quem está criando uma nova ajuda de custo.
	 */
	private void nomeInterno() {
		JLabel texto = new JLabel("Cadastro de ajuda de custo do interno(a) ");
		c.add(texto);
		texto.setBounds(10, 0, 500, 30);
		texto.setFont(new Font("Arial", Font.BOLD, 14));

		JLabel nome = new JLabel(internoDAO.returnNome(cpf));
		c.add(nome);
		nome.setBounds(10, 20, 500, 30);
		nome.setFont(new Font("Arial", Font.BOLD, 14));
	}

}
