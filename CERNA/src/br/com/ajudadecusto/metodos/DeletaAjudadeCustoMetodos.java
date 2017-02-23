package br.com.ajudadecusto.metodos;

import br.com.bean.AjudadeCustoBean;
import br.com.dao.AjudadeCustoDAO;

/**
 * Esta classe se comunica com a classe AjudadeCustoDAO para remover a ajuda de
 * custo juntamente com suas parcelas da base de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class DeletaAjudadeCustoMetodos {
	/**
	 * Esta fun��o far� a remo��o da ajuda de custo atrav�s do ID da ajuda de
	 * custo informado como par�metro.
	 * 
	 * @param idAjudadeCusto
	 */
	public void deletar(Integer idAjudadeCusto) {
		AjudadeCustoBean ajuda = new AjudadeCustoBean();
		ajuda.setId(idAjudadeCusto);

		AjudadeCustoDAO dao = new AjudadeCustoDAO();
		dao.delete(ajuda);
	}
}
