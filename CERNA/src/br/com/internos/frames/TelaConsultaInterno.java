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
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.internos.metodos.AlteraInternoMetodos;
import br.com.internos.metodos.BuscaInternoMetodos;
import br.com.internos.metodos.DeletaInternoMetodos;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta classe desenha o formulário de internos que será usado para consulta,
 * alteração, recuperação e remoção.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaConsultaInterno extends JDialog {

	private int idResponsavel = 0;
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

	public TelaConsultaInterno(JFrame jf, String s, boolean b) {
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
	 * Cria o formulário de dados dos internos através da chamada de outros
	 * métodos.
	 */
	private void formulario() {
		controleObjetos();
		dadosPessoais();
		dadosPrincipais();
		dadosComplementares();
		dadosResponsavel();
	}

	/**
	 * Este método é um dos mais importantes desta classe, pois realiza as
	 * instâncias dos objetos da interface gráfica como os textFields, labels,
	 * com boxes etc.
	 */
	@SuppressWarnings("unchecked")
	private void controleObjetos() {
		final int QUANTIDADE_PANELS = 4;
		final int QUANTIDADE_LABELS = 56;
		final int QUANTIDADE_TEXTFIELDS = 56;
		final int QUANTIDADE_BUTTONS = 4;
		final int QUANTIDADE_COMBO = 13;

		panel = new JPanel[QUANTIDADE_PANELS];
		label = new JLabel[QUANTIDADE_LABELS];
		campo = new JTextField[QUANTIDADE_TEXTFIELDS];
		button = new JButton[QUANTIDADE_BUTTONS];
		combo = new JComboBox[QUANTIDADE_COMBO];

		String[] txtLabels = metodoInterno.txtLabelsConsulta();
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
			campo[35] = new JFormattedTextField(new MaskFormatter(
					"###.###.###-##"));
			p.add(campo[35]);
			campo[36] = new JFormattedTextField(new MaskFormatter("##/##/####"));
			p.add(campo[36]);
			campo[50] = new JFormattedTextField(new MaskFormatter("##/##/####"));
			p.add(campo[50]);
			campo[51] = new JFormattedTextField(new MaskFormatter("##/##/####"));
			p.add(campo[51]);
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
		combo[6].addItem("Não");
		combo[6].addItem("Sim");
		p.add(combo[6]);

		combo[7] = new JComboBox<String>();
		combo[7].addItem("Sim");
		combo[7].addItem("Não");
		combo[7].addItem("Não sabe");
		combo[7].setSelectedIndex(1);
		p.add(combo[7]);

		combo[8] = metodoInterno.cbUF();
		p.add(combo[8]);
		combo[8].setSelectedIndex(metodoInterno.indexRO());

		combo[9] = metodoInterno.cbUF();
		p.add(combo[9]);
		combo[9].setSelectedIndex(metodoInterno.indexRO());

		combo[10] = metodoInterno.cbEstCivil();
		p.add(combo[10]);

		combo[11] = metodoInterno.cbTipoSaida();
		p.add(combo[11]);

		combo[12] = metodoInterno.cbDependencia();
		p.add(combo[12]);

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
			p.add(panel[i]);
			panel[i].setBorder(BorderFactory.createTitledBorder(txtPanels[i]));
			panel[i].setFont(new Font("Arial", Font.BOLD, 13));
			panel[i].setBackground(new Color(Config.RED, 230, Config.BLUE));
		}

		// ::::::::::::bloqueia os campos::::::::::::
		metodoInterno.bloquearFormulario(campo, button, textArea);
		// metodoInterno.bloqueiaConjuge(combo[10], campo[7]);

	}

	/**
	 * Formulário com os dados pessoais do interno. Aqui é possível visualizar a
	 * foto, nome, CPF, data de nascimento dentre outras informações.
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

		// ::::::::::::Botões de fotografia::::::::::::
		button[0].setBounds(500, 225, 125, 30);
		button[1].setBounds(625, 225, 125, 30);

		// ::::::::::::abrir webcam para fotografia::::::::::::
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoInterno.executarCamera();
			}
		});

		// ::::::::::::procurar a foto no arquivo e exibir na tela::::::::::::
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePath = metodoInterno.procurarFoto();
				metodoInterno.fotoPessoaLocalhost(imagePath, label[0]);
			}
		});

		// ::::::::::::foto::::::::::::
		label[0].setBounds(500, 40, MetodosGenericosInterno.LARGURA_FOTO,
				MetodosGenericosInterno.ALTURA_FOTO);

		// ::::::::::::cpf::::::::::::
		label[3].setBounds(20, 20, larguraLabel, altura);
		campo[3].setBounds(20, 40, 100, altura);
		campo[3].setFont(new Font("Arial", Font.BOLD, 14));

		// ::::::::::::nome::::::::::::
		label[1].setBounds(130, 20, larguraLabel, altura);
		campo[1].setBounds(130, 40, 350, altura);

		// ::::::::::::data nascimento::::::::::::
		label[2].setBounds(20, 60, larguraLabel, altura);
		campo[2].setBounds(20, 80, 120, altura);

		// ::::::::::::RG::::::::::::
		label[4].setBounds(150, 60, larguraLabel, altura);
		campo[4].setBounds(150, 80, 140, altura);

		// ::::::::::::Telefone::::::::::::
		label[5].setBounds(300, 60, larguraLabel, altura);
		campo[5].setBounds(300, 80, 180, altura);

		// ::::::::::::pai::::::::::::
		label[54].setBounds(20, 100, larguraLabel, altura);
		campo[54].setBounds(20, 120, 220, altura);

		// ::::::::::::mae::::::::::::
		label[55].setBounds(300, 100, larguraLabel, altura);
		campo[55].setBounds(250, 120, 228, altura);

		// ::::::::::::Estado civil::::::::::::
		label[6].setBounds(20, 140, larguraLabel, altura);
		// campo[6].setBounds(0,0,0,0);
		combo[10].setBounds(20, 160, 100, altura);

		// ::::::::::::Conjuge::::::::::::
		label[7].setBounds(130, 140, larguraLabel, altura);
		campo[7].setBounds(130, 160, 350, altura);

		// ::::::::::::Profissão::::::::::::
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

		// ::::::::::::País::::::::::::
		label[12].setBounds(340, 220, larguraLabel, altura);
		campo[12].setBounds(340, 240, 140, altura);
		campo[12].setText("Brasil");

		// ::::::::::::Endereço::::::::::::
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
	 * Este método cria o formulário com os dados principais como status(se é
	 * ativo ou passivo), escolha e adição de convênio e também se ele é
	 * contribuinte ou isento. Aqui também é feita uma chamada ao método
	 * dadosAlta que informa os dados de internação do paciente.
	 */
	private void dadosPrincipais() {
		// ::::::::::::scroll dados principais::::::::::::
		panel[1].setBounds(10, 320, 750, 120);

		// ::::::::::::Status::::::::::::
		label[17].setBounds(20, 340, larguraLabel, altura);
		// campo[17].setBounds(0,0,0,0);
		combo[3].setBounds(20, 360, 80, altura);

		dadosAlta();

		// ::::::::::::Convênio::::::::::::
		label[19].setBounds(130, 380, larguraLabel, altura);
		// campo[19].setBounds(0,0,0,0);
		combo[4].setBounds(130, 400, 300, altura);

		// ::::::::::::ADD Convênio::::::::::::
		// label[20].setBounds(0,0,0,0);
		button[2].setBounds(430, 400, 150, altura);
		button[2].setContentAreaFilled(false);

		// ::::::::::::ADD Dependência/vicio::::::::::::
		button[3].setBounds(160, 520, 30, altura);
		button[3].setContentAreaFilled(false);

		// ::::::::::::Ajuda de custo::::::::::::
		label[18].setBounds(20, 380, larguraLabel, altura);
		// campo[20].setBounds(0,0,0,0);
		combo[5].setBounds(20, 400, 100, altura);

		/*
		 * =====================================
		 * 
		 * FUNÇÂO DOS BOTÕES DO FORMULÁRIO
		 * 
		 * Aqui ficarão as funções dos botões de adicionar convênio e adicionar
		 * dependência.
		 * 
		 * =====================================
		 */
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoInterno.novoConvenio(combo[4]);
			}
		});

		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoInterno.novaDependencia(combo[12]);
			}
		});

	}

	/**
	 * Este método cria o formulário com os dados complementares do interno.
	 * Estes dados informam se o interno responde processo, tipo de dependência,
	 * medicações etc.
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

		// ::::::::::::sofre ameaça::::::::::::
		label[23].setBounds(660, 460, larguraLabel, altura);
		// campo[23].setBounds(0,0,0,0);
		combo[6].setBounds(660, 480, 90, altura);

		// ::::::::::::tipo de dependência/vicio::::::::::::
		label[24].setBounds(20, 500, larguraLabel, altura);
		// campo[24].setBounds(0,0,0,0);
		combo[12].setBounds(20, 520, 140, altura);

		// ::::::::::::1ª vez que usou::::::::::::
		label[25].setBounds(190, 500, larguraLabel, altura);
		campo[25].setBounds(190, 520, 160, altura);

		// ::::::::::::motivo do 1º uso::::::::::::
		label[26].setBounds(360, 500, larguraLabel, altura);
		campo[26].setBounds(360, 520, 390, altura);

		// ::::::::::::é diabético::::::::::::
		label[27].setBounds(20, 540, larguraLabel, altura);
		// campo[27].setBounds(0,0,0,0);
		combo[7].setBounds(20, 560, 70, altura);

		// ::::::::::::contraiu doença::::::::::::
		label[28].setBounds(100, 540, larguraLabel, altura);
		campo[28].setBounds(100, 560, 300, altura);

		// ::::::::::::usa medicação:::::::::::::
		label[29].setBounds(410, 540, larguraLabel, altura);
		campo[29].setBounds(410, 560, 340, altura);

		// ::::::::::::recomendação médica::::::::::::
		label[30].setBounds(20, 580, larguraLabel, altura);
		campo[30].setBounds(20, 600, 330, altura);

		// ::::::::::::documentos entregues::::::::::::
		label[31].setBounds(360, 580, larguraLabel, altura);
		campo[31].setBounds(360, 600, 390, altura);

		// ::::::::::::histórico do interno::::::::::::
		label[32].setBounds(20, 620, larguraLabel, altura);
		// textArea.setBounds(0,0,0,0);
		scroll[1].setBounds(20, 640, 730, 100);
	}

	/**
	 * Este método cria o formulário com os dados do responsável pelo interno.
	 * Os campos não são obrigatórios porque os internos não necessitam
	 * obrigatoriamente de um responsável.
	 */
	private void dadosResponsavel() {
		// ::::::::::::scroll do responsável::::::::::::
		panel[3].setBounds(10, 750, 750, 230);

		// ::::::::::::nome::::::::::::
		label[33].setBounds(20, 770, larguraLabel, altura);
		campo[33].setBounds(20, 790, 350, altura);

		// ::::::::::::RG::::::::::::
		label[34].setBounds(380, 770, larguraLabel, altura);
		campo[34].setBounds(380, 790, 180, altura);

		// ::::::::::::CPF::::::::::::
		label[35].setBounds(570, 770, larguraLabel, altura);
		campo[35].setBounds(570, 790, 100, altura);

		// ::::::::::::Data de nascimento::::::::::::
		label[36].setBounds(680, 770, larguraLabel, altura);
		campo[36].setBounds(680, 790, 70, altura);

		// ::::::::::::Endereço::::::::::::
		label[37].setBounds(20, 810, larguraLabel, altura);
		campo[37].setBounds(20, 830, 310, altura);

		// ::::::::::::Bairro::::::::::::
		label[38].setBounds(340, 810, larguraLabel, altura);
		campo[38].setBounds(340, 830, 140, altura);

		// ::::::::::::Cidade::::::::::::
		label[39].setBounds(490, 810, larguraLabel, altura);
		campo[39].setBounds(490, 830, 200, altura);

		// ::::::::::::UF::::::::::::
		label[40].setBounds(700, 810, larguraLabel, altura);
		// campo[40].setBounds(0,0,0,0);
		combo[8].setBounds(700, 830, 50, altura);

		// ::::::::::::Telefone::::::::::::
		label[41].setBounds(20, 850, larguraLabel, altura);
		campo[41].setBounds(20, 870, 200, altura);

		// ::::::::::::Celular::::::::::::
		label[42].setBounds(230, 850, larguraLabel, altura);
		campo[42].setBounds(230, 870, 200, altura);

		// ::::::::::::E-mail::::::::::::
		label[43].setBounds(440, 850, larguraLabel, altura);
		campo[43].setBounds(440, 870, 310, altura);

		// ::::::::::::Naturalidade::::::::::::
		label[44].setBounds(20, 890, larguraLabel, altura);
		campo[44].setBounds(20, 910, 250, altura);

		// ::::::::::::UF::::::::::::
		label[45].setBounds(280, 890, larguraLabel, altura);
		// campo[45].setBounds(0,0,0,0);
		combo[9].setBounds(280, 910, 50, altura);

		// ::::::::::::País::::::::::::
		label[46].setBounds(340, 890, larguraLabel, altura);
		campo[46].setBounds(340, 910, 140, altura);
		campo[46].setText("Brasil");

		// ::::::::::::Profissão::::::::::::
		label[47].setBounds(490, 890, larguraLabel, altura);
		campo[47].setBounds(490, 910, 260, altura);

		// ::::::::::::pai::::::::::::
		label[48].setBounds(20, 930, larguraLabel, altura);
		campo[48].setBounds(20, 950, 330, altura);

		// ::::::::::::mãe::::::::::::
		label[49].setBounds(360, 930, larguraLabel, altura);
		campo[49].setBounds(360, 950, 390, altura);
	}

	/**
	 * Formulário com os dados de admissão e alta do interno. Quando clicar em
	 * passivo, os campos dt saida, tipo e motivo tornam-se editáveis. Ao clicar
	 * em ativo, os tres campos dt saida, tipo e motivo são limpos e
	 * não-editáveis. O campo dt adm épreenchido automáticamente com a data do
	 * sistema no momento do cadastro.
	 */
	private void dadosAlta() {
		// ::::::::::::data admissao::::::::::::
		label[50].setBounds(110, 340, larguraLabel, altura);
		campo[50].setBounds(110, 360, 70, altura);
		// campo[50].setEditable(false);

		// ::::::::::::data saida::::::::::::
		label[51].setBounds(190, 340, larguraLabel, altura);
		campo[51].setBounds(190, 360, 70, altura);

		// ::::::::::::tipo saida::::::::::::
		label[52].setBounds(270, 340, larguraLabel, altura);
		// campo[52].setBounds(0,0,0,0);
		combo[11].setBounds(270, 360, 100, altura);

		// ::::::::::::motivo saida::::::::::::
		label[53].setBounds(380, 340, larguraLabel, altura);
		campo[53].setBounds(380, 360, 370, altura);

	}

	/**
	 * Este método gera o painel do formulário com uma barra de rolagem que
	 * desliza por toda a janela na vertical, para se ter uma visualização de
	 * todos os itens.
	 */
	private void barraRolagem() {
		p.setLayout(null);
		p.setPreferredSize(new Dimension(600, 1000));
		p.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));

		scroll = new JScrollPane[2];
		scroll[0] = new JScrollPane();
		scroll[0].setBounds(00, 52, 794, 519);
		// scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll[0].setViewportBorder(BorderFactory.createLoweredBevelBorder());
		// scroll.setAutoscrolls(true);
		scroll[0].setViewportView(p);
		getContentPane().add(scroll[0]);
	}

	/**
	 * Cria os botões de funções deste módulo.
	 */
	private void botoes() {

		final int Y = 0;
		final int LARGURA_BOTAO = 113;
		final int ALTURA_BOTAO = 50;

		final int QUANTIDADE_BOTOES = 7;
		button = new JButton[QUANTIDADE_BOTOES];
		urlButton = new URL[QUANTIDADE_BOTOES];

		String[] imagens = { "telainterno/file.png", "telainterno/search.png",
				"telainterno/add.png", "telainterno/edit.png",
				"telainterno/termos.png", "telainterno/pesquisar.png",
				"telainterno/remove.png" };

		String[] texto = { "Novo", "Busca", "Gravar", "Editar", "Termos",
				"CPF", "Deletar" };

		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			urlButton[i] = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(texto[i], new ImageIcon(urlButton[i]));
			c.add(button[i]);
			button[i].setFont(new Font("Arial", Font.BOLD, 12));
			// button[i].setContentAreaFilled(false);
		}

		// ::::::::::::limpa campos::::::::::::
		button[0].setBounds(0, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				metodoInterno.limpaCamposConsulta(campo, combo, textArea,
						label[0]);
				metodoInterno.bloquearFormulario(campo, button, textArea);
			}
		});
		// ::::::::::::buscar internos::::::::::::
		button[1].setBounds(LARGURA_BOTAO, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				idResponsavel = new BuscaInternoMetodos().buscaInterno(campo,
						combo, label[0], textArea);
				metodoInterno.bloquearFormulario(campo, button, textArea);
			}
		});
		// :::::::::::: gravar dados do interno::::::::::::
		button[2].setBounds(LARGURA_BOTAO * 2, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AlteraInternoMetodos().alteraInterno(campo, combo,
						textArea, imagePath, label[0]);
				metodoInterno.bloquearFormulario(campo, button, textArea);
			}
		});
		// ::::::::::::liberar edição de interno::::::::::::
		button[3].setBounds(LARGURA_BOTAO * 3, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				metodoInterno.liberarFormulario(campo, button, textArea,
						combo[10]);

			}
		});

		// :::::::::::: termos e fichas::::::::::::
		button[4].setBounds(LARGURA_BOTAO * 4, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// new BuscaInternoMetodos().buscaInterno(campo, combo,
				// label[0], textArea);
				// metodoInterno.bloquearFormulario(campo, button, textArea);
				metodoInterno.fichaInternacao(campo, combo, textArea,
						idResponsavel);
			}
		});

		// :::::::::::: Tabela de CPF::::::::::::
		button[5].setBounds(LARGURA_BOTAO * 5, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoInterno.tabelaCPFs();
			}
		});

		// ::::::::::::deletar interno::::::::::::
		button[6].setBounds(LARGURA_BOTAO * 6, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Show.caixaConfirmacao("<html>Deseja deletar o interno(a) <b>"
						+ campo[1].getText() + "?") == true) {
					new DeletaInternoMetodos().deletaInterno(campo, combo,
							textArea, label);
				}
			}
		});
	}

	/**
	 * cria a mensagem mostrando os campos obrigatórios.
	 */
	@SuppressWarnings("unused")
	private void obs() {
		label = new JLabel[1];
		label[0] = new JLabel(
				"Obs.: Os campos marcadados com * são obrigatórios.");
		p.add(label[0]);
		label[0].setFont(new Font("Arial", Config.fonteNegrito, 13));
		label[0].setBounds(10, 10, 360, 30);
	}

}
