package br.com.pontek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.Categoria;
import br.com.pontek.service.CategoriaService;
import br.com.pontek.util.FacesUtil;

@ManagedBean(name = "categoriaBean")
@Controller
@Scope("session")
public class CategoriaBean {
	
	@Autowired
	CategoriaService categoriaService;
	
	private Categoria categoria = new Categoria();
	private List<Categoria> listaCategorias = new ArrayList<Categoria>();
	

	//CONSTRUTOR
	public CategoriaBean() {

	}


	public void salvar() {
		try {
			categoriaService.salvar(categoria);
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

	public String editar(Categoria categoria){
		if(categoria.getId()!=null){
			this.categoria=categoria;
		  return null;
		}
		return null;
	}
	
	public void excluir(Categoria categoria) {
		try {
			categoriaService.excluir(categoria);
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
	}

	private void reset(){
		this.categoria = new Categoria();
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


	/*####### MENSAGENS  ##########*/
	public void addMessageCadastroAtivo() {
	}

}
