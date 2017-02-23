package br.com.internos.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import br.com.controller.Data;
import br.com.controller.Fonte;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.internos.relatorios.RelatoriodeAdmissoes;
import br.com.internos.relatorios.RelatoriodeAltas;
import br.com.internos.relatorios.RelatoriodeInternosPDF;

/**
 * Esta classe gera a tela de escolha do tipo de relatório que o usuário
 * necessite.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class MenuRelatorios extends JDialog {

	// private int status = 1;
	private final String ATIVO = "Ativo";
	private final String PASSIVO = "Passivo";
	private ButtonGroup grupo, grupo2;
	private Container c;
	private JButton botao;
	private JComboBox<String> comboStatus;
	private JComboBox<String> combo[], combo2[];
	private JComboBox<Integer> comboInt, comboInt2;
	private JLabel label[];
	private JPanel admissao, alta, interno;
	private JRadioButton radio[], radio2[];
	private JTabbedPane painelAbas;
	private JTextField campo[];

	public MenuRelatorios(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setBounds(20, 50, 370, 380);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		painelAbas = new JTabbedPane();

		descricaoMenu();
		painelAbas();
	}

	/**
	 * Adiciona a descrição 'Relatórios' no topo deste menu
	 */
	private void descricaoMenu() {
		JLabel labelDescricao = new JLabel("Relatórios");
		c.add(labelDescricao);
		labelDescricao.setBounds(10, 05, 250, 40);
		Fonte.bold(labelDescricao, 24);
	}

	/**
	 * Cria uma estrutura para criação de um grupos de abas.
	 */
	private void painelAbas() {

		interno = new JPanel();
		alta = new JPanel();
		admissao = new JPanel();

		String title[] = { "Internos", "Admissões", "Altas" };

		painelAbas.addTab(title[0], interno);
		painelAbas.addTab(title[1], admissao);
		painelAbas.addTab(title[2], alta);
		c.add(painelAbas);
		painelAbas.setBounds(10, 60, 340, 270);

		abaInternos();
		abaAltas();

		abaAdmissoes();

	}

	/**
	 * Cria a aba de geração de relatório dos internos.
	 */
	private void abaInternos() {
		int qtdLabel = 5;
		int qtdCampo = 4;
		String txtLabel[] = { "Nome", "Dependência", "Idade entre", "e",
				"Status" };

		interno.setLayout(null);
		interno.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));

		label = new JLabel[qtdLabel];
		campo = new JTextField[qtdCampo];

		for (int i = 0; i < qtdLabel; i++) {
			label[i] = new JLabel(txtLabel[i]);
			interno.add(label[i]);
		}

		for (int i = 0; i < qtdCampo; i++) {
			campo[i] = new JTextField();
			interno.add(campo[i]);
		}

		comboStatus = new JComboBox<String>();
		comboStatus = new JComboBox<String>();
		interno.add(comboStatus);
		comboStatus.addItem(ATIVO);
		comboStatus.addItem(PASSIVO);

		botao = new JButton("Gerar relatório");
		interno.add(botao);

		// ::::::::nome::::::::
		label[0].setBounds(10, 20, 50, 20);
		campo[0].setBounds(90, 20, 230, 20);

		// ::::::::dependencia::::::::
		label[1].setBounds(10, 50, 70, 20);
		campo[1].setBounds(90, 50, 230, 20);

		// ::::::::idade min::::::::
		label[2].setBounds(10, 80, 70, 20);
		campo[2].setBounds(90, 80, 30, 20);
		campo[2].setText("0");

		// ::::::::idade max::::::::
		label[3].setBounds(130, 80, 20, 20);
		campo[3].setBounds(150, 80, 30, 20);
		campo[3].setText("100");

		// ::::::::status::::::::
		label[4].setBounds(10, 110, 50, 20);
		comboStatus.setBounds(90, 110, 150, 20);

		// ::::::::botao gerar relatório::::::::
		botao.setBounds(90, 170, 120, 30);

		// ::::::::executar o relatório::::::::
		botao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if (comboStatus.getSelectedItem().toString().equals(ATIVO)) {
						gerarInternos(campo, 1);
					} else if (comboStatus.getSelectedItem().toString()
							.equals(PASSIVO)) {
						gerarInternos(campo, 2);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});
	}

	private void gerarInternos(JTextField[] campo, int status) {
		String metodoModificado = null;
		try {
			new RelatoriodeInternosPDF().executarRelatorio(campo[0].getText(),
					campo[1].getText(), campo[2].getText(), campo[3].getText(),
					status);

		//	 new teste.RelatoriodeInternosPDF().createPDF(campo[0].getText(),
		//	 campo[1].getText(), campo[2].getText(), campo[3].getText(),
		//	 status);

			Save.log(Config.usuarioLogado, "Solicitou o relatório de internos");
		} catch (Exception e1) {
			e1.printStackTrace();
			Show.erro(e1.getMessage());
		}
	}

	/**
	 * Cria a aba de geração de relatório de admissões.
	 */

	@SuppressWarnings("unchecked")
	private void abaAdmissoes() {
		int qtdLabel = 3;
		int qtdRadio = 3;
		int qtdCombo = 2;

		String txtLabel[] = { "Mês", "Ano", "Ordem" };

		admissao.setLayout(null);
		admissao.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));

		radio2 = new JRadioButton[3];
		radio2[0] = new JRadioButton("Geral");
		radio2[1] = new JRadioButton("Anual");
		radio2[2] = new JRadioButton("Mensal");
		radio2[0].setSelected(true);

		grupo2 = new ButtonGroup();
		for (int i = 0; i < qtdRadio; i++) {
			grupo2.add(radio2[i]);
			admissao.add(radio2[i]);
			radio2[i].setBackground(new Color(Config.RED, Config.GREEN,
					Config.BLUE));
		}

		label = new JLabel[qtdLabel];
		combo2 = new JComboBox[qtdLabel];
		comboInt2 = new JComboBox<Integer>();

		for (int i = 0; i < qtdLabel; i++) {
			label[i] = new JLabel(txtLabel[i]);
			admissao.add(label[i]);

		}

		for (int i = 0; i < qtdCombo; i++) {
			combo2[i] = new JComboBox<String>();
			admissao.add(combo2[i]);
		}

		String[] meses = meses();
		for (int i = 0; i < meses.length; i++) {
			combo2[0].addItem(meses[i]);
		}

		for (int i = 1950; i <= Data.year(); i++) {
			comboInt2.addItem(i);
		}
		comboInt2.setSelectedItem(Data.year());
		admissao.add(comboInt2);

		String[] ordem = ordem();
		for (int i = 0; i < ordem.length; i++) {
			combo2[1].addItem(ordem[i]);
		}

		botao = new JButton("Gerar relatório");
		admissao.add(botao);

		// ::::::::radios::::::::
		radio2[0].setBounds(10, 20, 100, 20);
		radio2[1].setBounds(110, 20, 100, 20);
		radio2[2].setBounds(220, 20, 100, 20);

		// ::::::::mês::::::::
		label[0].setBounds(10, 70, 50, 20);
		combo2[0].setBounds(60, 70, 100, 20);
		combo2[0].setEnabled(false);

		// ::::::::ano::::::::
		label[1].setBounds(10, 100, 50, 20);
		comboInt2.setBounds(60, 100, 50, 20);
		comboInt2.setEnabled(false);

		// ::::::::tipo de ordenação::::::::
		label[2].setBounds(10, 130, 50, 20);
		combo2[1].setBounds(60, 130, 130, 20);

		// ::::::::botao gerar relatório::::::::
		botao.setBounds(60, 180, 120, 30);

		// :::::::::Funções::::::::
		// :::::::::geral::::::::
		radio2[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo2[0].setEnabled(false);
				comboInt2.setEnabled(false);
			}
		});

		// :::::::::anual::::::::
		radio2[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo2[0].setEnabled(false);
				comboInt2.setEnabled(true);
			}
		});

		// :::::::::mensal::::::::
		radio2[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo2[0].setEnabled(true);
				comboInt2.setEnabled(true);
			}
		});

		// :::::::::executar o relatório::::::::
		botao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mes = "";

				if (combo2[0].getSelectedItem().toString().equals("Janeiro")) {
					mes = "1";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Fevereiro")) {
					mes = "2";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Março")) {
					mes = "3";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Abril")) {
					mes = "4";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Maio")) {
					mes = "5";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Junho")) {
					mes = "6";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Julho")) {
					mes = "7";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Agosto")) {
					mes = "8";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Setembro")) {
					mes = "9";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Outubro")) {
					mes = "10";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Novembro")) {
					mes = "11";
				} else if (combo2[0].getSelectedItem().toString()
						.equals("Dezembro")) {
					mes = "12";
				}

				if (radio2[0].isSelected()) {
					new RelatoriodeAdmissoes().executarRelatorio(combo2[1]
							.getSelectedItem().toString());
				} else if (radio2[1].isSelected()) {
					new RelatoriodeAdmissoes().executarRelatorio(comboInt2
							.getSelectedItem().toString(), combo2[1]
							.getSelectedItem().toString());
				} else if (radio2[2].isSelected()) {
					new RelatoriodeAdmissoes().executarRelatorio(mes, comboInt2
							.getSelectedItem().toString(), combo2[1]
							.getSelectedItem().toString());
				}
			}
		});

	}

	/**
	 * Cria a aba de geração de relatório de altas.
	 */
	@SuppressWarnings("unchecked")
	private void abaAltas() {
		int qtdLabel = 3;
		int qtdRadio = 3;
		int qtdCombo = 2;

		String txtLabel[] = { "Mês", "Ano", "Ordem" };

		alta.setLayout(null);
		alta.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));

		radio = new JRadioButton[3];
		radio[0] = new JRadioButton("Geral");
		radio[1] = new JRadioButton("Anual");
		radio[2] = new JRadioButton("Mensal");
		radio[0].setSelected(true);

		grupo = new ButtonGroup();
		for (int i = 0; i < qtdRadio; i++) {
			grupo.add(radio[i]);
			alta.add(radio[i]);
			radio[i].setBackground(new Color(Config.RED, Config.GREEN,
					Config.BLUE));
		}

		label = new JLabel[qtdLabel];
		combo = new JComboBox[qtdLabel];
		comboInt = new JComboBox<Integer>();

		for (int i = 0; i < qtdLabel; i++) {
			label[i] = new JLabel(txtLabel[i]);
			alta.add(label[i]);

		}

		for (int i = 0; i < qtdCombo; i++) {
			combo[i] = new JComboBox<String>();
			alta.add(combo[i]);
		}

		String[] meses = meses();
		for (int i = 0; i < meses.length; i++) {
			combo[0].addItem(meses[i]);
		}

		for (int i = 1950; i <= Data.year(); i++) {
			comboInt.addItem(i);
		}
		comboInt.setSelectedItem(Data.year());
		alta.add(comboInt);

		String[] ordem = ordem();
		for (int i = 0; i < ordem.length; i++) {
			combo[1].addItem(ordem[i]);
		}

		botao = new JButton("Gerar relatório");
		alta.add(botao);

		// ::::::::radios::::::::
		radio[0].setBounds(10, 20, 100, 20);
		radio[1].setBounds(110, 20, 100, 20);
		radio[2].setBounds(220, 20, 100, 20);

		// ::::::::mês::::::::
		label[0].setBounds(10, 70, 50, 20);
		combo[0].setBounds(60, 70, 100, 20);
		combo[0].setEnabled(false);

		// ::::::::ano::::::::
		label[1].setBounds(10, 100, 50, 20);
		comboInt.setBounds(60, 100, 50, 20);
		comboInt.setEnabled(false);

		// ::::::::tipo de ordenação::::::::
		label[2].setBounds(10, 130, 50, 20);
		combo[1].setBounds(60, 130, 130, 20);

		// ::::::::botao gerar relatório::::::::
		botao.setBounds(60, 180, 120, 30);

		// :::::::::Funções::::::::
		// :::::::::geral::::::::
		radio[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo[0].setEnabled(false);
				comboInt.setEnabled(false);
			}
		});

		// :::::::::anual::::::::
		radio[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo[0].setEnabled(false);
				comboInt.setEnabled(true);
			}
		});

		// :::::::::mensal::::::::
		radio[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				combo[0].setEnabled(true);
				comboInt.setEnabled(true);
			}
		});

		// :::::::::executar o relatório::::::::
		botao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mes = "";

				if (combo[0].getSelectedItem().toString().equals("Janeiro")) {
					mes = "1";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Fevereiro")) {
					mes = "2";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Março")) {
					mes = "3";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Abril")) {
					mes = "4";
				} else if (combo[0].getSelectedItem().toString().equals("Maio")) {
					mes = "5";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Junho")) {
					mes = "6";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Julho")) {
					mes = "7";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Agosto")) {
					mes = "8";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Setembro")) {
					mes = "9";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Outubro")) {
					mes = "10";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Novembro")) {
					mes = "11";
				} else if (combo[0].getSelectedItem().toString()
						.equals("Dezembro")) {
					mes = "12";
				}

				if (radio[0].isSelected()) {
					new RelatoriodeAltas().executarRelatorio(combo[1]
							.getSelectedItem().toString());
				} else if (radio[1].isSelected()) {
					new RelatoriodeAltas().executarRelatorio(comboInt
							.getSelectedItem().toString(), combo[1]
							.getSelectedItem().toString());
				} else if (radio[2].isSelected()) {
					new RelatoriodeAltas().executarRelatorio(mes, comboInt
							.getSelectedItem().toString(), combo[1]
							.getSelectedItem().toString());
				}
			}
		});
	}

	private String[] meses() {
		String[] meses = new String[12];
		meses[0] = "Janeiro";
		meses[1] = "Fevereiro";
		meses[2] = "Março";
		meses[3] = "Abril";
		meses[4] = "Maio";
		meses[5] = "Junho";
		meses[6] = "Julho";
		meses[7] = "Agosto";
		meses[8] = "Setembro";
		meses[9] = "Outubro";
		meses[10] = "Novembro";
		meses[11] = "Dezembro";
		return meses;
	}

	private String[] ordem() {
		String[] ordem = new String[4];
		ordem[0] = "Nome";
		ordem[1] = "Data_admissao";
		ordem[2] = "Data_saida";
		ordem[3] = "tipo";
		return ordem;
	}
}
