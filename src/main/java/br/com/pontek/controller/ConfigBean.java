package br.com.pontek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.Categoria;
import br.com.pontek.service.CategoriaService;
import br.com.pontek.util.FacesUtil;

@ManagedBean(name = "configBean")
@Controller
@Scope("view")
public class ConfigBean{
	
	@Autowired 
	CategoriaService categoriaService;
	
	List<Categoria> listaRaizes = new  ArrayList<Categoria>();
	private TreeNode raiz;
	private TreeNode noSelecionado;
	
	public ConfigBean() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init(){
		listaRaizes=categoriaService.listaRaizes();
		 this.raiz = new DefaultTreeNode("Raiz", null);
	     adicionarNos(listaRaizes,this.raiz);
	}
	
	private void adicionarNos(List<Categoria> raizes, TreeNode pai){
        for(Categoria categoria : raizes){
        	TreeNode no = new DefaultTreeNode(categoria,pai); 
        	 adicionarNos(categoria.getListaFilhos(),no);
        }
    }
	
	
	public void onNodeSelect(NodeSelectEvent evento) {
        Categoria categoriaTemp= (Categoria) evento.getTreeNode().getData();
        FacesUtil.exibirMensagemSucesso("Categoria selecionada: "+categoriaTemp.getNome());
    }
	
	/*####### GETS E SETS ##########*/
	public TreeNode getRaiz() {
		return raiz;
	}
	public void setRaiz(TreeNode raiz) {
		this.raiz = raiz;
	}
	public TreeNode getNoSelecionado() {
		return noSelecionado;
	}
	public void setNoSelecionado(TreeNode noSelecionado) {
		this.noSelecionado = noSelecionado;
	}

}

