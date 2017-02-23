package br.com.itens.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import br.com.controller.Config;

/**
 * Nesta classe o usuario seleciona qual entidade o item que ele quer remover
 * pertence.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaSelecaoItem extends JDialog {

	private Container c;
	private JLabel label;
	private JRadioButton radio[];
	private JButton botao;
	private ButtonGroup grupo;

	public TelaSelecaoItem(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(280, 280);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		screen();
	}

	/**
	 * Este método monta a view de escolha de entidade para remoção de item
	 * cadastrado.
	 */
	private void screen() {
		Font fonte = new Font("Tahoma", Font.PLAIN, 12);

		// ::::::::: label ::::::::
		label = new JLabel("Selecione a entidade para remover o item");
		c.add(label);
		label.setFont(fonte);

		// ::::::::: radio buttons ::::::::
		String txtRadio[] = { "Categoria de lançamento", "Convênios",
				"Departamento", "Dependência", "Forma de pagamento" };
		radio = new JRadioButton[5];
		grupo = new ButtonGroup();
		for (int i = 0; i < radio.length; i++) {
			radio[i] = new JRadioButton(txtRadio[i]);
			c.add(radio[i]);
			radio[i].setBackground(new Color(Config.RED, Config.GREEN,
					Config.BLUE));
			radio[i].setFont(fonte);
			grupo.add(radio[i]);
		}
		radio[0].setSelected(true);

		// :::::::: botão ::::::::
		URL url = resources.Recursos.class.getResource("telaitem/accept.png");
		botao = new JButton("OK", new ImageIcon(url));
		c.add(botao);
		botao.setFont(fonte);

		// :::::::: label ::::::::
		label.setBounds(10, 10, 300, 25);

		// :::::::: radio ::::::::
		radio[0].setBounds(10, 40, 200, 25);
		radio[1].setBounds(10, 70, 200, 25);
		radio[2].setBounds(10, 100, 200, 25);
		radio[3].setBounds(10, 130, 200, 25);
		radio[4].setBounds(10, 160, 200, 25);

		// :::::::: botão ::::::::
		botao.setBounds(110, 210, 70, 25);

		// :::::::: função do botão ::::::::
		botao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaItens();
			}
		});
	}

	/**
	 * Este método abre a tela de ítens informando um valor como índice de
	 * acordo com o radio button selecionado.
	 */
	private void telaItens() {
		byte itemId = radioSelecionado();
		TelaItens screen = new TelaItens(null, Config.tituloJanela, true,
				itemId);
		screen.setVisible(true);
	}

	/**
	 * Este método retorna o número do radio button selecionado.
	 * 
	 * @return byte
	 */
	private byte radioSelecionado() {
		byte radioselecionado = 0;
		for (byte i = 0; i < radio.length; i++) {
			if (radio[i].isSelected()) {
				radioselecionado = (byte) (i + 1);
			}
		}
		return radioselecionado;
	}
}
