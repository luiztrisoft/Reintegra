package br.com.ajudadecusto.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosAjudadeCusto;

/**
 * Esta classe apresenta a view contendo a tabela com as ajudas de custo do
 * interno para realizar a quita��o das parcelas.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class SelecaoContaQuitacao extends JDialog {

	private int idInterno;
	private Container c;
	private DefaultTableModel coluna;
	private JButton botao[];
	private JScrollPane scroll;
	private JTable tabela;
	private MetodosGenericosAjudadeCusto metodos;

	/**
	 * Contrutor padr�o da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 * @param idInterno
	 */
	public SelecaoContaQuitacao(JFrame jf, String s, boolean b, int idInterno) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(470, 280);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		this.idInterno = idInterno;
		metodos = new MetodosGenericosAjudadeCusto();

		label();
		tabela();
		botoes();
	}

	/**
	 * Este m�todo cria um JLabel com a frase 'Selecione uma ajuda de custo' que
	 * ficar� no topo da view.
	 */
	private void label() {
		JLabel label = new JLabel("Selecione uma ajuda de custo");
		label.setFont(new Font("Arial", Font.BOLD, 14));
		c.add(label);
		label.setBounds(10, 10, 400, 20);
	}

	/**
	 * Este m�todo apresenta a tabela contendo as ajudas de custo do interno
	 * informado.
	 */
	private void tabela() {
		coluna = new DefaultTableModel();
		scroll = new JScrollPane();
		tabela = new JTable();

		coluna.addColumn("Cod. ajuda de custo");
		coluna.addColumn("Nome do interno");

		tabela = new JTable(coluna);
		tabela.setPreferredScrollableViewportSize(new Dimension(100, 100));
		tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(300);

		scroll = new JScrollPane(tabela);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		c.add(scroll);
		scroll.setBounds(10, 30, 445, 150);

		metodos.modelarTabela(tabela, 2);
		metodos.tabelaAjudadeCusto(coluna, idInterno);

		tabela.changeSelection(0, 0, false, false);
	}

	/**
	 * Este m�todo constr�i os bot�es e as suas respectivas funcionalidades na
	 * tela de sele��o de ajuda de custo.
	 */
	private void botoes() {
		String imagePath[] = { "telaajudadecusto/accept.png",
				"telaajudadecusto/cancel.png" };
		String txtBotao[] = { "OK", "Cancelar" };
		botao = new JButton[2];

		for (int i = 0; i < txtBotao.length; i++) {
			URL url = resources.Recursos.class.getResource(imagePath[i]);
			botao[i] = new JButton(txtBotao[i], new ImageIcon(url));
			c.add(botao[i]);
		}

		// ::::::::botao OK::::::::
		botao[0].setBounds(10, 200, 100, 30);
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaQuitacao();
			}
		});

		// ::::::::botao Cancelar::::::::
		botao[1].setBounds(120, 200, 100, 30);
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Este m�todo abre a tela de quita��o de ajuda de custo para o usu�rio
	 * selecionar a parcela que dever� ser quitada, o valor pago pelo
	 * respons�vel e o coment�rio respectivo.
	 */
	private void telaQuitacao() {
		dispose();
		String str = "";
		str = (String) tabela.getValueAt(tabela.getSelectedRow(), 0);

		Integer idAjudadeCusto = new Integer(str);
		TelaQuitacaoAjudadeCusto view = new TelaQuitacaoAjudadeCusto(null,
				Config.tituloJanela, true, idAjudadeCusto);
		view.setVisible(true);
	}
}
