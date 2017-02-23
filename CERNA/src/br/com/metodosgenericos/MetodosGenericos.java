package br.com.metodosgenericos;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import br.com.bean.ConvenioBean;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.controller.TableControl;
import br.com.dao.AjudadeCustoDAO;
import br.com.dao.ConvenioDAO;
import br.com.dao.DependenciaDAO;
import br.com.dao.InternoDAO;

/**
 * Esta classe contém os métodos que podem ser usados em toda a aplicação, como
 * por exemplo a execução da webcam que pode ser usada tanto no cadastro de
 * internos como de funcionários. É uma classe genérica que fornece recursos as
 * suas subclasses e também não pode ser instanciada devido a ser abstrata.
 * 
 * @author Luiz Alberto
 * 
 */
public abstract class MetodosGenericos {

	// public final static int LARGURA_FOTO = 250;
	public final static int LARGURA_FOTO = 190;
	public final static int ALTURA_FOTO = 175;

	/**
	 * Este método serve para mostrar foto indisponível na tela quando não há
	 * foto cadastrada para o funcionário. Este recebe o array de JLabels e pega
	 * o que está na última posição.
	 * 
	 * @param label
	 */
	public void semFoto(JLabel[] label) {
		URL foto = resources.Recursos.class.getResource("nophoto/sem foto.jpg");
		ImageIcon imagem = new ImageIcon(foto);
		Image redimensionadora = imagem.getImage().getScaledInstance(
				LARGURA_FOTO, ALTURA_FOTO, Image.SCALE_DEFAULT);
		imagem = new ImageIcon(redimensionadora);
		label[label.length - 1].setIcon(imagem);
	}

	/**
	 * Este método serve para mostrar foto indisponível na tela quando não há
	 * foto cadastrada para o interno. Ao contrário do método anterior o label
	 * já vem específico.
	 * 
	 * @param label
	 */
	public void semFoto(JLabel label) {
		URL foto = resources.Recursos.class.getResource("nophoto/sem foto.jpg");
		ImageIcon imagem = new ImageIcon(foto);
		Image redimensionadora = imagem.getImage().getScaledInstance(
				LARGURA_FOTO, ALTURA_FOTO, Image.SCALE_DEFAULT);
		imagem = new ImageIcon(redimensionadora);
		label.setIcon(imagem);
	}

	/*
	 * =================================================
	 * 
	 * FOTO PESSOA
	 * 
	 * =================================================
	 */

	/**
	 * Este método verifica se o typeConnection é localhost ou por rede e invoca
	 * o método equivalente a ele.
	 * 
	 * @param imagePath
	 * @param label
	 */
	public void fotoPessoa(String imagePath, JLabel label) {

		if (ConnectionFactory.typeConnection() == 1) {
			fotoPessoaLocalhost(imagePath, label);
		} else if (ConnectionFactory.typeConnection() == 2) {
			fotoPessoaPorCompartilhamento(imagePath, label);
		}

	}

	/**
	 * Exibe na tela a foto escolhida do arquivo a ser salva no banco de dados
	 * da aplicação. Diferente do método anterior, o label já deve vir
	 * especificado.
	 * 
	 * @param imagePath
	 * @param label
	 */
	public void fotoPessoaLocalhost(String imagePath, JLabel label) {
		ImageIcon imagem = new ImageIcon(imagePath);
		Image redimensionadora = imagem.getImage().getScaledInstance(
				LARGURA_FOTO, ALTURA_FOTO, Image.SCALE_DEFAULT);
		imagem = new ImageIcon(redimensionadora);
		label.setIcon(imagem);
	}

	/**
	 * Este método serve para mostrar a foto na tela quando a aplicação está
	 * funcionando em mais de uma máquina. Ao invés de usar o diretório padrão
	 * C: ele usa o formato \\IP\nome da pasta compartilhada\foto.png.
	 * 
	 * @param imagePath
	 * @param label
	 */
	private void fotoPessoaPorCompartilhamento(String imagePath, JLabel label) {
		imagePath = imagePath.substring(17);
		ImageIcon imagem = new ImageIcon(ConnectionFactory.folderAddress()
				+ imagePath);
		Image redimensionadora = imagem.getImage().getScaledInstance(
				LARGURA_FOTO, ALTURA_FOTO, Image.SCALE_DEFAULT);
		imagem = new ImageIcon(redimensionadora);
		label.setIcon(imagem);
	}

	/**
	 * Este método verifica se o typeConnection é localhost ou por rede e invoca
	 * o método equivalente a ele.
	 * 
	 * @param imagePath
	 * @param label
	 */
	/*
	 * public void fotoPessoa(String imagePath, JLabel[] label) {
	 * 
	 * if (Static.typeConnection() == 1) { fotoPessoaLocalhost(imagePath,
	 * label); } else if (Static.typeConnection() == 2) {
	 * fotoPessoaPorCompartilhamento(imagePath, label); }
	 * 
	 * }
	 */
	/**
	 * Exibe na tela a foto escolhida do arquivo a ser salva no banco de dados
	 * da aplicação. Note na última linha do método, que a imagem está na última
	 * posição do array de JLabels:
	 * 
	 * label[label.length - 1] indica a última posição do array.
	 * 
	 * @param imagePath
	 * @param label
	 */
	/*
	 * public void fotoPessoaLocalhost(String imagePath, JLabel[] label) {
	 * ImageIcon imagem = new ImageIcon(imagePath); Image redimensionadora =
	 * imagem.getImage().getScaledInstance( LARGURA_FOTO, ALTURA_FOTO,
	 * Image.SCALE_DEFAULT); imagem = new ImageIcon(redimensionadora);
	 * label[label.length - 1].setIcon(imagem); }
	 */
	/**
	 * Este método serve para mostrar a foto na tela quando a aplicação está
	 * funcionando em mais de uma máquina. Ao invés de usar o diretório padrão
	 * C: ele usa o formato \\IP\nome da pasta compartilhada\foto.png.
	 * 
	 * @param imagePath
	 * @param label
	 */
	/*
	 * private void fotoPessoaPorCompartilhamento(String imagePath, JLabel
	 * label[]) { imagePath = imagePath.substring(17); ImageIcon imagem = new
	 * ImageIcon(Static.ipPasta() + imagePath); Image redimensionadora =
	 * imagem.getImage().getScaledInstance( LARGURA_FOTO, ALTURA_FOTO,
	 * Image.SCALE_DEFAULT); imagem = new ImageIcon(redimensionadora);
	 * label[label.length - 1].setIcon(imagem); }
	 */
	/*
	 * =================================================
	 * 
	 * TRANSFERIR FOTO
	 * 
	 * =================================================
	 */

	/**
	 * Este método verifica se o typeConnection é localhost ou por rede e invoca
	 * o método equivalente a ele.
	 * 
	 * @param fotoNova
	 * @param fotoAtual
	 * @return String com o nome do novo diretório
	 */
	public String transferirFoto(String fotoNova, String fotoAtual) {
		String destino = null;

		if (ConnectionFactory.typeConnection() == 1) {
			destino = transferirFotoLocalhost(fotoNova, fotoAtual);
		} else if (ConnectionFactory.typeConnection() == 2) {
			destino = transferirFotoPorCompartilhamento(fotoNova, fotoAtual);
		}

		return destino;
	}

	/**
	 * Este método recebe o diretório de uma foto e através do método copiar
	 * desta mesma classe, faz uma cópia dela para uma pasta específica do
	 * sistema retornando uma String com este novo diretório.
	 * 
	 * @param fotoNova
	 * @param fotoAtual
	 * @return String com o nome do novo diretório
	 */
	private String transferirFotoLocalhost(String fotoNova, String fotoAtual) {
		File fonte = null, destino = null;
		try {
			new File("C:/CERNA/imagens").mkdir();
			fonte = new File(fotoNova);
			destino = new File("C:/CERNA/imagens/foto" + renomearArquivo()
					+ ".png");
			copiar(fonte, destino);
		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.system, e.getMessage());
			Show.alerta("A imagem original não foi alterada.");
			return fotoAtual;
		}
		return destino.toString();
	}

	/**
	 * Este método copia as fotos para a pasta "imagens" do servidor e retorna o
	 * endereço da nova foto no servidor para que possa ser exibida na tela
	 * posteriormente. Para isso, tal pasta deve estar compartilhada na rede. A
	 * cópia é feita para a pasta do servidor pelo endereço
	 * \\IP\imagens\foto.png.
	 * 
	 * @param fotoNova
	 * @param fotoAtual
	 * @return String com o nome do novo diretório
	 */
	private String transferirFotoPorCompartilhamento(String fotoNova,
			String fotoAtual) {
		File fonte = null, destino = null;
		String nomeFoto = null;
		try {
			nomeFoto = "foto" + renomearArquivo() + ".png";
			fonte = new File(fotoNova);
			destino = new File(ConnectionFactory.folderAddress() + nomeFoto);
			copiar(fonte, destino);
		} catch (Exception e) {
			e.printStackTrace();
			Save.log(Config.system, e.getMessage());
			Show.alerta("A imagem original não foi alterada.");
			return fotoAtual;
		}
		return "C:\\CERNA\\imagens\\" + nomeFoto;
	}

	/**
	 * Este método abre o arquivo executável MyCam.exe para o usuári poder tirar
	 * uma foto da nova pessoa cadastrada.
	 */
	public void executarCamera() {
		try {
			String myCam = "C:/CERNA/MyCam/MyCam.exe";
			Runtime.getRuntime().exec(myCam);
		} catch (Exception excecao) {
			JOptionPane.showMessageDialog(null,
					"Não foi possível abrir o dispositivo.", "",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Método utilizado para buscar fotos do HD para inserção na base de dados,
	 * retornando uma String com o caminho absoluto da imagem.
	 * 
	 * @return String
	 */
	public String procurarFoto() {
		File nome;
		String path = "";
		JFileChooser chooser = new JFileChooser(new File(
				"C:/CERNA/MyCam Pictures/"));
		chooser.getComponent(0).setVisible(false);
		int res = chooser.showOpenDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			nome = chooser.getSelectedFile();
			path = nome.getAbsolutePath();
		}
		return path;
	}

	/**
	 * Método que retorna uma String contendo ano, mês, dia, hora, minutos e
	 * segundos fazendo dela uma String única. Utilizada para renomear arquivos
	 * e backups permitindo que eles fiquem no mesmo diretório devido a sua
	 * customização.
	 * 
	 * @return String contendo ano, mês, dia, hora, minutos e segundos.
	 */
	public String renomearArquivo() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}

	/**
	 * Antes de fazer a verificação do CPF através do método validaCpf(strCpf) é
	 * necessário testar para descobrir se o CPF informado não possui uma
	 * estrura não permitida de números sequenciais como 111.111.111-11 por
	 * exemplo.
	 * 
	 * @param strCpf
	 * @return boolean
	 */
	public boolean verCpf(String strCpf) {
		boolean retorno = false;
		for (int i = 0; i < 10; i++) {
			String cpf = "" + i + i + i + "." + i + i + i + "." + i + i + i
					+ "-" + i + i;
			if (strCpf.equals(cpf)) {
				Show.erro("O CPF não pode conter a estrutura " + cpf);
				return false;
			}
			retorno = validaCpf(strCpf);
		}
		return retorno;
	}

	/**
	 * Este método recebe dois parâmetros do tipo file(fonte e destino) para
	 * fazer uma cópia de uma foto para outro ponto no sistema.
	 * 
	 * @param fonte
	 * @param destino
	 * @throws IOException
	 */
	private void copiar(File fonte, File destino) throws IOException {
		InputStream in = new FileInputStream(fonte);
		OutputStream out = new FileOutputStream(destino);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	/**
	 * Método utilizado para verificar se um número de cpf é válido ou não
	 * retornando um valor boolean 'true' para um resultado válido e 'false'
	 * para um resultado negativo.
	 * 
	 * @param strCpf
	 * @return boolean
	 */
	private boolean validaCpf(String strCpf) {

		if (!strCpf.substring(0, 1).equals("")) {
			try {
				@SuppressWarnings("unused")
				boolean validado = true;
				int d1, d2;
				int digito1, digito2, resto;
				int digitoCPF;
				String nDigResult;
				strCpf = strCpf.replace('.', ' ');
				strCpf = strCpf.replace('-', ' ');
				strCpf = strCpf.replaceAll(" ", "");
				d1 = d2 = 0;
				digito1 = digito2 = resto = 0;
				for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
					digitoCPF = Integer.valueOf(
							strCpf.substring(nCount - 1, nCount)).intValue();
					d1 = d1 + (11 - nCount) * digitoCPF;
					d2 = d2 + (12 - nCount) * digitoCPF;
				}
				;
				resto = (d1 % 11);
				if (resto < 2)
					digito1 = 0;
				else
					digito1 = 11 - resto;
				d2 += 2 * digito1;
				resto = (d2 % 11);
				if (resto < 2)
					digito2 = 0;
				else
					digito2 = 11 - resto;
				String nDigVerific = strCpf.substring(strCpf.length() - 2,
						strCpf.length());
				nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
				return nDigVerific.equals(nDigResult);
			} catch (Exception e) {
				System.err.println("Erro !" + e);
				return false;
			}
		} else
			return false;
	}

	/**
	 * Verifica se o campo de texto possui no mínimo 3 caracteres.
	 * 
	 * @param string
	 * @param qtd
	 * @return boolean
	 */
	public boolean validaCampo(String string, int qtd) {
		if (string.equals(null) || string.length() < qtd) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Método que verifica o número de caracteres do login de usuário.
	 * 
	 * @param login
	 * @return boolean
	 */
	public boolean validaLogin(String login) {
		if (login.length() < 4 || login.equalsIgnoreCase(null)) {
			Show.alerta("O usuário deve ter no mínimo 4 caracteres.");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Método que verifica o número de caracteres da senha de usuário.
	 * 
	 * @param senha
	 * @return boolean
	 */
	public boolean validaSenha(String senha) {
		if (senha.length() < 6 || senha.equalsIgnoreCase(null)) {
			Show.alerta("A senha deve ter no mínimo 6 caracteres.");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Este método faz com que a tabela seja editada a fim de melhorar sua
	 * qualidade visual e deixá-la mais consistente, impedindo por exemplo que
	 * ela seja reordenada ou que se possa selecionar mais de um ítem por vez.
	 * Note que neste método é importante informar a quantidade de colunas que a
	 * tabela conterá a fim de que as modificações sejam aplicadas a todas as
	 * colunas e também, para que o método seja compatível com todas as tabelas.
	 * 
	 * @param tabela
	 * @param qtdColunas
	 */
	public void modelarTabela(JTable tabela, int qtdColunas) {
		for (int i = 0; i < qtdColunas; i++) {
			tabela.getColumnModel().getColumn(i).setResizable(false);
		}
		TableControl funcao = new TableControl();
		funcao.modelarTabela(tabela);
	}

	/**
	 * Este método retorna um comboBox contendo a sigla de todos os estados.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbUF() {
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("--");
		combo.addItem("AC");
		combo.addItem("AL");
		combo.addItem("AP");
		combo.addItem("AM");
		combo.addItem("BA");
		combo.addItem("CE");
		combo.addItem("DF");
		combo.addItem("ES");
		combo.addItem("GO");
		combo.addItem("MA");
		combo.addItem("MT");
		combo.addItem("MS");
		combo.addItem("MG");
		combo.addItem("PA");
		combo.addItem("PB");
		combo.addItem("PR");
		combo.addItem("PE");
		combo.addItem("PI");
		combo.addItem("RJ");
		combo.addItem("RN");
		combo.addItem("RS");
		combo.addItem("RO");
		combo.addItem("RR");
		combo.addItem("SC");
		combo.addItem("SP");
		combo.addItem("SE");
		combo.addItem("TO");
		return combo;
	}

	/**
	 * Este método é usado para obter o índice do estado de rondônia para
	 * aparecer no combobox
	 * 
	 * @return
	 */
	public int indexRO() {
		return 22;
	}

	/**
	 * Este método recebe o cpf do interno e retorna se ele existe ou não.
	 * 
	 * @param cpf
	 * @return boolean
	 */
	public boolean verifyCpf(String cpf) {
		InternoDAO dao = new InternoDAO();
		if (dao.verifyCPF(cpf) == true) {
			return true;
		} else {
			Show.erro("Este interno não existe");
			return false;
		}
	}

	/**
	 * este método recebe o cpf do interno como parâmetro e retorna o seu id.
	 * 
	 * @param cpf
	 * @return long
	 */
	public long returnIdInterno(String cpf) {
		InternoDAO dao = new InternoDAO();
		long id = dao.returnId(cpf);
		return id;
	}

	/**
	 * Este método preenche e retorna o combo box com os convênios cadastrados
	 * na base de dados.
	 * 
	 * @return JComboBox<String>
	 */
	public JComboBox<String> cbConvenio() {
		JComboBox<String> combo = new JComboBox<String>();
		ConvenioDAO dao = new ConvenioDAO();
		List<ConvenioBean> lista = dao.listaConvenio();
		for (ConvenioBean convenio : lista) {
			combo.addItem(convenio.getNome());
		}
		return combo;
	}

	/**
	 * Este método retorna um valor long com o ID do interno caso esteja gravado
	 * na base de dados.
	 * 
	 * @param cpf
	 * @return long
	 */
	public int getIdInterno(String cpf) {
		InternoDAO dao = new InternoDAO();
		int id = dao.getId(cpf);
		return id;
	}

	/**
	 * Este método recebe um nome como parâmetro para realizar uma busca na base
	 * de dados e retorna um inteiro contendo o valor do indice da entidade
	 * Convenios.
	 * 
	 * @param string
	 * @return int
	 */
	public int indiceFormaPagto(String string) {
		AjudadeCustoDAO dao = new AjudadeCustoDAO();
		int id = dao.getIdFormaPagto(string);
		return id;
	}

	/**
	 * Este método retorna o indice dos convênios de acordo com o nome passado
	 * como parâmetro.
	 * 
	 * @param nome
	 * @return int
	 */
	public int indiceConvenios(String nome) {
		AjudadeCustoDAO dao = new AjudadeCustoDAO();
		int id = dao.getIdConvenios(nome);
		return id;
	}

	/**
	 * Este método retorna o indice das dependências de acordo com o tipo
	 * passado como parâmetro.
	 * 
	 * @param tipo
	 * @return int
	 */
	public int indiceDependencia(String tipo) {
		DependenciaDAO dao = new DependenciaDAO();
		int id = dao.getIdConvenios(tipo);
		return id;
	}
}
