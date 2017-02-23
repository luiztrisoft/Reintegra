package br.com.pedidodemedicamentos.metodos;

import br.com.controller.Show;
import br.com.dao.InternoDAO;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta classe funciona como uma ponte na solicita��o de dados do interno como a
 * exist�ncia de um CPF ou a tabela contendo todos os CPFs.
 * 
 * @author Luiz Alberto
 * 
 */
public class InternoBridge {

	/**
	 * Este m�todo se comunica com a classe InternoDAO para verificar a
	 * exist�ncia do CPF digitado.
	 * 
	 * @param cpf
	 * @return boolean
	 */
	public boolean cpfExists(String cpf) {
		InternoDAO dao = new InternoDAO();
		boolean cpfExists = dao.verifyCPF(cpf);
		if (cpfExists == false) {
			Show.alerta("Este CPF n�o est� cadastrado na base de dados ou foi digitado incorretamente!");
		}
		return cpfExists;
	}

	/**
	 * Este m�todo abre a tabela de CPF dos internos para verifica��o dos nomes
	 * e respectivos CPFs.
	 */
	public void tabelaCPFs() {
		MetodosGenericosInterno funcao = new MetodosGenericosInterno();
		funcao.tabelaCPFs();
	}
}
