package br.com.pontek.controller.sistema;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.autenticacao.Usuario;
import br.com.pontek.service.autenticacao.UsuarioService;


@ManagedBean(name = "usuarioSessaoBean")
@Controller
@Scope("session")
public class UsuarioSessaoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	UsuarioService usuarioService;

	private String usuarioLogado= "Não logado...";
	private Usuario usuario = null;
	

	/**Mostrar na tela o usuario logado no sistema*/
	public String getUsuarioLogado() {
		String userTemp = SecurityContextHolder.getContext().getAuthentication().getName();
		if(userTemp!=null && userTemp!=""){
			if(usuario==null){
				usuario = usuarioService.buscarPorEmail(userTemp);//busca usuario
				String primeiroNome = null;
				if(usuario.getNome().contains(" ")){
					primeiroNome= usuario.getNome().substring(0,usuario.getNome().indexOf(" ")); 
				}
				else{
					primeiroNome = usuario.getNome();
				}
				usuarioLogado=primeiroNome;
			}
		}
		return usuarioLogado;
	}


	public void checkLogado() throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	    if (SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
	        ec.redirect(ec.getRequestContextPath() + "/Login.jsf");
	    }else{
		    ec.redirect(ec.getRequestContextPath() + "/sistema/Dashboard.jsf");
	    }
	}
	
}
