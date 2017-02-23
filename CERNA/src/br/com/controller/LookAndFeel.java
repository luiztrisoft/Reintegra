package br.com.controller;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe respons�vel por modificar a apar�ncia do sistema. At� a vers�o 7 do
 * Windows funcionava perfeitamente. N�o houve testes posteriores
 * 
 * @author Tiko
 * 
 */
public class LookAndFeel {

	/**
	 * Visual Nimbus nativo do Java.
	 */
	public static boolean Nimbus() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return true;
	}

	/**
	 * Usa o visual do sistema operacional da m�quina.
	 */
	public static boolean SystemLook() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception er) {

		}
		return true;
	}

	public static boolean Java() {
		return false;
	}
}