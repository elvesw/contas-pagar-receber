package br.com.pontek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.Lancamento;
import br.com.pontek.service.LancamentoService;
import br.com.pontek.util.FacesUtil;

@ManagedBean(name = "lancamentoBean")
@Controller
@Scope("session")
public class LancamentoBean {
	
	@Autowired
	LancamentoService lancamentoService;
	
	private Lancamento lancamento = new Lancamento();
	private List<Lancamento> listaLancamentos = new ArrayList<Lancamento>();

	//CONSTRUTOR
	public LancamentoBean() {

	}


	public void salvar() {
		try {
			lancamentoService.salvar(lancamento);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso!");
			this.reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
	}

	public void novo() {
		this.reset();
    } 

	public String editar(Lancamento lancamento){
		if(lancamento.getId()!=null){
			this.lancamento=lancamento;
		  return null;
		}
		return null;
	}

	private void reset(){
		this.lancamento = new Lancamento();
	}

	/*####### GETS E SETS##########*/
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public List<Lancamento> getListaLancamentos() {
		return listaLancamentos;
	}
	public void setListaLancamentos(List<Lancamento> listaLancamentos) {
		this.listaLancamentos = listaLancamentos;
	}

	




}
