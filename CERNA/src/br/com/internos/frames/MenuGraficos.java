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
 * Esta classe abre a tela de sele��o de gr�ficos de internos.
 * 
 * @author Luiz Alberto
 * 
 */

public class MenuGraficos {

	private String[] opcoes;

	/**
	 * Contrutor padr�o da classe.
	 */
	public MenuGraficos() {
		opcoes = new String[5];
		opcoes[0] = "Tipos de depend�ncia";
		opcoes[1] = "Faixa et�ria dos internos";
		opcoes[2] = "Admiss�es separadas por m�s";
		opcoes[3] = "Altas separadas por m�s";
		opcoes[4] = "Altas separadas por tipo";

		String opcao = Show.caixaOpcao(
				"<html><H4>Selecione o gr�fico desejado", opcoes);

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
	 * Este m�todo abre a tela com o gr�fico de depend�ncia dos internos.
	 */
	private void graficoTipodeDependencia() {
		Save.log(Config.usuarioLogado,
				"Solicitou o gr�fico [Depend�ncia dos internos]");
		GraficoTipodeDependencia grafico = new GraficoTipodeDependencia();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela com o gr�fico de faixa et�ria dos internos.
	 */
	private void graficoFaixaEtaria() {
		Save.log(Config.usuarioLogado, "Solicitou o gr�fico [Faixa et�ria]");
		GraficoFaixaEtaria grafico = new GraficoFaixaEtaria();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela com o gr�fico de alta dos internos.
	 */
	private void graficoTipodeAltas() {
		Save.log(Config.usuarioLogado, "Solicitou o gr�fico [Tipo de altas]");
		GraficoTipodeAltas grafico = new GraficoTipodeAltas();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela com o gr�fico com os meses de admiss�o de
	 * internos.
	 */
	private void graficoAdmissoesMensais() {
		Save.log(Config.usuarioLogado,
				"Solicitou o gr�fico [Admiss�es mensais]");
		GraficoAdmissoesMensais grafico = new GraficoAdmissoesMensais();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

	/**
	 * Este m�todo abre a tela com o gr�fico de meses de alta dos internos
	 * passivos.
	 */
	private void graficoAltasMensais() {
		Save.log(Config.usuarioLogado, "Solicitou o gr�fico [Altas mensais]");
		GraficoAltasMensais grafico = new GraficoAltasMensais();
		grafico.pack();
		RefineryUtilities.centerFrameOnScreen(grafico);
		grafico.setVisible(true);
	}

}
