package br.com.internos.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.controller.Save;
import br.com.controller.Config;
import br.com.internos.metodos.CadastraInternoMetodos;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta classe cria a tela de que ser� utilizada para o cadastro dos internos.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaCadastroInterno extends JDialog {
	private int altura = 20, larguraLabel = 170;
	private Container c;
	private JScrollPane[] scroll;
	private JPanel p;
	private JPanel[] panel;
	private JButton[] button;
	private JLabel[] label;
	private JTextField[] campo;
	private JComboBox<String>[] combo;
	private JTextArea textArea;
	private MetodosGenericosInterno metodoInterno;
	private String imagePath;
	private URL[] urlButton;

	public TelaCadastroInterno(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(Config.WIDTH, Config.HEIGHT);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		p = new JPanel();
		metodoInterno = new MetodosGenericosInterno();

		barraRolagem();
		// obs();
		botoes();
		formulario();
	}

	/**
	 * Cria o formul�rio de dados dos internos atrav�s da chamada de outros
	 * m�todos.
	 */
	private void formulario() {
		controleObjetos();
		dadosPessoais();
		dadosPrincipais();
		dadosComplementares();
		// dadosResponsavel();
	}

	/**
	 * Este m�todo � um dos mais importantes desta classe, pois realiza as
	 * inst�ncias dos objetos da interface gr�fica como os textFields, labels,
	 * com boxes etc.
	 */
	@SuppressWarnings("unchecked")
	private void controleObjetos() {
		final int QUANTIDADE_PANELS = 3;
		final int QUANTIDADE_LABELS = 39;
		final int QUANTIDADE_TEXTFIELDS = 39;
		final int QUANTIDADE_BUTTONS = 4;
		final int QUANTIDADE_COMBO = 11;

		panel = new JPanel[QUANTIDADE_PANELS];
		label = new JLabel[QUANTIDADE_LABELS];
		campo = new JTextField[QUANTIDADE_TEXTFIELDS];
		button = new JButton[QUANTIDADE_BUTTONS];
		combo = new JComboBox[QUANTIDADE_COMBO];

		String[] txtLabels = metodoInterno.txtLabelsCadastro();
		String[] txtPanels = metodoInterno.txtPanels();
		String[] txtButtons = metodoInterno.txtButtons();
		String[] pathImages = metodoInterno.pathImagens();

		// ::::::::::::cria textfields::::::::::::
		for (int i = 0; i < QUANTIDADE_TEXTFIELDS; i++) {
			campo[i] = new JTextField();
			p.add(campo[i]);
		}
		// ::::::::::::mascaramento de campos::::::::::::
		try {
			campo[2] = new JFormattedTextField(new MaskFormatter("##/##/####"));
			p.add(campo[2]);

			campo[3] = new JFormattedTextField(new MaskFormatter("###########"));
			p.add(campo[3]);
			campo[33] = new JFormattedTextField(new MaskFormatter("##/##/####"));
			p.add(campo[33]);
			campo[34] = new JFormattedTextField(new MaskFormatter("##/##/####"));
			p.add(campo[34]);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// ::::::::::::cria o comboBox::::::::::::
		combo[0] = metodoInterno.cbEscolaridade();
		p.add(combo[0]);

		combo[1] = metodoInterno.cbUF();
		p.add(combo[1]);
		combo[1].setSelectedIndex(metodoInterno.indexRO());

		combo[2] = metodoInterno.cbUF();
		p.add(combo[2]);
		combo[2].setSelectedIndex(metodoInterno.indexRO());

		combo[3] = metodoInterno.cbStatus();
		p.add(combo[3]);

		combo[4] = metodoInterno.cbConvenio();
		p.add(combo[4]);

		combo[5] = metodoInterno.cbContribuicao();
		p.add(combo[5]);

		combo[6] = new JComboBox<String>();
		combo[6].addItem("N�o");
		combo[6].addItem("Sim");
		p.add(combo[6]);

		combo[7] = new JComboBox<String>();
		combo[7].addItem("Sim");
		combo[7].addItem("N�o");
		combo[7].addItem("N�o sabe");
		combo[7].setSelectedIndex(1);
		p.add(combo[7]);

		combo[8] = metodoInterno.cbTipoSaida();
		p.add(combo[8]);

		combo[9] = metodoInterno.cbEstCivil();
		p.add(combo[9]);

		combo[10] = metodoInterno.cbDependencia();
		p.add(combo[10]);

		// combo[8] = metodoInterno.cbUF();
		// p.add(combo[8]);
		// combo[8].setSelectedIndex(21);
		// combo[9] = metodoInterno.cbUF();
		// p.add(combo[9]);
		// combo[9].setSelectedIndex(21);
		// combo[11] = metodoInterno.cbEstCivil();
		// p.add(combo[11]);

		// ::::::::::::cria os labels::::::::::::
		for (int i = 0; i < QUANTIDADE_LABELS; i++) {
			label[i] = new JLabel(txtLabels[i]);
			p.add(label[i]);
			label[i].setFont(new Font("Arial", Font.BOLD, 12));
		}

		// ::::::::::::cria os botoes::::::::::::
		for (int i = 0; i < QUANTIDADE_BUTTONS; i++) {
			URL url = resources.Recursos.class.getResource(pathImages[i]);
			button[i] = new JButton(txtButtons[i], new ImageIcon(url));
			p.add(button[i]);
		}

		funcoesFormulario();

		// ::::::::::::cria o textArea::::::::::::
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scroll[1] = new JScrollPane(textArea);
		scroll[1]
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		p.add(scroll[1]);

		// ::::::::::::cria os scrolls::::::::::::
		for (int i = 0; i < QUANTIDADE_PANELS; i++) {
			panel[i] = new JPanel();
			panel[i].setFont(new Font("Arial", Font.BOLD, 13));
			p.add(panel[i]);
			panel[i].setBorder(BorderFactory.createTitledBorder(txtPanels[i]));
			panel[i].setBackground(new Color(Config.RED, 230, Config.BLUE));
		}
	}

	/**
	 * Formul�rio com os dados pessoais do interno. Aqui � poss�vel visualizar a
	 * foto, nome, CPF, data de nascimento dentre outras informa��es.
	 */
	private void dadosPessoais() {
		// ::::::::::::scroll dados pessoais::::::::::::
		panel[0].setBounds(10, 05, 750, 315);
		// ::::::::::::fotografia::::::::::::
		try {
			metodoInterno.semFoto(label[0]);
			// campo[0].setBounds(0,0,0,0);
		} catch (Exception e) {
			Save.log(Config.system, e.getMessage());
			System.out.println(e.getMessage());
		}
		button[0].setBounds(500, 225, 125, 30);
		button[1].setBounds(625, 225, 125, 30);
		label[0].setBounds(500, 40, MetodosGenericosInterno.LARGURA_FOTO,
				MetodosGenericosInterno.ALTURA_FOTO);

		// ::::::::::::nome::::::::::::
		label[1].setBounds(20, 20, larguraLabel, altura);
		campo[1].setBounds(20, 40, 320, altura);

		// ::::::::::::data de nascimento::::::::::::
		label[2].setBounds(350, 20, larguraLabel, altura);
		campo[2].setBounds(350, 40, 130, altura);

		// ::::::::::::CPF::::::::::::
		label[3].setBounds(20, 60, larguraLabel, altura);
		campo[3].setBounds(20, 80, 90, altura);

		// ::::::::::::RG::::::::::::
		label[4].setBounds(120, 60, larguraLabel, altura);
		campo[4].setBounds(120, 80, 170, altura);

		// ::::::::::::Telefone::::::::::::
		label[5].setBounds(300, 60, larguraLabel, altura);
		campo[5].setBounds(300, 80, 180, altura);

		// ::::::::::::pai::::::::::::
		label[37].setBounds(20, 100, larguraLabel, altura);
		campo[37].setBounds(20, 120, 220, altura);

		// ::::::::::::mae::::::::::::
		label[38].setBounds(300, 100, larguraLabel, altura);
		campo[38].setBounds(250, 120, 228, altura);

		// ::::::::::::Estado civil::::::::::::
		label[6].setBounds(20, 140, larguraLabel, altura);
		// campo[6].setBounds(0,0,0,0);
		combo[9].setBounds(20, 160, 100, altura);

		// ::::::::::::Conjuge::::::::::::
		label[7].setBounds(130, 140, larguraLabel, altura);
		campo[7].setBounds(130, 160, 350, altura);

		// ::::::::::::Profiss�o::::::::::::
		label[8].setBounds(20, 180, larguraLabel, altura);
		campo[8].setBounds(20, 200, 250, altura);

		// ::::::::::::Escolaridade::::::::::::
		label[9].setBounds(280, 180, larguraLabel, altura);
		// campo[9].setBounds(0,0,0,0);
		combo[0].setBounds(280, 200, 200, altura);

		// ::::::::::::Naturalidade::::::::::::
		label[10].setBounds(20, 220, larguraLabel, altura);
		campo[10].setBounds(20, 240, 250, altura);

		// ::::::::::::UF::::::::::::
		label[11].setBounds(280, 220, larguraLabel, altura);
		// campo[11].setBounds(0,0,0,0);
		combo[1].setBounds(280, 240, 50, altura);

		// ::::::::::::Pa�s::::::::::::
		label[12].setBounds(340, 220, larguraLabel, altura);
		campo[12].setBounds(340, 240, 140, altura);
		campo[12].setText("Brasil");

		// ::::::::::::Endere�o::::::::::::
		label[13].setBounds(20, 260, larguraLabel, altura);
		campo[13].setBounds(20, 280, 310, altura);

		// ::::::::::::Bairro::::::::::::
		label[14].setBounds(340, 260, larguraLabel, altura);
		campo[14].setBounds(340, 280, 140, altura);

		// ::::::::::::Cidade::::::::::::
		label[15].setBounds(490, 260, larguraLabel, altura);
		campo[15].setBounds(490, 280, 200, altura);

		// ::::::::::::UF::::::::::::
		label[16].setBounds(700, 260, larguraLabel, altura);
		// campo[16].setBounds(0,0,0,0);
		combo[2].setBounds(700, 280, 50, altura);

	}

	/**
	 * Este m�todo cria o formul�rio com os dados principais como status(se �
	 * ativo ou passivo), escolha e adi��o de conv�nio e tamb�m se ele �
	 * contribuinte ou isento. Aqui tamb�m � feita uma chamada ao m�todo
	 * dadosAlta que informa os dados de interna��o do paciente.
	 */
	private void dadosPrincipais() {
		// ::::::::::::scroll dados principais::::::::::::
		panel[1].setBounds(10, 320, 750, 120);

		// ::::::::::::Status::::::::::::
		label[17].setBounds(20, 340, larguraLabel, altura);
		// campo[17].setBounds(0,0,0,0);
		combo[3].setBounds(20, 360, 80, altura);
		combo[3].setEnabled(false);

		dadosAlta();

		// ::::::::::::Conv�nio::::::::::::
		label[19].setBounds(130, 380, larguraLabel, altura);
		// campo[18].setBounds(0,0,0,0);
		combo[4].setBounds(130, 400, 300, altura);

		// ::::::::::::ADD Conv�nio::::::::::::
		// label[20].setBounds(0,0,0,0);
		button[2].setBounds(430, 400, 150, altura);
		button[2].setContentAreaFilled(false);

		// ::::::::::::ADD Depend�ncia/vicio::::::::::::
		button[3].setBounds(160, 520, 30, altura);
		button[3].setContentAreaFilled(false);

		// ::::::::::::Ajuda de custo::::::::::::
		label[18].setBounds(20, 380, larguraLabel, altura);
		// campo[20].setBounds(0,0,0,0);
		combo[5].setBounds(20, 400, 100, altura);
	}

	/**
	 * Este m�todo cria o formul�rio com os dados complementares do interno.
	 * Estes dados informam se o interno responde processo, tipo de depend�ncia,
	 * medica��es etc.
	 */
	private void dadosComplementares() {
		// ::::::::::::scroll dados complementares::::::::::::
		panel[2].setBounds(10, 440, 750, 310);

		// ::::::::::::responde processo criminal::::::::::::
		label[21].setBounds(20, 460, larguraLabel, altura);
		campo[21].setBounds(20, 480, 420, altura);

		// ::::::::::::onde::::::::::::
		label[22].setBounds(450, 460, larguraLabel, altura);
		campo[22].setBounds(450, 480, 200, altura);

		// ::::::::::::sofre amea�a::::::::::::
		label[23].setBounds(660, 460, larguraLabel, altura);
		// campo[23].setBounds(0,0,0,0);
		combo[6].setBounds(660, 480, 90, altura);

		// ::::::::::::tipo de vicio::::::::::::
		label[24].setBounds(20, 500, larguraLabel, altura);
		// campo[24].setBounds(0, 0, 0, 0);
		combo[10].setBounds(20, 520, 140, altura);

		// ::::::::::::tempo de uso::::::::::::
		label[25].setBounds(190, 500, larguraLabel, altura);
		campo[25].setBounds(190, 520, 160, altura);

		// ::::::::::::motivo do 1� uso::::::::::::
		label[26].setBounds(360, 500, larguraLabel, altura);
		campo[26].setBounds(360, 520, 390, altura);

		// ::::::::::::� diab�tico::::::::::::
		label[27].setBounds(20, 540, larguraLabel, altura);
		// campo[27].setBounds(0,0,0,0);
		combo[7].setBounds(20, 560, 70, altura);

		// ::::::::::::contraiu doen�a::::::::::::
		label[28].setBounds(100, 540, larguraLabel, altura);
		campo[28].setBounds(100, 560, 300, altura);

		// ::::::::::::usa medica��o:::::::::::::
		label[29].setBounds(410, 540, larguraLabel, altura);
		campo[29].setBounds(410, 560, 340, altura);

		// ::::::::::::recomenda��o m�dica::::::::::::
		label[30].setBounds(20, 580, larguraLabel, altura);
		campo[30].setBounds(20, 600, 330, altura);

		// ::::::::::::documentos entregues::::::::::::
		label[31].setBounds(360, 580, larguraLabel, altura);
		campo[31].setBounds(360, 600, 390, altura);

		// ::::::::::::hist�rico do interno::::::::::::
		label[32].setBounds(20, 620, larguraLabel, altura);
		// textArea.setBounds(0,0,0,0);
		scroll[1].setBounds(20, 640, 730, 100);
	}

	/**
	 * Formul�rio com os dados de admiss�o e alta do interno. Quando clicar em
	 * passivo, os campos dt saida, tipo e motivo tornam-se edit�veis. Ao clicar
	 * em ativo, os tres campos dt saida, tipo e motivo s�o limpos e
	 * n�o-edit�veis. O campo dt adm �preenchido autom�ticamente com a data do
	 * sistema no momento do cadastro.
	 */
	private void dadosAlta() {
		// ::::::::::::data admissao::::::::::::
		label[33].setBounds(110, 340, larguraLabel, altura);
		campo[33].setBounds(110, 360, 70, altura);
		// campo[33].setEditable(false);
		campo[33].setText(metodoInterno.dataAdmissao());

		// ::::::::::::data saida::::::::::::
		label[34].setBounds(190, 340, larguraLabel, altura);
		campo[34].setBounds(190, 360, 70, altura);
		// campo[34].setEditable(false);
		campo[34].setText(metodoInterno.dataSaida());

		// ::::::::::::tipo saida::::::::::::
		label[35].setBounds(270, 340, larguraLabel, altura);
		// campo[35].setBounds(0,0,0,0);
		combo[8].setBounds(270, 360, 100, altura);
		combo[8].setEnabled(false);

		// ::::::::::::motivo::::::::::::
		label[36].setBounds(380, 340, larguraLabel, altura);
		campo[36].setBounds(380, 360, 370, altura);
		campo[36].setEditable(false);
	}

	// private void dadosAlta() {
	// // ::::::::::::data admissao::::::::::::
	// label[50].setBounds(110, 340, larguraLabel, altura);
	// campo[50].setBounds(110, 360, 70, altura);
	// campo[50].setEditable(false);
	// campo[50].setText(Data.showDate());
	//
	// // ::::::::::::data saida::::::::::::
	// label[51].setBounds(190, 340, larguraLabel, altura);
	// campo[51].setBounds(190, 360, 70, altura);
	// campo[51].setEditable(false);
	//
	// // ::::::::::::tipo saida::::::::::::
	// label[52].setBounds(270, 340, larguraLabel, altura);
	// // campo[52].setBounds(0,0,0,0);
	// combo[12].setBounds(270, 360, 100, altura);
	//
	// // ::::::::::::motivo::::::::::::
	// label[53].setBounds(380, 340, larguraLabel, altura);
	// campo[53].setBounds(380, 360, 370, altura);
	//
	// }

	/**
	 * Este m�todo gera o painel do formul�rio com uma barra de rolagem que
	 * desliza por toda a janela na vertical, para se ter uma visualiza��o de
	 * todos os itens.
	 */
	private void barraRolagem() {
		p.setLayout(null);
		p.setPreferredSize(new Dimension(600, 760));
		p.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));

		scroll = new JScrollPane[2];
		scroll[0] = new JScrollPane();
		scroll[0].setBounds(00, 52, 794, 519);
		// scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll[0].setViewportBorder(BorderFactory.createLoweredBevelBorder());
		// scroll[0].setAutoscrolls(true);
		scroll[0].setViewportView(p);
		getContentPane().add(scroll[0]);
	}

	/**
	 * Cria os bot�es de fun��es deste m�dulo.
	 */
	private void botoes() {
		final int Y = 0;
		final int LARGURA_BOTAO = 230;
		final int ALTURA_BOTAO = 50;
		final int QUANTIDADE_BOTOES = 1;
		button = new JButton[QUANTIDADE_BOTOES];
		urlButton = new URL[QUANTIDADE_BOTOES];
		String[] imagens = { "telainterno/add.png" };
		String[] texto = { "Gravar dados do interno" };
		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			urlButton[i] = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(texto[i], new ImageIcon(urlButton[i]));
			c.add(button[i]);
			button[i].setFont(new Font("Arial", Font.BOLD, 12));
		}
		// ::::::::::::Novo cadastro::::::::::::
		button[0].setBounds(0, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CadastraInternoMetodos().cadastraInterno(campo, combo,
						textArea, imagePath, label[0]);
			}
		});
	}

	/**
	 * Este m�todo executa as fun��es webcam, abrir arquivo e adicionar conv�nio
	 * do formul�rio.
	 */
	private void funcoesFormulario() {
		// ::::::::::::bloqueia conjuge::::::::::::
		metodoInterno.bloqueiaConjuge(combo[9], campo[7]);

		// ::::::::::::abre cam�ra::::::::::::
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoInterno.executarCamera();
			}
		});

		// ::::::::::::busca foto no arquivo::::::::::::
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePath = metodoInterno.procurarFoto();
				metodoInterno.fotoPessoaLocalhost(imagePath, label[0]);
			}
		});

		// ::::::::::::adiciona novo conv�nio::::::::::::
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoInterno.novoConvenio(combo[4]);
			}
		});

		// ::::::::adiciona nova depend�ncia::::::::
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoInterno.novaDependencia(combo[10]);
			}
		});
	}

	/**
	 * cria a mensagem mostrando os campos obrigat�rios.
	 */
	@SuppressWarnings("unused")
	private void obs() {
		label = new JLabel[1];
		label[0] = new JLabel(
				"Obs.: Os campos marcadados com * s�o obrigat�rios.");
		p.add(label[0]);
		label[0].setFont(new Font("Arial", Config.fonteNegrito, 13));
		label[0].setBounds(10, 10, 360, 30);
	}
}