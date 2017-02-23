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
 * Esta classe gera um gráfico que informa a faixa etária dos internos da
 * comunidade.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class GraficoFaixaEtaria extends GraficoControl {
	private static final byte TAMANHO_DO_GRAFICO = 6;
	private static String[] idade;

	/**
	 * Consstrutor padrão da classe
	 */
	public GraficoFaixaEtaria() {
		super(Config.tituloJanela);
		setContentPane(createDemoPanel());
	}

	/**
	 * Cria a janela que receberá o gráfico
	 * 
	 * @return JPanel
	 */
	private static JPanel createDemoPanel() {
		JFreeChart chart = createChart("Gráfico por faixa etária de internos",
				createDataset(), TAMANHO_DO_GRAFICO, idade);
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

		int[] value = new int[TAMANHO_DO_GRAFICO];
		value[0] = dao.idadeEntre(0, 12);
		value[1] = dao.idadeEntre(13, 17);
		value[2] = dao.idadeEntre(18, 25);
		value[3] = dao.idadeEntre(26, 35);
		value[4] = dao.idadeEntre(36, 45);
		value[5] = dao.idadeAcima(46);

		idade = new String[TAMANHO_DO_GRAFICO];
		idade[0] = "De 0 a 12 anos";
		idade[1] = "De 13 a 17 anos";
		idade[2] = "De 18 a 25 anos";
		idade[3] = "De 26 a 35 anos";
		idade[4] = "De 36 a 45 anos";
		idade[5] = "Acima de 45 anos";

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < TAMANHO_DO_GRAFICO; i++) {
			dataset.setValue(idade[i], value[i]);
		}
		return dataset;
	}
}
