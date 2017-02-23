package br.com.funcionarios.metodos;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.FuncionarioBean;
import br.com.dao.FuncionarioDAO;
import br.com.metodosgenericos.MetodosGenericosFuncionario;

/**
 * Classe responsável por se comunicar com FuncionarioDAO e recuperar os dados
 * de um funcionário através do CPF passado como parâmetro.
 * 
 * @author Luiz Alberto
 * 
 */
public class BuscaFuncionarioMetodos {

	/**
	 * Este método realiza uma verificação para ver se o CPF existe no banco de
	 * dados e se ele é válido. Se as duas condições forem positivas ele invoca
	 * o método buscarFuncionario(string) da classe FuncionarioDAO para popular
	 * os campos do formulário com os dados do funcionário.
	 * 
	 * @param textField
	 * @param radioButton
	 * @param labelFoto
	 * @param observacoes
	 */
	public void buscaFuncionario(JTextField[] textField,
			JRadioButton[] radioButton, JLabel[] labelFoto,
			JTextArea observacoes) {

		MetodosGenericosFuncionario metFuncionario = new MetodosGenericosFuncionario();
		FuncionarioDAO dao = new FuncionarioDAO();

		FuncionarioBean funcionario = dao.buscarFuncionario(textField[0]
				.getText());
		textField[0].setText(funcionario.getNome());
		textField[1].setText(funcionario.getCpf());
		textField[2].setText(funcionario.getTelefone());
		textField[3].setText(funcionario.getNascimento().getDate());
		textField[4].setText(funcionario.getEmail());
		textField[5].setText(funcionario.getEndereco());
		textField[6].setText(funcionario.getBairro());
		textField[7].setText(funcionario.getCidade());
		textField[8].setText(funcionario.getFiliacao());
		textField[9].setText(funcionario.getCargo());

		textField[10].setText(funcionario.getFoto());
	//	metFuncionario.fotoPessoa(textField[10].getText(), labelFoto);
	//	metFuncionario.fotoPessoaPorCompartilhamento(textField[10].getText(), labelFoto);
		metFuncionario.fotoPessoa(textField[10].getText(), labelFoto[labelFoto.length - 1]);

		if (funcionario.getStatus().equalsIgnoreCase("remunerado")) {
			radioButton[0].setSelected(true);
		} else if (funcionario.getStatus().equalsIgnoreCase("voluntário")) {
			radioButton[1].setSelected(true);
		} else {
			radioButton[2].setSelected(true);
		}
		observacoes.setText(funcionario.getObservacao());
	}

}
