package br.com.ajudadecusto.frames;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import br.com.ajudadecusto.controle.ControleAjudadeCusto;
import br.com.ajudadecusto.metodos.DeletaAjudadeCustoMetodos;
import br.com.bean.ControleBean;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.controller.TableControl;
import br.com.dao.ControleDAO;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta � a classe principal do m�dulo ajuda de custo pois apresenta a camada
 * visual da tela de controle. Nesta tela est�o contidas as informa��es gerais
 * da ajuda de custo dos internos como o nome do interno, a data de vencimento,
 * o valor pago, as observa��es etc.
 * 
 * @author Luiz Alberto
 * 
 */

@SuppressWarnings("serial")
public class TelaControle extends JFrame {

	private byte status;
	private byte contribuicao;
	private boolean todaAjudadeCusto;
	private byte pagamentoAjudadeCusto;
	private int width, height;
	private String convenio;

	private Container c;
	private DefaultTableModel coluna;
	private JButton botao[];
	private JTable tabela;
	private JScrollPane scroll;
	private TableControl funcao;
	private ControleAjudadeCusto controle;

	/**
	 * Contrutor padr�o da classe.
	 * 
	 * @param todaAjudadeCusto
	 * @param pagamentoAjudadeCusto
	 * @param status
	 * @param contribuicao
	 * @param convenio
	 */
	public TelaControle(boolean todaAjudadeCusto, byte pagamentoAjudadeCusto,
			byte status, byte contribuicao, String convenio) {
		super(Config.tituloJanela);
		setLayout(null);
		setSize(Config.WIDTH, Config.HEIGHT);
		setExtendedState(MAXIMIZED_BOTH);
		c = getContentPane();
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));

		this.todaAjudadeCusto = todaAjudadeCusto;
		this.pagamentoAjudadeCusto = pagamentoAjudadeCusto;
		this.status = status;
		this.contribuicao = contribuicao;
		this.convenio = convenio;

		controle = new ControleAjudadeCusto();

		coluna = new DefaultTableModel();
		tabela = new JTable(coluna);
		scroll = new JScrollPane(tabela);
		c.add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		tabelaControle();

	}

	private void tabelaControle() {
		addColumn();
		columnProperties();
		columnDimension();
		addItem();
		botoes();

	}

	/**
	 * Este � um dos m�todos principais da classe pois ele � o respons�vel por
	 * buscar os dados no banco e popular a tabela de controle com eles.
	 */
	private void addItem() {
		ControleDAO dao = new ControleDAO();
		List<ControleBean> lista = dao.getControle(todaAjudadeCusto,
				pagamentoAjudadeCusto, status, contribuicao, convenio);

		CellRendererToolTip renderer[] = new CellRendererToolTip[6];
		for (int i = 0; i < renderer.length; i++) {
			renderer[i] = new CellRendererToolTip();
		}

		int linha = 0;
		for (ControleBean controle : lista) {
			coluna.addRow(new Object[] { controle.getIdAjudadeCusto(),
					controle.getNomeInterno(), controle.getEntrada().getDate(),
					controle.getV1().getDate(), controle.getV2().getDate(),
					controle.getV3().getDate(), controle.getV4().getDate(),
					controle.getV5().getDate(), controle.getV6().getDate(),
					controle.getFormadePagamento(),
					controle.getObservacaoGeral(),
					controle.getResponsavelPagto() });

			/*
			 * =================================================
			 * 
			 * PREENCHE OS DADOS DO TOOLTIPTEXT
			 * 
			 * Este script insere o texto tooltip nas colunas das parcelas da
			 * ajuda de custo.
			 * 
			 * =================================================
			 */
			renderer[0].addToolTip(linha, controle.getOb1());
			tabela.getColumn("1� venc.").setCellRenderer(renderer[0]);
			renderer[1].addToolTip(linha, controle.getOb2());
			tabela.getColumn("2� venc.").setCellRenderer(renderer[1]);
			renderer[2].addToolTip(linha, controle.getOb3());
			tabela.getColumn("3� venc.").setCellRenderer(renderer[2]);
			renderer[3].addToolTip(linha, controle.getOb4());
			tabela.getColumn("4� venc.").setCellRenderer(renderer[3]);
			renderer[4].addToolTip(linha, controle.getOb5());
			tabela.getColumn("5� venc.").setCellRenderer(renderer[4]);
			renderer[5].addToolTip(linha, controle.getOb6());
			tabela.getColumn("6� venc.").setCellRenderer(renderer[5]);

			/*
			 * =================================================
			 * 
			 * FILTRO DE AJUDA DE CUSTO
			 * 
			 * Este script remove as colunas que possuam TODAS as parcelas em
			 * branco. A fun��o � mostrar apenas as ajudas de custo que possuam
			 * pend�ncias quando o usu�rio selecionar esta op��o.
			 * 
			 * =================================================
			 */
			for (int j = 0; j < coluna.getRowCount(); j++) {
				if (coluna.getValueAt(j, 3).equals("")
						&& coluna.getValueAt(j, 3).equals("")
						&& coluna.getValueAt(j, 4).equals("")
						&& coluna.getValueAt(j, 5).equals("")
						&& coluna.getValueAt(j, 6).equals("")
						&& coluna.getValueAt(j, 7).equals("")
						&& coluna.getValueAt(j, 8).equals("")) {
					coluna.removeRow(j);
				}
			}
			linha++;
		}
		tabela.changeSelection(0, 0, false, false);
	}

	/**
	 * Este m�todo adiciona o cabe�alho da tabela de ajuda de custo.
	 */
	private void addColumn() {
		coluna.addColumn("C�d.");
		coluna.addColumn("Nome");
		coluna.addColumn("Entrada");
		coluna.addColumn("1� venc.");
		coluna.addColumn("2� venc.");
		coluna.addColumn("3� venc.");
		coluna.addColumn("4� venc.");
		coluna.addColumn("5� venc.");
		coluna.addColumn("6� venc.");
		coluna.addColumn("Forma pagto");
		coluna.addColumn("Obs.:");
		coluna.addColumn("Resp. pagto");
	}

	/**
	 * Este m�todo trata das propriedades da coluna como sua largura e a
	 * impossibilidade de se fazer a reordena��o.
	 */
	private void columnProperties() {
		tabela.getColumnModel().getColumn(0).setPreferredWidth(30);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(250);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(6).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(7).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(8).setPreferredWidth(60);
		tabela.getColumnModel().getColumn(9).setPreferredWidth(100);
		tabela.getColumnModel().getColumn(10).setPreferredWidth(250);
		tabela.getColumnModel().getColumn(11).setPreferredWidth(150);

		funcao = new TableControl();
		funcao.modelarTabela(tabela);

	}

	/**
	 * Este m�todo define a dimens�o da coluna, que ter� o mesmo tamanho da
	 * tela.
	 */
	private void columnDimension() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrnsize = toolkit.getScreenSize();
		width = (int) scrnsize.getWidth();
		height = (int) scrnsize.getHeight() - 150;
		scroll.setBounds(0, 0, width, height);

	}

	/**
	 * Este m�todo implementa os bot�es necess�rios a classe que s�o o alterar,
	 * quitar e deletar ajuda de custo.
	 */
	private void botoes() {
		botao = new JButton[5];
		String txtButtons[] = { "Cadastrar", "Alterar", "Quitar", "Deletar",
				"Consultar" };
		String pathImages[] = { "telacontrole/novo.png",
				"telacontrole/update.png", "telacontrole/quitar.png",
				"telacontrole/delete.png", "telacontrole/table.png" };

		for (int i = 0; i < botao.length; i++) {
			URL url = resources.Recursos.class.getResource(pathImages[i]);
			botao[i] = new JButton(txtButtons[i], new ImageIcon(url));
			c.add(botao[i]);
		}

		botao[0].setBounds(50, height + 40, 120, 30);
		botao[1].setBounds(180, height + 40, 120, 30);
		botao[2].setBounds(310, height + 40, 120, 30);
		botao[3].setBounds(440, height + 40, 120, 30);
		botao[4].setBounds(570, height + 40, 120, 30);

		// ::::::::cadastrar ajuda de custo::::::::
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle.cadastroAjudadeCusto();
				updateTable();
			}
		});

		// ::::::::alterar ajuda de custo::::::::
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaAlteracao();
				updateTable();
			}
		});

		// ::::::::quitar ajuda de custo::::::::
		botao[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaQuitacao();
				updateTable();
			}
		});

		// ::::::::remover ajuda de custo::::::::
		botao[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmarRemocao();
				updateTable();
			}
		});

		// ::::::::consultar cpf::::::::
		botao[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MetodosGenericosInterno mgi = new MetodosGenericosInterno();
				mgi.tabelaCPFs();
			}
		});

	}

	/**
	 * Este m�todo abre a tela de altera��o de ajuda de custo para o usu�rio
	 * inserir coment�rios, editar o valor das parcelas, a forma de pagamento, o
	 * valor pago pelo cliente etc.
	 */
	private void telaAlteracao() {
		int i = 0;
		i = (int) tabela.getValueAt(tabela.getSelectedRow(), 0);

		Integer idAjudadeCusto = new Integer(i);

		TelaAlteracaoAjudadeCusto view = new TelaAlteracaoAjudadeCusto(null,
				Config.tituloJanela, true, idAjudadeCusto);
		view.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela de quita��o de ajuda de custo para o usu�rio
	 * selecionar a parcela que dever� ser quitada, o valor pago pelo
	 * respons�vel e o coment�rio respectivo.
	 */
	private void telaQuitacao() {
		int idAjudadeCusto = 0;
		idAjudadeCusto = (int) tabela.getValueAt(tabela.getSelectedRow(), 0);

		TelaQuitacaoAjudadeCusto view = new TelaQuitacaoAjudadeCusto(null,
				Config.tituloJanela, true, idAjudadeCusto);
		view.setVisible(true);
	}

	/**
	 * Este m�todo remove a ajuda de custo da base de dados completamente se o
	 * usu�rio confirmar a op��o.
	 */
	private void confirmarRemocao() {
		boolean confirmarRemocao = Show
				.caixaConfirmacao("<html><h3>Deseja realmente deletar a ajuda de custo selecionada?");
		if (confirmarRemocao == true) {
			remover();
		}
	}

	/**
	 * Este m�todo � invocado ap�s o usu�rio confirmar a remo��o da ajuda de
	 * custo selecionada da base de dados. Ele invoca o m�todo deletar da classe
	 * DeletaAjudadeCustoMetodos passando o ID da ajuda de custo selecionado.
	 */
	private void remover() {
		int idAjudadeCusto = 0;
		idAjudadeCusto = (int) tabela.getValueAt(tabela.getSelectedRow(), 0);

		DeletaAjudadeCustoMetodos funcao = new DeletaAjudadeCustoMetodos();
		funcao.deletar(idAjudadeCusto);
	}

	/**
	 * Este m�todo realiza a atualiza��o da tabela atrav�s da chamada aos
	 * m�todos removeItem e addItem, limpando e repopulando-a com os novos
	 * dados.
	 */
	private void updateTable() {
		removeItem();
		addItem();
	}

	/**
	 * Este m�todo remove todas as linhas da tabela de ajuda de custo. Sua
	 * fun��o principal � auxiliar a atualiza��o da tabela. Ap�s executar
	 * qualquer opera��o b�sica.
	 */
	private void removeItem() {
		funcao.removeItem(coluna);
	}

	/**
	 * Esta classe � usada para adicionar o tool tip text em uma c�lula
	 * espec�fica da tabela.
	 * 
	 * @author Luiz Alberto
	 * 
	 */
	class CellRendererToolTip extends DefaultTableCellRenderer {
		private Map<Long, String> tooltip = new HashMap<Long, String>();

		private int row;

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component c = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			this.row = row;
			return c;
		}

		public String getToolTipText() {
			return tooltip.get(new Long(row));
		}

		public void addToolTip(int row, String text) {
			tooltip.put(new Long(row), text);
		}
	}
}