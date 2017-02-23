package br.com.controller;

import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.PieDataset;

@SuppressWarnings("serial")
public abstract class GraficoControl extends JFrame {

	public GraficoControl(String title) {
		super(title);
	}

	/**
	 * Este é o principal método desta classe. Ele gera o gráfico propriamente
	 * dito. É necessário passar o título do gráfico, os dados através do
	 * PieDataset, o tamanho do gráfico em fatias e um array de Strings que são
	 * as legendas do gráfico.
	 * 
	 * @param title
	 * @param dataset
	 * @param tamanhodoGrafico
	 * @param arg
	 * @return JFreeChart
	 */
	protected static JFreeChart createChart(String title, PieDataset dataset,
			int tamanhodoGrafico, String arg[]) {
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, true,
				true, true);
		chart.addSubtitle(new TextTitle("Data de emissão: " + Data.showDate()));

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})"));
		plot.setBackgroundPaint(Color.WHITE);
		plot.setOutlinePaint(null);
		Point2D center = new Point2D.Float(0, 0);
		float radius = 200;
		float[] dist = { 0.2f, 1.0f };

		// :::::::::cor do gráfico:::::::::
		for (int i = 0; i < tamanhodoGrafico; i++) {
			RadialGradientPaint p = new RadialGradientPaint(center, radius,
					dist, new Color[] { patternColor()[i], patternColor()[i] });
			plot.setSectionPaint(arg[i], p);
		}
		return chart;
	}

	/**
	 * Este método retorna um array do tipo {@link Color} que contém as cores
	 * que serão utilizados em todos os gráfico.
	 * 
	 * @return Color
	 */
	private static Color[] patternColor() {
		Color color[] = new Color[12];
		color[0] = new Color(80, 130, 189);
		color[1] = new Color(196, 84, 80);
		color[2] = new Color(156, 195, 92);
		color[3] = new Color(140, 96, 164);
		color[4] = new Color(74, 175, 198);
		color[5] = new Color(211, 108, 33);
		color[6] = new Color(197, 197, 197);
		color[7] = new Color(142, 142, 142);
		color[8] = new Color(243, 243, 243);
		color[9] = new Color(155, 183, 208);
		color[10] = new Color(253, 203, 81);
		color[11] = new Color(56, 69, 107);
		return color;
	}

	/**
	 * Este método retorna um array do tipo {@link Color} que contém as cores
	 * que serão utilizados em todos os gráfico.
	 * 
	 * @return Color
	 */
	@SuppressWarnings("unused")
	private static Color[] trisoftColor() {
		Color color[] = new Color[12];
		color[0] = new Color(56, 69, 107);
		color[1] = Color.BLUE;
		color[2] = new Color(19, 68, 144);
		color[3] = new Color(94, 109, 157);
		color[4] = new Color(144, 166, 233);
		color[5] = new Color(207, 241, 254);
		color[6] = new Color(84, 68, 218);
		color[7] = new Color(127, 166, 202);
		color[8] = new Color(3, 140, 252);
		color[9] = new Color(23, 178, 244);
		color[10] = new Color(207, 230, 254);
		color[11] = new Color(243, 243, 243);
		return color;
	}
}
