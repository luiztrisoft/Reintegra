package br.com.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Esta classe é utilizada para calcular a idade baseada na data de nascimento
 * no formato DD/MM/AAAA.
 * 
 * @author Luiz Alberto
 * 
 */
public class CalculoIdade {

	/**
	 * Este método retorna o intervalo de dias entre duas datas.
	 * 
	 * @param dataInicialBR
	 * @param dataFinalBR
	 * @return String
	 * @throws ParseException
	 */
	private static String contaDias(String dataInicialBR, String dataFinalBR)
			throws ParseException {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);

		Date dataInicio = df.parse(dataInicialBR);
		Date dataFim = df.parse(dataFinalBR);
		long dt = (dataFim.getTime() - dataInicio.getTime()) + 3600000;
		Long diasCorridosAnoLong = (dt / 86400000L);

		Integer diasDecorridosInt = Integer.valueOf(diasCorridosAnoLong
				.toString());

		String diasDecorridos = String.valueOf(diasDecorridosInt);

		return diasDecorridos;

	}

	/**
	 * Este método realiza o cálculo da idade.
	 * 
	 * @param dataDoMeuNascimento
	 * @return BigDecimal
	 * @throws ParseException
	 */
	private static BigDecimal calculaIdade(String dataDoMeuNascimento)
			throws ParseException {
		BigDecimal qtdDias = new BigDecimal(contaDias(dataDoMeuNascimento,
				Data.showDate()));
		BigDecimal ano = new BigDecimal(365.25);
		BigDecimal idade = qtdDias.divide(ano, 0, RoundingMode.DOWN);

		return idade;
	}

	/**
	 * Este método retorna a idade de acordo com a data de nascimento.
	 * 
	 * @param dateNasc
	 * @return int
	 */
	public static int calculoIdade(String dateNasc) {
		int idade = 0;
		try {
			BigDecimal bigDecimal;
			bigDecimal = calculaIdade(dateNasc);

			StringBuilder sb = new StringBuilder();
			sb.append("");
			sb.append(bigDecimal);

			idade = Integer.parseInt(sb.toString());

			return idade;
		} catch (ParseException e) {
			e.printStackTrace();
			return idade;
		}
	}
}
