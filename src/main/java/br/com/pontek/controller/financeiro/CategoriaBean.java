package br.com.pontek.controller.financeiro;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.controller.AbstractBean;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.service.financeiro.CategoriaService;
import br.com.pontek.service.financeiro.LancamentoService;
import br.com.pontek.util.filtro.FiltroCategoria;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "categoriaBean")
@Controller
@Scope("view")
public class CategoriaBean extends AbstractBean{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	CategoriaService categoriaService;
	@Autowired
	LancamentoService lancamentoService;
	
	/*############# NOVO LANÇAMENTO #############*/
	private Categoria categoria = new Categoria();
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	/*############# FIM - NOVO LANÇAMENTO #############*/
	
	/*########### LAZY DATATABLE ##############*/
	private FiltroCategoria filtro= new FiltroCategoria();;
	private LazyDataModel<Categoria> model;

	/*########### FIM - LAZY DATATABLE ##############*/


	//CONSTRUTOR
	public CategoriaBean() {
		model = new LazyDataModel<Categoria>() {
			private static final long serialVersionUID = 1L;
			public List<Categoria> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(categoriaService.quantidadeFiltrados(filtro));
				List<Categoria> listaTemp = categoriaService.filtrados(filtro);
				return listaTemp;
			}
		};
	}


	public void salvar() {
		try {
			categoriaService.salvar(categoria);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso!");
			reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
	}

	public void novo() {
		reset();
		viewAtiva = estadoDaView.INSERINDO.toString();
    } 
	
	public void voltar() {
		reset();
		viewAtiva = estadoDaView.LISTANDO.toString();
    } 

	public void editar(Categoria categoria){
		if(categoria.getId()!=null){
			this.categoria=categoria;
			viewAtiva = estadoDaView.EDITANDO.toString();
		}
	}
	
	public String excluir(Categoria categoria) {
		if(lancamentoService.existeCategoriaEmLancamentos(categoria)){
			FacesUtil.exibirMensagemErro("Não permitido, existe lançamentos nessa categoria");
			return null;
		}
		try {
			categoriaService.excluir(categoria);
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
		return null;
	}

	private void reset(){
		categoria = new Categoria();
		viewAtiva = estadoDaView.LISTANDO.toString();
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
	public String getViewAtiva() {
		return viewAtiva;
	}
	public void setViewAtiva(String viewAtiva) {
		this.viewAtiva = viewAtiva;
	}
	/*########### GETS LAZY DATATABLE ##############*/
	public FiltroCategoria getFiltro() {
		return filtro;
	}
	public LazyDataModel<Categoria> getModel() {
		return model;
	}
}
