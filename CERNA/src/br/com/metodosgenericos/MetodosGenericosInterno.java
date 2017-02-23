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
 * Esta classe cont�m os m�todos gen�ricos pertinentes ao interno e que servem
 * para todas as necessidades. Um exemplo � o m�todo limpaCampos que � usado
 * ap�s um cadastro ou ent�o ao apertar um bot�o.
 * 
 * @author Luiz Alberto
 * 
 */
public class MetodosGenericosInterno extends MetodosGenericos {
	/**
	 * Este m�todo retorna um array de Strings para ser usado nos JLabels do
	 * formul�rio de cadastro.
	 * 
	 * @return String[]
	 */
	public String[] txtLabelsCadastro() {
		String[] txtLabel = {
				// ::::::::::::dados pessoais::::::::::::
				"Voc� n�o selecionou a foto", "Nome*", "Data de Nascimento*",
				"CPF*", "RG", "Telefone", "Estado civil", "C�njuge",
				"Profiss�o", "Escolaridade", "Naturalidade*", "UF*", "Pa�s*",
				"Endere�o", "Bairro", "Cidade", "UF", "Status",
				"Ajuda de custo", "Conv�nio", "ADD CONV�NIO(N�O UTILIZADO)",
				"Responde processo criminal?", "Onde?", "Sofre amea�a?",
				"Tipo de v�cio", "H� quanto tempo usa", "Motivo do 1� uso",
				"� diab�tico?", "Contraiu alguma doen�a?",
				"Usa alguma medica��o?", "Recomenda��o m�dica",
				"Documentos entregues", "Hist�rico", "Admiss�o*", "Sa�da*",
				"Tipo", "Motivo", "Pai", "M�e" };
		return txtLabel;
	}

	/**
	 * Este m�todo retorna um array de Strings para ser usado nos JLabels do
	 * formul�rio de cadastro.
	 * 
	 * @return String[]
	 */
	public String[] txtLabelsConsulta() {
		String[] txtLabel = {
				// ::::::::::::dados pessoais::::::::::::
				"Voc� n�o selecionou a foto", "Nome*", "Data de Nascimento*",
				"CPF*", "RG", "Telefone", "Estado civil", "C�njuge",
				"Profiss�o", "Escolaridade", "Naturalidade*", "UF*", "Pa�s*",
				"Endere�o", "Bairro", "Cidade", "UF", "Status",
				"Ajuda de custo", "Conv�nio", "ADD CONV�NIO(N�O UTILIZADO)",
				"Responde processo criminal?", "Onde?", "Sofre amea�a?",
				"Tipo de v�cio", "H� quanto tempo usa", "Motivo do 1� uso",
				"� diab�tico?", "Contraiu alguma doen�a?",
				"Usa alguma medica��o?", "Recomenda��o m�dica",
				"Documentos entregues", "Hist�rico", "Nome", "RG", "CPF",
				"Dt. Nasc.", "Endere�o", "Bairro", "Cidade", "UF", "Telefone",
				"Celular", "E-Mail", "Naturalidade", "UF", "Pa�s", "Profiss�o",
				"Pai", "M�e", "Admiss�o*", "Sa�da*", "Tipo", "Motivo", "Pai",
				"M�e" };
		return txtLabel;
	}

	/**
	 * Este m�todo retorna um array de Strings contendo os t�tulos usados no
	 * JPanel.
	 * 
	 * @return String[]
	 */
	public String[] txtPanels() {
		String txtpanel[] = { "Dados pessoais", "Informa��es principais",
				"Dados complementares", "Respons�vel pelo interno" };
		return txtpanel;
	}

	/**
	 * Este m�todo retorna um array de Strings contendo os textos usados nos
	 * bot�es.
	 * 
	 * @return String[]
	 */
	public String[] txtButtons() {
		String txtButtons[] = { "Webcam", "Arquivo", "Adicionar conv�nio", "" };
		return txtButtons;
	}

	/**
	 * Este m�todo retorna um array de Strings contendo o caminho das imagens
	 * para os bot�es do formul�rio.
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
	 * M�todo que instancia e retorna um novo objeto JComboBox<String> com dados
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
		combo.addItem("Uni�o est�vel");
		combo.addItem("Separado");
		return combo;
	}

	/**
	 * M�todo que instancia e retorna um novo objeto JComboBox<String> com dados
	 * sobre escolaridade.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbEscolaridade() {
		JComboBox<String> combo = new JComboBox<>();
		combo.addItem("Ensino fundamental incompleto");
		combo.addItem("Ensino fundamental completo");
		combo.addItem("Ensino m�dio incompleto");
		combo.addItem("Ensino m�dio completo");
		combo.addItem("Ensino superior incompleto");
		combo.addItem("Ensino superior completo");
		combo.addItem("P�s-Gradua��o");
		combo.addItem("Mestrado");
		combo.addItem("Doutorado");
		return combo;
	}

	/**
	 * Este m�todo retorna um combo box com os tipos de sa�da que o interno pode
	 * ter da comunidade. Estes tipos s�o: solicitada, administrativa, evas�o e
	 * terap�utica. A primeira op��o � em branco caso o interno ainda n�o tenha
	 * sa�do da reabilita��o por qualquer motivo.
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
	 * Este m�todo preenche e retorna o combo box com os status cadastrados na
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
	 * Este m�todo retorna um combo box com as op��es que informam se o interno
	 * � isento ou contribuinte.
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
	 * Este m�todo retorna um combo box com as op��es que informam a depend�ncia
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
	 * Este m�todo preenche e retorna o combo box com os internos cadastrados na
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
	 * Este m�todo recebe um nome como par�metro para realizar uma busca na base
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
	 * Este m�todo recebe um nome como par�metro para realizar uma busca na base
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
	 * Este m�todo adiciona um novo conv�nio a base de dados e j� popula seu
	 * nome no JComboBox.
	 * 
	 * @param combo
	 */
	public void novoConvenio(JComboBox<String> combo) {
		try {
			String novoConvenio = Show
					.caixaTexto("Insira o nome do novo conv�nio");
			if (novoConvenio.length() < 2) {
				Show.alerta("O conv�nio deve ter no m�nimo 2 caracteres");
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
	 * Este m�todo adiciona uma nova depend�ncia na base de dados e j� popula
	 * seu tipo no JComboBox.
	 * 
	 * @param combo
	 */
	public void novaDependencia(JComboBox<String> combo) {
		try {
			String novaDependencia = Show
					.caixaTexto("Insira o tipo de depend�ncia");
			if (novaDependencia.length() < 2) {
				Show.alerta("A depend�ncia deve ter no m�nimo 2 caracteres");
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
	 * Este m�todo serve para fazer uma chamada a classe SelectInternos que abre
	 * uma janela contendo os CPFs e seus respectivos donos.
	 */
	public void tabelaCPFs() {
		SelectInternos s = new SelectInternos(null, Config.tituloJanela, true);
		s.setVisible(true);
	}

	/**
	 * Este m�todo preenche uma DefaultTableModel contendo os nomes de todos os
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
	 * Este m�todo faz a limpeza dos campos de cadstro ao realizar uma opera��o
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
	 * Este m�todo limpa os campos do formul�rio de consulta do interno.
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
	 * Este m�todo bloqueia o formul�rio com exce��o do campo nome, que serve
	 * como par�metro de busca.
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
	 * Este m�todo libera o formul�rio para edi��o. O �nico campo n�o liberado �
	 * o CPF e a data de admiss�o do interno que � pega pela data do sistema no
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
	 * Este m�todo faz o controle do interno informando seu estado civil. Caso
	 * seja solteiro(a) o campo de conjuge fica desabilitado, caso contr�rio
	 * ficar� dispon�vel para edi��o.
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
	 * Este m�todo verifica a exist�ncia do CPF do interno no banco de dados.
	 * Caso ele esteja cadastrado, a ficha de interna��o em PDF � gerada com
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
			Show.erro("Este interno n�o existe. Verifique o CPF digitado.");
		}

	}

	/**
	 * Este m�todo retorna a data de admiss�o do interno, que � a data em que
	 * foi efetuado o seu cadastro.
	 * 
	 * @return String
	 */
	public String dataAdmissao() {
		return Data.showDate();
	}

	/**
	 * Este m�todo retorna a data de sa�da default do interno que � de seis
	 * meses ap�s a admiss�o.
	 * 
	 * @return String
	 */
	public String dataSaida() {
		return Data.showDate(0, 6, 0);
	}
}
