package br.com.funcionarios.frames;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.controller.Config;
import br.com.funcionarios.metodos.CadastraFuncionarioMetodos;
import br.com.metodosgenericos.MetodosGenericosFuncionario;

/**
 * Esta classe desenha o formul�rio de funcion�rios que ser� usado para
 * cadastro, navega��o, altera��o, recupera��o e remo��o.
 * 
 * @author Luiz Alberto
 * 
 */
public class TelaCadastroFuncionario extends JDialog {
	private static final long serialVersionUID = 1L;

	private Container c;

	private int fonteSimples = Font.PLAIN;
	private int fonteNegrito = Font.BOLD;
	private int tamanhoFonte = 14;

	private JLabel[] label;
	private JTextField[] textField;
	private JButton[] button;
	private URL[] urlButton;
	private JRadioButton[] radioButton;
	private JComboBox<String> cargo;
	private JTextArea observacoes;

	MetodosGenericosFuncionario metFuncionario;

	String imagePath;

	/**
	 * M�todo construtor padr�o da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public TelaCadastroFuncionario(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(Config.WIDTH, Config.HEIGHT);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		metFuncionario = new MetodosGenericosFuncionario();

		botoes();
		formulario();
	}

	/**
	 * Cria os bot�es de fun��es deste m�dulo.
	 */
	private void botoes() {

		final int Y = 20;
		final int LARGURA_BOTAO = 230;
		final int ALTURA_BOTAO = 50;

		final int QUANTIDADE_BOTOES = 1;
		button = new JButton[QUANTIDADE_BOTOES];
		urlButton = new URL[QUANTIDADE_BOTOES];

		String[] imagens = { "telafuncionario/add.png" };

		String[] texto = { " Gravar dados do funcion�rio" };

		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			urlButton[i] = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(texto[i], new ImageIcon(urlButton[i]));
			c.add(button[i]);
			button[i].setFont(new Font("Arial", fonteNegrito, 12));
			// button[i].setContentAreaFilled(false);
		}

		// cadastrar funcionario
		button[0].setBounds(10, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField[9].setText(cargo.getSelectedItem().toString());
				new CadastraFuncionarioMetodos().cadastraFuncionario(textField,
						radioButton, imagePath, observacoes, label);
			}
		});
	}

	/**
	 * Cria o formul�rio para entrada de dados dos funcion�rios.
	 */
	private void formulario() {

		final int ALTURA = 25;
		final byte QUANTIDADE_CAMPOS = 13;
		final byte QUANTIDADE_BOTOES = 3;
		final byte QUANTIDADE_RADIO_BUTTONS = 3;

		// texto dos formul�rios
		String[] txtLabels = { "Nome(*)", "CPF(*)", "Telefone",
				"Nasc.(dd-mm-aaaa)(*)", "E-mail", "Endere�o(*)", "Bairro(*)",
				"Cidade(*)", "Filia��o", "Cargo/Fun��o(*)", "Status(*)",
				"Observa��es", "Voc� n�o selecionou a foto" };
		String[] txtBotoes = { "Webcam", "Arquivo", "" };
		String[] txtRadio = { "Remunerado", "Volunt�rio", "Cedido" };
		String[] imagens = { "telafuncionario/camera.png",
				"telafuncionario/arquivo.png", "telafuncionario/function.png" };

		label = new JLabel[QUANTIDADE_CAMPOS];
		textField = new JTextField[QUANTIDADE_CAMPOS];
		button = new JButton[QUANTIDADE_BOTOES];
		radioButton = new JRadioButton[QUANTIDADE_RADIO_BUTTONS];

		// radio button de status(remunerado, volunt�rio, cedido)
		ButtonGroup grupo = new ButtonGroup();
		for (int i = 0; i < QUANTIDADE_RADIO_BUTTONS; i++) {
			radioButton[i] = new JRadioButton(txtRadio[i]);
			c.add(radioButton[i]);
			radioButton[i]
					.setFont(new Font("Arial", fonteNegrito, tamanhoFonte));
			radioButton[i].setBackground(new Color(Config.RED, Config.GREEN,
					Config.BLUE));
			grupo.add(radioButton[i]);
		}
		radioButton[0].setSelected(true);

		// textarea observa��es
		observacoes = new JTextArea();
		observacoes.setFont(new Font("Arial", fonteSimples, tamanhoFonte));
		observacoes.setLineWrap(true);
		observacoes.setWrapStyleWord(true);
		JScrollPane rolagem = new JScrollPane(observacoes);
		rolagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		c.add(rolagem);

		// textField com os dados do funcion�rio
		for (int i = 0; i < QUANTIDADE_CAMPOS; i++) {
			label[i] = new JLabel(txtLabels[i]);
			textField[i] = new JTextField();
			c.add(label[i]);
			c.add(textField[i]);
			label[i].setFont(new Font("Arial", fonteNegrito, tamanhoFonte));
			textField[i].setFont(new Font("Arial", fonteSimples, tamanhoFonte));
		}

		// combobox do cargo
		cargo = new JComboBox<String>();
		c.add(cargo);

		// bot�es camera, foto e adicionar cargo
		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			URL url = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(txtBotoes[i], new ImageIcon(url));
			c.add(button[i]);
			button[i].setFont(new Font("Arial", fonteNegrito, 12));
		}

		// mascara de cpf e nascimento
		MaskFormatter maskCPF = null;
		MaskFormatter maskNascimento = null;
		try {
			maskCPF = new MaskFormatter("###.###.###-##");
			maskCPF.setPlaceholderCharacter(' ');
			maskNascimento = new MaskFormatter("##/##/####");
			maskNascimento.setPlaceholderCharacter(' ');
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		textField[1] = new JFormattedTextField(maskCPF);
		c.add(textField[1]);
		textField[1].setFont(new Font("Arial", fonteSimples, tamanhoFonte));
		textField[3] = new JFormattedTextField(maskNascimento);
		c.add(textField[3]);
		textField[3].setFont(new Font("Arial", fonteSimples, tamanhoFonte));

		// imagem "image not available"
		try {
			metFuncionario.semFoto(label);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// nome
		label[0].setBounds(15, 90, 120, ALTURA);
		textField[0].setBounds(10, 115, 450, ALTURA);

		// cpf
		label[1].setBounds(475, 90, 120, ALTURA);
		textField[1].setBounds(470, 115, 150, ALTURA);

		// telefone
		label[2].setBounds(635, 90, 120, ALTURA);
		textField[2].setBounds(630, 115, 150, ALTURA);

		// data de nascimento
		label[3].setBounds(15, 150, 150, ALTURA);
		textField[3].setBounds(10, 175, 160, ALTURA);

		// email
		label[4].setBounds(185, 150, 120, ALTURA);
		textField[4].setBounds(180, 175, 598, ALTURA);

		// endere�o
		label[5].setBounds(15, 210, 120, ALTURA);
		textField[5].setBounds(10, 235, 300, ALTURA);

		// bairro
		label[6].setBounds(325, 210, 120, ALTURA);
		textField[6].setBounds(320, 235, 200, ALTURA);

		// cidade
		label[7].setBounds(535, 210, 120, ALTURA);
		textField[7].setBounds(530, 235, 248, ALTURA);

		// filia��o
		label[8].setBounds(15, 270, 120, ALTURA);
		textField[8].setBounds(10, 295, 510, ALTURA);

		// cargo
		label[9].setBounds(535, 270, 120, ALTURA);
		cargo.setBounds(530, 295, 180, ALTURA);
		metFuncionario.preencheCombocargo(cargo);

		// abre camera & seleciona do arquivo
		button[0].setBounds(15, 340, 150, 35);
		button[1].setBounds(180, 340, 150, 35);

		// adicionar cargo/fun��o
		button[2].setBounds(710, 295, 70, ALTURA);
		button[2].setToolTipText("Adicionar novo cargo");
		button[2].setContentAreaFilled(false);
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metFuncionario.adicionaCargo(cargo);
			}
		});

		// status
		label[10].setBounds(405, 330, 120, ALTURA);
		radioButton[0].setBounds(400, 355, 120, ALTURA);
		radioButton[1].setBounds(550, 355, 120, ALTURA);
		radioButton[2].setBounds(690, 355, 120, ALTURA);

		// observa��es
		label[11].setBounds(315, 385, 120, ALTURA);
		rolagem.setBounds(310, 410, 460, 155);

		// foto do funcion�rio
		label[12].setBounds(30, 390, MetodosGenericosFuncionario.LARGURA_FOTO,
				MetodosGenericosFuncionario.ALTURA_FOTO);

		// abrir webcam para fotografia
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metFuncionario.executarCamera();
			}
		});

		// procurar a foto no arquivo e exibir na tela
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imagePath = metFuncionario.procurarFoto();
				metFuncionario.fotoPessoaLocalhost(imagePath, label[label.length - 1]);
			}
		});
	}
}