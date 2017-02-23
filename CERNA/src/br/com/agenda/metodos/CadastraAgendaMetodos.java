package br.com.agenda.metodos;

import javax.swing.JTextField;

import br.com.bean.AgendaBean;
import br.com.controller.Data;
import br.com.dao.AgendaDAO;

/**
 * Esta classe informa a classe AgendaDAO os par�metros dos eventos que devem
 * ser registrados para a entidade agenda.
 * 
 * @author Luiz Alberto
 * 
 */
public class CadastraAgendaMetodos {

	/**
	 * Este m�todo envia os dados do evento para persist�ncia na classe
	 * {@link AgendaDAO}.
	 * 
	 * @param campo
	 */
	public void cadastrar(JTextField[] campo) {
		AgendaBean agenda = new AgendaBean();
		agenda.setAtivo("S");
		agenda.getDia()
				.setCalendar(Data.stringToDate(null, campo[0].getText()));
		agenda.setEvento(campo[1].getText());

		AgendaDAO dao = new AgendaDAO();
		dao.insert(agenda);
	}
}
