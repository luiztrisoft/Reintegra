package br.com.ajudadecusto.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import br.com.ajudadecusto.relatorios.RelatoriodeControle;
import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta classe � a tela em que o usu�rio escolhe os parametros dos JRadioButtons
 * dispon�veis para filtrar os dados e abrir a planilha de ajuda de custo.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaFiltroControle extends JDialog {

	private byte status = 0;
	private byte contribuicao = 0;
	private boolean todaAjudadeCusto = false;
	private byte pagamentoAjudadeCusto = 0;
	private String convenio;

	private ButtonGroup grupo[];
	private Container c;
	private JButton botao[];
	private JComboBox<String> combo;
	private JRadioButton radio[];
	private MetodosGenericosInterno metodoInterno;

	/**
	 * Contrutor padr�o da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public TelaFiltroControle(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setBounds(20, 50, 370, 330);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		metodoInterno = new MetodosGenericosInterno();

		mensagem();
		menu();
	}

	/**
	 * Tela de sele��o de par�metros.
	 */
	private void menu() {
		byte radios = 9, grupos = 4, botoes = 2;
		String txt[] = { "Regulares", "Em d�bito", "Contribuintes", "Isentos",
				"Ativos", "Passivos", "Todos", "Par�metros", "Geral" };
		String txtBotao[] = { "Controle", "Relat�rio" };
		String imagePath[] = { "telaajudadecusto/controle.png",
				"telaajudadecusto/relatorio.png" };

		radio = new JRadioButton[radios];
		combo = metodoInterno.cbConvenio();
		c.add(combo);
		grupo = new ButtonGroup[grupos];
		botao = new JButton[botoes];

		for (int i = 0; i < radios; i++) {
			radio[i] = new JRadioButton(txt[i]);
			c.add(radio[i]);
			radio[i].setBackground(new Color(Config.RED, Config.GREEN,
					Config.BLUE));
		}

		for (int i = 0; i < grupos; i++) {
			grupo[i] = new ButtonGroup();
		}

		for (int i = 0; i < botoes; i++) {
			URL url = resources.Recursos.class.getResource(imagePath[i]);
			botao[i] = new JButton(txtBotao[i], new ImageIcon(url));
			c.add(botao[i]);
		}

		radio[8].setSelected(true);
		radio[2].setSelected(true);
		radio[4].setSelected(true);
		radio[6].setSelected(true);

		grupo[0].add(radio[0]);
		grupo[0].add(radio[1]);
		grupo[0].add(radio[8]);
		grupo[1].add(radio[2]);
		grupo[1].add(radio[3]);
		grupo[2].add(radio[4]);
		grupo[2].add(radio[5]);
		grupo[3].add(radio[6]);
		grupo[3].add(radio[7]);

		// ::::::::todos/parametrizado::::::::
		radio[6].setBounds(55, 60, 125, 20);
		radio[7].setBounds(180, 60, 125, 20);

		// ::::::::ativos/passivos::::::::
		radio[4].setBounds(55, 90, 125, 20);
		radio[5].setBounds(180, 90, 125, 20);

		// ::::::::geral/regular/debito::::::::
		// radio[8].setBounds(55, 150, 80, 20);
		// radio[0].setBounds(145, 150, 100, 20);
		// radio[1].setBounds(245, 150, 125, 20);
		radio[8].setBounds(55, 150, 80, 20);
		radio[1].setBounds(180, 150, 80, 20);

		// ::::::::contribuinte/isento::::::::
		radio[2].setBounds(55, 120, 125, 20);
		radio[3].setBounds(180, 120, 125, 20);

		// ::::::::conv�nio::::::::
		combo.setBounds(55, 180, 260, 20);

		// ::::::::bot�es::::::::
		botao[0].setBounds(55, 230, 120, 35);
		botao[1].setBounds(185, 230, 120, 35);

		/*
		 * ====================================================
		 * 
		 * FUN��ES DE BOT�ES E RADIOBUTTONS
		 * 
		 * Estes scripts servem para realizar a fun��o l�gica da view. Os bot�es
		 * abrem a tabela ou o relat�rio enquanto os radio buttons e o combobox
		 * definem os par�metros de filtro da ajuda de custo do cliente ou ent�o
		 * n�o envia par�metro algum para recuperar todos os registros desta
		 * ajuda de custo.
		 * 
		 * ====================================================
		 */
		bloquearParametros();
		radio[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bloquearParametros();
			}
		});
		radio[7].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liberarParametros();
			}
		});

		// ::::::::controle::::::::
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parametros();
				geraTabela();
			}
		});

		// ::::::::relat�rios::::::::
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parametros();
				geraRelatorio();
			}
		});
	}

	/**
	 * Exibe uma mensagem no topo da tela informando para selecionar os
	 * par�metros para apresenta��o da ajuda de custo
	 */
	private void mensagem() {
		JLabel msg = new JLabel("Selecione os par�metros da ajuda de custo");
		c.add(msg);
		msg.setFont(new Font("Arial", Font.BOLD, 17));
		msg.setBounds(10, 5, 370, 30);
	}

	/**
	 * Este m�todo controla os par�metros que ser�o enviados para a tela de
	 * controle e para o relat�rio de controle de ajudas de custo.
	 */
	private void parametros() {
		status = 0;
		contribuicao = 0;
		todaAjudadeCusto = false;
		pagamentoAjudadeCusto = 0;
		convenio = combo.getSelectedItem().toString();

		// ::::::::arg para verificar vencimento::::::::
		if (radio[8].isSelected()) {
			pagamentoAjudadeCusto = 2;
		} else if (radio[0].isSelected()) {
			pagamentoAjudadeCusto = 1;
		} else if (radio[1].isSelected()) {
			pagamentoAjudadeCusto = 0;
		}

		// ::::::::id da tabela 'contribuicao'::::::::
		if (radio[2].isSelected()) {
			contribuicao = 1;
		} else if (radio[3].isSelected()) {
			contribuicao = 2;
		}

		// ::::::::id da tabela 'status_interno'::::::::
		if (radio[4].isSelected()) {
			status = 1;
		} else if (radio[5].isSelected()) {
			status = 2;
		}

		// ::::::::toda ajuda de custo ou parametrizada::::::::
		if (radio[6].isSelected()) {
			todaAjudadeCusto = true;
		} else if (radio[7].isSelected()) {
			todaAjudadeCusto = false;
		}

	}

	/**
	 * Este m�todo abre a tela controle informando alguns valores como par�metro
	 * de filtragem como a contribui��o e o conv�nio do interno.
	 */
	private void geraTabela() {
		TelaControle tc = new TelaControle(todaAjudadeCusto,
				pagamentoAjudadeCusto, status, contribuicao, convenio);
		tc.setVisible(true);
	}

	/**
	 * Este m�todo gera o relat�rio em PDF de acordo com os par�metros passados
	 * pelo usu�rio.
	 */
	private void geraRelatorio() {
		RelatoriodeControle rc = new RelatoriodeControle();
		rc.createPDF(todaAjudadeCusto, pagamentoAjudadeCusto, status,
				contribuicao, convenio);
	}

	/**
	 * Este m�todo mant�m os bot�es de radio e o combobox de conv�nios
	 * desabilitados para o usu�rio.
	 */
	private void bloquearParametros() {
		radio[0].setEnabled(false);
		radio[1].setEnabled(false);
		radio[2].setEnabled(false);
		radio[3].setEnabled(false);
		radio[4].setEnabled(false);
		radio[5].setEnabled(false);
		radio[8].setEnabled(false);
		combo.setEnabled(false);
	}

	/**
	 * Este m�todo mant�m os bot�es de radio e o combobox de conv�nios
	 * habilitados para o usu�rio.
	 */
	private void liberarParametros() {
		radio[0].setEnabled(true);
		radio[1].setEnabled(true);
		radio[2].setEnabled(true);
		radio[3].setEnabled(true);
		radio[4].setEnabled(true);
		radio[5].setEnabled(true);
		radio[8].setEnabled(true);
		combo.setEnabled(true);
	}
}
