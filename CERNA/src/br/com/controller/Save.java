package br.com.controller;

import java.util.Calendar;

import br.com.bean.LogBean;
import br.com.dao.LogDAO;

/**
 * Esta classe é responsável por se comunicar com a classe LogDAO a fim de
 * enviar os dados das ações dos usuários para a base de dados
 * 
 * @author Luiz Alberto
 * 
 */
public class Save {
	/**
	 * Este método é responsável por registrar o log no banco de dados,
	 * informando o usuário que efetuou a ação, o que ele fez, a data e a hora.
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