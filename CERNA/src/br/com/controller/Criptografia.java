package br.com.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Esta classe tem por função realizar a criptografia dos dados. É utilizada
 * para salvar senhas na base de dados no formato MD5.
 * 
 * @author Luiz Alberto
 * 
 */
public class Criptografia {
	private static MessageDigest md = null;

	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Métodos de criptografia.
	 * 
	 * @param text
	 * @return char
	 */
	private static char[] hexCodes(byte[] text) {
		char[] hexOutput = new char[text.length * 2];
		String hexString;

		for (int i = 0; i < text.length; i++) {
			hexString = "00" + Integer.toHexString(text[i]);
			hexString.toUpperCase().getChars(hexString.length() - 2,
					hexString.length(), hexOutput, i * 2);
		}

		return hexOutput;
	}

	/**
	 * Criptografa o parâmetro passado.
	 * 
	 * @param pwd
	 * @return String
	 */
	public static String criptografar(String pwd) {
		if (md != null) {
			return new String(hexCodes(md.digest(pwd.getBytes())));
		}
		return null;
	}
}