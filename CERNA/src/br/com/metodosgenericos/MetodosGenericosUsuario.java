package br.com.metodosgenericos;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.controller.Save;
import br.com.controller.Config;
import br.com.dao.UsuarioDAO;

/**
 * Esta classe cont�m os m�todos gen�ricos pertinentes ao usu�rio e que servem
 * para todas as necessidades. Um exemplo � o m�todo limpaCampos que � usado
 * ap�s um cadastro ou ent�o ao apertar um bot�o.
 * 
 * @author Luiz Alberto
 * 
 */
public class MetodosGenericosUsuario extends MetodosGenericos {

	/**
	 * Este m�todo limpa os campos do formul�rio.
	 * 
	 * @param textField
	 * @param passwordField
	 */
	public void limpaCampos(JTextField textField, JPasswordField passwordField,
			JPasswordField passwordConfirm) {
		textField.setText("");
		passwordField.setText("");
		passwordConfirm.setText("");
		textField.requestFocus();
	}

	/**
	 * Este m�todo preenche o combobox com os dados do campo NOME_FUNCIONARIO da
	 * tabela FUNCIONARIOS do banco de dados, atrav�s da classe FuncionarioDAO e
	 * seu m�todo listaFuncionarios.
	 * 
	 * @param comboBox
	 */
	public void preencheComboFuncionario(JComboBox<String> comboBox) {
		UsuarioDAO dao = new UsuarioDAO();
		List<String> list = dao.listaFuncionarios();
		for (String nome : list) {
			comboBox.addItem(nome);
		}
	}

	/**
	 * Este m�todo preenche o combobox com os dados do campo NOME_FUNCIONARIO da
	 * tabela USUARIOS do banco de dados, atrav�s da classe UsuarioDAO e seu
	 * m�todo listaFuncionarios.
	 * 
	 * @param comboBox
	 */
	public void preencheComboUsuario(JComboBox<String> comboBox) {
		UsuarioDAO dao = new UsuarioDAO();
		List<String> list = dao.listaUsuarios();
		for (String nome : list) {
			comboBox.addItem(nome);
		}
	}

	/**
	 * M�todo que verifica no banco de dados se o login e a senha est�o corretos
	 * dando acesso ao sistema.
	 * 
	 * @param login
	 * @param senha
	 * @return boolean
	 */
	public boolean logarSistema(String login, String senha) {
		UsuarioDAO dao = new UsuarioDAO();
		if (dao.logar(login, senha) == true) {
			return true;
		}
		
		Save.log(Config.usuarioLogado, "Falhou ao tentar entrar no sistema");
		return false;
	}

	/**
	 * Limpa os campos de preenchimento e coloca o focus no campo de usu�rio.
	 */
	public void limpaCampos(JTextField fieldUsuario, JTextField passSenha) {
		fieldUsuario.setText("");
		passSenha.setText("");
		fieldUsuario.requestFocus();
	}

}
