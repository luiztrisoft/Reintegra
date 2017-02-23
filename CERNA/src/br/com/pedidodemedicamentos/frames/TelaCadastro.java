package br.com.pedidodemedicamentos.frames;

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
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.com.controller.Data;
import br.com.controller.Config;
import br.com.dao.InternoDAO;
import br.com.pedidodemedicamentos.metodos.CadastraPedidoMetodos;

/**
 * Esta classe constrói a tela de cadastro de pedido de medicamentos.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaCadastro extends JDialog {

	private Container c;
	private String cpf;

	private JLabel label[];
	private JTextField campo[];
	private JTextArea area;
	private JRadioButton radio[];
	private ButtonGroup grupo;
	private JButton botao[];
	private JScrollPane scroll;

	/**
	 * Método construtor padrão da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 * @param cpf
	 */
	public TelaCadastro(JFrame jf, String s, boolean b, String cpf) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(500, 470);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		this.cpf = cpf;
		screen();
	}

	/**
	 * Este método constrói a parte visual da tela de cadastro de pedido de
	 * medicamentos.
	 */
	private void screen() {
		final byte QTD_RADIOS = 2;
		final byte QTD_BOTOES = 2;
		final byte QTD_CAMPOS = 4;
		final byte QTD_LABELS = 7;

		Font title = new Font("Tahoma", Font.BOLD, 20);
		Font plain = new Font("Tahoma", Font.PLAIN, 12);
		Font bold = new Font("Tahoma", Font.BOLD, 12);

		// ::::::::labels::::::::
		String nomedoInterno = getNomeInterno();
		String[] txtLabel = { nomedoInterno, "Nome do medicamento *",
				"Preço do medicamento *", "Data do pedido *", "Pago",
				"Data de pagamento", "Observações (250)" };
		label = new JLabel[QTD_LABELS];
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(txtLabel[i]);
			c.add(label[i]);
			label[i].setFont(plain);
		}
		label[0].setFont(title);

		// ::::::::Campos::::::::
		campo = new JTextField[QTD_CAMPOS];
		campo[0] = new JTextField();
		campo[1] = new JTextField();
		try {
			MaskFormatter formatoData = new MaskFormatter("##/##/####");
			campo[2] = new JFormattedTextField(formatoData);
			campo[3] = new JFormattedTextField(formatoData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		campo[1].setHorizontalAlignment(SwingConstants.RIGHT);
		campo[1].setText("0,00");
		campo[2].setText(Data.showDate());

		for (int i = 0; i < campo.length; i++) {
			c.add(campo[i]);
			campo[i].setFont(plain);
		}

		// ::::::::radiobutton::::::::
		radio = new JRadioButton[QTD_RADIOS];
		String[] txtRadio = { "Não", "Sim" };
		grupo = new ButtonGroup();
		for (int i = 0; i < radio.length; i++) {
			radio[i] = new JRadioButton(txtRadio[i]);
			c.add(radio[i]);
			radio[i].setFont(plain);
			radio[i].setBackground(new Color(Config.RED, Config.GREEN,
					Config.BLUE));
			grupo.add(radio[i]);
		}
		radio[0].setSelected(true);

		// ::::::::area::::::::
		area = new JTextArea();
		area.setFont(plain);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		scroll = new JScrollPane(area);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		c.add(scroll);

		// ::::::::botoes::::::::
		String txtBotao[] = { "Ok", "Cancelar" };
		String imagePath[] = { "telapedidodemedicamentos/accept.png",
				"telapedidodemedicamentos/cancel.png" };
		botao = new JButton[QTD_BOTOES];
		for (int i = 0; i < botao.length; i++) {
			URL url = resources.Recursos.class.getResource(imagePath[i]);
			botao[i] = new JButton(txtBotao[i], new ImageIcon(url));
			c.add(botao[i]);
			botao[i].setFont(bold);
		}

		/*
		 * =====================================
		 * 
		 * POSICIONAMENTO DOS OBJETOS
		 * 
		 * Estes scripts são responsáveis pela disposição dos itens na tela de
		 * cadastro de pedido de medicamentos
		 * 
		 * =====================================
		 */

		// ::::::::labels::::::::
		label[0].setBounds(10, 20, 350, 20);
		for (int i = 1; i < label.length; i++) {
			label[i].setBounds(10, 40 * (1 + i), 160, 20);
		}

		// ::::::::campos::::::::
		campo[0].setBounds(160, 80, 300, 20);
		campo[1].setBounds(160, 120, 80, 20);
		campo[2].setBounds(160, 160, 80, 20);
		campo[3].setBounds(160, 240, 80, 20);

		// ::::::::radio::::::::
		radio[0].setBounds(160, 200, 50, 20);
		radio[1].setBounds(210, 200, 50, 20);

		// ::::::::observações::::::::
		scroll.setBounds(160, 280, 300, 80);

		// ::::::::botão::::::::
		botao[0].setBounds(160, 380, 100, 30);
		botao[1].setBounds(270, 380, 120, 30);

		/*
		 * =====================================
		 * 
		 * FUNÇÕES DOS BOTÕES
		 * 
		 * Estes scripts são responsáveis pela função dos botões da tela.
		 * 
		 * =====================================
		 */
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastro();
				dispose();
			}
		});

		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}

	/**
	 * Método que se utiliza do método cadastrar da classe CadastraPedidoMetodos
	 * para persistir um novo pedido na base de dados. O método informa os
	 * campos como parâmetros, o cpf do interno e um valor byte que demonstra se
	 * o pedido está pago ou não: 0 - Não | 1 - Sim
	 */
	private void cadastro() {
		byte pago = 0;
		if (radio[0].isSelected()) {
			pago = 0;
		} else if (radio[1].isSelected()) {
			pago = 1;
		}
		CadastraPedidoMetodos funcao = new CadastraPedidoMetodos();
		funcao.cadastrar(cpf, campo, area, pago);
	}

	/**
	 * Através do CPF informado, este método retorna o nome do interno
	 * selecionado para vinculação do pedido de medicamento.
	 * 
	 * @return String
	 */
	private String getNomeInterno() {
		InternoDAO dao = new InternoDAO();
		String nomeInterno = dao.returnNome(cpf);
		return nomeInterno;
	}
}
