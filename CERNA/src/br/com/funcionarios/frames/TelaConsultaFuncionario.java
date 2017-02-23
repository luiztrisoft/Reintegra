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
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.controller.Show;
import br.com.controller.Config;
import br.com.funcionarios.metodos.AlteraFuncionarioMetodos;
import br.com.funcionarios.metodos.BuscaFuncionarioMetodos;
import br.com.funcionarios.metodos.DeletaFuncionarioMetodos;
import br.com.metodosgenericos.MetodosGenericosFuncionario;

/**
 * Esta classe desenha o formulário de funcionários que será usado para
 * cadastro,  alteração, recuperação e remoção.
 * 
 * @author Luiz Alberto
 * 
 */
public class TelaConsultaFuncionario extends JDialog {
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
	private JTextArea observacoes;

	MetodosGenericosFuncionario metFuncionario;

	String imagePath;

	public TelaConsultaFuncionario(JFrame jf, String s, boolean b) {
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
	 * Cria os botões de funções deste módulo.
	 */
	private void botoes() {

		final int Y = 0;
		final int LARGURA_BOTAO = 132;
		final int ALTURA_BOTAO = 50;

		final int QUANTIDADE_BOTOES = 7;//reduzir para 6
		button = new JButton[QUANTIDADE_BOTOES];
		urlButton = new URL[QUANTIDADE_BOTOES];

		String[] imagens = { "telafuncionario/file.png",
				"telafuncionario/search.png", "telafuncionario/add.png",
				"telafuncionario/edit.png", "telafuncionario/remove.png",
				"telafuncionario/function.png", "telafuncionario/next.png" };

		String[] texto = { "Novo", "Busca", "Gravar", "Editar", "Deletar",
				"Add. Cargo", "Próximo" };

		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			urlButton[i] = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(texto[i], new ImageIcon(urlButton[i]));
			c.add(button[i]);
			button[i].setFont(new Font("Arial", fonteNegrito, 12));
			// button[i].setContentAreaFilled(false);
		}

		// limpa campos
		button[0].setBounds(0, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				metFuncionario.limpaCampos(textField, radioButton, observacoes,
						label);
				metFuncionario.bloquearFormulario(textField, button,
						radioButton, observacoes);
			}
		});
		// buscar funcionarios
		button[1].setBounds(LARGURA_BOTAO, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BuscaFuncionarioMetodos().buscaFuncionario(textField,
						radioButton, label, observacoes);
				metFuncionario.bloquearFormulario(textField, button,
						radioButton, observacoes);
			}
		});
		// gravar dados do funcionario
		button[2].setBounds(LARGURA_BOTAO * 2, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Show.caixaConfirmacao("Deseja alterar os dados do funcionário?") == true) {
					new AlteraFuncionarioMetodos().alteraFuncionario(textField,
							radioButton, imagePath, observacoes, label);
				}
				metFuncionario.bloquearFormulario(textField, button,
						radioButton, observacoes);
			}
		});
		// liberar edição de funcionario
		button[3].setBounds(LARGURA_BOTAO * 3, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				metFuncionario.liberarFormulario(textField, button,
						radioButton, observacoes);
			}
		});

		// deletar funcionario
		button[4].setBounds(LARGURA_BOTAO * 4, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Show.caixaConfirmacao("Deseja deletar o funcionário?") == true) {
					new DeletaFuncionarioMetodos().deletaFuncionario(textField,
							radioButton, observacoes, label);
					metFuncionario.bloquearFormulario(textField, button,
							radioButton, observacoes);
				}
			}
		});

		// adicionar cargo
		button[5].setBounds(LARGURA_BOTAO * 5, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metFuncionario.adicionaCargo();
			}
		});
	}

	/**
	 * Cria o formulário para entrada de dados dos funcionários.
	 */
	private void formulario() {

		final int ALTURA = 25;
		final byte QUANTIDADE_CAMPOS = 13;
		final byte QUANTIDADE_BOTOES = 3;
		final byte QUANTIDADE_RADIO_BUTTONS = 3;

		String[] txtLabels = { "Nome(*)", "CPF(*)", "Telefone",
				"Nasc.(dd-mm-aaaa)(*)", "E-mail", "Endereço(*)", "Bairro(*)",
				"Cidade(*)", "Filiação", "Cargo/Função(*)", "Status(*)",
				"Observações", "Você não selecionou a foto" };
		String[] txtRadio = { "Remunerado", "Voluntário", "Cedido" };
		String[] txtBotoes = { "Webcam", "Arquivo", "" };
		String[] imagens = { "telafuncionario/camera.png",
				"telafuncionario/arquivo.png", "telafuncionario/function.png" };

		label = new JLabel[QUANTIDADE_CAMPOS];
		textField = new JTextField[QUANTIDADE_CAMPOS];
		button = new JButton[QUANTIDADE_BOTOES];
		radioButton = new JRadioButton[QUANTIDADE_RADIO_BUTTONS];

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

		observacoes = new JTextArea();
		observacoes.setFont(new Font("Arial", fonteSimples, tamanhoFonte));
		observacoes.setLineWrap(true);
		observacoes.setWrapStyleWord(true);
		JScrollPane rolagem = new JScrollPane(observacoes);
		rolagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		c.add(rolagem);

		for (int i = 0; i < QUANTIDADE_CAMPOS; i++) {
			label[i] = new JLabel(txtLabels[i]);
			textField[i] = new JTextField();
			c.add(label[i]);
			c.add(textField[i]);
			label[i].setFont(new Font("Arial", fonteNegrito, tamanhoFonte));
			textField[i].setFont(new Font("Arial", fonteSimples, tamanhoFonte));
		}

		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			URL url = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(txtBotoes[i], new ImageIcon(url));
			c.add(button[i]);
			button[i].setFont(new Font("Arial", fonteNegrito, 12));
		}

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

		try {
			metFuncionario.semFoto(label);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		metFuncionario.bloquearFormulario(textField, button, radioButton,
				observacoes);

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

		// endereço
		label[5].setBounds(15, 210, 120, ALTURA);
		textField[5].setBounds(10, 235, 300, ALTURA);

		// bairro
		label[6].setBounds(325, 210, 120, ALTURA);
		textField[6].setBounds(320, 235, 200, ALTURA);

		// cidade
		label[7].setBounds(535, 210, 120, ALTURA);
		textField[7].setBounds(530, 235, 248, ALTURA);

		// filiação
		label[8].setBounds(15, 270, 120, ALTURA);
		textField[8].setBounds(10, 295, 510, ALTURA);

		// cargo
		label[9].setBounds(535, 270, 120, ALTURA);
		textField[9].setBounds(530, 295, 180, ALTURA);

		// abre camera & seleciona do arquivo
		button[0].setBounds(15, 340, 150, 35);
		button[1].setBounds(180, 340, 150, 35);

		// adicionar cargo/função
		button[2].setBounds(710, 295, 70, ALTURA);
		button[2].setContentAreaFilled(false);
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField[9].setText(metFuncionario.recuperaCargo(textField[9]
						.getText()));

			}
		});

		// status
		label[10].setBounds(405, 330, 120, ALTURA);
		radioButton[0].setBounds(400, 355, 120, ALTURA);
		radioButton[1].setBounds(550, 355, 120, ALTURA);
		radioButton[2].setBounds(690, 355, 120, ALTURA);

		// observações
		label[11].setBounds(315, 385, 120, ALTURA);
		rolagem.setBounds(310, 410, 460, 155);

		// foto do funcionário
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
