package br.com.usuarios.metodos;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.bean.UsuarioBean;
import br.com.controller.Criptografia;
import br.com.controller.Config;
import br.com.dao.UsuarioDAO;
import br.com.metodosgenericos.MetodosGenericosUsuario;

/**
 * Esta classe se comunica com a classe UsuarioDAO para salvar as informa��es do
 * usu�rio no banco de dados
 * 
 * @author Luiz Alberto
 * 
 */
public class CadastraUsuarioMetodos {

	/**
	 * Este m�todo passa os par�metros de usu�rio para a classe UsuarioDAO
	 * realizar a persist�ncia. Note que a senha � criptografada neste m�todo
	 * antes de ser enviada.
	 * 
	 * @param textField
	 * @param passwordField
	 * @param idFuncionario
	 */
	@SuppressWarnings("deprecation")
	public void cadastraUsuario(JTextField textField,
			JPasswordField passwordField, int idFuncionario) {
		MetodosGenericosUsuario metodoUsuario = new MetodosGenericosUsuario();
		if (metodoUsuario.validaLogin(textField.getText()) == true
				&& metodoUsuario.validaSenha(passwordField.getText()) == true) {
			UsuarioBean usuario = new UsuarioBean();
			usuario.setLogin(textField.getText());
			usuario.setSenha(Criptografia.criptografar(passwordField.getText()));
			usuario.setIdFuncionario(idFuncionario);
			usuario.setIdUsuarioCriador(Config.idUsuarioLogado);

			UsuarioDAO dao = new UsuarioDAO();
			dao.inserirUsuario(usuario);
			metodoUsuario.limpaCampos(textField, passwordField);
		} else {
			metodoUsuario.limpaCampos(textField, passwordField);
		}

	}
}
