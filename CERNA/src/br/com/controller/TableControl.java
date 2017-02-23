package br.com.controller;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Esta classe controla o modelo visual das tabelas usadas no sistema, definindo
 * a fonte, cor do grid etc.
 * 
 * @author Luiz Alberto
 * 
 */
public class TableControl {

	/**
	 * Este método define que as tabelas não serão reordenadas, a cor do grid, a
	 * altura, o tipo de fonte, a cor do background selecionado e também que
	 * terá seleção simples(o usuário só poderá selecionar um item na tabela).
	 * 
	 * @param tabela
	 */

	public void modelarTabela(JTable tabela) {
		tabela.getTableHeader().setReorderingAllowed(false);

		tabela.setGridColor(new Color(Config.RED, Config.GREEN, Config.BLUE));
		tabela.setRowHeight(20);
		tabela.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tabela.setSelectionBackground(new Color(56, 69, 107));

		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * Este método remove todas as linhas da tabela de ajuda de custo. Sua
	 * função principal é auxiliar a atualização da tabela. Após executar
	 * qualquer operação básica.
	 */
	public void removeItem(DefaultTableModel coluna) {
		int tamCol = coluna.getRowCount();
		for (int i = 0; i < tamCol; i++) {
			coluna.removeRow(0);
		}
	}

}
