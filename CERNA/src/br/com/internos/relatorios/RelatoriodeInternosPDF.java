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

/**
 * Classe respons�vel por criar e executar o relat�rio de internos.
 * 
 * @author Luiz Alberto
 * 
 */
public class RelatoriodeInternosPDF {

	/**
	 * Este m�todo executa o relat�rio de internos com as informa��es b�sicas
	 * como nome, depend�ncia, idade m�nima, idade m�xima e status.
	 * 
	 * @param nome
	 * @param dependencia
	 * @param idadeMin
	 * @param idadeMax
	 * @param status
	 * @throws JRException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void executarRelatorio(String nome, String dependencia,
			String idadeMin, String idadeMax, int status) throws JRException,
			SQLException, ClassNotFoundException {
		// ::::::::gerando o jasper design::::::::
		JasperDesign desenho = JRXmlLoader
				.load("C:/CERNA/jasper/interno/relatorioInternos.jrxml");

		// ::::::::compila o relat�rio::::::::
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		// ::::::::estabelece conex�o::::::::
		Connection con = new ConnectionFactory().getConnection();
		Statement stm = con.createStatement();

		// ::::::::query::::::::
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT NOME, ID_STATUS, SITUACAO, YEAR(CURDATE()) - YEAR(DATA_NASCIMENTO) IDADE, DEPENDENCIA.TIPO TIPO ");
		sb.append("FROM INTERNOS, STATUS_INTERNO, DEPENDENCIA ");
		sb.append("WHERE STATUS_INTERNO.ID = ID_STATUS AND NOME LIKE'%");
		sb.append(nome);
		sb.append("%' AND DEPENDENCIA.TIPO LIKE '%");
		sb.append(dependencia);
		sb.append("%' AND ID_STATUS = ");
		sb.append(status);
		sb.append(" AND  YEAR(CURDATE()) - YEAR(DATA_NASCIMENTO)  >= ");
		sb.append(idadeMin);
		sb.append(" AND  YEAR(CURDATE()) - YEAR(DATA_NASCIMENTO)  <= ");
		sb.append(idadeMax);
		sb.append(" AND DEPENDENCIA.ID = INTERNOS.ID_DEPENDENCIA ");
		sb.append(" ORDER BY NOME");

		// ::::::::executa a query no resultset::::::::
		ResultSet rs = stm.executeQuery(sb.toString());

		// ::::::::implementa��o da interface JRDataSource para DataSource
		// ResultSet::::::::
		JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

		// ::::::::executa o relat�rio::::::::
		Map parametros = new HashMap();
		JasperPrint impressao = JasperFillManager.fillReport(relatorio,
				parametros, jrRS);

		// ::::::::exporta o relat�rio::::::::
		JasperExportManager.exportReportToPdfFile(impressao,
				"C:\\CERNA\\jasper\\interno\\relatorioInternos.pdf");

		// ::::::::executa o arquivo::::::::
		try {
			String command = "rundll32 SHELL32.DLL, ShellExec_RunDLL \"C:\\CERNA\\jasper\\interno\\relatorioInternos.pdf\"";
			Runtime.getRuntime().exec(command);
		} catch (Exception excecao) {
			excecao.printStackTrace();
		}
	}
}