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
 * Esta é a classe pai responsável por gerenciamento das funções de backup e
 * restore do sistema.
 * 
 * @author Luiz Alberto
 * 
 */
public abstract class Manager {

	/**
	 * Seleciona o diretório onde o backup deverá ser efetuado ou recupera o
	 * diretório onde está armazenado para restauração.
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
	 * Copia todos os arquivos do diretório de origem para o diretório de
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
					"Origem deve ser um diretório");
		}
		if (!destino.isDirectory()) {
			throw new UnsupportedOperationException(
					"Destino deve ser um diretório");
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
	 * Este método recebe um {@link DefaultListModel} e uma {@link String} para
	 * adicionar mensagens ao usuário sobre o andamento do backup de dados.
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
	 * Este método é especifico para copiar o arquivo SQL da pasta temporária do
	 * sistema para o diretório especificado pelo usuário. Isto foi necessário
	 * devido as restrições de criação de arquivos provenientes a partir do
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
