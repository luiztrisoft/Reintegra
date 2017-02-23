package br.com.funcionarios.metodos;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.FuncionarioBean;
import br.com.controller.Show;
import br.com.dao.FuncionarioDAO;
import br.com.metodosgenericos.MetodosGenericosFuncionario;

/**
 * Esta classe recebe o valor do textField que corresponde ao CPF e o seta no
 * atributo cfp de funcionarioBean. Em seguida este parâmetro é enviado a classe
 * FuncinarioDAO para que seja feita a remoção do funcionário da base de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class DeletaFuncionarioMetodos {

	/**
	 * Método que recebe uma String do textField contendo o CPF do funcionário a
	 * ser removido na classe FuncionarioDAO, porém antes é feita um teste de
	 * validação do CPF. Se ele for válido, é feita uma nova verificação para
	 * ver se o CPF está cadastrado na base de dados.
	 * 
	 * @param textField
	 * @param radioButton
	 * @param observacoes
	 * @param label
	 */
	public void deletaFuncionario(JTextField[] textField,
			JRadioButton[] radioButton, JTextArea observacoes, JLabel[] label) {
		MetodosGenericosFuncionario metFuncionario = new MetodosGenericosFuncionario();
		FuncionarioDAO dao = new FuncionarioDAO();

		if (metFuncionario.verCpf(textField[1].getText()) == true) {
			if (dao.existenciaCPF(textField[1].getText()) == true) {
				try {
					FuncionarioBean funcionario = new FuncionarioBean();
					funcionario.setNome(textField[0].getText());
					funcionario.setCpf(textField[1].getText());
					dao.deletarFuncionario(funcionario);
					metFuncionario.limpaCampos(textField, radioButton,
							observacoes, label);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Show.alerta("O CPF informado não está cadastrado na base de dados.");
			}
		} else {
			Show.alerta("CPF inválido!");
		}
	}
}