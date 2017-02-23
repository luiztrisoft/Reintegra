package br.com.itens.metodos;

import java.util.List;

import javax.swing.JComboBox;

import br.com.dao.CategoriaLancamentoDAO;
import br.com.dao.ConvenioDAO;
import br.com.dao.DepartamentoDAO;
import br.com.dao.DependenciaDAO;
import br.com.dao.FormaPagamentoDAO;
import br.com.itens.frames.TelaItens;

/**
 * Este método adiciona os itens para o combo box da classe {@link TelaItens}.
 * 
 * @author Luiz Alberto
 * 
 */
public class ItemManagement {

	/*
	 * =================================================
	 * 
	 * ADICIONA ITENS
	 * 
	 * Estes métodos servem para popular o combo box com os ítens cadastrados do
	 * banco de dados. De acordo com o índice na classe TelaItens é invocado um
	 * dos métodos a seguir. Os indices correspondem da seguinte maneira:
	 * 
	 * | 1 - categoria_lancamento | 2 - convenios | 3 - departamento | 4 -
	 * dependencia | 5 - forma_pagto |
	 * 
	 * =================================================
	 */

	/**
	 * Preenche combo box com as categorias de lançamento.
	 * 
	 * @param combo
	 */
	public void addCategoriaLancamento(JComboBox<String> combo) {
		CategoriaLancamentoDAO dao = new CategoriaLancamentoDAO();
		List<String> lista = dao.getItem();
		for (String nome : lista) {
			combo.addItem(nome);
		}
	}

	/**
	 * Preenche combo box com os convênios.
	 * 
	 * @param combo
	 */
	public void addConvenios(JComboBox<String> combo) {
		ConvenioDAO dao = new ConvenioDAO();
		List<String> lista = dao.getItem();
		for (String nome : lista) {
			combo.addItem(nome);
		}
	}

	/**
	 * Preenche combo box com os departamentos.
	 * 
	 * @param combo
	 */
	public void addDepartamento(JComboBox<String> combo) {
		DepartamentoDAO dao = new DepartamentoDAO();
		List<String> lista = dao.getItem();
		for (String nome : lista) {
			combo.addItem(nome);
		}
	}

	/**
	 * Preenche combo box com as dependências.
	 * 
	 * @param combo
	 */
	public void addDependencia(JComboBox<String> combo) {
		DependenciaDAO dao = new DependenciaDAO();
		List<String> lista = dao.getItem();
		for (String nome : lista) {
			combo.addItem(nome);
		}
	}

	/**
	 * Preenche combo box com as formas de pagamento.
	 * 
	 * @param combo
	 */
	public void addFormaPagto(JComboBox<String> combo) {
		FormaPagamentoDAO dao = new FormaPagamentoDAO();
		List<String> lista = dao.getItem();
		for (String nome : lista) {
			combo.addItem(nome);
		}
	}

	/*
	 * 
	 * =================================================
	 * 
	 * REMOVE ITENS
	 * 
	 * Estes métodos servem para deletar o item selecionado no combo box. De
	 * acordo com o índice na classe TelaItens é invocado um dos métodos a
	 * seguir. Os indices correspondem da seguinte maneira:
	 * 
	 * | 1 - categoria_lancamento | 2 - convenios | 3 - departamento | 4 -
	 * dependencia | 5 - forma_pagto |
	 * 
	 * =================================================
	 */

	/**
	 * Este método é usuado para remover a categoria de lançamento selecionada.
	 * 
	 * @param combo
	 */
	public void removecategoriaLancamento(JComboBox<String> combo) {
		String string = combo.getItemAt(combo.getSelectedIndex());
		CategoriaLancamentoDAO dao = new CategoriaLancamentoDAO();
		dao.delete(string);
	}

	/**
	 * Este método é usuado para remover o convênio selecionado.
	 * 
	 * @param combo
	 */
	public void removeConvenios(JComboBox<String> combo) {
		String string = combo.getItemAt(combo.getSelectedIndex());
		ConvenioDAO dao = new ConvenioDAO();
		dao.delete(string);
	}

	/**
	 * Este método é usuado para remover o departamento selecionado.
	 * 
	 * @param combo
	 */
	public void removeDepartamento(JComboBox<String> combo) {
		String string = combo.getItemAt(combo.getSelectedIndex());
		DepartamentoDAO dao = new DepartamentoDAO();
		dao.delete(string);
	}

	/**
	 * Este método é usuado para remover a dependência selecionada.
	 * 
	 * @param combo
	 */
	public void removeDependencia(JComboBox<String> combo) {
		String string = combo.getItemAt(combo.getSelectedIndex());
		DependenciaDAO dao = new DependenciaDAO();
		dao.delete(string);
	}

	/**
	 * Este método é usuado para remover a forma de pagamento selecionada.
	 * 
	 * @param combo
	 */
	public void removeFormaPagto(JComboBox<String> combo) {
		String string = combo.getItemAt(combo.getSelectedIndex());
		FormaPagamentoDAO dao = new FormaPagamentoDAO();
		dao.delete(string);
	}
}
