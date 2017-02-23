package br.com.backup.frames;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import br.com.backup.metodos.BackupManager;
import br.com.backup.metodos.RestoreManager;
import br.com.controller.Save;
import br.com.controller.Show;
import br.com.controller.Config;
import br.com.metodosgenericos.MetodosGenericosBackup;

/**
 * Esta classe � respons�vel por apresentar a view principal do m�dulo de backup
 * e restaura��o dos dados do sistema.
 * 
 * @author Luiz Alberto
 * 
 */
@SuppressWarnings("serial")
public class TelaBackup extends JDialog {

	private File origem, destino, destinoImagens;

	private Container c;
	private JButton botao[];
	private JScrollPane pane;

	@SuppressWarnings("rawtypes")
	private DefaultListModel model;
	@SuppressWarnings("rawtypes")
	private JList list;

	private BackupManager backupManager;
	private RestoreManager restoreManager;

	public TelaBackup(JFrame jf, String s, boolean b) {
		super(jf, s, b);
		c = getContentPane();
		setLayout(null);
		setSize(600, 350);
		setLocationRelativeTo(null);
		c.setBackground(new Color(Config.RED, Config.GREEN, Config.BLUE));
		setResizable(false);

		screen();
	}

	/**
	 * Este m�todo constr�i a tela principal do m�dulo de backup.
	 */
	@SuppressWarnings("unchecked")
	private void screen() {
		Font fonte = new Font("Tahoma", Font.BOLD, 18);
		Font fontNormal = new Font("Tahoma", Font.PLAIN, 14);

		// ::::::::bot�es::::::::
		byte qtdBotoes = 2;
		botao = new JButton[qtdBotoes];
		String txtButton[] = { "Backup", "Restore" };
		String imagePath[] = { "telabackup/filesaveas.png",
				"telabackup/revert.png" };
		URL urlButton[] = new URL[qtdBotoes];

		for (int i = 0; i < botao.length; i++) {
			urlButton[i] = resources.Recursos.class.getResource(imagePath[i]);
			botao[i] = new JButton(txtButton[i], new ImageIcon(urlButton[i]));
			c.add(botao[i]);
			botao[i].setFont(fonte);
		}

		// ::::::::lista de mensagens::::::::
		model = new DefaultListModel<>();
		list = new JList<>(model);
		list.setFont(fontNormal);
		list.setForeground(new Color(56, 69, 107));

		pane = new JScrollPane(list);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		c.add(pane);

		// ::::::::backup::::::::
		botao[0].setBounds(20, 20, 170, 50);

		// ::::::::restore::::::::
		botao[1].setBounds(400, 20, 170, 50);

		// ::::::::lista de mensagens::::::::
		pane.setBounds(20, 90, 550, 200);

		/*
		 * ==========================================
		 * 
		 * FUN��ES
		 * 
		 * Fun��es dos bot�es deste m�dulo.
		 * 
		 * ==========================================
		 */
		botao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backup();
			}
		});

		botao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restore();
			}
		});
	}

	/*
	 * ==========================================================
	 * 
	 * BACKUP DO SISTEMA
	 * 
	 * Os m�todos a seguir s�o utilizados para realiza��o do backup dos dados e
	 * imagens do sistema.
	 * 
	 * ==========================================================
	 */

	/**
	 * Este m�todo invoca os m�todos backupImagens e backupScript desta classe
	 * respons�veis por gerenciar o backup das imagens e dos dados do sistema.
	 */
	@SuppressWarnings("unchecked")
	private void backup() {
		backupManager = new BackupManager();

		boolean imagens = backupImagens();
		boolean script = backupScript();

		if (imagens == true && script == true) {
			model.addElement("Backup completo!");
			Show.informacao("Backup da base de dados efetuado com sucesso!");
			Save.log(Config.usuarioLogado, "Efetuou o backup da base de dados");
		} else {
			// model.addElement("Falha no backup!");
			// Show.erro("O backup n�o foi efetuado corretamente!");
			// Save.log(Config.usuarioLogado,
			// "Falhou ao efetuar o backup das imagens!");
		}
	}

	/**
	 * Este m�todo se comunica com a classe BackupManager para gerenciar o
	 * backup das fotos do banco e abre o JFileChooser para selecionar o
	 * diret�rio onde estas imagens ser�o guardadas.
	 */
	@SuppressWarnings("unchecked")
	private boolean backupImagens() {
		MetodosGenericosBackup funcao = new MetodosGenericosBackup();

		origem = new File(Config.getDiretorio());

		// ::::::::cria o diret�rio geral::::::::
		destino = new File(backupManager.selectDirectory()
				+ "\\Backup_Reintegra_" + funcao.renomearArquivo());
		destino.mkdir();

		// ::::::::cria o diret�rio das imagens::::::::
		destinoImagens = new File(destino.getPath() + "\\imagens");
		destinoImagens.mkdir();

		try {
			backupManager.copyAll(origem, destinoImagens, true, model);
			return true;
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
			model.addElement(e.getMessage());
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			model.addElement(e.getMessage());
			return false;
		}

	}

	/**
	 * Este m�todo � respons�vel pelo gerenciamento do backup do pr�prio banco
	 * de dados, gerando assim um arquivo com a extens�o ".SQL" com o script de
	 * cria��o e os dados que est�o salvos nele.
	 */
	@SuppressWarnings("unchecked")
	private boolean backupScript() {
		try {
			boolean backup = backupManager.backupScript(destino, model);
			return backup;
		} catch (Exception e) {
			model.addElement(e.getMessage());
			return false;
		}

	}

	/*
	 * ==========================================================
	 * 
	 * RESTORE DO SISTEMA
	 * 
	 * Os m�todos a seguir s�o utilizados para realiza��o da restaura��o dos
	 * dados e imagens do sistema.
	 * 
	 * ==========================================================
	 */

	/**
	 * Este m�todo invoca os m�todos restoreImagens e restoreScript para poder
	 * restaurar o banco de dados.
	 * 
	 */
	@SuppressWarnings("unchecked")
	private void restore() {

		boolean confirm = Show
				.caixaConfirmacao("Deseja realmente realizar a restaura��o completa dos dados do sistema?");

		if (confirm == true) {
			restoreManager = new RestoreManager();
			boolean imagens = restoreImagens();
			boolean script = restoreScript();

			if (imagens == true && script == true) {
				model.addElement("Restaura��o de dados completa!");
				Show.informacao("Restaura��o da base de dados efetuada com sucesso!");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Save.log(Config.usuarioLogado,
						"Efetuou a restaura��o da base de dados");
			} else {
//				model.addElement("Falha na restaura��o!");
//				Show.erro("A restaura��o n�o foi efetuada corretamente!");
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				Save.log(Config.usuarioLogado,
//						"Falhou ao efetuar a restaura��o das imagens!");
			}
		}
	}

	/**
	 * Este m�todo � usado para restaurar as imagens do banco de dados.
	 * 
	 * @return boolean
	 */
	private boolean restoreImagens() {

		try {
			String diretorioOrigem = restoreManager.selectDirectory();
			origem = new File(diretorioOrigem);
			File imagemOrigem = new File(origem.getPath() + "\\imagens");
			destino = new File(Config.getDiretorio());

			restoreManager.copyAll(imagemOrigem, destino, true, model);
			return true;

		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Este m�todo � usado para restaurar o arquivo contendo as informa��es do
	 * banco de dados.
	 * 
	 * @return boolean
	 */
	private boolean restoreScript() {
		try {
			boolean b = restoreManager.restoreScript(origem, model);
			return b;
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
			return false;
		}
	}
}
