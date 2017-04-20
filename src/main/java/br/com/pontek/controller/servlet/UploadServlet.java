package br.com.pontek.controller.servlet;

import java.io.File;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import br.com.pontek.util.jsf.FacesUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold=1024*1024*2,	// 2MB
				 maxFileSize=1024*1024*10,		// 10MB
				 maxRequestSize=1024*1024*50)	// 50MB
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String SAVE_DIR = FacesUtil.pathImagens();

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String savePath = File.separator + SAVE_DIR;
		System.err.println("UploadServlet.doPost() savePath: "+savePath);

		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}

		for (Part part : request.getParts()) {
			String fileName = extractFileName(part);
			fileName = new File(fileName).getName();
			part.write(savePath + File.separator + fileName);
			Thumbnails.of(savePath+File.separator+fileName).size(140,129).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		}
	}

	/**
	 *Extrai o nome do arquivo do cabeçalho HTTP content-disposition
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
	}
}