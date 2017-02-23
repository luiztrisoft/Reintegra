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

import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.FuncionarioDAO;
import br.com.usuarios.metodos.DeletaUsuarioMetodos;

/**
 * Esta classe cria a tela de remoção de usuários.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaDeletaUsuario extends JDialog {

	private Container c;

	private int idFuncionario;

	private JLabel label;
	private JButton button;

	public TelaDeletaUsuario(JFrame jf, String s, boolean b, int idFuncionario) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(420, 150);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		this.idFuncionario = idFuncionario;
		formulario();
	}

	/**
	 * Formulário de cadastro de usuários.
	 */
	private void formulario() {
		final int ALTURA = 25;
		label = new JLabel("Usuário " + getNomeFuncionario());
		c.add(label);

		URL url = resources.Recursos.class
				.getResource("telausuario/remove.png");
		button = new JButton("Deletar", new ImageIcon(url));
		c.add(button);

		// fonte
		Font fonte = new Font("Tahoma", Font.BOLD, 12);
		label.setFont(fonte);

		button.setFont(fonte);

		label.setBounds(20, 20, 365, ALTURA);

		// botão remover
		button.setBounds(85, 60, 150, 35);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeUsuario();
			}
		});
	}

	/**
	 * Este método serve para remover o usuário da base de dados através do
	 * parâmetro ID_FUNCIONARIO.
	 */
	private void removeUsuario() {
		boolean confirm = Show.caixaConfirmacao("Deseja remover o usuário: "
				+ getNomeFuncionario());

		if (confirm == true) {
			DeletaUsuarioMetodos funcao = new DeletaUsuarioMetodos();
			funcao.deletaUsuario(idFuncionario);
			dispose();
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
