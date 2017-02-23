package br.com.agenda.frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.com.bean.AgendaBean;
import br.com.controller.Data;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.controller.TableControl;
import br.com.dao.AgendaDAO;

/**
 * Esta classe é responsável por construir a tela de agenda do sistema. Nesta
 * screen o usuário pode registrar a data e o recado/evento.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaAgenda extends JDialog {

	private Container c;
	private JButton botao[];
	private JLabel label;
	private JTextField campo[];
	private JPanel panel;

	private JTable tabela;
	private DefaultTableModel coluna;
	private JScrollPane scroll;

	private TableControl funcao;
	private AgendaDAO dao;

	/**
	 * Método construtor padrão da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public TelaAgenda(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(Config.WIDTH, Config.HEIGHT);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		screen();

	}

	/**
	 * Este método constrói a tela do módulo de agenda. Aqui possui os botões de
	 * registro e exclusão do evento, os campos com data para verificar os
	 * recados do período e uma tabela com os registros inseridos.
	 */
	private void screen() {
		final byte QUANT_BOTOES = 3;
		final byte QUANT_CAMPOS = 2;
		Font bold = new Font("Tahoma", Font.BOLD, 12);
		Font plain = new Font("Tahoma", Font.PLAIN, 12);

		// ::::::::botões::::::::
		String txtButtons[] = { "Adicionar", "Remover", "Verificar" };
		String pathImages[] = { "telaagenda/notes_edit.png",
				"telaagenda/notes_delete.png", "telaagenda/search.png" };

		botao = new JButton[QUANT_BOTOES];
		for (int i = 0; i < botao.length; i++) {
			URL url = resources.Recursos.class.getResource(pathImages[i]);
			botao[i] = new JButton(/* txtButtons[i], */new ImageIcon(url));
			botao[i].setToolTipText(txtButtons[i]);
			botao[i].setFont(bold);
			c.add(botao[i]);
		}

		label = new JLabel("a");
		label.setFont(plain);
		c.add(label);

		// ::::::::campos de datas::::::::
		campo = new JTextField[QUANT_CAMPOS];
		for (int i = 0; i < campo.length; i++) {
			try {
				campo[i] = new JFormattedTextField(new MaskFormatter(
						"##/##/####"));
				c.add(campo[i]);
				campo[i].setFont(plain);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		campo[0].setText(Data.showDate());
		campo[1].setText(Data.showDate(0, 0, 5));

		// ::::::::Jpanel::::::::
		panel = new JPanel();
		c.add(panel);
		panel.setBorder(BorderFactory.createTitledBorder("Agenda"));
		panel.setBackground(new Color(Config.RED, 230, Config.BLUE));
		panel.setBounds(5, 5, 780, 60);

		// ::::::::tabela::::::::
		coluna = new DefaultTableModel();
		coluna.addColumn("Cód.");
		coluna.addColumn("Data");
		coluna.addColumn("Evento");
		tabela = new JTable(coluna);
		scroll = new JScrollPane(tabela);
		c.add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		funcao = new TableControl();
		funcao.modelarTabela(tabela);

		columnProperties();
		addItem();

		// ::::::::adicionar evento::::::::
		botao[0].setBounds(20, 20, 35, 35);

		// ::::::::excluir evento::::::::
		botao[1].setBounds(60, 20, 35, 35);

		// ::::::::verificar evento::::::::
		campo[0].setBounds(110, 25, 100, 25);
		label.setBounds(225, 25, 20, 25);
		campo[1].setBounds(250, 25, 100, 25);
		botao[2].setBounds(360, 20, 35, 35);

		// ::::::::tabela de eventos::::::::
		scroll.setBounds(10, 80, 770, 480);

		// ::::::::funções dos botões::::::::

		// ::::::::incluir::::::::
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaInserir();
				updateTable();
			}
		});

		// ::::::::excluir::::::::
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletaEvento();
				updateTable();
			}
		});

		// ::::::::pesquisar::::::::
		botao[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
	}

	/**
	 * Este método trata das propriedades de largura das colunas.
	 */
	private void columnProperties() {
		tabela.getColumnModel().getColumn(0).setMaxWidth(40);
		tabela.getColumnModel().getColumn(0).setMaxWidth(80);
		tabela.getColumnModel().getColumn(1).setMaxWidth(700);
	}

	/*
	 * ===============================================
	 * 
	 * FUNÇÃO DOS BOTÕES DA TELA AGENDA
	 * 
	 * Estas são as funções básicas dos botões da tela do módulo de agenda que
	 * servem para incluir(abrir a tela de inclusão), deletar e pesquisar
	 * eventos cadastrados.
	 * 
	 * ===============================================
	 */

	/**
	 * Este método abre a tela de inclusão de registros da agenda.
	 */
	private void telaInserir() {
		TelaIncluirEvento tela = new TelaIncluirEvento(null,
				Config.tituloJanela, true);
		tela.setVisible(true);
	}

	/**
	 * Este método envia o id da agenda para a classe {@link AgendaDAO} para que
	 * tenha seu atributo 'ATIVO' alterado para não ser mais visualizado na
	 * tabela de eventos.
	 */
	private void deletaEvento() {
		Integer idAgenda = (Integer) tabela.getValueAt(tabela.getSelectedRow(),
				0);

		StringBuilder sb = new StringBuilder();
		sb.append("<html>Deseja excluir o registro <b>Nº ");
		sb.append(idAgenda);
		sb.append("</b> ?");

		boolean confirm = Show.caixaConfirmacao(sb.toString());

		if (confirm == true) {
			dao = new AgendaDAO();
			dao.delete(idAgenda);
		}
	}

	/*
	 * ==============================================
	 * 
	 * ATUALIZAÇÃO DA VIEW
	 * 
	 * Os scripts a seguir atualizam os dados que são vistos na tela. Ao efetuar
	 * qualquer operação de inclusão, remoção ou até consulta de eventos, os
	 * dados da tabela são imediatamente atualizados.
	 * 
	 * ==============================================
	 */

	/**
	 * Este método realiza a atualização da tabela através da chamada aos
	 * métodos removeItem e addItem, limpando e repopulando-a com os novos
	 * dados.
	 */
	private void updateTable() {
		removeItem();
		addItem();
	}

	/**
	 * Este é um dos métodos principais da classe pois ele é o responsável por
	 * buscar a lista de registros de eventos no banco e popular a tabela da
	 * view com eles.
	 */
	private void addItem() {
		dao = new AgendaDAO();
		List<AgendaBean> lista = dao.agendaList(campo[0].getText(),
				campo[1].getText());
		for (AgendaBean agendaBean : lista) {
			int id = agendaBean.getId();
			String data = agendaBean.getDia().getDate();
			String evento = agendaBean.getEvento();
			coluna.addRow(new Object[] { id, data, evento });
		}
		tabela.changeSelection(0, 0, false, false);
	}

	/**
	 * Este método remove todas as linhas da tabela de ajuda de custo. Sua
	 * função principal é auxiliar a atualização da tabela. Após executar
	 * qualquer operação básica.
	 */
	private void removeItem() {
		funcao.removeItem(coluna);
	}

}
