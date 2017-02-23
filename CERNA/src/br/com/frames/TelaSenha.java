package br.com.frames;

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

import br.com.backup.frames.TelaBackup;
import br.com.controller.Config;
import br.com.livrocaixa.frames.TelaEntradaLivroCaixa;
import br.com.livrocaixa.frames.TelaLivroCaixa;
import br.com.metodosgenericos.MetodosGenericosUsuario;

/**
 * Esta classe � respons�vel pela cria��o da tela para o usu�rio inserir sua
 * senha antes de entrar no m�dulo de livro caixa.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaSenha extends JDialog {

	private byte frame;
	private Container c;
	private JButton botao;
	private JLabel label;
	private JPasswordField campo;
	private MetodosGenericosUsuario funcao;

	/**
	 * M�todo construtor padr�o.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public TelaSenha(JFrame jf, String s, boolean b, byte frame) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(300, 120);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		funcao = new MetodosGenericosUsuario();
		this.frame = frame;

		screen();
	}

	/**
	 * Este m�todo constr�i o formul�rio que possui o campo e o bot�o de entrar
	 * no m�dulo.
	 */
	private void screen() {
		label = new JLabel("Senha:");
		c.add(label);
		label.setFont(new Font("Tahoma", Font.BOLD, 12));

		campo = new JPasswordField();
		c.add(campo);

		URL url = resources.Recursos.class
				.getResource("telalivrocaixa/accept.png");
		botao = new JButton("OK", new ImageIcon(url));
		c.add(botao);

		// ::::::::label::::::::
		label.setBounds(10, 20, 60, 20);

		// ::::::::campo::::::::
		campo.setBounds(60, 20, 220, 20);

		// ::::::::bot�o::::::::
		botao.setBounds(60, 50, 100, 25);

		// ::::::::fun��es::::::::
		botao.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (funcao.logarSistema(Config.loginUsuario, campo.getText()) == true) {
					campo.setText("");
					if (frame == 1) {
						abrirLivroCaixa();
					} else if (frame == 2) {
						abrirEntradaLivroCaixa();
					} else if (frame == 3) {
						backupRestore();
					}
				} else {
					campo.setText("");
				}
			}
		});
	}

	/**
	 * Se o login for efetuado corretamente a tela de livro caixa ser�
	 * apresentada ao usu�rio.
	 */
	private void abrirLivroCaixa() {
		dispose();
		TelaLivroCaixa livroCaixa = new TelaLivroCaixa(null,
				Config.tituloJanela, true);
		livroCaixa.setVisible(true);

	}

	/**
	 * Este m�todo abre a tela de inser��o no livro caixa, logo ap�s realizar a
	 * quita��o de parcelas de ajuda de custo.
	 */
	private void abrirEntradaLivroCaixa() {
		dispose();
		TelaEntradaLivroCaixa t = new TelaEntradaLivroCaixa(null,
				Config.tituloJanela, true);
		t.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela de backup e restaura��o dos dados do sistema.
	 */
	private void backupRestore() {
		dispose();
		TelaBackup backup = new TelaBackup(null, Config.tituloJanela, true);
		backup.setVisible(true);
	}

}
