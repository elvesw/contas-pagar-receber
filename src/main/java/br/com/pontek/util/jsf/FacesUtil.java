package br.com.pontek.util.jsf;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
/**
 * @author Elves Gama
 */
public class FacesUtil {
     
    
    public static String getRequestParameter(String name) {
        return (String)FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }
 
    public static void exibirMensagemSucesso(String mensagem) {
        exibirMensagem(FacesMessage.SEVERITY_INFO, mensagem);
    }
 
    public static void exibirMensagemAlerta(String mensagem) {
        exibirMensagem(FacesMessage.SEVERITY_WARN, mensagem);
    }
     
    public static void exibirMensagemErro(String mensagem) {
        exibirMensagem(FacesMessage.SEVERITY_ERROR, mensagem);
    }
     
    private static void exibirMensagem(FacesMessage.Severity severity, String mensagem) {
        FacesMessage facesMessage = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
 
    public static ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }
     
    @SuppressWarnings("rawtypes")
	public static Map getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }
    
    public static String pathImagens() {
		String path = "";
		if (System.getProperties().get("os.name").toString().trim().equalsIgnoreCase("Linux")) {
			path = "/home/elves/imagens/";
		} else {
			path = "C://imagens//";
		}
		return path;
    }
    
   /* ##############################################*/
	public static void exibirMensagemSucessoForMessages(String forMessages,String mensagem) {
		exibirMensagemForMessages(FacesMessage.SEVERITY_INFO,forMessages,mensagem);
    }
 
    public static void exibirMensagemAlertaForMessages(String forMessages,String mensagem) {
    	exibirMensagemForMessages(FacesMessage.SEVERITY_WARN, forMessages,mensagem);
    }
     
    public static void exibirMensagemErroForMessages(String forMessages,String mensagem) {
    	exibirMensagemForMessages(FacesMessage.SEVERITY_ERROR, forMessages,mensagem);
    }
    
    private static void exibirMensagemForMessages(FacesMessage.Severity severity, String forMessages,String mensagem) {
        FacesMessage facesMessage = new FacesMessage(severity, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(forMessages, facesMessage);
    }
}