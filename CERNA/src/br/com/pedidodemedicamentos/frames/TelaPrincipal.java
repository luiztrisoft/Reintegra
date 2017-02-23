package br.com.pedidodemedicamentos.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jfree.ui.RefineryUtilities;

import br.com.bean.PedidoMedicamentosBeanTabela;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.controller.TableControl;
import br.com.dao.PedidoMedicamentosDAO;
import br.com.pedidodemedicamentos.graficos.GraficoPedidoMedicamentos;
import br.com.pedidodemedicamentos.metodos.DeletaPedidoMetodos;
import br.com.pedidodemedicamentos.metodos.InternoBridge;
import br.com.pedidodemedicamentos.relatorios.RelatorioGeral;
import br.com.pedidodemedicamentos.relatorios.RelatorioIndividual;

/**
 * Esta classe gera a tela principal deste m�dulo que d� acesso as demais.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaPrincipal extends JDialog {

	byte statusPagamento;
	private String dataInicial;
	private String dataFinal;
	private DecimalFormat df;
	private InternoBridge internoBridge;
	private TableControl funcao;
	private Container c;
	private JButton botao[];
	private JScrollPane scroll;
	private DefaultTableModel coluna;
	private JTable tabela;

	/**
	 * Contrutor padr�o da classe que recebe o intervalo de datas e o status do
	 * pagamento comopar�metros de filtragem na hora de detalhar o pedido de
	 * medicamentos.
	 * 
	 * @param jf
	 * @param s
	 * @param b
	 * @param dataInicial
	 * @param dataFinal
	 * @param statusPagamento
	 */
	public TelaPrincipal(JFrame jf, String s, boolean b, byte statusPagamento,
			String dataInicial, String dataFinal) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(Config.WIDTH, Config.HEIGHT);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);
		this.statusPagamento = statusPagamento;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		df = new DecimalFormat();
		df.applyPattern("#0.00");
		screen();
	}

	/**
	 * Este m�todo constr�i a parte visual da tela de par�metro de pedido de
	 * medicamentos.
	 */
	private void screen() {
		final byte QTD_BOTAO = 7;

		Font plain = new Font("Tahoma", Font.PLAIN, 12);

		// ::::::::JButton::::::::
		String txtButton[] = { "<html><center>Detalhe",
				"<html><center>Cadastro<br>", "<html><center>Exclus�o<br>",
				"<html><center>Relat�rio<br>geral",
				"<html><center>Relat�rio<br>individual",
				"<html><center>Consultar<br>CPF",
				"<html><center>Gr�fico de<br>pedidos" };
		String imagePath[] = { "telapedidodemedicamentos/world.png",
				"telapedidodemedicamentos/add.png",
				"telapedidodemedicamentos/delete.png",
				"telapedidodemedicamentos/pages_written.png",
				"telapedidodemedicamentos/page_written.png",
				"telapedidodemedicamentos/info.png",
				"telapedidodemedicamentos/chart.png" };
		botao = new JButton[QTD_BOTAO];
		for (int i = 0; i < botao.length; i++) {
			URL url = resources.Recursos.class.getResource(imagePath[i]);
			botao[i] = new JButton(txtButton[i], new ImageIcon(url));
			c.add(botao[i]);
			botao[i].setHorizontalTextPosition(SwingConstants.CENTER);
			botao[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			botao[i].setContentAreaFilled(false);
			botao[i].setFont(plain);
		}

		// ::::::::tabela::::::::
		String nomeColuna[] = { "C�d.", "Nome do interno", "Medicamento",
				"Data do pedido", "Pre�o", "Pago" };
		coluna = new DefaultTableModel();
		for (int i = 0; i < nomeColuna.length; i++) {
			coluna.addColumn(nomeColuna[i]);
		}
		tabela = new JTable(coluna);
		scroll = new JScrollPane(tabela);
		c.add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// ::::::::modela e preenche a tabela com os dados::::::::
		columnProperties();

		/*
		 * =====================================
		 * 
		 * POSICIONAMENTO DOS OBJETOS
		 * 
		 * Estes scripts s�o respons�veis pela disposi��o dos itens na tela de
		 * pedido de medicamentos
		 * 
		 * =====================================
		 */

		// ::::::::bot�es::::::::
		byte width = 100;
		byte height = 100;
		for (int i = 0; i < botao.length; i++) {
			botao[i].setBounds(width * i, 10, width, height);
		}

		// ::::::::tabela::::::::
		scroll.setBounds(0, 130, 795, 440);

		/*
		 * =====================================
		 * 
		 * FUN��ES DOS BOT�ES
		 * 
		 * Estes scripts s�o respons�veis pela fun��o dos bot�es da tela.
		 * 
		 * =====================================
		 */

		// ::::::::detalhe::::::::
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaDetalhe();
				updateTable();
			}
		});

		// ::::::::cadastro::::::::
		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
				updateTable();
			}
		});

		// ::::::::exclus�o::::::::
		botao[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
				updateTable();
			}
		});

		// ::::::::relat�rio geral::::::::
		botao[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioGeral();
			}
		});

		// ::::::::relat�rio individual::::::::
		botao[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioIndividual();
			}
		});

		// ::::::::consultar CPF::::::::
		botao[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultaCPF();
			}
		});

		// ::::::::gr�fico de pedidos::::::::
		botao[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graficoPedidos();
			}
		});

	}

	/**
	 * Este m�todo abre a tela com o pedido para que possa ter seus
	 * valores/par�metros alterados. Os dados ser�o recuperados a partir do
	 * atributo idPedido(int) informado a classe TelaDetalhe.
	 */
	private void telaDetalhe() {
		int idPedido = (int) tabela.getValueAt(tabela.getSelectedRow(), 0);
		String nomeInterno = (String) tabela.getValueAt(
				tabela.getSelectedRow(), 1);
		TelaDetalhe tela = new TelaDetalhe(null, Config.tituloJanela, true,
				idPedido, nomeInterno);
		tela.setVisible(true);
	}

	/**
	 * Este m�todo � usado no cadastro de pedido de medicamentos.
	 */
	private void insert() {
		String cpf = Show.caixaTexto("Digite o CPF do interno");

		internoBridge = new InternoBridge();
		boolean existe = internoBridge.cpfExists(cpf);

		if (existe == true) {
			TelaCadastro tela = new TelaCadastro(null, Config.tituloJanela,
					true, cpf);
			tela.setVisible(true);
		}
	}

	/**
	 * Este m�todo remove um pedido de medicamento especificado por seu ID.
	 */
	private void delete() {
		int idPedido = (int) tabela.getValueAt(tabela.getSelectedRow(), 0);

		StringBuilder msg = new StringBuilder();
		msg.append("<html>Deseja remover o pedido <b>n� ");
		msg.append(idPedido);
		msg.append(" ?");

		boolean confirm = Show.caixaConfirmacao(msg.toString());

		if (confirm == true) {
			DeletaPedidoMetodos funcao = new DeletaPedidoMetodos();
			funcao.delete(idPedido);
		}
	}

	/**
	 * Este m�todo gera o pedido geral de medicamentos em formato PDF.
	 */
	private void relatorioGeral() {
		RelatorioGeral relatorio = new RelatorioGeral();
		relatorio.pdf(statusPagamento, dataInicial, dataFinal);
	}

	/**
	 * Este m�todo abre o ralat�rio de pedidos de um interno especificado por
	 * seu CPF.
	 */
	private void relatorioIndividual() {
		String cpf = Show.caixaTexto("Digite o CPF do interno");

		internoBridge = new InternoBridge();
		boolean existe = internoBridge.cpfExists(cpf);

		if (existe == true) {
			RelatorioIndividual relatorio = new RelatorioIndividual();
			relatorio.cpf(statusPagamento, cpf, dataInicial, dataFinal);
		}

	}

	/**
	 * Este m�todo abre a tabela de CPF dos internos para verifica��o dos nomes
	 * e respectivos CPFs.
	 */
	private void consultaCPF() {
		internoBridge = new InternoBridge();
		internoBridge.tabelaCPFs();
	}

	/**
	 * Este m�todo gera o gr�fico que demonstra a quantidade de pedidos pagos e
	 * a quantidade de pedidos pendentes.
	 */
	private void graficoPedidos() {
		dispose();
		GraficoPedidoMedicamentos grafico = new GraficoPedidoMedicamentos(
				dataInicial, dataFinal);
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/*
	 * ===============================================
	 * 
	 * PROPRIEDADES DA TABELA DE DADOS
	 * 
	 * Os seguintes scripts s�o utilizados para manuten��o da tabela de dados
	 * como realizar sua atualiza��o e definir a largura de cada coluna por
	 * exemplo.
	 * 
	 * ===============================================
	 */

	/**
	 * Este m�todo � utilizado para realizar a atualiza��o dos dados da tabela.
	 */
	private void updateTable() {
		removeItem();
		addItem();
	}

	/**
	 * M�todo que popula a tabela com as informa��es de pedidos contidas na base
	 * de dados.
	 */
	private void addItem() {
		PedidoMedicamentosDAO dao = new PedidoMedicamentosDAO();
		// dao.getPedidos(statusPagamento, dataInicial, dataFinal, coluna);

		List<PedidoMedicamentosBeanTabela> lista = dao.getList(statusPagamento,
				dataInicial, dataFinal);
		String pagto = "";
		for (PedidoMedicamentosBeanTabela pmbt : lista) {
			if (pmbt.getPago().equals("N")) {
				pagto = "N�o";
			} else if (pmbt.getPago().equals("S")) {
				pagto = "Sim";
			}

			coluna.addRow(new Object[] { pmbt.getId(), pmbt.getNomeInterno(),
					pmbt.getMedicamento(), pmbt.getDataPedido().getDate(),
					df.format(pmbt.getPreco()), pagto });
		}

		tabela.changeSelection(0, 0, false, false);
	}

	/**
	 * Este m�todo remove todos os itens da tabela de dados.
	 */
	private void removeItem() {
		funcao.removeItem(coluna);
	}

	/**
	 * Este m�todo trata das propriedades da tabela como seus itens/informa��es,
	 * cores, fontes e largura das colunas.
	 */
	private void columnProperties() {
		funcao = new TableControl();
		funcao.modelarTabela(tabela);
		addItem();
		tabela.getColumnModel().getColumn(0).setMaxWidth(40);
		tabela.getColumnModel().getColumn(1).setMaxWidth(400);
		tabela.getColumnModel().getColumn(2).setMaxWidth(300);
		tabela.getColumnModel().getColumn(3).setMaxWidth(100);
		tabela.getColumnModel().getColumn(4).setMaxWidth(70);
		tabela.getColumnModel().getColumn(5).setMaxWidth(50);
	}
}
