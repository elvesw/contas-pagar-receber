package br.com.pontek.controller.sistema;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.autenticacao.Usuario;
import br.com.pontek.model.sistema.Configuracao;
import br.com.pontek.service.autenticacao.UsuarioService;
import br.com.pontek.service.sistema.ConfiguracaoService;


@ManagedBean(name = "sessaoBean")
@Controller
@Scope("session")
public class SessaoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	ConfiguracaoService  configuracaoService;

	private String usuarioLogado= "Não logado...";
	private Usuario usuario = null;
	private Configuracao configuracao=null;
	
	@PostConstruct
	public void init(){
		if(configuracao==null){
			configuracao=configuracaoService.carregar();
		}
	}

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

	/*############# GETS E SETS ##################*/
	public Usuario getUsuario() {
		return usuario;
	}

	public Configuracao getConfiguracao() {
		return configuracao;
	}
	public void setConfiguracao(Configuracao configuracao) {
		this.configuracao = configuracao;
	}

}
