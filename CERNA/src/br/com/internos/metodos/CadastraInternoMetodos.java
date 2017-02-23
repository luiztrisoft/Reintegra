package br.com.internos.metodos;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.InternoBean;
import br.com.controller.Data;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.InternoDAO;
import br.com.metodosgenericos.MetodosGenericosInterno;
import br.com.metodosgenericos.MetodosGenericosResponsavel;
import br.com.responsavel.frames.TelaCadastroResponsavel;

/**
 * Esta classe recebe os parâmetros da classe TelaCadastroInterno e os envia a
 * classe InternoDAO/ResponsavelDAO para posterior persistência na base de
 * dados.
 * 
 * @author Luiz Alberto
 * 
 */
public class CadastraInternoMetodos {

	/**
	 * Este método faz a validação dos dados e os envia a classe
	 * InternoDAO/ResponsavelDAO para ser registrado na base de dados.
	 * 
	 * Se o id do interno não for 0 ele salva na base de dados e retorna um long
	 * com o valor do id do interno para passar para o responsável como
	 * parâmetro para salvar a chave do interno.
	 * 
	 * @param campo
	 * @param combo
	 * @param area
	 * @param imagePath
	 */
	public void cadastraInterno(JTextField[] campo, JComboBox<String>[] combo,
			JTextArea area, String imagePath, JLabel label) {
		MetodosGenericosInterno metodosInterno = new MetodosGenericosInterno();
		if (metodosInterno.validaCampo(campo[1].getText(), 5) == true) {
			if (!campo[2].getText().equals("  /  /    ")
					&& !campo[33].getText().equals("  /  /    ")
					&& !campo[34].getText().equals("  /  /    ")) {
				if (metodosInterno.validaCampo(campo[10].getText(), 2) == true
						&& metodosInterno.validaCampo(campo[12].getText(), 2) == true) {
					try {
						InternoBean interno = new InternoBean();

						// ::::::::::::id das chaves estrangeiras::::::::::::
						int idStatus = metodosInterno.indiceStatus(combo[3]
								.getItemAt(combo[3].getSelectedIndex()));
						int idContribuicao = metodosInterno
								.indiceContribuicao(combo[5].getItemAt(combo[5]
										.getSelectedIndex()));
						int idConvenio = metodosInterno
								.indiceConvenios(combo[4].getItemAt(combo[4]
										.getSelectedIndex()));

						int idDependencia = metodosInterno
								.indiceDependencia(combo[10]
										.getItemAt(combo[10].getSelectedIndex()));

						// imprimeIndices(combo, imagePath);

						// ::::::::::::foto::::::::::::
						if (imagePath == null) {
							interno.setFoto("***************************************");
						} else {
						//	interno.setFoto(metodosInterno.transferirFoto(imagePath, ""));
						//	interno.setFoto(metodosInterno.transferirFotoPorCompartilhamento(imagePath, ""));
							interno.setFoto(metodosInterno.transferirFoto(imagePath, ""));

						}

						// ::::::::::::dados pessoais::::::::::::
						interno.setStatus(idStatus);
						interno.setContribuicao(idContribuicao);
						interno.setConvenio(idConvenio);

						interno.setNome(campo[1].getText());
						interno.getNascimento().setCalendar(
								Data.stringToDate(null, campo[2].getText()));
						interno.setCpf(campo[3].getText());
						interno.setRg(campo[4].getText());
						interno.setTelefone(campo[5].getText());
						interno.setPai(campo[37].getText());
						interno.setMae(campo[38].getText());
						interno.setEstadoCivil(combo[9].getSelectedItem()
								.toString());
						interno.setConjuge(campo[7].getText());
						interno.setProfissao(campo[8].getText());
						interno.setEscolaridade(combo[0].getSelectedItem()
								.toString());
						interno.setNaturalidade(campo[10].getText());
						interno.setUfNascimento(combo[1].getSelectedItem()
								.toString());
						interno.setNacionalidade(campo[12].getText());
						interno.setEndereco(campo[13].getText());
						interno.setBairro(campo[14].getText());
						interno.setCidade(campo[15].getText());
						interno.setUf(combo[2].getSelectedItem().toString());

						// ::::::::::::dados principais::::::::::::
						interno.getDataAdmissao().setCalendar(
								Data.stringToDate(null, campo[33].getText()));
						interno.getDataSaida().setCalendar(
								Data.stringToDate(null, campo[34].getText()));
						interno.setTipoSaida(combo[8].getSelectedItem()
								.toString());
						interno.setMotivoSaida(campo[36].getText());

						// ::::::::::::dados complementares::::::::::::
						interno.setProcessoCriminal(campo[21].getText());
						interno.setLocal(campo[22].getText());
						interno.setSofreAmeaca(combo[6].getSelectedItem()
								.toString());
						interno.setDependencia(idDependencia);
						interno.setTempodeUso(campo[25].getText());
						interno.setMotivodeUso(campo[26].getText());
						interno.setDiabetico(combo[7].getSelectedItem()
								.toString());
						interno.setContraiuDoenca(campo[28].getText());
						interno.setUsaMedicacao(campo[29].getText());
						interno.setRecomendacaoMedica(campo[30].getText());
						interno.setDocumentosEntregues(campo[31].getText());
						interno.setObservacao(area.getText());

						InternoDAO internoDAO = new InternoDAO();
						boolean gravouDados = internoDAO.insert(interno);

						if (gravouDados == true) {
							if (Show.caixaConfirmacao("<html>Este interno ainda não possui um responsável.\n<html><b> Deseja cadastrar um responsável para este interno?</b>") == true) {
								cadastroResponsavel(campo[3].getText());
							} else {
								Show.informacao("Cadastro finalizado");
							}

							metodosInterno.limpaCamposCadastro(campo, combo,
									area, label);
						}

					} catch (Exception e) {
						Save.log(Config.system, e.getMessage());
						Show.erro(e.getMessage());
					}
				}

				else {
					Show.alerta("Os campos 'Cidade', 'UF' e 'País' devem conter  no mínimo 2 caracteres cada um.");
				}
			} else {
				Show.alerta("Insira uma data válida no formaro dd/mm/aaaa \nnos campos data de nascimento, admissão e saída.");
			}
		} else {
			Show.alerta("O nome deve conter no mínimo 5 caracteres.");
		}
	}

	/**
	 * Este método abre o cadastro de responsáveis logo após efetuar o cadastro
	 * de internos caso o usuário opte por adicionar novo responsável.
	 * 
	 * @param cpf
	 */
	private void cadastroResponsavel(String cpf) {
		MetodosGenericosResponsavel metodoResponsavel = new MetodosGenericosResponsavel();

		if (metodoResponsavel.verifyCpf(cpf) == true) {
			long id = metodoResponsavel.returnIdInterno(cpf);

			TelaCadastroResponsavel cadastroResponsavel = new TelaCadastroResponsavel(
					null, Config.tituloJanela, true, id);
			cadastroResponsavel.setVisible(true);
		}
	}

}
