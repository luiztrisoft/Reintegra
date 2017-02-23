package br.com.funcionarios.relatorios;

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
 * Esta classe gera um relatório de todos os funcionários cadastrados.
 * 
 * @author Luiz Alberto
 * 
 */
public class ListaFuncionarioRelatorio {

	/**
	 * Este é o método que irá gerar o relatório de funcionários em pdf de
	 * acordo com o parãmetro de status informado.
	 * 
	 * @param status
	 * @throws JRException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void gerar(String status) throws JRException, SQLException,
			ClassNotFoundException {
		// gerando o jasper design
		JasperDesign desenho = JRXmlLoader
				.load("C:/CERNA/jasper/funcionario/listaFuncionarios.jrxml");

		// compila o relatório
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		// estabelece conexão
		Connection con = new ConnectionFactory().getConnection();
		Statement stm = con.createStatement();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT NOME_FUNCIONARIO, TELEFONE_FUNCIONARIO, CARGO_FUNCIONARIO,STATUS_FUNCIONARIO FROM FUNCIONARIOS WHERE STATUS_FUNCIONARIO LIKE '%");
		sb.append(status);
		sb.append("%' ORDER BY NOME_FUNCIONARIO");

		ResultSet rs = stm.executeQuery(sb.toString());

		// implementação da interface JRDataSource para DataSource ResultSet
		JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

		// executa o relatório
		@SuppressWarnings("rawtypes")
		Map parametros = new HashMap();
		parametros.put("nota", new Double(10));
		JasperPrint impressao = JasperFillManager.fillReport(relatorio,
				parametros, jrRS);

		// HÁ DUAS ESCOLHAS DE VISUALIZAÇÃO, EM JASPER OU PDF
		/**
		 * Exibe o resultado no jasper
		 */
		// JasperViewer viewer = new JasperViewer(impressao, true);
		// viewer.show();

		/**
		 * Exibe o resultado em PDF
		 */
		JasperExportManager.exportReportToPdfFile(impressao,
				"C:\\CERNA\\jasper\\funcionario\\listaFuncionarios.pdf");
		try {
			String command = "rundll32 SHELL32.DLL, ShellExec_RunDLL \"C:\\CERNA\\jasper\\funcionario\\listaFuncionarios.pdf\"";
			Runtime.getRuntime().exec(command);
		} catch (Exception excecao) {
			excecao.printStackTrace();
		}
	}

}
