package br.com.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Classe utilizada para se trabalhar e converter tipos de datas como Date e
 * Calendar. � recomend�vel que seja utilizada como atributo de classes Bean.
 * 
 * @author Luiz Alberto
 * 
 */
public class Data {

	private Calendar calendar;
	private Date date;

	/**
	 * M�todo usado em conjunto com o metodo stringToDate para manipular a data
	 * passada pelo usu�rio.
	 * 
	 * @param date
	 * @return dd/MM/yyyy
	 */
	public static String dateFormat(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return (sdf.format(date));
	}

	/**
	 * Este m�todo retorna um n�mero inteiro correspondente ao ano atual do
	 * sistema.
	 * 
	 * @return int
	 */
	public static int year() {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		int ano = Integer.parseInt(df.format(c.getTime()));
		return ano;
	}

	/**
	 * Este m�todo retorna uma String com a hora do sistema no formato hh:mm:ss.
	 * 
	 * @return hora do sistema
	 */
	public static String hourFormat() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");

		return sdf.format(date);
	}

	/**
	 * M�todo usado em conjunto com o metodo data para manipular a data passada
	 * pelo usu�rio.
	 * 
	 * @param calendar
	 * @param string
	 * @return calendar
	 */
	public static Calendar stringToDate(Calendar calendar, String string) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(string);
			calendar = Calendar.getInstance();
			calendar.setTime(date);
		} catch (Exception exception) {
			Show.erro("Erro de convers�o de data.\n" + exception.getMessage());
			exception.printStackTrace();
		}
		return calendar;
	}

	/**
	 * M�todo que converte um atributo do tipo Date em um atributo do tipo
	 * String.
	 * 
	 * @param date
	 * @return String
	 */
	public static String dateToString(Date date) {
		StringBuilder s = new StringBuilder();
		s.append("");
		s.append(date);
		return s.toString();
	}

	/**
	 * M�todo que converte um atributo do tipo Calendar em um atributo do tipo
	 * String.
	 * 
	 * @param calendar
	 * @return String
	 */
	public static String dateToString(Calendar calendar) {
		StringBuilder s = new StringBuilder();
		s.append("");
		s.append(calendar);
		return s.toString();
	}

	/**
	 * M�todo que modifica a String no formato aaaa-MM-dd para dd/MM/aaaa
	 * 
	 * @param data
	 * @return String com a data formatada
	 */
	public static String brFormat(String data) {
		StringBuilder s = new StringBuilder();
		try {
			String ano = data.substring(0, 4);
			String mes = data.substring(5, 7);
			String dia = data.substring(8, 10);
			s.append(dia);
			s.append("/");
			s.append(mes);
			s.append("/");
			s.append(ano);
			return s.toString();
		} catch (Exception e) {
			Show.erro("Erro no formato de data.\n" + e.getMessage());
			e.printStackTrace();
		}
		return s.toString();
	}

	/**
	 * Este m�todo pega uma data no formato dd/MM/aaaa e converte para o
	 * tradicional aaaa-MM-dd.
	 * 
	 * @param data
	 * @return String
	 */
	public static String traditionalFormat(String data) {
		StringBuilder s = new StringBuilder();
		try {
			String dia = data.substring(0, 2);
			String mes = data.substring(3, 5);
			String ano = data.substring(6, 10);
			s.append(ano);
			s.append("-");
			s.append(mes);
			s.append("-");
			s.append(dia);
			return s.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s.toString();
	}

	/**
	 * M�todo que modifica o Date no formato aaaa-MM-dd para dd/MM/aaaa
	 * 
	 * @param data
	 * @return String com a data formatada
	 */
	public static String brFormat(Date data) {
		StringBuilder s = new StringBuilder();
		try {
			String d = "" + data;
			String ano = d.substring(0, 4);
			String mes = d.substring(5, 7);
			String dia = d.substring(8, 10);
			s.append(dia);
			s.append("/");
			s.append(mes);
			s.append("/");
			s.append(ano);
			return s.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s.toString();
	}

	/**
	 * M�todo que pega uma String e retorna somente seus caracteres num�ricos.
	 * 
	 * @param x
	 * @return String contendo somente os n�meros
	 */
	public static String onlyNumbers(String x) {
		String numero = "";
		for (int i = 0; i < x.length(); i++) {
			if (x.charAt(i) >= 48 && x.charAt(i) <= 57) {
				numero += x.charAt(i);
			}
		}
		return numero;
	}

	/**
	 * Este m�todo pega a data do sistema e retorna em formato de String. Note
	 * que o atributo currentLocale possui os par�metros "pt","BR" declarados, o
	 * que significa que seu retorno ser� no formato DD/MM/AAAA.
	 * 
	 * @return String
	 */
	static public String showDate() {
		Locale currentLocale = new Locale("pt", "BR");
		Date today;
		String dateOut;
		DateFormat dateFormatter;
		dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT,
				currentLocale);
		today = new Date();
		dateOut = dateFormatter.format(today);
		return dateOut;
	}

	/**
	 * Este m�todo retorna uma String com a data atual acrescida de poss�veis
	 * valores que o usu�rio possa inserir. O m�todo DateFormat.MEDIUM
	 * especifica que o formato da data ser� dd/mm/aaaa.
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return String
	 */
	public static String showDate(int day, int month, int year) {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DAY_OF_YEAR, day);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.YEAR, year);
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String dateOut = df.format(c.getTime());
		return dateOut;
	}

	/**
	 * Este m�todo tem a mesma funcionalidade que o anterior, por�m o usu�rio �
	 * quem insere a data para posteriormente ser manipulada. Caso ocorra algum
	 * erro, a data a ser retornada ser� a do sistema. A String data deve estar
	 * no formato dd/mm/aaaa.
	 * 
	 * @param stringData
	 * @param day
	 * @param month
	 * @param year
	 * @return String
	 */
	public static String showDate(String stringData, int day, int month,
			int year) {
		Date d;
		try {
			d = new SimpleDateFormat("dd/MM/yyyy").parse(stringData);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DAY_OF_YEAR, day);
			c.add(Calendar.MONTH, month);
			c.add(Calendar.YEAR, year);
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			String dateOut = df.format(c.getTime());
			return dateOut;
		} catch (ParseException e) {
			e.printStackTrace();
			return showDate();
		}
	}

	/**
	 * Retorna a data do sistema por extenso. Exemplo: 29 de Dezembro de 2007. *
	 * 
	 * @return String
	 */
	public static String dateTextLong() {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		String dateOut = df.format(c.getTime());
		return dateOut;
	}

	/**
	 * Retorna a data do sistema por extenso. Exemplo: S�bado, 29 de Dezembro de
	 * 2007.
	 * 
	 * @return String
	 */
	public static String dateTextFull() {
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
		String dateOut = df.format(c.getTime());
		return dateOut;
	}

	/**
	 * Este m�todo faz o retorno da data configurada no sistema operacional.
	 * 
	 * @return Calendar
	 */
	public static Calendar today() {
		return Calendar.getInstance();
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public String getDate() {
		return brFormat(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
