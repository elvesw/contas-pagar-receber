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
import br.com.pontek.model.financeiro.Descricao;
import br.com.pontek.service.financeiro.DescricaoService;
import br.com.pontek.util.filtro.FiltroDescricao;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "descricaoBean")
@Controller
@Scope("view")
public class DescricaoBean extends AbstractBean{
	
	private static final long serialVersionUID = 1L;

	@Autowired DescricaoService descricaoService;

	
	/*############# NOVO LANÇAMENTO #############*/
	private Descricao descricao = new Descricao();
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	/*############# FIM - NOVO LANÇAMENTO #############*/
	
	/*########### LAZY DATATABLE ##############*/
	private FiltroDescricao filtro= new FiltroDescricao();;
	private LazyDataModel<Descricao> model;

	/*########### FIM - LAZY DATATABLE ##############*/


	//CONSTRUTOR
	public DescricaoBean() {
		model = new LazyDataModel<Descricao>() {
			private static final long serialVersionUID = 1L;
			public List<Descricao> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(descricaoService.quantidadeFiltrados(filtro));
				List<Descricao> listaTemp = descricaoService.filtrados(filtro);
				return listaTemp;
			}
		};
	}


	public void salvar() {
		try {
			descricaoService.salvar(descricao);
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

	public void editar(Descricao descricao){
		if(descricao.getId()!=null){
			this.descricao=descricao;
			viewAtiva = estadoDaView.EDITANDO.toString();
		}
	}
	
	public String excluir(Descricao descricao) {
		try {
			descricaoService.excluir(descricao);
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
		return null;
	}

	private void reset(){
		descricao = new Descricao();
		viewAtiva = estadoDaView.LISTANDO.toString();
	}
	
	
	/*#######  ENUMS ##########*/
	public TipoDeLancamento[] getTipoDeLancamentoEnums() {
		return TipoDeLancamento.values();
	}

	/*####### GETS E SETS##########*/
	public Descricao getDescricao() {
		return descricao;
	}
	public void setDescricao(Descricao descricao) {
		this.descricao = descricao;
	}
	public String getViewAtiva() {
		return viewAtiva;
	}
	public void setViewAtiva(String viewAtiva) {
		this.viewAtiva = viewAtiva;
	}
	/*########### GETS LAZY DATATABLE ##############*/
	public FiltroDescricao getFiltro() {
		return filtro;
	}
	public LazyDataModel<Descricao> getModel() {
		return model;
	}
}
