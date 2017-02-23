package br.com.responsavel.metodos;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import br.com.bean.ResponsavelBean;
import br.com.controller.Show;
import br.com.dao.ResponsavelDAO;

public class BuscaResponsavelMetodos {

	/**
	 * Este método traz os dados do responsável de acordo com o cpf do interno
	 * informado
	 * 
	 * @param campo
	 * @param combo
	 * @param idInterno
	 */
	public void buscaResponsavel(JTextField[] campo, JComboBox<String>[] combo,
			long idInterno) {

		ResponsavelDAO respDAO = new ResponsavelDAO();
		ResponsavelBean responsavel = respDAO.getResponsavel(idInterno);

		try {
			// ::::::::::::responsável::::::::::::
			campo[0].setText(responsavel.getNome());
			campo[1].setText(responsavel.getRg());
			campo[2].setText(responsavel.getCpf());
			campo[3].setText(responsavel.getNascimento().getDate());
			campo[4].setText(responsavel.getEndereco());
			campo[5].setText(responsavel.getBairro());
			campo[6].setText(responsavel.getCidade());
			combo[0].setSelectedItem(responsavel.getUf());
			campo[8].setText(responsavel.getTelefone());
			campo[9].setText(responsavel.getCelular());
			campo[10].setText(responsavel.getEmail());
			campo[11].setText(responsavel.getNaturalidade());
			combo[1].setSelectedItem(responsavel.getUfNascimento());
			campo[13].setText(responsavel.getNacionalidade());
			campo[14].setText(responsavel.getProfissao());
			campo[15].setText(responsavel.getPai());
			campo[16].setText(responsavel.getMae());
		} catch (Exception e) {
			e.printStackTrace();
			Show.erro(e.getMessage());
		}

	}
}
