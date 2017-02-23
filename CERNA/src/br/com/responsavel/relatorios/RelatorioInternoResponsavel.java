package br.com.responsavel.relatorios;

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

/**
 * Esta classe � respons�vel por gerar o PDF de internos e seus respectivos
 * respons�veis.
 * 
 * @author Luiz Alberto
 * 
 */
public class RelatorioInternoResponsavel {

	/**
	 * Este m�todo gera o PDF que cont�m os nomes de internos e respons�veis.
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void pdf() throws SQLException {
		Save.log(Config.usuarioLogado,
				"Solicitou o relat�rio de internos e respons�veis");
		String query = "SELECT INTERNOS.NOME, RESPONSAVEL.NOME'NOMERESP', RESPONSAVEL.TELEFONE, RESPONSAVEL.CELULAR FROM INTERNOS, RESPONSAVEL, INTERNO_RESPONSAVEL WHERE ID_INTERNO = INTERNOS.ID AND ID_RESPONSAVEL = RESPONSAVEL.ID ORDER BY INTERNOS.NOME";

		try {

			// ::::::::gerando o jasper design::::::::
			JasperDesign desenho = JRXmlLoader
					.load("C:/CERNA/jasper/internoresponsavel/internoresponsavel.jrxml");

			// ::::::::compila o relat�rio::::::::
			JasperReport relatorio = JasperCompileManager
					.compileReport(desenho);

			// ::::::::estabelece conex�o::::::::
			Connection con = new ConnectionFactory().getConnection();
			Statement stm = con.createStatement();

			// ::::::::executa a query no resultset::::::::
			ResultSet rs = stm.executeQuery(query);

			// ::::::::implementa��o da interface JRDataSource para DataSource
			// ResultSet::::::::
			JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);

			// ::::::::executa o relat�rio::::::::
			Map parametros = new HashMap();
			JasperPrint impressao = JasperFillManager.fillReport(relatorio,
					parametros, jrRS);

			// ::::::::exporta o relat�rio::::::::
			JasperExportManager
					.exportReportToPdfFile(impressao,
							"C:\\CERNA\\jasper\\internoresponsavel\\internoresponsavel.pdf");

			// ::::::::executa o arquivo::::::::
			try {
				String command = "rundll32 SHELL32.DLL, ShellExec_RunDLL \"C:\\CERNA\\jasper\\internoresponsavel\\internoresponsavel.pdf\"";
				Runtime.getRuntime().exec(command);
			} catch (Exception excecao) {
				excecao.printStackTrace();
			}

		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
