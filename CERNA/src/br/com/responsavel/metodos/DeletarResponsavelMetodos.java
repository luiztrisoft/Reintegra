package br.com.responsavel.metodos;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import br.com.bean.ResponsavelBean;
import br.com.dao.ResponsavelDAO;
import br.com.metodosgenericos.MetodosGenericosResponsavel;

/**
 * Este m�todo se comunica com a classe ResponsavelDAO para efetuar a remo��o do
 * respons�vel da base de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class DeletarResponsavelMetodos {

	/**
	 * Este m�todo recupera alguns par�metros do respons�vel e envia para a
	 * classe ResponsavelDAO para remov�-lo do banco de dados.
	 * 
	 * @param campo
	 * @param combo
	 */
	public void removeResponsavel(JTextField[] campo, JComboBox<String>[] combo) {
		ResponsavelBean responsavel = new ResponsavelBean();
		responsavel.setNome(campo[0].getText());
		responsavel.setCpf(campo[2].getText());

		ResponsavelDAO dao = new ResponsavelDAO();
		dao.delete(responsavel);
		
		MetodosGenericosResponsavel mgr = new MetodosGenericosResponsavel();
		mgr.limpaCampos(campo, combo);
	}
}
