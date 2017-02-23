package br.com.responsavel.metodos;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import br.com.bean.InternoResponsavelBean;
import br.com.bean.ResponsavelBean;
import br.com.controller.Data;
import br.com.controller.Show;
import br.com.dao.FuncionarioDAO;
import br.com.dao.InternoResponsavelDAO;
import br.com.dao.ResponsavelDAO;
import br.com.metodosgenericos.MetodosGenericosResponsavel;

/**
 * Esta classe pega os dados do responsável e os manda para a classe
 * {@link FuncionarioDAO} para que seja feita persistência destes dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class CadastraResponsavelMetodos {

	/**
	 * Método responsável por pegar os dados do formulário do responsável e
	 * enviar para a classe {@link FuncionarioDAO}.
	 * 
	 * @param idInterno
	 * @param campo
	 * @param combo
	 */
	public void cadastraResponsavel(long idInterno, JTextField[] campo,
			JComboBox<String>[] combo) {

		MetodosGenericosResponsavel metodosResponsavel = new MetodosGenericosResponsavel();

		if (metodosResponsavel.validaCampo(campo[0].getText(), 5) == true) {
			if (metodosResponsavel.verCpf(campo[2].getText()) == true) {
				if (!campo[3].getText().equals("  /  /    ")) {

					ResponsavelBean responsavel = new ResponsavelBean();
					// responsavel.setIdInterno(idInterno);
					responsavel.setNome(campo[0].getText());
					responsavel.setRg(campo[1].getText());
					responsavel.setCpf(campo[2].getText());
					responsavel.getNascimento().setCalendar(
							Data.stringToDate(null, campo[3].getText()));
					responsavel.setEndereco(campo[4].getText());
					responsavel.setBairro(campo[5].getText());
					responsavel.setCidade(campo[6].getText());
					responsavel.setUf(combo[0].getSelectedItem().toString());
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
					dao.insert(responsavel);

					// ::::::::interno_responsavel::::::::
					long idResponsavel = dao.returnId(campo[2].getText());
					String cpfResponsavel = campo[2].getText();

					relacionaInternoResponsavel(idInterno, idResponsavel,
							cpfResponsavel);

					// ::::::::Após completar remove os dados dos campos::::::::
					metodosResponsavel.limpaCampos(campo, combo);

				} else {
					Show.alerta("Insira uma data de nascimento válida.");
				}
			} else {
				Show.alerta("CPF inválido.");
			}
		} else {
			Show.alerta("O nome deve conter no mínimo 5 caracteres.");
		}
	}

	/**
	 * Depois de salvar o registro do responsável, este método é invocado para
	 * gerar o relacionamento das entidades Interno e responsável e guardá-las
	 * na base de dados.
	 * 
	 * @param idInterno
	 * @param idResponsavel
	 * @param cpf
	 */
	private void relacionaInternoResponsavel(long idInterno,
			long idResponsavel, String cpf) {
		ResponsavelDAO respDAO = new ResponsavelDAO();

		InternoResponsavelBean ir = new InternoResponsavelBean();
		ir.setIdInterno(idInterno);
		ir.setIdResponsavel(idResponsavel);

		InternoResponsavelDAO irDAO = new InternoResponsavelDAO();

		if (respDAO.verifyId(cpf) == true) {
			long idExistente = respDAO.returnId(cpf);

			boolean vincular = Show
					.caixaConfirmacao("Deseja vincular o responsável ao interno pré-selecionado?");
			if (vincular == true) {
				ir.setIdResponsavel(idExistente);
				irDAO.insert(ir);
			}
		}
	}
}