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
 * Esta classe mostra o gr�fico com os meses de altas dos internos. Obs.: O
 * gr�fico apresenta somente os internos passivos.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class GraficoAltasMensais extends GraficoControl {
	private static final byte TAMANHO_D0_GRAFICO = 12;
	private static String[] mes;

	/**
	 * Contrutor da classe.
	 */
	public GraficoAltasMensais() {
		super(Config.tituloJanela);
		setContentPane(createDemoPanel());
	}

	/**
	 * Cria a janela que receber� o gr�fico
	 * 
	 * @return JPanel
	 */
	private static JPanel createDemoPanel() {
		JFreeChart chart = createChart(
				"Gr�fico por m�s de alta de internos passivos",
				createDataset(), TAMANHO_D0_GRAFICO, mes);
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

		InternoDAO dao = new InternoDAO();

		int[] value = new int[TAMANHO_D0_GRAFICO];
		value[0] = dao.countMesAlta(1);
		value[1] = dao.countMesAlta(2);
		value[2] = dao.countMesAlta(3);
		value[3] = dao.countMesAlta(4);
		value[4] = dao.countMesAlta(5);
		value[5] = dao.countMesAlta(6);
		value[6] = dao.countMesAlta(7);
		value[7] = dao.countMesAlta(8);
		value[8] = dao.countMesAlta(9);
		value[9] = dao.countMesAlta(10);
		value[10] = dao.countMesAlta(11);
		value[11] = dao.countMesAlta(12);

		mes = new String[TAMANHO_D0_GRAFICO];
		mes[0] = "Janeiro";
		mes[1] = "Fevereiro";
		mes[2] = "Mar�o";
		mes[3] = "Abril";
		mes[4] = "Maio";
		mes[5] = "Junho";
		mes[6] = "Julho";
		mes[7] = "Agosto";
		mes[8] = "Setembro";
		mes[9] = "Outubro";
		mes[10] = "Novembro";
		mes[11] = "Dezembro";

		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < TAMANHO_D0_GRAFICO; i++) {
			dataset.setValue(mes[i], value[i]);
		}
		return dataset;
	}
}
