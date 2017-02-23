package br.com.controller;

import java.util.Calendar;

import br.com.bean.LogBean;
import br.com.dao.LogDAO;

/**
 * Esta classe � respons�vel por se comunicar com a classe LogDAO a fim de
 * enviar os dados das a��es dos usu�rios para a base de dados
 * 
 * @author Luiz Alberto
 * 
 */
public class Save {
	/**
	 * Este m�todo � respons�vel por registrar o log no banco de dados,
	 * informando o usu�rio que efetuou a a��o, o que ele fez, a data e a hora.
	 * 
	 * @param usuario
	 * @param acao
	 */
	public static void log(String usuario, String acao) {
		LogBean log;
		log = new LogBean();
		log.setUsuario(usuario);
		log.setEvento(acao);
		log.setData(Calendar.getInstance());
		log.setHora(Data.hourFormat());

		LogDAO dao = new LogDAO();
		dao.salvarLOG(log);
	}
}