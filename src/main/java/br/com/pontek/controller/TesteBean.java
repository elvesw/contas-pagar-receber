package br.com.pontek.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.sistema.Documento;
import br.com.pontek.service.sistema.DocumentoService;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "testeBean")
@Controller
@Scope("view")
public class TesteBean implements Serializable {  
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private DocumentoService documentoService;
	
	private Pessoa pessoa=new Pessoa();;
	private Documento documento=new Documento();;
	
    

  
    public TesteBean() {  
    	
    }  
  
    public void salvar() {  
    	documento.setTexto(documento.getTexto().replaceAll("\\r|\\n", ""));
        FacesUtil.exibirMensagemSucesso(documento.getTexto().length() > 150 ? documento.getTexto().substring(0, 100) : documento.getTexto());
        pessoa=new Pessoa();
        pessoa.setId(9);
        documento.setPessoa(pessoa);
        documento.setNome("TESTE CK EDITOR ");
        documentoService.salvar(documento);
    }  

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Documento getDocumento() {
		if (documento.getTexto()==null){
			documento.setTexto("Digite seu texto...");
		}
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}  

}
