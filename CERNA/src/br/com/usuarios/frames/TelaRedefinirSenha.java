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

import br.com.controller.Config;
import br.com.usuarios.metodos.RedefinirSenhaMetodos;

@SuppressWarnings("serial")
public class TelaRedefinirSenha extends JDialog {

	private Container c;
	private JPasswordField[] campo;
	private JLabel[] label;
	private JButton botao;

	/**
	 * Construtor padrão da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public TelaRedefinirSenha(JFrame jf, String s, boolean b) {
		super(jf, Config.idUsuarioLogado + "", b);
		c = getContentPane();
		setLayout(null);
		setSize(390, 200);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		screen();
	}

	/**
	 * Este método define a parte visual da redefinição de senha.
	 */
	private void screen() {
		byte qtdCampos = 3;
		byte qtdLabels = 3;

		Font fonte = new Font("Tahoma", Font.PLAIN, 12);

		// ::::::::label::::::::
		label = new JLabel[qtdLabels];
		String txt[] = { "Digite a senha atual", "Digite a nova senha",
				"Redigite a nova senha" };
		for (int i = 0; i < qtdLabels; i++) {
			label[i] = new JLabel(txt[i]);
			c.add(label[i]);
			label[i].setFont(fonte);
		}

		// ::::::::password field:::::::::
		campo = new JPasswordField[qtdCampos];
		for (int i = 0; i < qtdCampos; i++) {
			campo[i] = new JPasswordField();
			c.add(campo[i]);
		}

		// ::::::::botão OK::::::::
		URL url = resources.Recursos.class.getResource("telausuario/edit.png");
		botao = new JButton("Alterar", new ImageIcon(url));
		c.add(botao);

		/*
		 * ================================================
		 * 
		 * POSICIONAMENTO DOS OBJETOS
		 * 
		 * Os scripts a seguir definem a disposição dos itens na tela.
		 * 
		 * ================================================
		 */

		// ::::::::senha atual:::::::::
		label[0].setBounds(20, 20, 140, 20);
		campo[0].setBounds(160, 20, 200, 20);

		// ::::::::digite a nova senha:::::::::
		label[1].setBounds(20, 50, 140, 20);
		campo[1].setBounds(160, 50, 200, 20);

		// ::::::::redigite a nova senha:::::::::
		label[2].setBounds(20, 80, 140, 20);
		campo[2].setBounds(160, 80, 200, 20);

		// ::::::::Alterar::::::::
		botao.setBounds(160, 120, 120, 30);

		botao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				redefinir();
			}
		});

	}

	/**
	 * Este método é usado na redefinição da senha de usuário.
	 */
	@SuppressWarnings("deprecation")
	private void redefinir() {
		String senhaAtual = campo[0].getText();
		String novaSenha = campo[1].getText();
		String novaSenhaRedigitada = campo[2].getText();

		RedefinirSenhaMetodos funcao = new RedefinirSenhaMetodos();
		funcao.redefinir(senhaAtual, novaSenha, novaSenhaRedigitada);

		dispose();
	}
}
