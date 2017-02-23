package br.com.connectionfactory;

import java.sql.Connection;
import java.sql.DriverManager;

import br.com.controller.DbConfig;
import br.com.controller.Show;

/**
 * Classe respons�vel por realizar a conex�o com a base de dados. Como o sistema
 * necessita de seu uso tamb�m pela rede foi criado o m�todo typeConnection que
 * informa se o sistema � local ou pela rede. Atrav�s deste m�todo v�rios
 * par�metros s�o configurados no sistema como endere�o, usu�rio e senha do
 * banco al�m de outros m�todos das demais classes que o utilizam para definir a
 * transfer�ncia de dados e arquivos, como as fotos de internos e funcion�rios
 * por exemplo.
 * 
 * OBS.: � necess�rio criar os privil�gios de usu�rios na m�quina local e tamb�m
 * pela rede.
 * 
 * @author Luiz Alberto
 * 
 */
public class ConnectionFactory {

	/**
	 * M�todo que retorna a conex�o.
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
			Show.erro("<html><h3>Erro!</h3>Houve falha na conex�o com o banco de dados.\nEncerre o programa e tente novamente.\n"
					+ e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * Este m�todo informa se a aplica��o funcionar� apenas localmente ou por
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
	 * Retorna o endere�o da pasta compartilhada que deve ser a 'imagens'.
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
	 * Caso o typeConnection seja igual a 2, este m�todo dever� ser invocado
	 * para retornar o endere�o do servidor MySQL na rede.
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
	 * Caso o typeConnection seja igual a 2, este m�todo dever� ser invocado
	 * para retornar o endere�o do servidor MySQL na rede.
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
	 * Retorna o IP da m�quina servidora do banco de dados.
	 * 
	 * @return String
	 */
	private final static String serverIP() {
		return "192.168.137.1";
	}

}
