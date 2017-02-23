package br.com.usuarios.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.FuncionarioDAO;
import br.com.metodosgenericos.MetodosGenericosUsuario;
import br.com.usuarios.metodos.CadastraUsuarioMetodos;

/**
 * Esta classe cria a tela de cadastro de usuários.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaCadastroUsuario extends JDialog {

	private static final int QUANTIDADE_BOTOES = 2;
	private static final int QUANTIDADE_CAMPOS = 4;

	private Container c;
	private MetodosGenericosUsuario metodoUsuario;

	private int idFuncionario;

	private JLabel[] label;
	private JButton[] button;
	private JTextField textField;
	private JPasswordField passwordField, passwordConfirm;

	public TelaCadastroUsuario(JFrame jf, String s, boolean b, int idFuncionario) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(500, 270);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		metodoUsuario = new MetodosGenericosUsuario();

		this.idFuncionario = idFuncionario;
		

		formulario();

	}

	/**
	 * Formulário de cadastro de usuários.
	 */
	private void formulario() {
		final int ALTURA = 25;
		label = new JLabel[QUANTIDADE_CAMPOS];
		String[] textoLabel = { "Funcionário: " + getNomeFuncionario(),
				"Usuário", "Senha", "Redigite a senha" };

		button = new JButton[2];
		String[] textoButton = { "Gravar", "Limpar" };
		String[] imagemButton = { "telausuario/add.png",
				"telausuario/clean.png" };

		// campo login
		textField = new JTextField();
		c.add(textField);

		// campo senha
		passwordField = new JPasswordField();
		c.add(passwordField);

		passwordConfirm = new JPasswordField();
		c.add(passwordConfirm);

		// font type
		Font fonte = new Font("Tahoma", Font.BOLD, 12);

		textField.setFont(fonte);
		passwordField.setFont(fonte);
		passwordConfirm.setFont(fonte);

		for (int i = 0; i < QUANTIDADE_CAMPOS; i++) {
			label[i] = new JLabel(textoLabel[i]);
			c.add(label[i]);
			label[i].setFont(new Font("Arial", Config.fonteNegrito,
					Config.tamanhoFonte));
		}
		for (int i = 0; i < QUANTIDADE_BOTOES; i++) {
			URL url = resources.Recursos.class.getResource(imagemButton[i]);
			button[i] = new JButton(textoButton[i], new ImageIcon(url));
			c.add(button[i]);
			button[i].setFont(fonte);
		}

		// login
		label[0].setBounds(20, 20, 400, ALTURA);
		textField.setBounds(140, 60, 300, ALTURA);

		// senha
		label[1].setBounds(20, 60, 85, ALTURA);
		passwordField.setBounds(140, 100, 300, ALTURA);

		// confirme senha
		label[3].setBounds(20, 140, 115, ALTURA);
		passwordConfirm.setBounds(140, 140, 300, ALTURA);

		// nome do novo usuario
		label[2].setBounds(20, 100, 85, ALTURA);

		// botões
		button[0].setBounds(140, 180, 150, 35);
		button[1].setBounds(290, 180, 150, 35);

		// gravar dados
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarUsuario();
			}
		});

		// limpar campos
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoUsuario.limpaCampos(textField, passwordField,
						passwordConfirm);
			}
		});
	}

	/**
	 * Este método envia os dados do formulário do usuário para serem salvos na
	 * base de dados.
	 */
	@SuppressWarnings("deprecation")
	private void salvarUsuario() {
		if (passwordField.getText().equals(passwordConfirm.getText())) {
			try {
				new CadastraUsuarioMetodos().cadastraUsuario(textField,
						passwordField, idFuncionario);
				metodoUsuario.limpaCampos(textField, passwordField,
						passwordConfirm);
				dispose();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} else {
			Show.alerta("A senha foi redigitada incorretamente.");
			metodoUsuario
					.limpaCampos(textField, passwordField, passwordConfirm);
		}
	}

	/**
	 * Através deste método podemos recuperar o nome do funcionario através de
	 * seu ID.
	 */
	private String getNomeFuncionario() {
		FuncionarioDAO dao = new FuncionarioDAO();
		String nomeFuncionario = dao.getNome(idFuncionario);
		return nomeFuncionario;
	}
}
