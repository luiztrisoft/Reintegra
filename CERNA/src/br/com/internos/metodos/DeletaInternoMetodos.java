package br.com.internos.metodos;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.InternoBean;
import br.com.controller.Show;
import br.com.dao.InternoDAO;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta classe se comunica com a classe {@link InternoDAO} para realizar a
 * exclusão do registro da base de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class DeletaInternoMetodos {

	MetodosGenericosInterno metodosInterno = new MetodosGenericosInterno();
	InternoDAO dao = new InternoDAO();

	public void deletaInterno(JTextField[] campo, JComboBox<String>[] combo,
			JTextArea area, JLabel[] label) {
		if (dao.verifyCPF(campo[3].getText()) == true) {
			try {
				InternoBean interno = new InternoBean();
				interno.setNome(campo[1].getText());
				interno.setCpf(campo[3].getText());

				dao.delete(interno);
				// ::::::::::::deleta interno e vinculo responsável::::::::::::
			//	if (dao.delete(interno) == true) {
			//	}

				metodosInterno
						.limpaCamposCadastro(campo, combo, area, label[0]);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			Show.alerta("O CPF informado não está cadastrado na base de dados.");
		}
	}
}
