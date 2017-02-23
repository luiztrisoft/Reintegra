package br.com.pedidodemedicamentos.graficos;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import br.com.controller.GraficoControl;
import br.com.controller.Config;
import br.com.dao.PedidoMedicamentosDAO;

/**
 * Esta classe gera um gr�fico que informa a quantidade de pedidos pagos e a
 * quantidade de pedidos pendentes.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class GraficoPedidoMedicamentos extends GraficoControl {

	private static byte tamanhoGrafico = 2;
	private static String[] args;
	private static int value[];

	private static String dataMinima;
	private static String dataMaxima;

	/**
	 * Construtor padr�o da classe.
	 * 
	 * @param dataMinima
	 * @param dataMaxima
	 */
	public GraficoPedidoMedicamentos(String dataMinima, String dataMaxima) {
		super(Config.tituloJanela);

		GraficoPedidoMedicamentos.dataMinima = dataMinima;
		GraficoPedidoMedicamentos.dataMaxima = dataMaxima;

		setContentPane(createDemoPanel());
	}

	/**
	 * Cria a janela que receber� o gr�fico
	 * 
	 * @return JPanel
	 */
	private static JPanel createDemoPanel() {
		StringBuilder sb = new StringBuilder();
		sb.append("Per�odo: ");
		sb.append(dataMinima);
		sb.append(" a ");
		sb.append(dataMaxima);
		JFreeChart chart = createChart("Gr�fico de pedidos de medicamentos",
				createDataset(), tamanhoGrafico, args);
		chart.addSubtitle(new TextTitle(sb.toString()));
		ChartPanel panel = new ChartPanel(chart);
		panel.setMouseWheelEnabled(true);
		return panel;
	}

	/**
	 * Este m�todo gera e adiciona os dados do gr�fico.
	 * 
	 * @return PieDataset
	 */
	private static PieDataset createDataset() {

		PedidoMedicamentosDAO dao = new PedidoMedicamentosDAO();

		value = new int[tamanhoGrafico];
		value[0] = dao.getCountPago("S", dataMinima, dataMaxima);
		value[1] = dao.getCountPago("N", dataMinima, dataMaxima);

		args = new String[tamanhoGrafico];
		args[0] = "Pedidos pagos=" + value[0];
		args[1] = "Pedidos pendentes=" + value[1];

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < tamanhoGrafico; i++) {
			dataset.setValue(args[i], value[i]);
		}
		return dataset;
	}

}
