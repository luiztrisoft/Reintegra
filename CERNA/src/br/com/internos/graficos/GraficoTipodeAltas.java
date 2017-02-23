package br.com.internos.graficos;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import br.com.controller.GraficoControl;
import br.com.controller.Config;
import br.com.dao.InternoDAO;

/**
 * Esta classe gera um gráfico que informa os tipos de alta que ocorrem na
 * comunidade.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class GraficoTipodeAltas extends GraficoControl {

	private static final byte TAMANHO_D0_GRAFICO = 4;
	private static String[] tipodeSaida;

	public GraficoTipodeAltas() {
		super(Config.tituloJanela);
		setContentPane(createDemoPanel());
	}

	/**
	 * Cria a janela que receberá o gráfico
	 * 
	 * @return JPanel
	 */
	private static JPanel createDemoPanel() {
		JFreeChart chart = createChart("Gráfico por tipo de alta de internos",
				createDataset(), TAMANHO_D0_GRAFICO, tipodeSaida);
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

		InternoDAO dao = new InternoDAO();

		int[] value = new int[TAMANHO_D0_GRAFICO];
		value[0] = dao.countTipoSaida(Config.TERAPEUTICO);
		value[1] = dao.countTipoSaida(Config.SOLICITADA);
		value[2] = dao.countTipoSaida(Config.ADMINISTRATIVA);
		value[3] = dao.countTipoSaida(Config.EVASAO);

		tipodeSaida = new String[TAMANHO_D0_GRAFICO];
		tipodeSaida[0] = Config.TERAPEUTICO;
		tipodeSaida[1] = Config.SOLICITADA;
		tipodeSaida[2] = Config.ADMINISTRATIVA;
		tipodeSaida[3] = Config.EVASAO;

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < TAMANHO_D0_GRAFICO; i++) {
			dataset.setValue(tipodeSaida[i], value[i]);
		}
		return dataset;
	}
}
