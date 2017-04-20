package br.com.pontek.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.imageio.stream.FileImageOutputStream;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.util.jsf.FacesUtil;
import net.coobird.thumbnailator.Thumbnails;

@ManagedBean(name = "fileBean")
@Controller
@Scope("view")
public class FileBean implements Serializable {  
	private static final long serialVersionUID = 1L;
	
	
	private List<String> listaArquivos = new ArrayList<String>();
	
	@PostConstruct
	private void carregarArquivos(){
		listaArquivos.clear();
		 File[] files = Paths.get(FacesUtil.pathImagens()).toFile().listFiles();
		 for(File f : files) {
			 if(!f.isDirectory()){
				 if(f.getName().contains("thumbnail"))
				 listaArquivos.add(f.getName());				 
			 }
		 }
	}
	
	public void upImagem(FileUploadEvent event){
		 UploadedFile uf = event.getFile();
		 String nomeArquivo=uf.getFileName().replace(" ", "");
	     String pathArquivo = FacesUtil.pathImagens()+nomeArquivo;
	     System.out.println("fileBean.upImagem(): "+pathArquivo);
	     FileImageOutputStream imageOutput;
	     try {
	            imageOutput = new FileImageOutputStream(new File(pathArquivo));
	            imageOutput.write(uf.getContents(),0,uf.getContents().length);
	            imageOutput.close();
	            FacesUtil.exibirMensagemSucesso("Upload realidado com sucesso");
	     } catch (IOException e) {
	        	FacesUtil.exibirMensagemErro("Upload falhou, tente novamente");
	        	throw new RuntimeException("Erro ao fazer upload ", e);
	     }
	    
	     try {
			//Thumbnails.of(pathArquivo).size(140,129).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
	    	 String nome140x129="140x129.thumbnail."+nomeArquivo;
			Thumbnails.of(pathArquivo).size(140,129).toFile(new File(FacesUtil.pathImagens()+nome140x129));
			 listaArquivos.add(nome140x129);
		} catch (IOException e) {
				throw new RuntimeException("Erro gerar thumbnail: ", e);
		}
	}

	public void excluir(String nome) throws IOException{
		try {
			System.err.println("FileBean.excluir() nome: "+nome);
			Files.delete(Paths.get(FacesUtil.pathImagens()+nome));
			Files.delete(Paths.get(FacesUtil.pathImagens()+nome.substring(18)));
			listaArquivos.remove(nome);				

		} catch (RuntimeException e) {
			FacesUtil.exibirMensagemErro(e.getMessage());
			throw new RuntimeException("Erro ao excluir: "+e.getMessage());
		}
	}

	/*########## GETS E SETS #############*/
	public List<String> getListaArquivos() {
		return listaArquivos;
	}
}
