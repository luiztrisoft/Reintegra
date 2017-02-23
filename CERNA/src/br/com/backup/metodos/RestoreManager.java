package br.com.backup.metodos;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;

import br.com.controller.DbConfig;
import br.com.controller.Show;

/**
 * Esta classe é responsável pelo gerenciamento do restore dos dados do sistema.
 * 
 * @author Luiz Alberto
 * 
 */

public class RestoreManager extends Manager {

	/**
	 * Este método faz a restauração do arquivo de dados com a extensão "SQL".
	 * 
	 * @param origem
	 * @param model
	 * @return boolean
	 */

	public boolean restoreScript(File origem,
			@SuppressWarnings("rawtypes") DefaultListModel model) {

		try {
			StringBuilder s = new StringBuilder();
			s.append("cmd /c mysql -u");
			s.append(DbConfig.DB_USER);
			s.append(" -p");
			s.append(DbConfig.DB_PASS);
			s.append(" ");
			s.append(DbConfig.DB_NAME);
			s.append(" < \"");
			s.append(origem.getPath());
			s.append("\\backup.sql\"");

			String restore = s.toString();
			String exit = "cmd /exit";

			try {
				mensagem(model, "Restaurando arquivo de dados no sistema...");
				mensagem(model, "Local dos dados: " + origem.getPath()
						+ "\\backup.sql");
				Runtime.getRuntime().exec(restore);
				Runtime.getRuntime().exec(exit);
				mensagem(model, "Arquivo de dados restaurado");

			} catch (IOException e) {
				e.printStackTrace();
				mensagem(model, e.getMessage());
				Show.erro("Falha ao executar o backup do arquivo de dados!\n"
						+ e.getMessage());
			}
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			mensagem(model, e1.getMessage());
			Show.erro("Falha ao copiar o backup do arquivo de dados para o diretório especificado!\n"
					+ e1.getMessage());
			return false;
		}
	}

}
