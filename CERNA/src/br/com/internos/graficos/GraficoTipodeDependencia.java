package br.com.internos.graficos;

import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import br.com.controller.GraficoControl;
import br.com.controller.Config;
import br.com.dao.DependenciaDAO;

/**
 * Esta classe gera um gráfico que informa os tipos de dependência dos internos.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class GraficoTipodeDependencia extends GraficoControl {

	private static List<String> listaDependencias;
	private static String tipoDependencia[];

	public GraficoTipodeDependencia() {
		super(Config.tituloJanela);
		setContentPane(createDemoPanel());
	}

	/**
	 * Cria a janela que receberá o gráfico
	 * 
	 * @return JPanel
	 */
	private static JPanel createDemoPanel() {

		JFreeChart chart = createChart(
				"Gráfico por tipo de dependência dos internos",
				createDataset(), listaDependencias.size(), tipoDependencia);

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
		DependenciaDAO dao = new DependenciaDAO();
		listaDependencias = dao.tiposdeDependencia();

		// ::::::::add quantidade de dependencias::::::::
		int[] value = new int[listaDependencias.size()];
		for (int i = 0; i < listaDependencias.size(); i++) {
			value[i] = dao.countTipoDependencia(listaDependencias.get(i));
		}

		// ::::::::add o tipo de dependência::::::::
		tipoDependencia = new String[listaDependencias.size()];
		for (int i = 0; i < listaDependencias.size(); i++) {
			tipoDependencia[i] = listaDependencias.get(i);
		}

		// ::::::::seta os valores do gráfico::::::::
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < listaDependencias.size(); i++) {
			dataset.setValue(tipoDependencia[i], value[i]);
		}
		return dataset;
	}

}
