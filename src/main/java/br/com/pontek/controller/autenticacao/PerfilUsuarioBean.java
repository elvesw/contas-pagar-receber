package br.com.pontek.controller.autenticacao;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.controller.sistema.UsuarioSessaoBean;
import br.com.pontek.model.autenticacao.Usuario;
import br.com.pontek.service.autenticacao.UsuarioService;
import br.com.pontek.util.jsf.FacesUtil;
import br.com.pontek.util.jsf.MD5;
import br.com.pontek.util.jsf.ServiceFactory;

@ManagedBean(name = "perfilUsuarioBean")
@Controller
@Scope("view")
public class PerfilUsuarioBean {

	@Autowired
	UsuarioService usuarioService;

	private Usuario usuario = new Usuario();
	private String senhaTemp = "";
	private String senhaTempConfirme = "";
	private boolean alterarSenha=false;

	public PerfilUsuarioBean() {

	}

	@PostConstruct
	public void init() {
		if (usuario.getId() == null) {
			UsuarioSessaoBean bean = (UsuarioSessaoBean) ServiceFactory.getBean("usuarioSessaoBean");
			usuario = bean.getUsuario();
		}
	}

	public String salvar() {
		String mgs="";
		if (!senhaTemp.equals(senhaTempConfirme)) {
			FacesUtil.exibirMensagemErro("Senhas não conferem");
			return null;
		}
		try {
			if (!senhaTemp.equals("")) {
				usuario.setSenha(MD5.convertPasswordToMD5(senhaTemp));
				mgs=",senha alterada";
			}
			usuarioService.salvar(usuario);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso "+mgs);
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
		senhaTemp="";
		senhaTempConfirme="";
		return null;
	}
	
	/* ############# GETS E SETS #################### */
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getSenhaTemp() {
		return senhaTemp;
	}
	public void setSenhaTemp(String senhaTemp) {
		this.senhaTemp = senhaTemp;
	}
	public String getSenhaTempConfirme() {
		return senhaTempConfirme;
	}
	public void setSenhaTempConfirme(String senhaTempConfirme) {
		this.senhaTempConfirme = senhaTempConfirme;
	}
	public boolean isAlterarSenha() {
		return alterarSenha;
	}
	public void setAlterarSenha(boolean alterarSenha) {
		this.alterarSenha = alterarSenha;
	}
}
