package br.com.pontek.controller.sistema;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.controller.AbstractBean.estadoDaView;
import br.com.pontek.model.sistema.DocumentoModelo;
import br.com.pontek.service.sistema.DocumentoModeloService;
import br.com.pontek.util.filtro.FiltroDocumentoModelo;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "documentoModeloBean")
@Controller
@Scope("view")
public class DocumentoModeloBean {

	@Autowired
	private DocumentoModeloService documentoModeloService;
	private DocumentoModelo documentoModelo;

    
    /*OBJETOS DE PAGINAÇÃO LAZY DATATABLE*/
	private FiltroDocumentoModelo filtro  = new FiltroDocumentoModelo();
	private LazyDataModel<DocumentoModelo> model;
	
	private String viewAtiva;
	
	//CONSTRUTOR
	public DocumentoModeloBean() {
		model = new LazyDataModel<DocumentoModelo>() {
			private static final long serialVersionUID = 1L;
			public List<DocumentoModelo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(documentoModeloService.quantidadeFiltrados(filtro));
				List<DocumentoModelo> listaTemp = documentoModeloService.filtrados(filtro);
				return listaTemp;
			}
		};
	}
    
    @PostConstruct
    private void init(){
    	viewLista();
    }
    
	  public String salvar(){
	   	 if(StringUtils.isEmpty(documentoModelo.getTexto())){
	   		 FacesUtil.exibirMensagemErro("Nenhum texto inserido");
	   		 return null;
	   	 }
	   	 documentoModeloService.salvar(documentoModelo);
	   	 documentoModelo=new DocumentoModelo();
	   	 viewLista();
	   	 FacesUtil.exibirMensagemSucesso("Salvo com sucesso");
	   	return null;
	   }
    
	public void excluir(DocumentoModelo documentoModelo){
		try {
			documentoModeloService.excluir(documentoModelo);
			FacesUtil.exibirMensagemSucesso("Excluído com sucesso!");
		} catch (RuntimeException e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
	}
	
	/*Vem do data table*/
	public void editar(DocumentoModelo documentoModelo){
		this.documentoModelo = documentoModelo;
		viewCadastroEditar();
	}
	
	public void viewLista(){
		this.viewAtiva=estadoDaView.LISTANDO.toString();
	 }
	public void viewCadastroNovo(){
		documentoModelo=new DocumentoModelo();
		this.viewAtiva=estadoDaView.INSERINDO.toString();
	}
	public void viewCadastroEditar(){
		this.viewAtiva=estadoDaView.EDITANDO.toString();
	}
	
	public void copia(DocumentoModelo documentoModelo){
	 this.documentoModelo=documentoModelo.copia();
	 this.viewAtiva=estadoDaView.INSERINDO.toString();
	 FacesUtil.exibirMensagemAlerta("Essa é uma cópia do modelo: ("+documentoModelo.getNome()+") não esqueça de salvar");
	}
	
	/*####### GETS E SETS  ##########*/
	public DocumentoModelo getDocumentoModelo() {
		return documentoModelo;
	}
	public void setDocumentoModelo(DocumentoModelo documentoModelo) {
		this.documentoModelo = documentoModelo;
	}
	public String getViewAtiva() {
		return viewAtiva;
	}
	/*####### GETS DE PAGINAÇÃO LAZY DATATABLE  ##########*/
	public FiltroDocumentoModelo getFiltro() {
		return filtro;
	}
	public LazyDataModel<DocumentoModelo> getModel() {
		return model;
	}
}
