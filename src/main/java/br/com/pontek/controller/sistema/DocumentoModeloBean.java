package br.com.pontek.controller.sistema;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.sistema.DocumentoModelo;
import br.com.pontek.service.sistema.DocumentoModeloService;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "documentoModeloBean")
@Controller
@Scope("view")
public class DocumentoModeloBean {

	@Autowired
	private DocumentoModeloService documentoModeloService;
	private DocumentoModelo documentoModelo;
    private List<DocumentoModelo> listaDocumentos = new ArrayList<DocumentoModelo>();
    
    
    @PostConstruct
    private String init(){
    	if(listaDocumentos.isEmpty()){
    		listaDocumentos=documentoModeloService.listaDeDocumentos();
    	}
    	String id = FacesUtil.getRequestParameter("codigo");
    	if(StringUtils.isNotEmpty(id)){
    		documentoModelo=documentoModeloService.buscar(Integer.valueOf(id));
    	}
    	if(documentoModelo==null){
    		documentoModelo=new DocumentoModelo();
    	}
    	return null;
    }
    
	  public String salvar(){
	   	 if(StringUtils.isEmpty(documentoModelo.getTexto())){
	   		 FacesUtil.exibirMensagemErro("Nenhum texto inserido");
	   		 return null;
	   	 }
	   	 	documentoModeloService.salvar(documentoModelo);
	   	 	documentoModelo=new DocumentoModelo();
	   	 	listaDocumentos.clear();
	   	 	listaDocumentos=documentoModeloService.listaDeDocumentos();
	   	 	FacesUtil.exibirMensagemSucesso("Salvo com sucesso");
	   	return null;
	   }
    
	public void excluir(DocumentoModelo documentoModelo){
		try {
			documentoModeloService.excluir(documentoModelo);
			listaDocumentos.remove(documentoModelo);
			FacesUtil.exibirMensagemSucesso("Excluído com sucesso!");
		} catch (RuntimeException e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
	}
	
	/*  GETS E SETS*/
	public DocumentoModelo getDocumentoModelo() {
		return documentoModelo;
	}
	public void setDocumentoModelo(DocumentoModelo documentoModelo) {
		this.documentoModelo = documentoModelo;
	}
	public List<DocumentoModelo> getListaDocumentos() {
		return listaDocumentos;
	}
	public void setListaDocumentos(List<DocumentoModelo> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

}
