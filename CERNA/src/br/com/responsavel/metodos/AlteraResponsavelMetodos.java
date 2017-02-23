package br.com.responsavel.metodos;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import br.com.bean.ResponsavelBean;
import br.com.controller.Data;
import br.com.controller.Show;
import br.com.dao.ResponsavelDAO;
import br.com.metodosgenericos.MetodosGenericosResponsavel;

/**
 * Esta classe se comunica com a classe {@link ResponsavelDAO} para alterar os
 * dados do responsavel através co CPF passado como parâmetro.
 * 
 * @author Luiz Alberto
 * 
 */
public class AlteraResponsavelMetodos {

	public void alteraFuncionario(JTextField[] campo, JComboBox<String>[] combo) {

		MetodosGenericosResponsavel metodosResponsavel = new MetodosGenericosResponsavel();

		if (metodosResponsavel.validaCampo(campo[0].getText(), 5) == true) {
			if (metodosResponsavel.verCpf(campo[2].getText()) == true) {
				if (!campo[3].getText().equals("  /  /    ")) {

					try {
						ResponsavelBean responsavel = new ResponsavelBean();
						responsavel.setNome(campo[0].getText());
						responsavel.setRg(campo[1].getText());
						responsavel.setCpf(campo[2].getText());
						responsavel.getNascimento().setCalendar(
								Data.stringToDate(null, campo[3].getText()));
						responsavel.setEndereco(campo[4].getText());
						responsavel.setBairro(campo[5].getText());
						responsavel.setCidade(campo[6].getText());
						responsavel
								.setUf(combo[0].getSelectedItem().toString());
						responsavel.setTelefone(campo[8].getText());
						responsavel.setCelular(campo[9].getText());
						responsavel.setEmail(campo[10].getText());
						responsavel.setNaturalidade(campo[11].getText());
						responsavel.setUfNascimento(combo[1].getSelectedItem()
								.toString());
						responsavel.setNacionalidade(campo[13].getText());
						responsavel.setProfissao(campo[14].getText());
						responsavel.setPai(campo[15].getText());
						responsavel.setMae(campo[16].getText());

						ResponsavelDAO dao = new ResponsavelDAO();
						dao.update(responsavel);						
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Show.alerta("Insira uma data válida.");
				}
			} else {
				Show.alerta("CPF inválido.");
			}
		} else {
			Show.alerta("O nome deve conter no mínimo 5 caracteres.");
		}
	}
}
