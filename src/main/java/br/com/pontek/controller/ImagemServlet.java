package br.com.pontek.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.pontek.util.jsf.FacesUtil;

@WebServlet("/imagem")
public class ImagemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestedImage = request.getParameter("n");
		File image = new File(FacesUtil.pathImagens(),requestedImage);
		String contentType = getServletContext().getMimeType(image.getName());
		response.reset();
		response.setContentType(contentType);
		response.setHeader("Content-Length", String.valueOf(image.length()));
		Files.copy(image.toPath(), response.getOutputStream());
	}


}

