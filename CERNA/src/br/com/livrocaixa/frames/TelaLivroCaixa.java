package br.com.livrocaixa.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DecimalFormat;
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

import org.jfree.ui.RefineryUtilities;

import br.com.bean.LivroCaixaBeanTabela;
import br.com.controller.Data;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.controller.TableControl;
import br.com.dao.LivroCaixaDAO;
import br.com.livrocaixa.graficos.GraficoLivroCaixa;
import br.com.livrocaixa.relatorios.RelatorioLivroCaixa;

/**
 * Esta view apresenta todas as funções pertinentes ao livro caixa como
 * inserção, remoção, consultas, relatórios etc.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaLivroCaixa extends JDialog {

	private double valor[];
	private Container c;
	private DecimalFormat df;
	private DefaultTableModel coluna;
	private JButton botao[];
	private JLabel label[];
	private JPanel panel[];
	private JScrollPane scroll;
	private String txtLabels[];
	private JTable tabela;
	private JTextField campo[];

	private TableControl funcao;
	private LivroCaixaDAO dao;

	/**
	 * Contrutor padrão da classe.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 */
	public TelaLivroCaixa(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(Config.WIDTH, Config.HEIGHT);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		df = new DecimalFormat();
		df.applyPattern("#0.00");

		valor = new double[6];
		livroCaixa();

	}

	/**
	 * Este método invoca os três métodos que formam a tela de controle de livro
	 * caixa.
	 */
	private void livroCaixa() {
		labels();
		botoes();
		campos();
		tabela();
		balanco();
		paineis();
	}

	/**
	 * Este método instancia os objetos do tipo JPanel necessários a construção
	 * da tela de controle de livro caixa.
	 */
	private void paineis() {
		panel = new JPanel[6];
		String txtPanels[] = { "Registro", "Período", "Exclusão", "Movimento",
				"Balanço geral", "Balanço parcial" };

		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel();
			c.add(panel[i]);
			panel[i].setBorder(BorderFactory.createTitledBorder(txtPanels[i]));
			panel[i].setBackground(new Color(Config.RED, 230, Config.BLUE));
		}

		// ::::::::registro::::::::
		panel[0].setBounds(10, 10, 115, 70);

		// ::::::::período::::::::
		panel[1].setBounds(125, 10, 595, 70);

		// ::::::::exclusão::::::::
		panel[2].setBounds(720, 10, 65, 70);

		// ::::::::tabela::::::::
		panel[3].setBounds(10, 80, 775, 340);

		// ::::::::balanço geral::::::::
		panel[4].setBounds(10, 420, 390, 140);

		// ::::::::balanço parcial::::::::
		panel[5].setBounds(400, 420, 385, 140);
	}

	/**
	 * Este método instancia os labels que serão utilizados porém a
	 * implementação fica para os outros métodos uma vez que existem labels
	 * específicos em cada parte da view do livro caixa.
	 */
	private void labels() {
		txtLabels = new String[14];
		txtLabels[0] = "De";
		txtLabels[1] = "a";
		txtLabels[2] = "Entradas";
		txtLabels[3] = "Saídas";
		txtLabels[4] = "Saldo";
		txtLabels[5] = "Entradas";
		txtLabels[6] = "Saídas";
		txtLabels[7] = "Saldo";

		label = new JLabel[14];
		for (int i = 0; i < label.length; i++) {
			label[i] = new JLabel(txtLabels[i]);
			c.add(label[i]);
		}

		Font font = new Font("Tahoma", Font.PLAIN, 12);
		Font bigFont = new Font("Tahoma", Font.BOLD, 16);

		label[0].setFont(font);
		label[1].setFont(font);

		label[2].setFont(bigFont);
		label[3].setFont(bigFont);
		label[4].setFont(bigFont);
		label[5].setFont(bigFont);
		label[6].setFont(bigFont);
		label[7].setFont(bigFont);
		label[8].setFont(bigFont);
		label[9].setFont(bigFont);
		label[10].setFont(bigFont);
		label[11].setFont(bigFont);
		label[12].setFont(bigFont);
		label[13].setFont(bigFont);

	}

	/**
	 * Este método contrói os botões e o campo onde o usuário insere as datas do
	 * período desejado.
	 */
	private void botoes() {
		byte bt = 40;
		botao = new JButton[6];
		String txtButtons[] = { "Registrar entrada no livro caixa",
				"Registrar saída no livro caixa",
				"Verificar o período selecionado", "Relatório do período",
				"Gráfico do período", "Estornar registro do livro caixa" };
		String imagePath[] = { "telalivrocaixa/up.png",
				"telalivrocaixa/down.png", "telalivrocaixa/search.png",
				"telalivrocaixa/pages.png", "telalivrocaixa/chart.png",
				"telalivrocaixa/delete.png" };

		for (int i = 0; i < botao.length; i++) {
			URL url = resources.Recursos.class.getResource(imagePath[i]);
			botao[i] = new JButton(new ImageIcon(url));
			botao[i].setToolTipText(txtButtons[i]);
			c.add(botao[i]);
		}

		/*
		 * ===============================================
		 * 
		 * FUNÇÕES E POSICIONAMENTO
		 * 
		 * Os scripts a seguir definem o posicionamento e as funções de cada
		 * JButton criado.
		 * 
		 * ===============================================
		 */

		// ::::::::registrar entrada::::::::
		botao[0].setBounds(20, 30, bt, bt);
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarEntrada();
				updateTable();
				atualizarBalanco();
			}
		});

		// ::::::::registrar saída::::::::
		botao[1].setBounds(70, 30, bt, bt);
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarSaida();
				updateTable();
				atualizarBalanco();
			}
		});

		// ::::::::verificar::::::::
		botao[2].setBounds(370, 30, bt, bt);
		botao[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTable();
				atualizarBalanco();
			}
		});

		// ::::::::relatório::::::::
		botao[3].setBounds(420, 30, bt, bt);
		botao[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarRelatorio();
				updateTable();
				atualizarBalanco();
			}
		});

		// ::::::::gráficos::::::::
		botao[4].setBounds(470, 30, bt, bt);
		botao[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gerarGrafico();
				updateTable();
				atualizarBalanco();
			}
		});

		// ::::::::excluir registro::::::::
		botao[5].setBounds(730, 30, bt, bt);
		botao[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirRegistro();
				updateTable();
				atualizarBalanco();
			}
		});

	}

	/**
	 * Este método apresenta na view os campos de texto que serão usados para
	 * inserir o período de busca do livro caixa.
	 */
	private void campos() {
		campo = new JTextField[2];
		Font font = new Font("tahoma", Font.PLAIN, 12);
		for (int i = 0; i < campo.length; i++) {
			try {
				campo[i] = new JFormattedTextField(new MaskFormatter(
						"##/##/####"));
				c.add(campo[i]);
				campo[i].setFont(font);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		campo[0].setText(Config.dataInicial());
		campo[1].setText(Data.showDate());

		// ::::::::data inicial::::::::
		label[0].setBounds(135, 40, 20, 20);
		campo[0].setBounds(160, 40, 80, 20);

		// ::::::::data final::::::::
		label[1].setBounds(250, 40, 20, 20);
		campo[1].setBounds(270, 40, 80, 20);
	}

	/**
	 * Este método apresenta na view a tabela com os dados do livro caixa
	 * selecionados pelo usuário.
	 */
	private void tabela() {

		coluna = new DefaultTableModel();
		coluna.addColumn("Cód.");
		coluna.addColumn("Data");
		coluna.addColumn("Categoria");
		coluna.addColumn("Observações");
		coluna.addColumn("Entrada");
		coluna.addColumn("Saída");
		coluna.addColumn("Saldo");

		tabela = new JTable(coluna);
		scroll = new JScrollPane(tabela);
		c.add(scroll);

		scroll.setBounds(20, 100, 755, 310);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		funcao = new TableControl();
		funcao.modelarTabela(tabela);

		columnProperties();

		addItem();
		atualizarBalanco();

	}

	/**
	 * Este método trata das propriedades de largura das colunas.
	 */
	private void columnProperties() {
		tabela.getColumnModel().getColumn(0).setMaxWidth(35);
		tabela.getColumnModel().getColumn(1).setMaxWidth(80);
		tabela.getColumnModel().getColumn(2).setMaxWidth(290);
		tabela.getColumnModel().getColumn(3).setMaxWidth(600);
		tabela.getColumnModel().getColumn(4).setMaxWidth(60);
		tabela.getColumnModel().getColumn(5).setMaxWidth(60);
		tabela.getColumnModel().getColumn(6).setMaxWidth(70);
	}

	/**
	 * Este método invoca os métodos balancoGeral e balancoPeriodo desta mesma
	 * classe.
	 */
	private void balanco() {
		balancoGeral();
		balancoPeriodo();
	}

	/**
	 * Este método é reponsável por apresentar na view o balanço geral do livro
	 * caixa.
	 */
	private void balancoGeral() {

		// ::::::::valores gerais::::::::

		// ::::::::entradas::::::::
		label[2].setBounds(50, 440, 300, 30);
		label[8].setBounds(180, 440, 300, 30);

		// ::::::::saídas::::::::
		label[3].setBounds(50, 480, 300, 30);
		label[9].setBounds(180, 480, 300, 30);

		// ::::::::saldo::::::::
		label[4].setBounds(50, 520, 300, 30);
		label[10].setBounds(180, 520, 300, 30);

		// ::::::::valores parciais::::::::

		// ::::::::entradas::::::::
		label[5].setBounds(450, 440, 300, 30);
		label[11].setBounds(580, 440, 300, 30);

		// ::::::::saídas::::::::
		label[6].setBounds(450, 480, 300, 30);
		label[12].setBounds(580, 480, 300, 30);

		// ::::::::saldo::::::::
		label[7].setBounds(450, 520, 300, 30);
		label[13].setBounds(580, 520, 300, 30);
	}

	/**
	 * Este método é responsável por apresentar na view o balanço por período
	 * selecionado do livro caixa.
	 */
	private void balancoPeriodo() {

	}

	/*
	 * ==============================================
	 * 
	 * ATUALIZAÇÃO DA VIEW
	 * 
	 * Os scripts a seguir atualizam os dados que são vistos na tela. Ao efetuar
	 * qualquer operação de inclusão, remoção ou até consulta do livro caixa, os
	 * dados da tabela e dos balanços geral e parcial são imediatamente
	 * atualizados.
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
	 * Este método remove todas as linhas da tabela de ajuda de custo. Sua
	 * função principal é auxiliar a atualização da tabela. Após executar
	 * qualquer operação básica.
	 */
	private void removeItem() {
		funcao.removeItem(coluna);
	}

	/**
	 * Este é um dos métodos principais da classe pois ele é o responsável por
	 * buscar a lista de registros do livro caixa no banco e popular a tabela da
	 * view com eles.
	 */
	private void addItem() {
		dao = new LivroCaixaDAO();
		List<LivroCaixaBeanTabela> lista = dao.getListLivroCaixa(
				campo[0].getText(), campo[1].getText());

		double soma = 0;
		// ::::::::preenche a tabela::::::::
		for (LivroCaixaBeanTabela registro : lista) {
			String dataRegistro = registro.getDataRegistro().getDate();
			soma = soma + registro.getEntrada() - registro.getSaida();
			coluna.addRow(new Object[] { registro.getId(), dataRegistro,
					registro.getTipo(), registro.getObservacao(),
					df.format(registro.getEntrada()),
					df.format(registro.getSaida()), df.format(soma) });
		}

		// ::::::::seleciona o primeiro item da tabela::::::::
		tabela.changeSelection(0, 0, false, false);

	}

	/**
	 * Este método é responsável invocar os métodos que atualizaram o balanço
	 * geral e o balanço parcial.
	 */
	private void atualizarBalanco() {
		dao = new LivroCaixaDAO();
		atualizarBalancoGeral();
		atualizarBalancoParcial();
		dao.closeConnection();
	}

	/**
	 * Este método atualiza os dados do balanço geral do livro caixa.
	 */
	private void atualizarBalancoGeral() {

		// ::::::::entrada geral::::::::
		valor[0] = dao.getEntradaGeral();
		txtLabels[8] = "R$ " + df.format(valor[0]);
		label[8].setText(txtLabels[8]);

		// ::::::::saída geral::::::::
		valor[1] = dao.getSaidaGeral();
		txtLabels[9] = "R$ " + df.format(valor[1]);
		label[9].setText(txtLabels[9]);

		// ::::::::saldo geral::::::::
		label[10].setText(saldo(valor[0], valor[1]));
	}

	/**
	 * Este método atualiza os dados do balanço parcial do livro caixa.
	 */
	private void atualizarBalancoParcial() {
		// ::::::::entrada parcial::::::::
		valor[3] = dao
				.getEntradaParcial(campo[0].getText(), campo[1].getText());
		txtLabels[11] = "R$ " + df.format(valor[3]);
		label[11].setText(txtLabels[11]);

		// ::::::::saída parcial::::::::
		valor[4] = dao.getSaidaParcial(campo[0].getText(), campo[1].getText());
		txtLabels[12] = "R$ " + df.format(valor[4]);
		label[12].setText(txtLabels[12]);

		// ::::::::saldo parcial::::::::
		label[13].setText(saldo(valor[3], valor[4]));
	}

	/**
	 * Este método recebe dois argumentos do tipo double que representa as
	 * entradas e saídas do livro caixa e retorna seu saldo representado em
	 * forma de String e formatado com estilo monetário.
	 * 
	 * @param entrada
	 * @param saida
	 * @return String
	 */
	private String saldo(double entrada, double saida) {
		double soma = entrada - saida;
		String saldo = "R$ " + df.format(soma);
		return saldo;
	}

	/*
	 * ===========================================
	 * 
	 * FUNÇÃO DOS BOTÕES
	 * 
	 * Estes métodos servem para abrir as telas de entrada e saída de valores do
	 * livro caixa, gerar os relatórios, os gráficos e efetuar as exclusões de
	 * registros.
	 * 
	 * ===========================================
	 */

	/**
	 * Este método abre a view de entrada de valores no livro caixa.
	 */
	private void registrarEntrada() {
		TelaEntradaLivroCaixa view = new TelaEntradaLivroCaixa(null,
				Config.tituloJanela, true);
		view.setVisible(true);
	}

	/**
	 * Este método abre a view de saída de valores no livro caixa.
	 */
	private void registrarSaida() {
		TelaSaidaLivroCaixa view = new TelaSaidaLivroCaixa(null,
				Config.tituloJanela, true);
		view.setVisible(true);
	}

	/**
	 * Este método gera o relatório do livro caixa.
	 */
	private void gerarRelatorio() {
		RelatorioLivroCaixa create = new RelatorioLivroCaixa();
		create.pdf(campo[0].getText(), campo[1].getText());
	}

	/**
	 * Este método gera o gráfico do livro caixa.
	 */
	private void gerarGrafico() {
		dispose();
		GraficoLivroCaixa grafico = new GraficoLivroCaixa(campo[0].getText(),
				campo[1].getText());
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/**
	 * Este método gera a exclusão da linha selecionada enviando o id do livro
	 * caixa para a classe LivroCaixaDAO como parâmetro.
	 */
	private void excluirRegistro() {
		Integer idLivroCaixa = (Integer) tabela.getValueAt(
				tabela.getSelectedRow(), 0);

		StringBuilder sb = new StringBuilder();
		sb.append("<html>Deseja excluir o registro <b>Nº ");
		sb.append(idLivroCaixa);
		sb.append("</b> ?");

		boolean confirm = Show.caixaConfirmacao(sb.toString());

		if (confirm == true) {
			dao = new LivroCaixaDAO();
			dao.delete(idLivroCaixa);
		}
	}
}
