package br.com.connectionfactory;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.controller.DbConfig;
import br.com.controller.Show;

/**
 * Classe responsável por realizar a conexão com a base de dados. Como o sistema
 * necessita de seu uso também pela rede foi criado o método typeConnection que
 * informa se o sistema é local ou pela rede. Através deste método vários
 * parâmetros são configurados no sistema como endereço, usuário e senha do
 * banco além de outros métodos das demais classes que o utilizam para definir a
 * transferência de dados e arquivos, como as fotos de internos e funcionários
 * por exemplo.
 * 
 * OBS.: É necessário criar os privilégios de usuários na máquina local e também
 * pela rede.
 * 
 * @author Luiz Alberto
 * 
 */
public class ConnectionFactory {

	/**
	 * Método que retorna a conexão.
	 * 
	 * @return Connection.
	 */
	public Connection getConnection() {

		String address = null;
		if (typeConnection() == 1) {
			address = localhostConnection();
		} else if (typeConnection() == 2) {
			address = networkConnection();
		}

		try {
			return DriverManager.getConnection(address, DbConfig.DB_USER,
					DbConfig.DB_PASS);
		} catch (Exception e) {
			Show.erro("<html><h3>Erro!</h3>Houve falha na conexão com o banco de dados.\nEncerre o programa e tente novamente.\n"
					+ e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * Este método informa se a aplicação funcionará apenas localmente ou por
	 * rede e compartilhamento.
	 * 
	 * 1 - Localhost
	 * 
	 * 2 - Rede
	 * 
	 * @return byte
	 */
	public final static byte typeConnection() {
		byte tipo = 1;
		return tipo;
	}

	/**
	 * Retorna o endereço da pasta compartilhada que deve ser a 'imagens'.
	 * 
	 * @return String
	 */
	public final static String folderAddress() {
		StringBuilder s = new StringBuilder();
		s.append("\\\\");
		s.append(serverIP());
		s.append("\\imagens\\");
		return s.toString();
	}

	/**
	 * Caso o typeConnection seja igual a 2, este método deverá ser invocado
	 * para retornar o endereço do servidor MySQL na rede.
	 * 
	 * @return String
	 */
	private String localhostConnection() {
		StringBuilder s = new StringBuilder();
		s.append("jdbc:mysql://localhost/");
		s.append(DbConfig.DB_NAME);
		return s.toString();
	}

	/**
	 * Caso o typeConnection seja igual a 2, este método deverá ser invocado
	 * para retornar o endereço do servidor MySQL na rede.
	 * 
	 * @return String
	 */
	private String networkConnection() {
		StringBuilder s = new StringBuilder();
		s.append("jdbc:mysql://");
		s.append(serverIP());
		s.append(":3306/");
		s.append(DbConfig.DB_NAME);
		return s.toString();
	}

	/**
	 * Retorna o IP da máquina servidora do banco de dados.
	 * 
	 * @return String
	 */
	private final static String serverIP() {
		return "192.168.137.1";
	}

}
