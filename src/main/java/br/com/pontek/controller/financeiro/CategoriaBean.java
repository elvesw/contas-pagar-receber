package br.com.pontek.controller.financeiro;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.controller.AbstractBean;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.service.financeiro.CategoriaService;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "categoriaBean")
@Controller
@Scope("view")
public class CategoriaBean extends AbstractBean{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	CategoriaService categoriaService;
	
	/*############# NOVO LANÇAMENTO #############*/
	private Categoria categoria = new Categoria();
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	/*############# FIM - NOVO LANÇAMENTO #############*/
	
	/*############# LISTAS #############*/
	private List<Categoria> listaCategorias  = new ArrayList<>();
	RequestContext rc = RequestContext.getCurrentInstance();
	/*############# FIM - LISTAS #############*/	

	//CONSTRUTOR
	public CategoriaBean() {
	}

	@PostConstruct
	private void inicializar(){
		listaCategorias=categoriaService.listaDeCategorias();
		//Aplicando filtro ao iniciar
	    rc.execute("PF('widgetCategoriaDT').filter();");
	}

	public void salvar() {
		try {
			categoriaService.salvar(categoria);
			listaCategorias=categoriaService.listaDeCategorias();
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso!");
			reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
	}

	public void novo() {
		reset();
		carregaListas();
		viewAtiva = estadoDaView.INSERINDO.toString();
    } 
	
	public void voltar() {
		reset();
		viewAtiva = estadoDaView.LISTANDO.toString();
    } 

	public void editar(Categoria categoria){
		if(categoria.getId()!=null){
			carregaListas();
			this.categoria=categoria;
			viewAtiva = estadoDaView.EDITANDO.toString();
		}
	}
	
	public void excluir(Categoria categoria) {
		try {
			categoriaService.excluir(categoria);
			listaCategorias.remove(categoria);
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
	}

	private void reset(){
		categoria = new Categoria();
		viewAtiva = estadoDaView.LISTANDO.toString();
	}
	
	private void carregaListas(){
		if(listaCategorias.isEmpty()){
			listaCategorias=categoriaService.listaDeCategorias();
		}	
	}
	
	/*#######  ENUMS ##########*/
	public TipoDeLancamento[] getTipoDeLancamentoEnums() {
		return TipoDeLancamento.values();
	}

	/*####### GETS E SETS##########*/
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}
	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}
	public String getViewAtiva() {
		return viewAtiva;
	}
	public void setViewAtiva(String viewAtiva) {
		this.viewAtiva = viewAtiva;
	}

	/*####### MENSAGENS  ##########*/
	public void addMessageCadastroAtivo() {
		
	}
}
