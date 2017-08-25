package br.com.pontek.controller.autenticacao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.autenticacao.Usuario;
import br.com.pontek.service.autenticacao.UsuarioService;
import br.com.pontek.util.RandomUtil;
import br.com.pontek.util.email.EmailUtil;
import br.com.pontek.util.jsf.FacesUtil;
import br.com.pontek.util.jsf.MD5;

@ManagedBean(name = "loginBean")
@Controller
@RequestScoped
public class LoginBean {

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	EmailUtil emailUtil;

	private boolean formNovaSenha = false;
	private String email = "";

	public String doLogin() throws IOException, ServletException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
				.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
		// It's OK to return null here because Faces is just going to exit.
		return null;
	}

	public String enviarNovaSenha() {
		Usuario usuario = usuarioService.buscarPorEmail(email);
		if (usuario == null) {
			reset();
			FacesUtil.exibirMensagemErro("Usuário não encontrado!");
			return null;
		}
		if (!usuario.isCadastroAtivo()) {
			reset();
			FacesUtil.exibirMensagemErro("Usuário não encontrado!");
			return null;
		}

		try {
			String senha = RandomUtil.inteiro(100000, 999999).toString();
			String endereco = "<br/><h4 style=\"font-weight:bold;\">Acesse em</h4><a href=\"http://www.pontek.com.br/app\">www.pontek.com.br/app</a>";
			String corpoEmail = "<p style=\"font-weight:bold;display:inline-block;\">Login: </p><p style=\"display:inline-block;\">"
					+ "&nbsp;" + usuario.getEmail() + "</p>" + "<br/>"
					+ "<p style=\"font-weight:bold;display:inline-block;\">Senha: </p><p style=\"display:inline-block;\">"
					+ "&nbsp;" + senha + "</p>" + endereco;
			emailUtil.sendEmail("Solicitação de nova senha", corpoEmail, usuario.getEmail());
			usuario.setSenha(MD5.convertPasswordToMD5(senha));
			usuarioService.salvar(usuario);
			reset();
			FacesUtil.exibirMensagemSucesso("Senha solicitada foi enviada por email");
		} catch (NoSuchAlgorithmException e) {
			FacesUtil.exibirMensagemErro("Erro :" + e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public void formLogin() {
		formNovaSenha = false;
	}

	public void formNovaSenhaEmail() {
		formNovaSenha = true;
	}

	private void reset() {
		formNovaSenha = false;
		email = "";
	}

	/* ############ GETS E SETS ############ */
	public boolean isFormNovaSenha() {
		return formNovaSenha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
