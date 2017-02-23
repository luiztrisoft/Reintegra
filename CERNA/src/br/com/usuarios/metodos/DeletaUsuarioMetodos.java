package br.com.usuarios.metodos;

import br.com.bean.UsuarioBean;
import br.com.dao.UsuarioDAO;

/**
 * Esta classe se comunica com a classe UsusarioDAO para remover o usu�rio
 * selecionado da base de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class DeletaUsuarioMetodos {

	/**
	 * Envia o nome do usu�rio para a classe UsuarioDAO como par�metro para que
	 * seja removido.
	 * 
	 * @param idFuncionario
	 */
	public void deletaUsuario(int idFuncionario) {
		UsuarioBean usuario = new UsuarioBean();
		usuario.setIdFuncionario(idFuncionario);
		UsuarioDAO dao = new UsuarioDAO();
		dao.deletarUsuario(usuario);
	}
}
