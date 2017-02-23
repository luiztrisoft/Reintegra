package br.com.internos.metodos;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.bean.InternoBean;
import br.com.bean.ResponsavelBean;
import br.com.dao.ContribuicaoDAO;
import br.com.dao.ConvenioDAO;
import br.com.dao.DependenciaDAO;
import br.com.dao.InternoDAO;
import br.com.dao.ResponsavelDAO;
import br.com.dao.StatusDAO;
import br.com.metodosgenericos.MetodosGenericosInterno;

/**
 * Esta classe é responsável por buscar as informações do interno da base de
 * dados. Esta classe atua entre a classe TelaConsultaInterno e InternoDAO.
 * 
 * @author Luiz Alberto
 * 
 */
public class BuscaInternoMetodos {

	/**
	 * Método usado para realizar a comunicação entre a tela de consulta de
	 * internos e a classe InternoDAO. Sua função é trazer os dados do interno
	 * de acordo com o parâmetro passado.
	 * 
	 * @param campo
	 * @param combo
	 * @param labelFoto
	 * @param area
	 * @return int
	 */
	public int buscaInterno(JTextField[] campo, JComboBox<String>[] combo,
			JLabel labelFoto, JTextArea area) {

		int idResponsavel = 0;

		MetodosGenericosInterno metodosInterno = new MetodosGenericosInterno();

		InternoDAO internoDAO = new InternoDAO();
		InternoBean interno = internoDAO.getInterno(campo[3].getText());

		ResponsavelDAO respDAO = new ResponsavelDAO();

		StatusDAO statusDAO = new StatusDAO();
		ConvenioDAO convenioDAO = new ConvenioDAO();
		ContribuicaoDAO contribuicaoDAO = new ContribuicaoDAO();
		DependenciaDAO dependenciaDAO = new DependenciaDAO();

		try {
			metodosInterno.limpaCamposConsulta(campo, combo, area, labelFoto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// :::::::::::: foto::::::::::::
			campo[0].setText(interno.getFoto());
			// metodosInterno.fotoPessoa(campo[0].getText(), labelFoto);
			// metodosInterno.fotoPessoaPorCompartilhamento(campo[0].getText(),
			// labelFoto);
			metodosInterno.fotoPessoa(campo[0].getText(), labelFoto);

			// :::::::::::: dados pessoais::::::::::::
			campo[1].setText(interno.getNome());
			campo[2].setText(interno.getNascimento().getDate());
			campo[3].setText(interno.getCpf());
			campo[4].setText(interno.getRg());
			campo[5].setText(interno.getTelefone());
			campo[54].setText(interno.getPai());
			campo[55].setText(interno.getMae());
			combo[10].setSelectedItem(interno.getEstadoCivil());
			campo[7].setText(interno.getConjuge());
			campo[8].setText(interno.getProfissao());
			combo[0].setSelectedItem(interno.getEscolaridade());
			campo[10].setText(interno.getNaturalidade());
			combo[1].setSelectedItem(interno.getUfNascimento());
			campo[12].setText(interno.getNacionalidade());
			campo[13].setText(interno.getEndereco());
			campo[14].setText(interno.getBairro());
			campo[15].setText(interno.getCidade());
		//	campo[16].setText(interno.getUf());// combo 2
			combo[2].setSelectedItem(interno.getUf());// combo 2

			// :::::::::::: dados complementares::::::::::::
			campo[21].setText(interno.getProcessoCriminal());
			campo[22].setText(interno.getLocal());
			combo[6].setSelectedItem(interno.getSofreAmeaca());
			// campo[24].setText(interno.getTipodeVicio());
			combo[12].setSelectedItem(dependenciaDAO.getTipo(interno
					.getdependencia()));
			campo[25].setText(interno.getTempodeUso());
			campo[26].setText(interno.getMotivodeUso());
			combo[7].setSelectedItem(interno.getDiabetico());
			campo[28].setText(interno.getContraiuDoenca());
			campo[29].setText(interno.getUsaMedicacao());
			campo[30].setText(interno.getRecomendacaoMedica());
			campo[31].setText(interno.getDocumentosEntregues());
			area.setText(interno.getObservacao());

			// ::::::::::::responsável::::::::::::
			if (respDAO.verifyInternoResponsavel(interno.getId()) == true) {

				ResponsavelBean responsavel = respDAO.getResponsavel(interno
						.getId());

				idResponsavel = responsavel.getId();

				campo[33].setText(responsavel.getNome());
				campo[34].setText(responsavel.getRg());
				campo[35].setText(responsavel.getCpf());
				/*
				 * if (responsavel.getNascimento().getDate()
				 * .matches("\\d{2}\\d{2}\\d{4}")) {
				 * campo[36].setText(responsavel.getNascimento().getDate()); }
				 * else { campo[36].setText(null); }
				 */
				campo[36].setText(responsavel.getNascimento().getDate());
				campo[37].setText(responsavel.getEndereco());
				campo[38].setText(responsavel.getBairro());
				campo[39].setText(responsavel.getCidade());
				combo[8].setSelectedItem(responsavel.getUf());
				campo[41].setText(responsavel.getTelefone());
				campo[42].setText(responsavel.getCelular());
				campo[43].setText(responsavel.getEmail());
				campo[44].setText(responsavel.getNaturalidade());
				combo[9].setSelectedItem(responsavel.getUfNascimento());
				campo[46].setText(responsavel.getNacionalidade());
				campo[47].setText(responsavel.getProfissao());
				campo[48].setText(responsavel.getPai());
				campo[49].setText(responsavel.getMae());

			}
			// :::::::::::: dados principais::::::::::::
			combo[3].setSelectedItem(statusDAO.getNome(interno.getStatus()));
			combo[4].setSelectedItem(convenioDAO.getNome(interno.getConvenio()));
			combo[5].setSelectedItem(contribuicaoDAO.getNome(interno
					.getContribuicao()));
			campo[50].setText(interno.getDataAdmissao().getDate());

			if (interno.getDataSaida().getDate().length() < 10) {
				campo[51].setText(null);
			} else {
				campo[51].setText(interno.getDataSaida().getDate());
			}
			combo[11].setSelectedItem(interno.getTipoSaida());
			campo[53].setText(interno.getMotivoSaida());

			return idResponsavel;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
