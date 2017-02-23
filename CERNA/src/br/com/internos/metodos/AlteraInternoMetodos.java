package br.com.internos.metodos;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.InternoBean;
import br.com.controller.Data;
import br.com.controller.Show;
import br.com.dao.InternoDAO;
import br.com.internos.frames.TelaConsultaInterno;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta classe se comnica com a classe {@link InternoDAO} para alterar os dados
 * de cadastro do interno.
 * 
 * @author Luiz Alberto
 * 
 */
public class AlteraInternoMetodos {

	/**
	 * Este método serve para fazer alterações no cadastro de internos, desde as
	 * informações mais básicas até a foto que está registrada. Após processar
	 * os dados que vem da classe {@link TelaConsultaInterno}, eles são enviados
	 * para a classe {@link InternoDAO} para que sejam feitas as devidas
	 * alterações.
	 * 
	 * @param campo
	 * @param combo
	 * @param area
	 * @param imagePath
	 * @param labelFoto
	 */
	public void alteraInterno(JTextField[] campo, JComboBox<String>[] combo,
			JTextArea area, String imagePath, JLabel labelFoto) {

		MetodosGenericosInterno metodosInterno = new MetodosGenericosInterno();

		if (metodosInterno.validaCampo(campo[1].getText(), 5) == true) {
			if (!campo[2].getText().equals("  /  /    ")
					&& !campo[50].getText().equals("  /  /    ")
					&& !campo[51].getText().equals("  /  /    ")) {
				if (metodosInterno.validaCampo(campo[10].getText(), 2) == true
						&& metodosInterno.validaCampo(campo[12].getText(), 2) == true) {

					// ::::::::::::id das chaves estrangeiras::::::::::::
					int idStatus = metodosInterno.indiceStatus(combo[3]
							.getItemAt(combo[3].getSelectedIndex()));
					int idContribuicao = metodosInterno
							.indiceContribuicao(combo[5].getItemAt(combo[5]
									.getSelectedIndex()));
					int idConvenio = metodosInterno.indiceConvenios(combo[4]
							.getItemAt(combo[4].getSelectedIndex()));
					int idDependencia = metodosInterno
							.indiceDependencia(combo[12].getItemAt(combo[12]
									.getSelectedIndex()));

					InternoBean interno = new InternoBean();
					// :::::::::::: dados pessoais::::::::::::
					interno.setNome(campo[1].getText());
					interno.getNascimento().setCalendar(
							Data.stringToDate(null, campo[2].getText()));
					interno.setCpf(campo[3].getText());
					interno.setRg(campo[4].getText());
					interno.setTelefone(campo[5].getText());
					interno.setPai(campo[54].getText());
					interno.setMae(campo[55].getText());
					interno.setEstadoCivil(combo[10].getSelectedItem()
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

					// :::::::::::: dados complementares::::::::::::
					interno.setProcessoCriminal(campo[21].getText());
					interno.setLocal(campo[22].getText());
					interno.setSofreAmeaca(combo[6].getSelectedItem()
							.toString());
					interno.setDependencia(idDependencia);
					interno.setTempodeUso(campo[25].getText());
					interno.setMotivodeUso(campo[26].getText());
					interno.setDiabetico(combo[7].getSelectedItem().toString());
					interno.setContraiuDoenca(campo[28].getText());
					interno.setUsaMedicacao(campo[29].getText());
					interno.setRecomendacaoMedica(campo[30].getText());
					interno.setDocumentosEntregues(campo[31].getText());
					interno.setObservacao(area.getText());

					// ::::::::::::responsável::::::::::::

					// :::::::::::: dados principais::::::::::::
					interno.setStatus(idStatus);
					interno.getDataAdmissao().setCalendar(
							Data.stringToDate(null, campo[50].getText()));
					interno.getDataSaida().setCalendar(
							Data.stringToDate(null, campo[51].getText()));
					interno.setTipoSaida(combo[11].getSelectedItem().toString());
					interno.setMotivoSaida(campo[53].getText());
					interno.setContribuicao(idContribuicao);
					interno.setConvenio(idConvenio);

					// ::::::::::::imagem do interno::::::::::::
					try {
						String fotoAtual = campo[0].getText();
					//	interno.setFoto(metodosInterno.transferirFoto(imagePath, fotoAtual));
					//	interno.setFoto(metodosInterno.transferirFotoPorCompartilhamento(imagePath, fotoAtual));
						interno.setFoto(metodosInterno.transferirFoto(imagePath, fotoAtual));
					} catch (Exception e) {
						e.printStackTrace();
						interno.setFoto("***************************************");
					}

					InternoDAO dao = new InternoDAO();
					boolean update = dao.update(interno);
					
					if(update==true){
						metodosInterno.limpaCamposConsulta(campo, combo, area,
								labelFoto);
					}


				} else {
					Show.alerta("Os campos 'Cidade', 'UF' e 'País' devem conter  no mínimo 2 caracteres cada um.");
				}
			} else {
				Show.alerta("Insira uma data válida no formaro dd/mm/aaaa \nnos campos data de nascimento, admissão e saída.");
			}
		} else {
			Show.alerta("O nome deve conter no mínimo 5 caracteres.");
		}

	}
}
