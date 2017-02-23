package br.com.usuarios.metodos;

import br.com.controller.Criptografia;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.UsuarioDAO;
import br.com.metodosgenericos.MetodosGenericosUsuario;

/**
 * Atrav�s desta classe o usu�riio que estiver logado poder� alterar sua senha.
 * 
 * @author Luiz Alberto
 * 
 */
public class RedefinirSenhaMetodos {

	/**
	 * Este m�todo � utilizado para atualizar a senha do usu�rio logado no
	 * sistema.
	 * 
	 * @param senhaAtual
	 * @param novaSenha
	 * @param novaSenhaRedigitada
	 */
	public void redefinir(String senhaAtual, String novaSenha,
			String novaSenhaRedigitada) {

		String login = Config.usuarioLogado;
		MetodosGenericosUsuario metodoUsuario = new MetodosGenericosUsuario();

		if (novaSenha.equals(novaSenhaRedigitada)) {
			if (metodoUsuario.validaLogin(login) == true
					&& metodoUsuario.validaSenha(senhaAtual) == true
					&& metodoUsuario.validaSenha(novaSenha)) {

				senhaAtual = Criptografia.criptografar(senhaAtual);
				novaSenha = Criptografia.criptografar(novaSenha);

				UsuarioDAO dao = new UsuarioDAO();

				if (senhaAtual.equals(dao.getSenha())) {
					dao.atualizarSenha(senhaAtual, novaSenha);
				} else {
					Show.erro("Senha incorreta!");
				}
			}
		} else {
			Show.alerta("A senha redigitada est� diferente da nova senha.");
		}
	}

}
