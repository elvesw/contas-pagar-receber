package br.com.pontek.controller.entidades;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.imageio.stream.FileImageOutputStream;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
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
	
	
	public void salvar(){
		try {
			empresaService.salvar(empresa);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso");
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+e.getMessage());
		}
	}

	
	public void upImagem(FileUploadEvent event){
		 UploadedFile uf = event.getFile();
		 String extensaoArquivo =uf.getFileName().substring(uf.getFileName().length()-4,uf.getFileName().length());
	     String pathArquivo = FacesUtil.pathImagens()+"empresa"+empresa.getId()+extensaoArquivo;
	     empresa.setLogo("empresa"+empresa.getId()+extensaoArquivo);
	     
	      FileImageOutputStream imageOutput;
	        try {
	            imageOutput = new FileImageOutputStream(new File(pathArquivo));
	            imageOutput.write(uf.getContents(),0,uf.getContents().length);
	            
	            imageOutput.close();
	        } catch (Exception e) {
	        	FacesUtil.exibirMensagemErro("Upload falhou, tente novamente");
	            return;
	        }
	        empresaService.salvar(empresa);
	         FacesUtil.exibirMensagemSucesso("Upload realidado com sucesso");
	}
	
	public void excluirImagem(){
		empresa.setLogo(null);
		empresaService.salvar(empresa);
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
