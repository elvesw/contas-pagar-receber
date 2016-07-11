package br.com.pontek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.CentroDeCusto;
import br.com.pontek.service.CentroDeCustoService;
import br.com.pontek.util.FacesUtil;

@ManagedBean(name = "centroDeCustoBean")
@Controller
@Scope("session")
public class CentroDeCustoBean {
	
	@Autowired
	CentroDeCustoService centroDeCustoService;
	
	private CentroDeCusto centroDeCusto = new CentroDeCusto();
	private List<CentroDeCusto> listaCentroDeCusto = new ArrayList<CentroDeCusto>();

	//CONSTRUTOR
	public CentroDeCustoBean() {

	}


	public void salvar() {
		try {
			centroDeCustoService.salvar(centroDeCusto);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso!");
			this.reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
	}

	public String novo() {
		this.reset();
		return null;
    } 

	public String editar(CentroDeCusto centroDeCusto){
		if(centroDeCusto.getId()!=null){
			this.centroDeCusto=centroDeCusto;
		  return null;
		}
		return null;
	}
	
	public void excluir(CentroDeCusto centroDeCusto) {
		try {
			centroDeCustoService.excluir(centroDeCusto);
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
	}

	private void reset(){
		this.centroDeCusto = new CentroDeCusto();
	}

	/*####### GETS E SETS##########*/
	public CentroDeCusto getCentroDeCusto() {
		return centroDeCusto;
	}
	public void setCentroDeCusto(CentroDeCusto centroDeCusto) {
		this.centroDeCusto = centroDeCusto;
	}
	public List<CentroDeCusto> getListaCentroDeCusto() {
		return listaCentroDeCusto;
	}
	public void setListaCentroDeCusto(List<CentroDeCusto> listaCentroDeCusto) {
		this.listaCentroDeCusto = listaCentroDeCusto;
	}


	/*####### MENSAGENS  ##########*/
	public void addMessageCadastroAtivo() {
	}


}
