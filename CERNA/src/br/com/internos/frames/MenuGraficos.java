package br.com.internos.frames;

import org.jfree.ui.RefineryUtilities;

import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.internos.graficos.GraficoAdmissoesMensais;
import br.com.internos.graficos.GraficoAltasMensais;
import br.com.internos.graficos.GraficoFaixaEtaria;
import br.com.internos.graficos.GraficoTipodeAltas;
import br.com.internos.graficos.GraficoTipodeDependencia;

/**
 * Esta classe abre a tela de seleção de gráficos de internos.
 * 
 * @author Luiz Alberto
 * 
 */

public class MenuGraficos {

	private String[] opcoes;

	/**
	 * Contrutor padrão da classe.
	 */
	public MenuGraficos() {
		opcoes = new String[5];
		opcoes[0] = "Tipos de dependência";
		opcoes[1] = "Faixa etária dos internos";
		opcoes[2] = "Admissões separadas por mês";
		opcoes[3] = "Altas separadas por mês";
		opcoes[4] = "Altas separadas por tipo";

		String opcao = Show.caixaOpcao(
				"<html><H4>Selecione o gráfico desejado", opcoes);

		if (opcao.equals(opcoes[0])) {
			graficoTipodeDependencia();
		} else if (opcao.equals(opcoes[1])) {
			graficoFaixaEtaria();
		} else if (opcao.equals(opcoes[2])) {
			graficoAdmissoesMensais();
		} else if (opcao.equals(opcoes[3])) {
			graficoAltasMensais();
		} else if (opcao.equals(opcoes[4])) {
			graficoTipodeAltas();
		}

	}

	/**
	 * Este método abre a tela com o gráfico de dependência dos internos.
	 */
	private void graficoTipodeDependencia() {
		Save.log(Config.usuarioLogado,
				"Solicitou o gráfico [Dependência dos internos]");
		GraficoTipodeDependencia grafico = new GraficoTipodeDependencia();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/**
	 * Este método abre a tela com o gráfico de faixa etária dos internos.
	 */
	private void graficoFaixaEtaria() {
		Save.log(Config.usuarioLogado, "Solicitou o gráfico [Faixa etária]");
		GraficoFaixaEtaria grafico = new GraficoFaixaEtaria();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/**
	 * Este método abre a tela com o gráfico de alta dos internos.
	 */
	private void graficoTipodeAltas() {
		Save.log(Config.usuarioLogado, "Solicitou o gráfico [Tipo de altas]");
		GraficoTipodeAltas grafico = new GraficoTipodeAltas();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/**
	 * Este método abre a tela com o gráfico com os meses de admissão de
	 * internos.
	 */
	private void graficoAdmissoesMensais() {
		Save.log(Config.usuarioLogado,
				"Solicitou o gráfico [Admissões mensais]");
		GraficoAdmissoesMensais grafico = new GraficoAdmissoesMensais();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/**
	 * Este método abre a tela com o gráfico de meses de alta dos internos
	 * passivos.
	 */
	private void graficoAltasMensais() {
		Save.log(Config.usuarioLogado, "Solicitou o gráfico [Altas mensais]");
		GraficoAltasMensais grafico = new GraficoAltasMensais();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

}
