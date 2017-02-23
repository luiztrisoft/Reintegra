package br.com.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.controller.LookAndFeel;
import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosUsuario;

/**
 * Classe principal que executa a tela de login e senha.
 * 
 * @author Tiko
 * 
 */
public class TelaLogin extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel labelLogo, labelUsuario, labelSenha;
	private JTextField fieldUsuario;
	private JPasswordField passSenha;
	private JButton buttonLogin;
	private static TelaLogin telaLogin;

	private Container c;
	private MetodosGenericosUsuario metodoUsuario;

	public static void main(String args[]) {
		telaLogin = new TelaLogin();
		telaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Construtor padrão da classe.
	 */
	public TelaLogin() {
		super(Config.tituloJanela);
		setVisible(true);
		setSize(470, 170);
		setLocationRelativeTo(null);
		setResizable(false);
		LookAndFeel.SystemLook();

		c = getContentPane();
		c.setLayout(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setIconImage(Config.icone.getImage());
		metodoUsuario = new MetodosGenericosUsuario();
		logo();
		formulario();
		botaoEntrar();
	}

	/**
	 * Insere o timbre do CERNA redimensionado para o tamanho X:100/Y:150.
	 */
	private void logo() {
		URL logo = resources.Recursos.class.getResource("logo/timbre.png");
		ImageIcon imagem = new ImageIcon(logo);
		Image redimensionadora = imagem.getImage().getScaledInstance(100, 100,
				Image.SCALE_DEFAULT);
		imagem = new ImageIcon(redimensionadora);

		labelLogo = new JLabel();
		labelLogo.setIcon(imagem);
		c.add(labelLogo);
		labelLogo.setBounds(10, 00, 100, 150);
	}

	/**
	 * Método que cria o formulario contendo campos e o botão de logar para
	 * entrar no sistema em caso de sucesso.
	 */
	private void formulario() {
		final int ALTURA = 25;

		labelUsuario = new JLabel("Usuário");
		c.add(labelUsuario);
		labelUsuario.setBounds(120, 30, 70, ALTURA);
		labelUsuario.setFont(new Font("Arial", Config.fonteNegrito,
				Config.tamanhoFonte));

		fieldUsuario = new JTextField();
		c.add(fieldUsuario);
		fieldUsuario.setBounds(190, 30, 250, ALTURA);
		fieldUsuario.setFont(new Font("Arial", Config.fonteNegrito,
				Config.tamanhoFonte));
		fieldUsuario.requestFocus();

		labelSenha = new JLabel("Senha");
		c.add(labelSenha);
		labelSenha.setBounds(120, 60, 70, ALTURA);
		labelSenha.setFont(new Font("Arial", Config.fonteNegrito,
				Config.tamanhoFonte));

		passSenha = new JPasswordField();
		c.add(passSenha);
		passSenha.setBounds(190, 60, 250, ALTURA);
		passSenha.setFont(new Font("Arial", Config.fonteNegrito,
				Config.tamanhoFonte));
	}

	/**
	 * Botão para entrar no sistema após a inserção de login e senha.
	 */
	private void botaoEntrar() {
		URL botaoEntrar = resources.Recursos.class
				.getResource("buttonicon/accept.png");
		buttonLogin = new JButton("Entrar", new ImageIcon(botaoEntrar));
		c.add(buttonLogin);
		buttonLogin.setBounds(190, 90, 120, 30);
		buttonLogin.setFont(new Font("Arial", Config.fonteNegrito,
				Config.tamanhoFonte));
		buttonLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if (metodoUsuario.logarSistema(fieldUsuario.getText(),
						passSenha.getText()) == true) {
					metodoUsuario.limpaCampos(fieldUsuario, passSenha);
					abrirJanelaPrincipal();
				} else {
					metodoUsuario.limpaCampos(fieldUsuario, passSenha);
				}
			}
		});
	}

	/**
	 * Se o login for efetuado corretamente no banco de dados, este método é
	 * invocado abrindo assim a janela principal do sistema.
	 */
	private void abrirJanelaPrincipal() {
		TelaPrincipal telaPrincipal = new TelaPrincipal();
		telaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		telaPrincipal.setVisible(true);

		telaLogin.setVisible(false);
	}
}
