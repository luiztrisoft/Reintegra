package br.com.metodosgenericos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import br.com.bean.ContribuicaoBean;
import br.com.bean.DependenciaBean;
import br.com.bean.StatusBean;
import br.com.controller.Data;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.dao.ContribuicaoDAO;
import br.com.dao.ConvenioDAO;
import br.com.dao.DependenciaDAO;
import br.com.dao.InternoDAO;
import br.com.dao.StatusDAO;
import br.com.internos.frames.SelectInternos;
import br.com.internos.relatorios.FichadeInternacaoPDF;

/**
 * Esta classe contém os métodos genéricos pertinentes ao interno e que servem
 * para todas as necessidades. Um exemplo é o método limpaCampos que é usado
 * após um cadastro ou então ao apertar um botão.
 * 
 * @author Luiz Alberto
 * 
 */
public class MetodosGenericosInterno extends MetodosGenericos {
	/**
	 * Este método retorna um array de Strings para ser usado nos JLabels do
	 * formulário de cadastro.
	 * 
	 * @return String[]
	 */
	public String[] txtLabelsCadastro() {
		String[] txtLabel = {
				// ::::::::::::dados pessoais::::::::::::
				"Você não selecionou a foto", "Nome*", "Data de Nascimento*",
				"CPF*", "RG", "Telefone", "Estado civil", "Cônjuge",
				"Profissão", "Escolaridade", "Naturalidade*", "UF*", "País*",
				"Endereço", "Bairro", "Cidade", "UF", "Status",
				"Ajuda de custo", "Convênio", "ADD CONVÊNIO(NÃO UTILIZADO)",
				"Responde processo criminal?", "Onde?", "Sofre ameaça?",
				"Tipo de vício", "Há quanto tempo usa", "Motivo do 1° uso",
				"É diabético?", "Contraiu alguma doença?",
				"Usa alguma medicação?", "Recomendação médica",
				"Documentos entregues", "Histórico", "Admissão*", "Saída*",
				"Tipo", "Motivo", "Pai", "Mãe" };
		return txtLabel;
	}

	/**
	 * Este método retorna um array de Strings para ser usado nos JLabels do
	 * formulário de cadastro.
	 * 
	 * @return String[]
	 */
	public String[] txtLabelsConsulta() {
		String[] txtLabel = {
				// ::::::::::::dados pessoais::::::::::::
				"Você não selecionou a foto", "Nome*", "Data de Nascimento*",
				"CPF*", "RG", "Telefone", "Estado civil", "Cônjuge",
				"Profissão", "Escolaridade", "Naturalidade*", "UF*", "País*",
				"Endereço", "Bairro", "Cidade", "UF", "Status",
				"Ajuda de custo", "Convênio", "ADD CONVÊNIO(NÃO UTILIZADO)",
				"Responde processo criminal?", "Onde?", "Sofre ameaça?",
				"Tipo de vício", "Há quanto tempo usa", "Motivo do 1° uso",
				"É diabético?", "Contraiu alguma doença?",
				"Usa alguma medicação?", "Recomendação médica",
				"Documentos entregues", "Histórico", "Nome", "RG", "CPF",
				"Dt. Nasc.", "Endereço", "Bairro", "Cidade", "UF", "Telefone",
				"Celular", "E-Mail", "Naturalidade", "UF", "País", "Profissão",
				"Pai", "Mãe", "Admissão*", "Saída*", "Tipo", "Motivo", "Pai",
				"Mãe" };
		return txtLabel;
	}

	/**
	 * Este método retorna um array de Strings contendo os títulos usados no
	 * JPanel.
	 * 
	 * @return String[]
	 */
	public String[] txtPanels() {
		String txtpanel[] = { "Dados pessoais", "Informações principais",
				"Dados complementares", "Responsável pelo interno" };
		return txtpanel;
	}

	/**
	 * Este método retorna um array de Strings contendo os textos usados nos
	 * botões.
	 * 
	 * @return String[]
	 */
	public String[] txtButtons() {
		String txtButtons[] = { "Webcam", "Arquivo", "Adicionar convênio", "" };
		return txtButtons;
	}

	/**
	 * Este método retorna um array de Strings contendo o caminho das imagens
	 * para os botões do formulário.
	 * 
	 * @return String[]
	 */
	public String[] pathImagens() {
		String[] imagens = { "telainterno/camera.png",
				"telainterno/arquivo.png", "telainterno/plus.png",
				"telainterno/plus.png" };
		return imagens;
	}

	/**
	 * Método que instancia e retorna um novo objeto JComboBox<String> com dados
	 * sobre estado civil.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbEstCivil() {
		JComboBox<String> combo = new JComboBox<>();
		combo.addItem("Solteiro(a)");
		combo.addItem("Casado(a)");
		combo.addItem("Viuvo(a)");
		combo.addItem("Divorciado(a)");
		combo.addItem("União estável");
		combo.addItem("Separado");
		return combo;
	}

	/**
	 * Método que instancia e retorna um novo objeto JComboBox<String> com dados
	 * sobre escolaridade.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbEscolaridade() {
		JComboBox<String> combo = new JComboBox<>();
		combo.addItem("Ensino fundamental incompleto");
		combo.addItem("Ensino fundamental completo");
		combo.addItem("Ensino médio incompleto");
		combo.addItem("Ensino médio completo");
		combo.addItem("Ensino superior incompleto");
		combo.addItem("Ensino superior completo");
		combo.addItem("Pós-Graduação");
		combo.addItem("Mestrado");
		combo.addItem("Doutorado");
		return combo;
	}

	/**
	 * Este método retorna um combo box com os tipos de saída que o interno pode
	 * ter da comunidade. Estes tipos são: solicitada, administrativa, evasão e
	 * terapêutica. A primeira opção é em branco caso o interno ainda não tenha
	 * saído da reabilitação por qualquer motivo.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbTipoSaida() {
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem(Config.VOID);
		combo.addItem(Config.SOLICITADA);
		combo.addItem(Config.ADMINISTRATIVA);
		combo.addItem(Config.EVASAO);
		combo.addItem(Config.TERAPEUTICO);
		return combo;
	}

	/**
	 * Este método preenche e retorna o combo box com os status cadastrados na
	 * base da dados.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbStatus() {
		JComboBox<String> combo = new JComboBox<>();
		StatusDAO dao = new StatusDAO();
		List<StatusBean> statusList = dao.listaStatus();
		for (StatusBean statusBean : statusList) {
			combo.addItem(statusBean.getSituacao());
		}
		return combo;
	}

	/**
	 * Este método retorna um combo box com as opções que informam se o interno
	 * é isento ou contribuinte.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbContribuicao() {
		JComboBox<String> combo = new JComboBox<>();
		ContribuicaoDAO dao = new ContribuicaoDAO();
		List<ContribuicaoBean> lista = dao.listaContribuicao();
		for (ContribuicaoBean contribuicaoBean : lista) {
			combo.addItem(contribuicaoBean.getSituacao());
		}
		return combo;
	}

	/**
	 * Este método retorna um combo box com as opções que informam a dependência
	 * do interno.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbDependencia() {
		JComboBox<String> combo = new JComboBox<>();
		DependenciaDAO dao = new DependenciaDAO();
		List<DependenciaBean> lista = dao.listaDependencia();
		for (DependenciaBean dependenciaBean : lista) {
			combo.addItem(dependenciaBean.getTipo());
		}
		return combo;
	}

	/**
	 * Este método preenche e retorna o combo box com os internos cadastrados na
	 * 
	 * @return JComboBox<String>
	 */
	// public JComboBox<String> cbInternos() {
	// JComboBox<String> combo = new JComboBox<>();
	// InternoDAO dao = new InternoDAO();
	// List<InternoBean> internoList = dao.listaInterno();
	// for (InternoBean internoBean : internoList) {
	// combo.addItem(internoBean.getNome());
	// }
	// return combo;
	// }

	/**
	 * Este método recebe um nome como parâmetro para realizar uma busca na base
	 * de dados e retorna um inteiro contendo o valor do indice da entidade
	 * Status.
	 * 
	 * @param string
	 * @return int
	 */
	public int indiceStatus(String string) {
		StatusDAO dao = new StatusDAO();
		int id = dao.status(string);
		return id;
	}

	/**
	 * Este método recebe um nome como parâmetro para realizar uma busca na base
	 * de dados e retorna um inteiro contendo o valor do indice da entidade
	 * Contribuicao.
	 * 
	 * @param string
	 * @return int
	 */
	public int indiceContribuicao(String string) {
		ContribuicaoDAO dao = new ContribuicaoDAO();
		int id = dao.contribuicao(string);
		return id;
	}

	/**
	 * Este método adiciona um novo convênio a base de dados e já popula seu
	 * nome no JComboBox.
	 * 
	 * @param combo
	 */
	public void novoConvenio(JComboBox<String> combo) {
		try {
			String novoConvenio = Show
					.caixaTexto("Insira o nome do novo convênio");
			if (novoConvenio.length() < 2) {
				Show.alerta("O convênio deve ter no mínimo 2 caracteres");
			} else {
				combo.addItem(novoConvenio);
				ConvenioDAO dao = new ConvenioDAO();
				dao.insert(novoConvenio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método adiciona uma nova dependência na base de dados e já popula
	 * seu tipo no JComboBox.
	 * 
	 * @param combo
	 */
	public void novaDependencia(JComboBox<String> combo) {
		try {
			String novaDependencia = Show
					.caixaTexto("Insira o tipo de dependência");
			if (novaDependencia.length() < 2) {
				Show.alerta("A dependência deve ter no mínimo 2 caracteres");
			} else {
				combo.addItem(novaDependencia);
				DependenciaDAO dao = new DependenciaDAO();
				dao.insert(novaDependencia);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método serve para fazer uma chamada a classe SelectInternos que abre
	 * uma janela contendo os CPFs e seus respectivos donos.
	 */
	public void tabelaCPFs() {
		SelectInternos s = new SelectInternos(null, Config.tituloJanela, true);
		s.setVisible(true);
	}

	/**
	 * Este método preenche uma DefaultTableModel contendo os nomes de todos os
	 * internos cadastrados.
	 * 
	 * @param coluna
	 */
	public void tabelaInternos(DefaultTableModel coluna) {
		Timer timer = new Timer(0, null);
		timer.start();

		InternoDAO dao = new InternoDAO();
		dao.preencherTabela(coluna);
		timer.stop();
	}

	/**
	 * Este método faz a limpeza dos campos de cadstro ao realizar uma operação
	 * de cadastro.
	 * 
	 * @param textField
	 * @param combo
	 * @param textArea
	 * @param label
	 */
	public void limpaCamposCadastro(JTextField[] textField,
			JComboBox<String>[] combo, JTextArea textArea, JLabel label) {
		for (int i = 0; i < textField.length; i++) {
			textField[i].setText("");
		}

		for (int i = 0; i < combo.length; i++) {
			combo[i].setSelectedIndex(0);
		}
		textArea.setText("");
		semFoto(label);
		combo[1].setSelectedIndex(21);
		combo[2].setSelectedIndex(21);
		combo[7].setSelectedIndex(1);
		textField[12].setText("Brasil");
		textField[1].requestFocus();
		textField[33].setText(dataAdmissao());
		textField[34].setText(dataSaida());
	}

	/**
	 * Este método limpa os campos do formulário de consulta do interno.
	 * 
	 * @param textField
	 * @param combo
	 * @param textArea
	 * @param label
	 */
	public void limpaCamposConsulta(JTextField[] textField,
			JComboBox<String>[] combo, JTextArea textArea, JLabel label) {
		for (int i = 0; i < textField.length; i++) {
			textField[i].setText("");
		}

		for (int i = 0; i < combo.length; i++) {
			combo[i].setSelectedIndex(0);
		}
		textArea.setText("");
		semFoto(label);
		combo[1].setSelectedIndex(21);
		combo[2].setSelectedIndex(21);
		combo[7].setSelectedIndex(1);
		textField[12].setText("Brasil");
		textField[1].requestFocus();
	}

	/**
	 * Este método bloqueia o formulário com exceção do campo nome, que serve
	 * como parâmetro de busca.
	 * 
	 * @param textField
	 * @param button
	 * @param textArea
	 */
	public void bloquearFormulario(JTextField[] textField, JButton[] button,
			JTextArea textArea) {
		textField[1].setEditable(false);
		textField[2].setEditable(false);
		textField[3].setEditable(true);
		for (int i = 4; i < textField.length; i++) {
			textField[i].setEditable(false);
		}

		for (int i = 0; i < button.length; i++) {
			button[i].setEnabled(false);
		}

		textArea.setEditable(false);
	}

	/**
	 * Este método libera o formulário para edição. O único campo não liberado é
	 * o CPF e a data de admissão do interno que é pega pela data do sistema no
	 * momento do cadastro.
	 * 
	 * @param textField
	 * @param button
	 * @param textArea
	 */
	public void liberarFormulario(JTextField[] textField, JButton[] button,
			JTextArea textArea, JComboBox<String> combo) {
		for (int i = 0; i < textField.length; i++) {
			textField[i].setEditable(true);
		}
		textField[3].setEditable(false);
		for (int i = 33; i < 50; i++) {
			textField[i].setEditable(false);
		}
		for (int i = 0; i < button.length; i++) {
			button[i].setEnabled(true);
		}
		textArea.setEditable(true);
	}

	/**
	 * Este método faz o controle do interno informando seu estado civil. Caso
	 * seja solteiro(a) o campo de conjuge fica desabilitado, caso contrário
	 * ficará disponível para edição.
	 * 
	 * @param combo
	 * @param campo
	 */
	public void bloqueiaConjuge(final JComboBox<String> combo,
			final JTextField campo) {
		if (combo.getSelectedItem().equals("Solteiro(a)")) {
			campo.setEnabled(false);
		}
		// ::::::::::::bloqueia conjuge::::::::::::
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (combo.getSelectedItem().equals("Solteiro(a)")) {
					campo.setText("");
					campo.setEnabled(false);
				} else {
					campo.setEnabled(true);
				}
			}
		});
	}

	/**
	 * Este método verifica a existência do CPF do interno no banco de dados.
	 * Caso ele esteja cadastrado, a ficha de internação em PDF é gerada com
	 * seus dados.
	 * 
	 * @param campo
	 * @param combo
	 * @param textArea
	 * @param idResponsavel 
	 */
	public void fichaInternacao(JTextField[] campo, JComboBox<String>[] combo,
			JTextArea textArea, int idResponsavel) {
		InternoDAO dao = new InternoDAO();
		if (dao.verifyCPF(campo[3].getText()) == true) {
			FichadeInternacaoPDF pdf = new FichadeInternacaoPDF();
			pdf.createPDF(campo, combo, textArea, idResponsavel);
		} else {
			Show.erro("Este interno não existe. Verifique o CPF digitado.");
		}

	}

	/**
	 * Este método retorna a data de admissão do interno, que é a data em que
	 * foi efetuado o seu cadastro.
	 * 
	 * @return String
	 */
	public String dataAdmissao() {
		return Data.showDate();
	}

	/**
	 * Este método retorna a data de saída default do interno que é de seis
	 * meses após a admissão.
	 * 
	 * @return String
	 */
	public String dataSaida() {
		return Data.showDate(0, 6, 0);
	}
}
