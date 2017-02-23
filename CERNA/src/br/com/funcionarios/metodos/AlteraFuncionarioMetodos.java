package br.com.funcionarios.metodos;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.FuncionarioBean;
import br.com.controller.Data;
import br.com.controller.Show;
import br.com.dao.FuncionarioDAO;
import br.com.metodosgenericos.MetodosGenericosFuncionario;

/**
 * Esta classe se comunica com a classe FuncionarioDAO para alterar os dados do
 * funcionário através co CPF passado como parâmetro.
 * 
 * @author Luiz Alberto
 * 
 */
public class AlteraFuncionarioMetodos {

	/**
	 * Este é o método que passa os parâmetros para a classe FuncionarioDAO
	 * fazer a alteração dos dados do funcionários.
	 * 
	 * @param textField
	 * @param radioButton
	 * @param imagePath
	 * @param observacoes
	 */
	public void alteraFuncionario(JTextField[] textField,
			JRadioButton[] radioButton, String imagePath,
			JTextArea observacoes, JLabel[] label) {

		MetodosGenericosFuncionario metFuncionario = new MetodosGenericosFuncionario();
		FuncionarioDAO dao = new FuncionarioDAO();
		byte tres = 3;
		if (dao.existenciaCPF(textField[1].getText()) == true) {
			if (metFuncionario.validaCampo(textField[0].getText(),tres) == true
					&& metFuncionario.validaCampo(textField[3].getText(),tres) == true
					&& metFuncionario.validaCampo(textField[5].getText(),tres) == true
					&& metFuncionario.validaCampo(textField[6].getText(),tres) == true
					&& metFuncionario.validaCampo(textField[7].getText(),tres) == true
					&& metFuncionario.validaCampo(textField[9].getText(),2) == true) {

				FuncionarioBean funcionario = new FuncionarioBean();

				funcionario.setNome(textField[0].getText());
				funcionario.setCpf(textField[1].getText());
				funcionario.setTelefone(textField[2].getText());
				funcionario.getNascimento().setCalendar(Data.stringToDate(null,
						textField[3].getText()));
				funcionario.setEmail(textField[4].getText());
				funcionario.setEndereco(textField[5].getText());
				funcionario.setBairro(textField[6].getText());
				funcionario.setCidade(textField[7].getText());
				funcionario.setFiliacao(textField[8].getText());
				funcionario.setCargo(textField[9].getText());

				try {
					String fotoAtual = textField[10].getText();
				//	funcionario.setFoto(metFuncionario.transferirFoto(imagePath, fotoAtual));
				//	funcionario.setFoto(metFuncionario.transferirFotoPorCompartilhamento(imagePath, fotoAtual));
					funcionario.setFoto(metFuncionario.transferirFoto(imagePath, fotoAtual));
				} catch (Exception e) {
					e.printStackTrace();
					funcionario.setFoto("");
				}
				funcionario.setObservacao(observacoes.getText());

				for (int i = 0; i < radioButton.length; i++) {
					if (radioButton[i].isSelected()) {
						funcionario.setStatus(radioButton[i].getText());
						dao.alterarFuncionario(funcionario);
					}
				}

				MetodosGenericosFuncionario mgf = new MetodosGenericosFuncionario();
				mgf.limpaCampos(textField, radioButton, observacoes, label);

			} else {
				MetodosGenericosFuncionario.msgValidacao();
			}

		} else {
			Show.alerta("O CPF informado não está cadastrado na base de dados.");
		}
	}
}
