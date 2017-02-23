package br.com.livrocaixa.metodos;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import br.com.bean.LivroCaixaBean;
import br.com.controller.Data;
import br.com.dao.CategoriaLancamentoDAO;
import br.com.dao.LivroCaixaDAO;
import br.com.metodosgenericos.MetodosGenericosLivroCaixa;

/**
 * Esta classe pega os dados do formulário de entrada e saída do livro caixa e
 * os envia a classe {@link CategoriaLancamentoDAO} para realizar a persistência
 * na base de dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class CadastraRegistroLivroMetodos {

	/**
	 * Este método invoca o método registroLivroCaixa informando que se trata de
	 * uma entrada no livro caixa.
	 * 
	 * @param campo
	 * @param combo
	 */
	public void entradaLivroCaixa(JTextField[] campo, JComboBox<String> combo) {
		registroLivroCaixa(campo, combo, "E",
				"<html>A <b>Entrada</b> foi registrada com êxito no livro caixa.");
	}

	/**
	 * Este método invoca o método registroLivroCaixa informando que se trata de
	 * uma saída no livro caixa.
	 * 
	 * @param campo
	 * @param combo
	 */
	public void saidaLivroCaixa(JTextField[] campo, JComboBox<String> combo) {
		registroLivroCaixa(campo, combo, "S",
				"<html>A <b>Saída</b> foi registrada com êxito no livro caixa.");
	}

	/**
	 * Este método se comunica com a classe {@link CategoriaLancamentoDAO} para
	 * efetuar a persistência.
	 * 
	 * @param campo
	 * @param combo
	 * @param tipo
	 * @param mensagem
	 */
	private void registroLivroCaixa(JTextField[] campo,
			JComboBox<String> combo, String tipo, String mensagem) {
		MetodosGenericosLivroCaixa metodoLivro = new MetodosGenericosLivroCaixa();
		String s = campo[2].getText().replaceAll(",", ".");
		double valor = Double.parseDouble(s);
		int idCategoria = metodoLivro.indiceCategoria(combo.getItemAt(combo
				.getSelectedIndex()));

		LivroCaixaBean livroCaixa = new LivroCaixaBean();
		livroCaixa.setTipo(tipo);
		livroCaixa.getDataRegistro().setCalendar(
				Data.stringToDate(null, campo[0].getText()));
		livroCaixa.setIdCategoria(idCategoria);
		livroCaixa.setObservacao(campo[1].getText());
		livroCaixa.setValor(valor);

		LivroCaixaDAO dao = new LivroCaixaDAO();
		dao.insert(livroCaixa, mensagem);

	}
}
