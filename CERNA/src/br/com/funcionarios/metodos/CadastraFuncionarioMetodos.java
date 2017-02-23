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
 * Classe resopons�vel por controlar as funcionalidades e m�todos da entidade
 * Funcionario.
 * 
 * @author Luiz Alberto
 * 
 */
public class CadastraFuncionarioMetodos {

	/**
	 * M�todo que pega os par�metros do formul�rio e envia para a classe
	 * FuncionarioDAO para inser��o na base de dados.
	 */
	public void cadastraFuncionario(JTextField[] textField,
			JRadioButton[] radioButton, String imagePath,
			JTextArea observacoes, JLabel[] label) {

		MetodosGenericosFuncionario mgf = new MetodosGenericosFuncionario();
		byte tres = 3;
		if (mgf.validaCampo(textField[0].getText(), tres) == true
				&& mgf.validaCampo(textField[3].getText(), tres) == true
				&& mgf.validaCampo(textField[5].getText(), tres) == true
				&& mgf.validaCampo(textField[6].getText(), tres) == true
				&& mgf.validaCampo(textField[7].getText(), tres) == true
				&& mgf.validaCampo(textField[9].getText(), 2) == true) {

			if (mgf.verCpf(textField[1].getText()) == true) {
				FuncionarioBean f = new FuncionarioBean();
				f.setNome(textField[0].getText());
				f.setCpf(textField[1].getText());
				f.setTelefone(textField[2].getText());
				f.getNascimento().setCalendar(
						Data.stringToDate(null, textField[3].getText()));
				f.setEmail(textField[4].getText());
				f.setEndereco(textField[5].getText());
				f.setBairro(textField[6].getText());
				f.setCidade(textField[7].getText());
				f.setFiliacao(textField[8].getText());
				f.setCargo(textField[9].getText());
				for (int i = 0; i < radioButton.length; i++) {
					if (radioButton[i].isSelected()) {
						f.setStatus(radioButton[i].getText());
					}
				}
				if (imagePath == null) {
					f.setFoto("n�o h� foto");
				} else {
				//	f.setFoto(mgf.transferirFoto(imagePath, ""));
				//	f.setFoto(mgf.transferirFotoPorCompartilhamento(imagePath, ""));
					f.setFoto(mgf.transferirFoto(imagePath, ""));
				}
				f.setObservacao(observacoes.getText());

				FuncionarioDAO dao = new FuncionarioDAO();
				dao.inserirFuncionario(f);

				mgf.limpaCampos(textField, radioButton, observacoes, label);
			} else {
				Show.alerta("CPF inv�lido!");
			}
		} else {
			MetodosGenericosFuncionario.msgValidacao();
		}

	}

}
