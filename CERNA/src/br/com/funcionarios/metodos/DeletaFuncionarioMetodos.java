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
 * atributo cfp de funcionarioBean. Em seguida este par�metro � enviado a classe
 * FuncinarioDAO para que seja feita a remo��o do funcion�rio da base de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class DeletaFuncionarioMetodos {

	/**
	 * M�todo que recebe uma String do textField contendo o CPF do funcion�rio a
	 * ser removido na classe FuncionarioDAO, por�m antes � feita um teste de
	 * valida��o do CPF. Se ele for v�lido, � feita uma nova verifica��o para
	 * ver se o CPF est� cadastrado na base de dados.
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
				Show.alerta("O CPF informado n�o est� cadastrado na base de dados.");
			}
		} else {
			Show.alerta("CPF inv�lido!");
		}
	}
}