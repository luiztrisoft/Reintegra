package br.com.usuarios.metodos;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.bean.UsuarioBean;
import br.com.controller.Criptografia;
import br.com.controller.Config;
import br.com.dao.UsuarioDAO;
import br.com.metodosgenericos.MetodosGenericosUsuario;

/**
 * Esta classe se comunica com a classe UsuarioDAO para salvar as informações do
 * usuário no banco de dados
 * 
 * @author Luiz Alberto
 * 
 */
public class CadastraUsuarioMetodos {

	/**
	 * Este método passa os parÂmetros de usuário para a classe UsuarioDAO
	 * realizar a persistência. Note que a senha é criptografada neste método
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
