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
 * Esta classe informa o mês de entrada do internos através de gráficos na tela.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class GraficoAdmissoesMensais extends GraficoControl {

	private static final byte TAMANHO_D0_GRAFICO = 12;
	private static String[] mes;

	/**
	 * Contrutor da classe.
	 */
	public GraficoAdmissoesMensais() {
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
				"Gráfico por mês de admissão de internos", createDataset(),
				TAMANHO_D0_GRAFICO, mes);
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
		value[0] = dao.countMesAdmissao(1);
		value[1] = dao.countMesAdmissao(2);
		value[2] = dao.countMesAdmissao(3);
		value[3] = dao.countMesAdmissao(4);
		value[4] = dao.countMesAdmissao(5);
		value[5] = dao.countMesAdmissao(6);
		value[6] = dao.countMesAdmissao(7);
		value[7] = dao.countMesAdmissao(8);
		value[8] = dao.countMesAdmissao(9);
		value[9] = dao.countMesAdmissao(10);
		value[10] = dao.countMesAdmissao(11);
		value[11] = dao.countMesAdmissao(12);

		mes = new String[TAMANHO_D0_GRAFICO];
		mes[0] = "Janeiro";
		mes[1] = "Fevereiro";
		mes[2] = "Março";
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
