package br.com.pontek.controller.entidades;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.entidades.Empresa;
import br.com.pontek.service.entidades.EmpresaService;
import br.com.pontek.util.jsf.CepWebService;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "empresaBean")
@Controller
@Scope("view")
public class EmpresaBean {

	@Autowired
	EmpresaService empresaService;
	
	Empresa empresa=new Empresa();
	
	public EmpresaBean() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	void init(){
		empresa=empresaService.carregarDados();
		if(empresa==null) empresa=new Empresa();
	}
	
	
	public String verCadastroPDF(ActionEvent actionEvent) throws Exception{
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;	
	}
	
	
	public void salvar(){
		try {
			empresaService.salvar(empresa);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso");
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+e.getMessage());
		}
	}

	
	/* #####CEP WEB SERVIÇE###### */
	public void encontraCep(String cep) {
		CepWebService cws = new CepWebService();
			cws.buscaCep(cep);
			if (cws.getResultado() == 1) {
				this.empresa.setLogradouro(cws.getLogradouro());
				this.empresa.setBairro(cws.getBairro());
				this.empresa.setCep(cws.getCep());
				this.empresa.setCidade(cws.getCidade());
				this.empresa.setUf(cws.getUf());
				FacesUtil.exibirMensagemSucesso("CEP encontrado com sucesso");
			} 
			if (cws.getResultado() == 0){
				FacesUtil.exibirMensagemAlerta("Servidor indisponível, colocar manualmente o endereço");
			}
	}
	
	
	/*	############ GETS E SETS ######################*/
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}
