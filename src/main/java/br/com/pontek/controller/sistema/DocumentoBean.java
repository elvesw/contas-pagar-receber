package br.com.pontek.controller.sistema;

import java.util.ArrayList;
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

import br.com.pontek.controller.AbstractBean;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.sistema.Documento;
import br.com.pontek.model.sistema.DocumentoModelo;
import br.com.pontek.service.entidades.PessoaService;
import br.com.pontek.service.sistema.DocumentoModeloService;
import br.com.pontek.service.sistema.DocumentoService;
import br.com.pontek.service.sistema.ReplaceDocumentoService;
import br.com.pontek.util.filtro.FiltroDocumento;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "documentoBean")
@Controller
@Scope("view")
public class DocumentoBean extends AbstractBean{

	private static final long serialVersionUID = 1L;
	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private DocumentoModeloService documentoModeloService;
	@Autowired
	private ReplaceDocumentoService replaceDocumentoService;
	@Autowired
	private PessoaService pessoaService;
	
	/*OBJETOS DE PAGINAÇÃO LAZY DATATABLE*/
	private FiltroDocumento filtro  = new FiltroDocumento();
	private LazyDataModel<Documento> model;

	//Cadastro
	private DocumentoModelo documentoModeloEscolhido = new DocumentoModelo();
	private List<DocumentoModelo> listaDocumentosModelo = new ArrayList<DocumentoModelo>();
	private Documento documento;
	
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	
	//CONSTRUTOR
	public DocumentoBean() {
		model = new LazyDataModel<Documento>() {
			private static final long serialVersionUID = 1L;
			public List<Documento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(documentoService.quantidadeFiltrados(filtro));
				List<Documento> listaTemp = documentoService.filtrados(filtro);
				return listaTemp;
			}
		};
	}
	
	@PostConstruct
    private String init(){
		String id_pessoa = FacesUtil.getRequestParameter("p");
		Pessoa pessoa=new Pessoa();
		if(StringUtils.isNotEmpty(id_pessoa)){
			pessoa = pessoaService.buscar(Integer.valueOf(id_pessoa));
			if(pessoa!=null){
				novo(pessoa);
				return null;
			}
		}
		viewLista();
		return null;
    }
	
	public void renderizaDocumento(DocumentoModelo documentoModelo){
		if(documentoModelo.getId()!=null){
			documentoModeloEscolhido=documentoModeloService.buscar(documentoModelo.getId());			
			documento.setNome(documentoModeloEscolhido.getNome()+" ("+documento.getPessoa().getNome()+")");
			documento.setTexto(replaceDocumentoService.gerarDocumentoPessoa(documento.getPessoa(), documentoModeloEscolhido.getTexto()));
			documento.setEmitidoPor(replaceDocumentoService.getUsuario().getNome());
		}
	}

	public void salvar() {
		try {
			documentoService.salvar(documento);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso");
		} catch (RuntimeException e) {
			FacesUtil.exibirMensagemErro("Erro: " + e.getMessage());
		}
	}

	// Vem do cadastro de pessoa
	public void novo(Pessoa pessoa) {
		this.documento = new Documento();
		documento.setPessoa(pessoa);
		carregarDocumentoModelo();
		this.viewAtiva=estadoDaView.INSERINDO.toString();
	}

	/*Vem do data table em emitidos*/
	public void editar(Documento documento){
		this.documento = documento;
		this.documento.atualizaHistorico("Visualizar");
		this.viewAtiva=estadoDaView.EDITANDO.toString();
	}
	
	public void copia(Documento documento){
		this.documento=documento.copia();
		carregarDocumentoModelo();
		this.viewAtiva=estadoDaView.INSERINDO.toString();
		FacesUtil.exibirMensagemAlerta("Essa é uma cópia do documento: ("+documento.getNome()+") não esqueça de salvar");
	}

	 public void excluir(Documento documento){
		  try {
			  documentoService.excluir(documento);
			  FacesUtil.exibirMensagemSucesso("Excluído com sucesso");			
		} catch (RuntimeException e) {
			FacesUtil.exibirMensagemErro("Erro: "+e.getMessage());
		}
	  }
	 
	 public void viewLista(){
		this.viewAtiva=estadoDaView.LISTANDO.toString();
	 }

	 public void viewCadastro(){
			this.viewAtiva=estadoDaView.EDITANDO.toString();
	 }
	 
	 private void carregarDocumentoModelo(){
		 if(listaDocumentosModelo.isEmpty())
				listaDocumentosModelo = documentoModeloService.listaDeDocumentos();
		 this.documentoModeloEscolhido = new DocumentoModelo();
	 }
	/* GETS E SETS */
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public DocumentoModelo getDocumentoModeloEscolhido() {
		return documentoModeloEscolhido;
	}
	public void setDocumentoModeloEscolhido(DocumentoModelo documentoModeloEscolhido) {
		this.documentoModeloEscolhido = documentoModeloEscolhido;
	}
	public List<DocumentoModelo> getListaDocumentosModelo() {
		return listaDocumentosModelo;
	}
	public String getViewAtiva() {
		return viewAtiva;
	}
	/*####### GETS DE PAGINAÇÃO LAZY DATATABLE  ##########*/
	public FiltroDocumento getFiltro() {
		return filtro;
	}
	public LazyDataModel<Documento> getModel() {
		return model;
	}
}
