package br.com.pontek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.Conta;
import br.com.pontek.service.ContaService;
import br.com.pontek.util.FacesUtil;

@ManagedBean(name = "contaBean")
@Controller
@Scope("view")
public class ContaBean {
	
	@Autowired
	ContaService contaService;
	
	/*############# NOVO LANÇAMENTO #############*/
	private Conta conta = new Conta();
	private boolean telaDeCadastro=false;/*(true-cadastro | false-lista)*/
	/*############# FIM - NOVO LANÇAMENTO #############*/
	
	/*############# LISTAS #############*/
	private List<Conta> listaContas  = new ArrayList<>();
	/*############# FIM - LISTAS #############*/
	
	/*############# EDITAR LANÇAMENTO #############*/
	private boolean editando=false;
	/*############# FIM - EDITAR LANÇAMENTO #############*/
	

	//CONSTRUTOR
	public ContaBean() {
	}

	@PostConstruct
	private void inicializar(){
		listaContas=contaService.listaDeContas();		
	}

	public void salvar() {
		try {
			contaService.salvar(conta);
			if(!listaContas.contains(conta)){
				listaContas.add(conta);				
			}
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso!");
			this.reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
	}

	public void novo() {
		reset();
		carregaListas();
		telaDeCadastro=true;
    } 
	
	public void voltar() {
		reset();
		telaDeCadastro=false;
    } 

	public void editar(Conta conta){
		if(conta.getId()!=null){
			carregaListas();
			this.conta=conta;
			this.telaDeCadastro=true;
			this.editando=true;
		}
	}
	
	public void excluir(Conta conta) {
		try {
			contaService.excluir(conta);
			listaContas.remove(conta);
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
	}

	private void reset(){
		conta = new Conta();
		editando=false;
	}
	
	private void carregaListas(){
		if(listaContas.isEmpty()){
			listaContas=contaService.listaDeContas();
		}	
	}

	/*####### GETS E SETS##########*/
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public boolean isTelaDeCadastro() {
		return telaDeCadastro;
	}
	public void setTelaDeCadastro(boolean telaDeCadastro) {
		this.telaDeCadastro = telaDeCadastro;
	}
	public List<Conta> getListaContas() {
		return listaContas;
	}
	public void setListaContas(List<Conta> listaContas) {
		this.listaContas = listaContas;
	}
	public boolean isEditando() {
		return editando;
	}
	public void setEditando(boolean editando) {
		this.editando = editando;
	}



	/*####### MENSAGENS  ##########*/
	public void addMessageCadastroAtivo() {
		System.out.println("Metodo executado com sucesso");
	}



}
