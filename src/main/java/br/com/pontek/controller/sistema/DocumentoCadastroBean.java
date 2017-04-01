package br.com.pontek.controller.sistema;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.autenticacao.Usuario;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.sistema.Documento;
import br.com.pontek.model.sistema.DocumentoModelo;
import br.com.pontek.service.autenticacao.UsuarioService;
import br.com.pontek.service.sistema.DocumentoModeloService;
import br.com.pontek.service.sistema.DocumentoService;
import br.com.pontek.service.sistema.ReplaceDocumentoService;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "documentoCadastroBean")
@Controller
@Scope("session")
public class DocumentoCadastroBean {

	@Autowired
	private DocumentoService documentoService;
	@Autowired
	private DocumentoModeloService documentoModeloService;
	@Autowired
	private ReplaceDocumentoService replaceDocumentoService;
	@Autowired
	private UsuarioService usuarioService;

	// Documento Modelo
	private DocumentoModelo documentoModeloEscolhido = new DocumentoModelo();
	private List<DocumentoModelo> listaDocumentosModelo = new ArrayList<DocumentoModelo>();
	private Documento documento;
	private Usuario usuario;
	
	

	public void renderizaDocumento(DocumentoModelo documentoModelo){
		documentoModeloEscolhido=documentoModeloService.buscar(documentoModelo.getId());
		documento.setNome(documentoModelo.getNome());
		documento.setTexto(
		replaceDocumentoService.gerarDocumentoPessoa(documento.getPessoa(), documentoModeloEscolhido.getTexto()));
	}

	public void salvar() {
		try {
			documentoService.salvar(documento);
			documento = new Documento();
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso");
		} catch (RuntimeException e) {
			FacesUtil.exibirMensagemErro("Erro: " + e.getMessage());
		}
	}

	// Vem do documentoListaBean
	public String novo(Pessoa pessoa) {
		carregar();
		documento.setEmitidoPor(usuario.getNome());
		documento.setEmitidoPara(pessoa.getNome());
		documento.setPessoa(pessoa);
		return "/documento/cadastro.xhtml";
	}

	public String editar(Documento documento) {
		carregar();
		this.documento = documento;
		return "/documento/cadastro.xhtml";//não colocar redirect para não ter ural para acessar
	}
	
	private void carregar(){
		if (listaDocumentosModelo.isEmpty())
			listaDocumentosModelo = documentoModeloService.listaDeDocumentos();
		if(usuario==null)
			usuario = usuarioService.usuarioLogado();
		this.documento = new Documento();
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
}
