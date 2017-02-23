package br.com.responsavel.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosResponsavel;
import br.com.responsavel.metodos.CadastraResponsavelMetodos;

/**
 * Esta classe constrói a view de cadastro de responsáveis. Em primeiro lugar, é
 * necessário selecionar a qual interno estará vinculado o responsável.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaCadastroResponsavel extends JDialog {

	private int altura = 20, larguraLabel = 170;
	private long idInterno;
	private Container c;
	private JButton[] button;
	private JLabel[] label;
	private JTextField[] campo;
	private JComboBox<String>[] combo;
	private MetodosGenericosResponsavel metodosResponsavel;
	private URL[] urlButton;

	public TelaCadastroResponsavel(JFrame jf, String s, boolean b,
			long idInterno) {
		super(jf, s, b);
		this.idInterno = idInterno;
		menuFrame();
	}

	private void menuFrame() {
		c = getContentPane();
		setLayout(null);
		setSize(Config.WIDTH, 350);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		metodosResponsavel = new MetodosGenericosResponsavel();

		botoes();
		controledeObjetos();
		dadosResponsavel();
	}

	/**
	 * Este método faz a instância dos objeto utilizados no formulário. Estes
	 * objetos são os JLabels, JTextFields e JComboBoxes.
	 */
	@SuppressWarnings("unchecked")
	private void controledeObjetos() {
		final int QTD_CAMPOS = 17;

		label = new JLabel[QTD_CAMPOS];
		campo = new JTextField[QTD_CAMPOS];
		combo = new JComboBox[2];

		String txtLabel[] = metodosResponsavel.txtLabels();

		for (int i = 0; i < QTD_CAMPOS; i++) {
			label[i] = new JLabel(txtLabel[i]);
			c.add(label[i]);
			label[i].setFont(new Font("Arial", Font.BOLD, 12));
			campo[i] = new JTextField();
			c.add(campo[i]);
		}

		for (int i = 0; i < 2; i++) {
			combo[i] = metodosResponsavel.cbUF();
			combo[i].setSelectedIndex(metodosResponsavel.indexRO());
			c.add(combo[i]);
		}

		// ::::::::::::mascaramento de campos::::::::::::
		try {
			campo[2] = new JFormattedTextField(new MaskFormatter(
					"###.###.###-##"));
			c.add(campo[2]);
			campo[3] = new JFormattedTextField(new MaskFormatter("##/##/####"));
			c.add(campo[3]);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Método responsável pela criação dos campos formulário
	 */
	private void dadosResponsavel() {
		// ::::::::::::nome::::::::::::
		label[0].setBounds(20, 80, larguraLabel, altura);
		campo[0].setBounds(20, 100, 350, altura);

		// ::::::::::::RG::::::::::::
		label[1].setBounds(380, 80, larguraLabel, altura);
		campo[1].setBounds(380, 100, 180, altura);

		// ::::::::::::CPF::::::::::::
		label[2].setBounds(570, 80, larguraLabel, altura);
		campo[2].setBounds(570, 100, 100, altura);

		// ::::::::::::Data de nascimento::::::::::::
		label[3].setBounds(680, 80, larguraLabel, altura);
		campo[3].setBounds(680, 100, 70, altura);

		// ::::::::::::Endereço::::::::::::
		label[4].setBounds(20, 120, larguraLabel, altura);
		campo[4].setBounds(20, 140, 310, altura);

		// ::::::::::::Bairro::::::::::::
		label[5].setBounds(340, 120, larguraLabel, altura);
		campo[5].setBounds(340, 140, 140, altura);

		// ::::::::::::Cidade::::::::::::
		label[6].setBounds(490, 120, larguraLabel, altura);
		campo[6].setBounds(490, 140, 200, altura);

		// ::::::::::::UF::::::::::::
		label[7].setBounds(700, 120, larguraLabel, altura);
		// campo[40].setBounds(0,0,0,0);
		combo[0].setBounds(700, 140, 50, altura);

		// ::::::::::::Telefone::::::::::::
		label[8].setBounds(20, 160, larguraLabel, altura);
		campo[8].setBounds(20, 180, 200, altura);

		// ::::::::::::Celular::::::::::::
		label[9].setBounds(230, 160, larguraLabel, altura);
		campo[9].setBounds(230, 180, 200, altura);

		// ::::::::::::E-mail::::::::::::
		label[10].setBounds(440, 160, larguraLabel, altura);
		campo[10].setBounds(440, 180, 310, altura);

		// ::::::::::::Naturalidade::::::::::::
		label[11].setBounds(20, 200, larguraLabel, altura);
		campo[11].setBounds(20, 220, 250, altura);

		// ::::::::::::UF::::::::::::
		label[12].setBounds(280, 200, larguraLabel, altura);
		// campo[45].setBounds(0,0,0,0);
		combo[1].setBounds(280, 220, 50, altura);

		// ::::::::::::País::::::::::::
		label[13].setBounds(340, 200, larguraLabel, altura);
		campo[13].setBounds(340, 220, 140, altura);

		// ::::::::::::Profissão::::::::::::
		label[14].setBounds(490, 200, larguraLabel, altura);
		campo[14].setBounds(490, 220, 260, altura);

		// ::::::::::::pai::::::::::::
		label[15].setBounds(20, 240, larguraLabel, altura);
		campo[15].setBounds(20, 260, 330, altura);

		// ::::::::::::mãe::::::::::::
		label[16].setBounds(360, 240, larguraLabel, altura);
		campo[16].setBounds(360, 260, 390, altura);
	}

	/**
	 * Este método gera os botões superiores desta view.
	 */
	private void botoes() {
		final int Y = 10;
		final int LARGURA_BOTAO = 230;
		final int ALTURA_BOTAO = 50;
		final int QUANTIDADE_BOTOES = 1;
		button = new JButton[QUANTIDADE_BOTOES];
		urlButton = new URL[QUANTIDADE_BOTOES];
		String[] imagens = { "telaresponsavel/add.png" };
		String[] texto = { "Gravar dados do responsável" };
		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			urlButton[i] = resources.Recursos.class.getResource(imagens[i]);
			button[i] = new JButton(texto[i], new ImageIcon(urlButton[i]));
			c.add(button[i]);
			button[i].setFont(new Font("Arial", Font.BOLD, 12));
		}
		// ::::::::::::Novo cadastro::::::::::::
		button[0].setBounds(10, Y, LARGURA_BOTAO, ALTURA_BOTAO);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insert();
			}
		});
	}

	/**
	 * Este método cadastra um novo responsável na base de dados
	 */
	private void insert() {
		CadastraResponsavelMetodos funcao = new CadastraResponsavelMetodos();
		funcao.cadastraResponsavel(idInterno, campo, combo);
	}

}
