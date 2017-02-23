package br.com.metodosgenericos;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Esta classe cont�m os m�todos gen�ricos pertinentes ao respons�vel do interno
 * e que servem para todas as necessidades. Um exemplo � o m�todo limpaCampos
 * que � usado ap�s um cadastro ou ent�o ao apertar um bot�o.
 * 
 * @author Luiz Alberto
 * 
 */
public class MetodosGenericosResponsavel extends MetodosGenericos {

	/**
	 * Este m�todo retorna um array de Strings para ser usado nos JLabels do
	 * formul�rio de cadastro de respons�veis.
	 * 
	 * @return String[]
	 */
	public String[] txtLabels() {
		String[] txtLabel = { "Nome(*)", "RG", "CPF(*)", "Dt. Nasc.(*)", "Endere�o",
				"Bairro", "Cidade", "UF", "Telefone", "Celular", "E-Mail",
				"Naturalidade", "UF", "Pa�s", "Profiss�o", "Pai", "M�e" };
		return txtLabel;
	}

	/**
	 * M�todo respons�vel por limpar os campos do formul�rio do respons�vel.
	 * 
	 * @param campo
	 * @param combo
	 */
	public void limpaCampos(JTextField[] campo, JComboBox<String>[] combo) {
		for (int i = 0; i < campo.length; i++) {
			campo[i].setText("");
		}

		for (int i = 0; i < combo.length; i++) {
			combo[i].setSelectedIndex(21);
		}
	}

}
