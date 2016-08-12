package br.com.pontek.controller.report;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

public class RelatorioUtil {
	
	
	public String verPdfBrowser(JRDataSource jrDataSource, Map<String,Object> parametros, String nomeArquivoJrxml,String nomeDeSaidaPdf) throws JRException, IOException{
			InputStream input = this.getClass().getClassLoader().getResourceAsStream(nomeArquivoJrxml);
			JasperReport relatorio = JasperCompileManager.compileReport(input);
			byte[] bytes = JasperRunManager.runReportToPdf(relatorio,parametros,jrDataSource);
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			response.setHeader("Content-Disposition","inline;filename=\""+nomeDeSaidaPdf+".pdf\"");
			ServletOutputStream outStream = response.getOutputStream();
			outStream.write(bytes, 0 , bytes.length);
			outStream.flush();
			outStream.close();
			FacesContext.getCurrentInstance().responseComplete();
		 
		return null;
	};
	

}
