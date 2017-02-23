package br.com.metodosgenericos;

import java.util.List;

import javax.swing.JComboBox;

import br.com.bean.CategoriaLancamentoBean;
import br.com.controller.Show;
import br.com.dao.CategoriaLancamentoDAO;

/**
 * Esta classe contém os métodos genéricos pertinentes ao livro caixa .
 * 
 * @author Luiz Alberto
 * 
 */
public class MetodosGenericosLivroCaixa {

	/**
	 * Este método preenche e retorna o combo box com as categorias de registro
	 * de livro caixa cadastrados na base de dados.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbCategoria() {
		JComboBox<String> combo = new JComboBox<>();
		CategoriaLancamentoDAO dao = new CategoriaLancamentoDAO();
		List<CategoriaLancamentoBean> lista = dao.listaCategorias();
		for (CategoriaLancamentoBean lancamentoBean : lista) {
			combo.addItem(lancamentoBean.getTipo());
		}
		return combo;
	}

	/**
	 * Este método adiciona uma nova categoria de registro de livro caixa na
	 * base de dados e já popula seu tipo no JComboBox.
	 * 
	 * @param combo
	 */
	public void addNovaCategoria(JComboBox<String> combo) {
		try {
			String novaCategoria = Show.caixaTexto("Insira a nova categoria");
			if (novaCategoria.length() < 2) {
				Show.alerta("A categoria deve ter no mínimo 2 caracteres");
			} else {
				combo.addItem(novaCategoria);
				CategoriaLancamentoDAO dao = new CategoriaLancamentoDAO();
				dao.insert(novaCategoria);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método retorna o indice da categoria de registro de livro caixa de
	 * acordo com o tipo passado como parâmetro.
	 * 
	 * @param tipo
	 * @return int
	 */
	public int indiceCategoria(String tipo) {
		CategoriaLancamentoDAO dao = new CategoriaLancamentoDAO();
		int id = dao.getIdCategoria(tipo);
		return id;
	}

}
