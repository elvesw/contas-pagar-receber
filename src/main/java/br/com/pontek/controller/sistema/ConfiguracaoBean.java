package br.com.pontek.controller.sistema;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.sistema.Configuracao;
import br.com.pontek.service.sistema.ConfiguracaoService;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "configuracaoBean")
@Controller
@Scope("view")
public class ConfiguracaoBean{
	
	@Autowired ConfiguracaoService configuracaoService;
	
	private Configuracao configuracao=new Configuracao();
	
	public ConfiguracaoBean() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	void init(){
		configuracao=configuracaoService.carregar();
		if(configuracao==null) configuracao=new Configuracao();
	}
	
	public void salvar(){
		try {
			configuracaoService.salvar(configuracao);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso");
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+e.getMessage());
		}
	}

	/*	############ GETS E SETS ######################*/
	public Configuracao getConfiguracao() {
		return configuracao;
	}
	public void setConfiguracao(Configuracao configuracao) {
		this.configuracao = configuracao;
	}
}

