package br.com.pontek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.Categoria;
import br.com.pontek.service.CategoriaService;
import br.com.pontek.util.FacesUtil;

@ManagedBean(name = "categoriaBean")
@Controller
@Scope("view")
public class CategoriaBean {
	
	@Autowired
	CategoriaService categoriaService;
	
	/*############# NOVO LANÇAMENTO #############*/
	private Categoria categoria = new Categoria();
	private boolean telaDeCadastro=false;/*(true-cadastro | false-lista)*/
	/*############# FIM - NOVO LANÇAMENTO #############*/
	
	/*############# LISTAS #############*/
	private List<Categoria> listaCategorias  = new ArrayList<>();
	/*############# FIM - LISTAS #############*/
	
	/*############# EDITAR LANÇAMENTO #############*/
	private boolean editando=false;
	/*############# FIM - EDITAR LANÇAMENTO #############*/
	

	//CONSTRUTOR
	public CategoriaBean() {
	}

	@PostConstruct
	private void inicializar(){
		listaCategorias=categoriaService.listaDeCategorias();		
	}

	public void salvar() {
		try {
			categoriaService.salvar(categoria);
			if(!listaCategorias.contains(categoria)){
				listaCategorias.add(categoria);				
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

	public void editar(Categoria categoria){
		if(categoria.getId()!=null){
			carregaListas();
			this.categoria=categoria;
			this.telaDeCadastro=true;
			this.editando=true;
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
		editando=false;
	}
	
	private void carregaListas(){
		if(listaCategorias.isEmpty()){
			listaCategorias=categoriaService.listaDeCategorias();
		}	
	}

	/*####### GETS E SETS##########*/
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public boolean isTelaDeCadastro() {
		return telaDeCadastro;
	}
	public void setTelaDeCadastro(boolean telaDeCadastro) {
		this.telaDeCadastro = telaDeCadastro;
	}
	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}
	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}
	public boolean isEditando() {
		return editando;
	}
	public void setEditando(boolean editando) {
		this.editando = editando;
	}



	/*####### MENSAGENS  ##########*/
	public void addMessageCadastroAtivo() {
		
	}



}
