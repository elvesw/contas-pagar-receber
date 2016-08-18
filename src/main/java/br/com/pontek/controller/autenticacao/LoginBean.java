package br.com.pontek.controller.autenticacao;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.stereotype.Controller;

@ManagedBean(name="loginBean")
@Controller
@RequestScoped
public class LoginBean {
	
	 public String doLogin() throws IOException, ServletException {
	        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
	        dispatcher.forward((ServletRequest) context.getRequest(),(ServletResponse) context.getResponse());
	        FacesContext.getCurrentInstance().responseComplete();
	        // It's OK to return null here because Faces is just going to exit.
	        return null;
	    }
}