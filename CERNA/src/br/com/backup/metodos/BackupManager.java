package br.com.backup.metodos;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;

import br.com.controller.DbConfig;
import br.com.controller.Show;

/**
 * Esta classe � respons�vel pelo gerenciamento do backup dos dados do sistema.
 * 
 * @author Luiz Alberto
 * 
 */
public class BackupManager extends Manager {

	/**
	 * Este m�todo faz o backup do arquivo de dados com a extens�o "SQL". Foi
	 * necess�ria a cria��o de uma thread que durasse alguns segundos at� o
	 * arquivo sql ser criado, sen�o o sistema n�o encontraria o caminho
	 * especificado.
	 * 
	 * @param destino
	 * @param model
	 * @return boolean
	 */

	public boolean backupScript(File destino,
			@SuppressWarnings("rawtypes") DefaultListModel model) {

		try {
			StringBuilder s = new StringBuilder();
			s.append("cmd /c mysqldump -u");
			s.append(DbConfig.DB_USER);
			s.append(" -p");
			s.append(DbConfig.DB_PASS);
			s.append(" ");
			s.append(DbConfig.DB_NAME);
			s.append(" > C:\\CERNA\\tmp\\backup.sql");
			
			String backup = s.toString();
			String exit = "cmd /exit";

			File fonte = null;
			try {
				mensagem(model, "Criando arquivo de dados no sistema...");
				Runtime.getRuntime().exec(backup);
				Runtime.getRuntime().exec(exit);
				Thread.sleep(3000);
				mensagem(model, "Arquivo de dados criado");
				fonte = new File("C:\\CERNA\\tmp\\backup.sql");
				destino = new File(destino.getPath() + "\\backup.sql");
			} catch (IOException e) {
				e.printStackTrace();
				mensagem(model, e.getMessage());
				Show.erro("Falha ao executar o backup do arquivo de dados!\n"
						+ e.getMessage());
			}

			// ::::::::copia para o diret�rio requerido::::::::
			mensagem(model, "Transferindo arquivos de dados");
			copiar(fonte, destino, model);

			// ::::::::remove o arquivo tempor�rio::::::::
			mensagem(model, "Removendo arquivo tempor�rio");
			fonte.delete();

			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			mensagem(model, e1.getMessage());
			Show.erro("Falha ao copiar o backup do arquivo de dados para o diret�rio especificado!\n"
					+ e1.getMessage());
			return false;
		}
	}

}