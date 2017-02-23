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
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.controller.Data;
import br.com.controller.Config;

/**
 * Esta classe constrói a tela de seleção de parâmetros em que o usuário
 * seleciona as datas que foram feitos os pedidos e a sua situação de pagamento:
 * Geral, Pago ou Pendente.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaParametros extends JDialog {

	private Container c;
	private JButton botao[];
	private JLabel label[];
	private JTextField campo[];
	private JRadioButton radio[];

	/**
	 * Construtor padrão da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public TelaParametros(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(400, 230);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		screen();
	}

	/**
	 * Este método constrói a parte visual da tela de parâmetro de pedido de
	 * medicamentos.
	 */
	private void screen() {
		final byte QTD_LABELS = 4;
		final byte QTD_CAMPOS = 2;
		final byte QTD_RADIOS = 3;
		final byte QTD_BOTOES = 2;

		Font bold = new Font("Tahoma", Font.BOLD, 12);
		Font plain = new Font("Tahoma", Font.PLAIN, 12);

		// ::::::::Labels::::::::
		String txtLabel[] = { "Selecione as datas:", "Entre", "e",
				"Selecione a situação:" };
		label = new JLabel[QTD_LABELS];
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(txtLabel[i]);
			c.add(label[i]);
			label[i].setFont(bold);
		}

		// ::::::::Campos::::::::
		campo = new JTextField[QTD_CAMPOS];
		for (int i = 0; i < campo.length; i++) {
			try {
				campo[i] = new JFormattedTextField(new MaskFormatter(
						"##/##/####"));
				c.add(campo[i]);
				campo[i].setFont(plain);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		campo[0].setText(Config.dataInicial());
		campo[1].setText(Data.showDate());

		// ::::::::radio::::::::
		radio = new JRadioButton[QTD_RADIOS];
		String txtRadio[] = { "Geral", "Pagos", "Pendentes" };
		ButtonGroup grupo = new ButtonGroup();
		for (int i = 0; i < radio.length; i++) {
			radio[i] = new JRadioButton(txtRadio[i]);
			c.add(radio[i]);
			radio[i].setFont(plain);
			radio[i].setBackground(new Color(Config.RED, Config.GREEN,
					Config.BLUE));
			grupo.add(radio[i]);
		}
		radio[0].setSelected(true);

		// ::::::::Botões::::::::
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
		 * parâmetros
		 * 
		 * =====================================
		 */

		// ::::::::selecione as datas::::::::
		label[0].setBounds(20, 20, 200, 20);

		// ::::::::entre **/**/**** e **/**/****::::::::
		label[1].setBounds(20, 50, 50, 20);
		campo[0].setBounds(70, 50, 80, 20);
		label[2].setBounds(160, 50, 20, 20);
		campo[1].setBounds(180, 50, 80, 20);

		// ::::::::selecione a situação::::::::
		label[3].setBounds(20, 90, 200, 20);

		// ::::::::radio::::::::
		radio[0].setBounds(20, 110, 100, 20);
		radio[1].setBounds(120, 110, 100, 20);
		radio[2].setBounds(240, 110, 100, 20);

		// ::::::::botão::::::::
		botao[0].setBounds(20, 160, 100, 30);
		botao[1].setBounds(130, 160, 120, 30);

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
				telaPedido();
			}
		});

		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Este método gera a tela de pedido de medicamentos. É necessário informar
	 * como parâmetros o intervalo de datas dos pedidos e o status de pagamento
	 * que pode ser geral, pagos e pendentes.
	 */
	private void telaPedido() {
		dispose();
		byte statusPagamento = 0;
		String dataInicial = campo[0].getText();
		String dataFinal = campo[1].getText();

		// ::::::::filtro de status::::::::
		// 0 - Geral | 1 - Pagos | 2 - Pendentes
		if (radio[0].isSelected()) {
			statusPagamento = 0;
		} else if (radio[1].isSelected()) {
			statusPagamento = 1;
		} else if (radio[2].isSelected()) {
			statusPagamento = 2;
		}

		TelaPrincipal tela = new TelaPrincipal(null, Config.tituloJanela, true,statusPagamento,
				dataInicial, dataFinal );
		tela.setVisible(true);

	}

}
