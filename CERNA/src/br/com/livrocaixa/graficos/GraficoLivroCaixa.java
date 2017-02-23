package br.com.livrocaixa.graficos;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import br.com.bean.LivroCaixaBeanTabela;
import br.com.controller.GraficoControl;
import br.com.controller.Config;
import br.com.dao.LivroCaixaDAO;

/**
 * Esta classe gera um gráfico com os dados do registro de entradas e saídas do
 * livro caixa.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class GraficoLivroCaixa extends GraficoControl {

	private static String[] args;
	private static final byte TAMANHO_DO_GRAFICO = 2;

	private static String dataMinima;
	private static String dataMaxima;

	private static DecimalFormat df;

	/**
	 * Construtor padrão da classe.
	 */
	public GraficoLivroCaixa(String dataMinima, String dataMaxima) {
		super(Config.tituloJanela);

		GraficoLivroCaixa.dataMinima = dataMinima;
		GraficoLivroCaixa.dataMaxima = dataMaxima;

		setContentPane(createDemoPanel());
	}

	/**
	 * Cria a janela que receberá o gráfico
	 * 
	 * @return JPanel
	 */
	private static JPanel createDemoPanel() {
		StringBuilder sb = new StringBuilder();
		sb.append("Período: ");
		sb.append(dataMinima);
		sb.append(" a ");
		sb.append(dataMaxima);
		JFreeChart chart = createChart(
				"Gráfico de entradas e saídas do livro caixa", createDataset(),
				TAMANHO_DO_GRAFICO, args);
		chart.addSubtitle(new TextTitle(sb.toString()));
		ChartPanel panel = new ChartPanel(chart);
		panel.setMouseWheelEnabled(true);
		return panel;
	}

	/**
	 * Este método gera e adiciona os dados do gráfico.
	 * 
	 * @return PieDataset
	 */
	private static PieDataset createDataset() {
		LivroCaixaDAO dao = new LivroCaixaDAO();
		List<LivroCaixaBeanTabela> lista = dao.getListLivroCaixa(dataMinima,
				dataMaxima);

		double[] value = new double[TAMANHO_DO_GRAFICO];
		for (LivroCaixaBeanTabela registro : lista) {
			value[0] = value[0] + registro.getEntrada();
			value[1] = value[1] + registro.getSaida();
		}

		df = new DecimalFormat();
		df.applyPattern("#0.00");

		args = new String[TAMANHO_DO_GRAFICO];
		args[0] = "Entradas - R$ " + df.format(value[0]);
		args[1] = "Saídas - R$ " + df.format(value[1]);

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < TAMANHO_DO_GRAFICO; i++) {
			dataset.setValue(args[i], value[i]);
		}
		return dataset;
	}
}
