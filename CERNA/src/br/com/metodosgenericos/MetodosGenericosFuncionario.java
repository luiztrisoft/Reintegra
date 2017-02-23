package br.com.metodosgenericos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.DepartamentoBean;
import br.com.controller.Show;
import br.com.dao.DepartamentoDAO;

/**
 * Esta classe contém os métodos genéricos pertinentes ao funcionário e que
 * servem para todas as necessidades. Um exemplo é o método limpaCampos que é
 * usado após um cadastro ou então ao apertar um botão.
 * 
 * @author Luiz Alberto
 * 
 */
public class MetodosGenericosFuncionario extends MetodosGenericos {

	/**
	 * Este método faz a limpeza dos campos ao apertar um botão ou realizar uma
	 * operação de cadastro ou remoção por exemplo.
	 * 
	 * @param textField
	 * @param radioButton
	 * @param observacoes
	 */
	public void limpaCampos(JTextField[] textField, JRadioButton[] radioButton,
			JTextArea observacoes, JLabel[] label) {
		for (int i = 0; i < textField.length; i++) {
			textField[i].setText("");
		}
		observacoes.setText("");
		radioButton[0].setSelected(true);
		semFoto(label);
	}

	/**
	 * Método que exibe uma mensagem caso os dados no formulário de funcionário
	 * não sejam válidos.
	 */
	public static void msgValidacao() {
		Show.alerta("Erro ao salvar dados. Verifique se os campos 'Nome', 'Endereço', 'Bairro' e 'Cidade'\n"
				+ " possuem no mínimo 3 caracteres, se 'Cargo/Função' possui ao menos 2 caracteres e se a\n"
				+ " data de nascimento foi preenchida no formato DD/MM/AAAA.\n");
	}

	/**
	 * Este método preenche o combobox cargo com os dados do campo
	 * FUNCAO_DEPARTAMENTO da tabela DEPARTAMENTO do banco de dados, através da
	 * classe DepartamentoDAO e seu método listaCargos.
	 * 
	 * @param cargo
	 */
	public void preencheCombocargo(JComboBox<String> cargo) {
		DepartamentoDAO dao = new DepartamentoDAO();
		List<DepartamentoBean> cargoList = dao.listaCargos();
		for (DepartamentoBean departamentoBean : cargoList) {
			cargo.addItem(departamentoBean.getCargo());
		}
	}

	/**
	 * Este método serve para alterar o cargo. Quando o usuário clica no botão
	 * aparece uma caixa de seleção de cargos/funções cadastrados para escolher.
	 */
	public String recuperaCargo(String cargoAtual) {
		try {
			DepartamentoDAO dao = new DepartamentoDAO();
			List<DepartamentoBean> cargoList = dao.listaCargos();
			List<String> listaString = new ArrayList<>();

			for (DepartamentoBean departamento : cargoList) {
				listaString.add(departamento.getCargo().toString());
			}

			String[] arrayStringCargo = new String[listaString.size()];

			for (int i = 0; i < listaString.size(); i++) {
				arrayStringCargo[i] = listaString.get(i);
			}
			return Show.caixaOpcao("Selecione o novo cargo", arrayStringCargo);
		} catch (Exception e) {
			Show.alerta("O cargo atual não foi modificado.\n" + e.getMessage());
			e.printStackTrace();
		}
		return cargoAtual;
	}

	/**
	 * Este método adiciona um novo cargo/função na base de dados através do
	 * método cadastrarFuncao da classe DepartamentoDAO.
	 * 
	 * @param cargo
	 */
	public void adicionaCargo(JComboBox<String> cargo) {
		String novoCargo = Show.caixaTexto("Adicionar novo cargo");
		if (novoCargo.length() >= 2) {
			DepartamentoDAO dao = new DepartamentoDAO();
			DepartamentoBean departamento = new DepartamentoBean();
			departamento.setCargo(novoCargo);
			dao.cadastrarFuncao(departamento);
			cargo.addItem(novoCargo);
		} else {
			Show.alerta("O cargo deve possuir no mínimo 2 caracteres.");
		}
	}

	/**
	 * Método usado para adicionar um novo cargo no banco de dados. Ele deve
	 * possuir no mínimo 2 caracteres.
	 */
	public void adicionaCargo() {
		String novoCargo = Show.caixaTexto("Adicionar novo cargo");
		if (novoCargo.length() >= 2) {
			DepartamentoDAO dao = new DepartamentoDAO();
			DepartamentoBean departamento = new DepartamentoBean();
			departamento.setCargo(novoCargo);
			dao.cadastrarFuncao(departamento);
		} else {
			novoCargo = " ";
			Show.alerta("O cargo deve possuir no mínimo 2 caracteres.");
		}
	}

	/**
	 * Este método bloqueia o formulário com exceção do campo nome, que serve
	 * como parâmetro de busca.
	 * 
	 * @param textField
	 * @param button
	 * @param radioButton
	 * @param observacoes
	 */
	public void bloquearFormulario(JTextField[] textField, JButton[] button,
			JRadioButton[] radioButton, JTextArea observacoes) {
		for (int i = 1; i < textField.length; i++) {
			textField[i].setEditable(false);
		}
		for (int i = 0; i < radioButton.length; i++) {
			radioButton[i].setEnabled(false);
		}
		for (int i = 0; i < button.length; i++) {
			button[i].setEnabled(false);
		}
		observacoes.setEditable(false);
	}

	/**
	 * Este método libera o formulário para preenchimento de dados.
	 * 
	 * @param textField
	 * @param button
	 * @param radioButton
	 * @param observacoes
	 */
	public void liberarFormulario(JTextField[] textField, JButton[] button,
			JRadioButton[] radioButton, JTextArea observacoes) {
		for (int i = 0; i < textField.length; i++) {
			textField[i].setEditable(true);
			textField[9].setEditable(false);
		}
		for (int i = 0; i < radioButton.length; i++) {
			radioButton[i].setEnabled(true);
		}
		for (int i = 0; i < button.length; i++) {
			button[i].setEnabled(true);
		}
		observacoes.setEditable(true);
	}

}
