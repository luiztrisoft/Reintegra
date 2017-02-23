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
 * Esta classe cont�m os m�todos gen�ricos pertinentes ao funcion�rio e que
 * servem para todas as necessidades. Um exemplo � o m�todo limpaCampos que �
 * usado ap�s um cadastro ou ent�o ao apertar um bot�o.
 * 
 * @author Luiz Alberto
 * 
 */
public class MetodosGenericosFuncionario extends MetodosGenericos {

	/**
	 * Este m�todo faz a limpeza dos campos ao apertar um bot�o ou realizar uma
	 * opera��o de cadastro ou remo��o por exemplo.
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
	 * M�todo que exibe uma mensagem caso os dados no formul�rio de funcion�rio
	 * n�o sejam v�lidos.
	 */
	public static void msgValidacao() {
		Show.alerta("Erro ao salvar dados. Verifique se os campos 'Nome', 'Endere�o', 'Bairro' e 'Cidade'\n"
				+ " possuem no m�nimo 3 caracteres, se 'Cargo/Fun��o' possui ao menos 2 caracteres e se a\n"
				+ " data de nascimento foi preenchida no formato DD/MM/AAAA.\n");
	}

	/**
	 * Este m�todo preenche o combobox cargo com os dados do campo
	 * FUNCAO_DEPARTAMENTO da tabela DEPARTAMENTO do banco de dados, atrav�s da
	 * classe DepartamentoDAO e seu m�todo listaCargos.
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
	 * Este m�todo serve para alterar o cargo. Quando o usu�rio clica no bot�o
	 * aparece uma caixa de sele��o de cargos/fun��es cadastrados para escolher.
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
			Show.alerta("O cargo atual n�o foi modificado.\n" + e.getMessage());
			e.printStackTrace();
		}
		return cargoAtual;
	}

	/**
	 * Este m�todo adiciona um novo cargo/fun��o na base de dados atrav�s do
	 * m�todo cadastrarFuncao da classe DepartamentoDAO.
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
			Show.alerta("O cargo deve possuir no m�nimo 2 caracteres.");
		}
	}

	/**
	 * M�todo usado para adicionar um novo cargo no banco de dados. Ele deve
	 * possuir no m�nimo 2 caracteres.
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
			Show.alerta("O cargo deve possuir no m�nimo 2 caracteres.");
		}
	}

	/**
	 * Este m�todo bloqueia o formul�rio com exce��o do campo nome, que serve
	 * como par�metro de busca.
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
	 * Este m�todo libera o formul�rio para preenchimento de dados.
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
