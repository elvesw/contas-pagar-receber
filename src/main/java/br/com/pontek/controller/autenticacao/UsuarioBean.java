package br.com.pontek.controller.autenticacao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.controller.AbstractBean.estadoDaView;
import br.com.pontek.model.autenticacao.Usuario;
import br.com.pontek.service.autenticacao.UsuarioService;
import br.com.pontek.util.RandomUtil;
import br.com.pontek.util.email.EmailUtil;
import br.com.pontek.util.jsf.FacesUtil;
import br.com.pontek.util.jsf.MD5;

@ManagedBean(name = "usuarioBean")
@Controller
@Scope("view")
public class UsuarioBean {

	@Autowired
	UsuarioService usuarioService;
	
	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	private Usuario usuario=new Usuario();
	private boolean novaSenha=false;

	
	public UsuarioBean() {
		
	}
	
	@PostConstruct
	public void init(){
		listaUsuarios=usuarioService.listaDeUsuarios();
		List<Usuario> listatemp = new ArrayList<Usuario>();
		for (Usuario u : listatemp) {
			if(u.getEmail().equals("elvesw@gmail.com"));
			listaUsuarios.remove(u);
		}
	}
	
	public void novo(){
		usuario=new Usuario();
		viewAtiva = estadoDaView.INSERINDO.toString();
	}
	
	
	public void editar(Usuario usuario){
		if(usuario!=null){
			this.usuario=usuario;
			viewAtiva = estadoDaView.EDITANDO.toString();
		}
	}
	
	
	public void excluir(Usuario usuario){
		try {
			usuarioService.excluir(usuario);
			listaUsuarios.remove(usuario);
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro :"+e.getMessage());
		}
	}
	
	
	public void enviarNovaSenha(Usuario usuario){
		
	}
	
	
	public void salvar(){
		try {
			if(usuario.getId()!=null){
				if(novaSenha){
					String senha=RandomUtil.inteiro(100000,999999).toString();
					String endereco ="<br/><h4 style=\"font-weight:bold;\">Acesse em</h4><a href=\"http://www.pontek.com.br/app\">www.pontek.com.br/app</a>"; 
					String corpoEmail="<p style=\"font-weight:bold;display:inline-block;\">Login: </p><p style=\"display:inline-block;\">"+"&nbsp;"+usuario.getEmail()+"</p>"
							+"<br/>"+"<p style=\"font-weight:bold;display:inline-block;\">Senha: </p><p style=\"display:inline-block;\">"+"&nbsp;"+senha+"</p>"
							+endereco;
					EmailUtil.sendEmail("Solicitação de nova senha", corpoEmail, usuario.getEmail());
					usuario.setSenha(MD5.convertPasswordToMD5(senha));
					usuarioService.salvar(usuario);
					FacesUtil.exibirMensagemSucesso("Senha solicitada foi enviada por email");
				}
			}else{
				String senha=RandomUtil.inteiro(100000,999999).toString();
				String endereco ="<br/><h4 style=\"font-weight:bold;\">Acesse em</h4><a href=\"http://www.pontek.com.br/app\">www.pontek.com.br/app</a>"; 
				String corpoEmail="<p style=\"font-weight:bold;display:inline-block;\">Login: </p><p style=\"display:inline-block;\">"+"&nbsp;"+usuario.getEmail()+"</p>"
						+"<br/>"+"<p style=\"font-weight:bold;display:inline-block;\">Senha: </p><p style=\"display:inline-block;\">"+"&nbsp;"+senha+"</p>"
						+endereco;
					EmailUtil.sendEmail("Novo cadastro no sistema", corpoEmail, usuario.getEmail());
					usuario.setSenha(MD5.convertPasswordToMD5(senha));
					usuarioService.salvar(usuario);
					FacesUtil.exibirMensagemSucesso("Salvo com sucesso, senha enviada por email");
			}
			
			if(!listaUsuarios.contains(usuario))
			listaUsuarios.add(usuario);
			viewAtiva = estadoDaView.LISTANDO.toString();
			usuario=new Usuario();
			
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro :"+e.getMessage());
		}
	}
	
	
	public void voltar(){
		viewAtiva = estadoDaView.LISTANDO.toString();
	}
	
/*	##################GETS E SETS ############################*/
	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getViewAtiva() {
		return viewAtiva;
	}
	public boolean isNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(boolean novaSenha) {
		this.novaSenha = novaSenha;
	}
}
