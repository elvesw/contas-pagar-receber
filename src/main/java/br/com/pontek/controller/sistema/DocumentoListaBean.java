package br.com.pontek.controller.sistema;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.sistema.Documento;
import br.com.pontek.service.sistema.DocumentoService;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "documentoListaBean")
@Controller
@Scope("view")
public class DocumentoListaBean {

	@Autowired
	private DocumentoService documentoService;
	
	private List<Documento> listaDocumentos = new ArrayList<Documento>();


    @PostConstruct
    private void init(){
    	if(listaDocumentos.isEmpty()){
    		listaDocumentos=documentoService.listaDeDocumentos();
    	}
    }
    
	  public void excluir(Documento documento){
		  try {
			  documentoService.excluir(documento);
			  listaDocumentos.remove(documento);
			  FacesUtil.exibirMensagemSucesso("Excluído com sucesso");			
		} catch (RuntimeException e) {
			FacesUtil.exibirMensagemErro("Erro: "+e.getMessage());
		}
	  }
	/*  GETS E SETS*/
	  public List<Documento> getListaDocumentos() {
		  return listaDocumentos;
	  }
}
