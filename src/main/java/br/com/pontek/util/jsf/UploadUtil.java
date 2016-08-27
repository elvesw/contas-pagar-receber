package br.com.pontek.util.jsf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

public class UploadUtil {
	
	public String upload(FileUploadEvent event){
	    UploadedFile uf = event.getFile();

	    String nomeArquivo = uf.getFileName();   
	    String path = "";
	    // aqui verifico se é linux ou windows
	    if(System.getProperties().get("os.name").toString().trim().equalsIgnoreCase("Linux")){
	        path = "/home/elves/files/";
	    }else{
	        path = "c://files//avaliacao//";
	    }

	    File f = new File(path+nomeArquivo);
	    OutputStream os = null;
	    InputStream is = null;
	    try
	    {
	        is = uf.getInputstream();
	        byte[] b = new byte[is.available()];
	        os = new FileOutputStream(f);
	        while(is.read(b) > 0)
	        {  
	            os.write(b);  
	        }
	        // aqui você pode colcar a gravação do path no BD
	        FacesUtil.exibirMensagemSucesso("O arquivo foi enviado corretamente, clique em enviar para concluir a operação.");
	    } 
	    catch(IOException ex) 
	    {  
	        FacesUtil.exibirMensagemErro("Erro: "+ ex.getMessage());
	        throw new RuntimeException("Erro criando pasta para salvar foto", ex);
	    } 
	    finally 
	    {  
	        try 
	        {  
	            os.flush();  
	            os.close();  
	            is.close();  
	        } 
	        catch(IOException ex) 
	        {  
	        	FacesUtil.exibirMensagemErro("Erro2: "+ ex.getMessage());
	        }  
	    }
		return f.getPath();
	}
}
