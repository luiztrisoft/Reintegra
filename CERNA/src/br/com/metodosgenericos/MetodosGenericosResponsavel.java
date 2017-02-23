package br.com.metodosgenericos;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Esta classe contém os métodos genéricos pertinentes ao responsável do interno
 * e que servem para todas as necessidades. Um exemplo é o método limpaCampos
 * que é usado após um cadastro ou então ao apertar um botão.
 * 
 * @author Luiz Alberto
 * 
 */
public class MetodosGenericosResponsavel extends MetodosGenericos {

	/**
	 * Este método retorna um array de Strings para ser usado nos JLabels do
	 * formulário de cadastro de responsáveis.
	 * 
	 * @return String[]
	 */
	public String[] txtLabels() {
		String[] txtLabel = { "Nome(*)", "RG", "CPF(*)", "Dt. Nasc.(*)", "Endereço",
				"Bairro", "Cidade", "UF", "Telefone", "Celular", "E-Mail",
				"Naturalidade", "UF", "País", "Profissão", "Pai", "Mãe" };
		return txtLabel;
	}

	/**
	 * Método responsável por limpar os campos do formulário do responsável.
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
