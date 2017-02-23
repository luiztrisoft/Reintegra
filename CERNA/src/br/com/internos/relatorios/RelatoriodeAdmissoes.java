package br.com.internos.relatorios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import br.com.connectionfactory.ConnectionFactory;
import br.com.controller.Save;
import br.com.controller.Config;

public class RelatoriodeAdmissoes {
	/**
	 * Este método gera uma consulta SQL requisitando todas as admissões de
	 * internos registradas no banco de dados e em seguida passa esta consulta
	 * para o método executarRelatorio gerando o PDF com os dados solicitados.
	 * 
	 * @param ordem
	 */
	public void executarRelatorio(String ordem) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT NOME, DATA_ADMISSAO, DATA_SAIDA FROM INTERNOS ORDER BY ");
		query.append(ordem);
		try {
			gerarPDF(query.toString());
			Save.log(Config.usuarioLogado,
					"Solicitou o relatório de admissão dos internos");
		} catch (ClassNotFoundException | JRException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método gera uma consulta SQL requisitando todos os registros de
	 * admissão de internos de um ano específico e em seguida passa esta
	 * consulta para o método executarRelatorio gerando o PDF com os dados
	 * solicitados.
	 * 
	 * @param ano
	 * @param ordem
	 */
	public void executarRelatorio(String ano, String ordem) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT NOME, DATA_ADMISSAO, DATA_SAIDA FROM INTERNOS WHERE YEAR(DATA_ADMISSAO)=");
		query.append(ano);
		query.append(" ORDER BY ");
		query.append(ordem);
		try {
			gerarPDF(query.toString());
			Save.log(Config.usuarioLogado,
					"Solicitou o relatório de admissão dos internos");
		} catch (ClassNotFoundException | JRException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este método gera uma consulta SQL requisitando todos os registros de
	 * admissão de internos de um ano e mês específicos e em seguida passa esta
	 * consulta para o método executarRelatorio gerando o PDF com os dados
	 * solicitados.
	 * 
	 * @param mes
	 * @param ano
	 * @param ordem
	 */
	public void executarRelatorio(String mes, String ano, String ordem) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT NOME, DATA_ADMISSAO, DATA_SAIDA FROM INTERNOS WHERE YEAR(DATA_ADMISSAO)=");
		query.append(ano);
		query.append(" AND MONTH(DATA_ADMISSAO)=");
		query.append(mes);
		query.append(" ORDER BY ");
		query.append(ordem);
		try {
			gerarPDF(query.toString());
			Save.log(Config.usuarioLogado,
					"Solicitou o relatório de admissão dos internos");
		} catch (ClassNotFoundException | JRException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método principal desta classe responsável por gerar e executar o
	 * relatório de admissão de internos.
	 * 
	 * @param query
	 * @throws JRException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void gerarPDF(String query) throws JRException, SQLException,
			ClassNotFoundException {
		// ::::::::gerando o jasper design::::::::
		JasperDesign desenho = JRXmlLoader
				.load("C:/CERNA/jasper/interno/admissao.jrxml");

		// ::::::::compila o relatório::::::::
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		// ::::::::estabelece conexão::::::::
		Connection con = new ConnectionFactory().getConnection();
		Statement stm = con.createStatement();

		// ::::::::query::::::::
		StringBuilder sb = new StringBuilder();
		sb.append(query);

		// ::::::::executa a query no resultset::::::::
		ResultSet rs = stm.executeQuery(sb.toString());

		// ::::::::implementação da interface JRDataSource para DataSource
		// ResultSet::::::::
		JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

		// ::::::::executa o relatório::::::::
		Map parametros = new HashMap();
		JasperPrint impressao = JasperFillManager.fillReport(relatorio,
				parametros, jrRS);

		// ::::::::exporta o relatório::::::::
		JasperExportManager.exportReportToPdfFile(impressao,
				"C:\\CERNA\\jasper\\interno\\admissao.pdf");

		// ::::::::executa o arquivo::::::::
		try {
			String command = "rundll32 SHELL32.DLL, ShellExec_RunDLL \"C:\\CERNA\\jasper\\interno\\admissao.pdf\"";
			Runtime.getRuntime().exec(command);
		} catch (Exception excecao) {
			excecao.printStackTrace();
		}
	}
}
