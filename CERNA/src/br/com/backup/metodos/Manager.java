package br.com.backup.metodos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;

/**
 * Esta � a classe pai respons�vel por gerenciamento das fun��es de backup e
 * restore do sistema.
 * 
 * @author Luiz Alberto
 * 
 */
public abstract class Manager {

	/**
	 * Seleciona o diret�rio onde o backup dever� ser efetuado ou recupera o
	 * diret�rio onde est� armazenado para restaura��o.
	 * 
	 * @return String
	 */
	public String selectDirectory() {
		JFileChooser chooser = new JFileChooser();

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = chooser.showOpenDialog(null);

		if (res == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().toString();
		}
		return null;
	}

	/**
	 * Copia todos os arquivos do diret�rio de origem para o diret�rio de
	 * destino.
	 * 
	 * @param origem
	 * @param destino
	 * @param overwrite
	 * @param model
	 * @throws IOException
	 * @throws UnsupportedOperationException
	 */
	@SuppressWarnings("rawtypes")
	public void copyAll(File origem, File destino, boolean overwrite,
			DefaultListModel model) throws IOException,
			UnsupportedOperationException {

		if (!destino.exists()) {
			destino.mkdir();
		}
		if (!origem.isDirectory()) {
			throw new UnsupportedOperationException(
					"Origem deve ser um diret�rio");
		}
		if (!destino.isDirectory()) {
			throw new UnsupportedOperationException(
					"Destino deve ser um diret�rio");
		}
		File[] files = origem.listFiles();
		for (int i = 0; i < files.length; ++i) {
			if (files[i].isDirectory()) {
				copyAll(files[i],
						new File(destino + "\\" + files[i].getName()),
						overwrite, model);
			} else {
				copy(files[i], new File(destino + "\\" + files[i].getName()),
						overwrite);
				mensagem(model, "Copiando arquivo: " + files[i].getName());
			}
		}
	}

	/**
	 * Copia arquivos de um local a outro.
	 * 
	 * @param origem
	 * @param destino
	 * @param overwrite
	 * @throws IOException
	 */
	private static void copy(File origem, File destino, boolean overwrite)
			throws IOException {

		if (destino.exists() && !overwrite) {
			return;
		}

		@SuppressWarnings("resource")
		FileInputStream source = new FileInputStream(origem);
		@SuppressWarnings("resource")
		FileOutputStream destination = new FileOutputStream(destino);

		FileChannel sourceFileChannel = source.getChannel();
		FileChannel destinationFileChannel = destination.getChannel();

		long size = sourceFileChannel.size();
		sourceFileChannel.transferTo(0, size, destinationFileChannel);
	}

	/**
	 * Este m�todo recebe um {@link DefaultListModel} e uma {@link String} para
	 * adicionar mensagens ao usu�rio sobre o andamento do backup de dados.
	 * 
	 * @param model
	 * @param msg
	 */
	@SuppressWarnings("unchecked")
	protected void mensagem(
			@SuppressWarnings("rawtypes") DefaultListModel model, String msg) {
		model.addElement(msg);
	}

	/**
	 * Este m�todo � especifico para copiar o arquivo SQL da pasta tempor�ria do
	 * sistema para o diret�rio especificado pelo usu�rio. Isto foi necess�rio
	 * devido as restri��es de cria��o de arquivos provenientes a partir do
	 * Windows Vista.
	 * 
	 * @param fonte
	 * @param destino
	 * @param model
	 * @throws IOException
	 */
	protected void copiar(File fonte, File destino,
			@SuppressWarnings("rawtypes") DefaultListModel model)
			throws IOException {
		try {
			InputStream in = new FileInputStream(fonte);
			OutputStream out = new FileOutputStream(destino);

			byte[] buf = new byte[1024];
			int len;

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();

			mensagem(model, "Arquivo de dados transferido");
		} catch (Exception e) {
			mensagem(model, e.getMessage());
		}

	}

}
