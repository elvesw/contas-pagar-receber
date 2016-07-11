package br.com.pontek.controller;

import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.enums.PerfilDePessoa;
import br.com.pontek.model.Pessoa;
import br.com.pontek.service.PessoaService;
import br.com.pontek.util.CepWebService;
import br.com.pontek.util.FacesUtil;
import br.com.pontek.util.filtro.FiltroPessoa;

@ManagedBean(name = "pessoaBean")
@Controller
@Scope("view")
public class PessoaBean {
	
	@Autowired
	private PessoaService pessoaService;
	
	private Pessoa pessoa = new Pessoa();
	private boolean telaDeCadastro=false;/*(true-cadastro | false-lista)*/
	
	/*OBJETOS DE PAGINAÇÃO LAZY DATATABLE*/
	private FiltroPessoa filtro  = new FiltroPessoa();
	private LazyDataModel<Pessoa> model;

	//CONSTRUTOR
	public PessoaBean() {
		model = new LazyDataModel<Pessoa>() {
			private static final long serialVersionUID = 1L;
			public List<Pessoa> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(pessoaService.quantidadeFiltrados(filtro));
				List<Pessoa> listaTemp = pessoaService.filtrados(filtro);
						System.out.println("Nome                 : "+filtro.getNome());
						System.out.println("PrimeiroRegistro     : "+filtro.getPrimeiroRegistro());
						System.out.println("QuantidadeRegistros  : "+filtro.getQuantidadeRegistros());
						System.out.println("Ascendente           : "+filtro.isAscendente());
						System.out.println("PropriedadeOrdenacao : "+filtro.getPropriedadeOrdenacao());
						System.out.println("RowCount             : "+getRowCount());
						System.out.println("QtdNaListaTemp       : "+listaTemp.size());
				return listaTemp;
			}
		};
	}
	

	public void salvar() {
		try {
			pessoaService.salvar(pessoa);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso!");
			this.reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
	}

	public void novo() {
		this.reset();
		this.telaDeCadastro=true;
    } 
	public void voltar() {
		this.reset();
		this.telaDeCadastro=false;
    } 

	public void editar(Pessoa pessoa){
		if(pessoa.getId()!=null){
			this.pessoa=pessoa;
			this.telaDeCadastro=true;
		}
	}

	/* ##(EXCLUIR) #####################*/
	public void excluir(Pessoa pessoa) {
		try {
			pessoaService.excluir(pessoa);
			FacesUtil.exibirMensagemSucesso("Excluído com sucesso!");
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
	}
	
	private void reset(){
		this.pessoa = new Pessoa();
	}

	/* #####CEP WEB SERVIÇE###### */
	public void encontraCep(String cep) {
		CepWebService cws = new CepWebService();
			cws.buscaCep(cep);
			if (cws.getResultado() == 1) {
				this.pessoa.setLogradouro(cws.getLogradouro());
				this.pessoa.setBairro(cws.getBairro());
				this.pessoa.setCep(cws.getCep());
				this.pessoa.setCidade(cws.getCidade());
				this.pessoa.setUf(cws.getUf());
				FacesUtil.exibirMensagemSucesso("CEP encontrado com sucesso");
			} 
			if (cws.getResultado() == 0){
				FacesUtil.exibirMensagemAlerta("Servidor indisponível, colocar manualmente o endereço");
			}
	}
	
	/*####### GETS E SETS##########*/
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public boolean isTelaDeCadastro() {
		return telaDeCadastro;
	}
	public void setTelaDeCadastro(boolean telaDeCadastro) {
		this.telaDeCadastro = telaDeCadastro;
	}

	/*############# ENUMS ###############*/
	public PerfilDePessoa[] getPerfilDePessoaEnums() {
		return PerfilDePessoa.values();
	}
	
	/*####### MENSAGENS  ##########*/
	public void addMessageCadastroAtivo(boolean ativo) {
		if(ativo){
			FacesUtil.exibirMensagemSucesso("Somente cadastros ativos");
		}else{
			FacesUtil.exibirMensagemAlerta("Somente cadastros desativados");			
		}
	}
	public void addMessageFiltro(PerfilDePessoa perfil) {
		if(PerfilDePessoa.Todos.equals(perfil)){
			FacesUtil.exibirMensagemSucesso("Todos perfis");	
		}else if(PerfilDePessoa.Clientes.equals(perfil)){
			FacesUtil.exibirMensagemSucesso("Somente clientes");	
		}else if(PerfilDePessoa.Fornecedores.equals(perfil)){
			FacesUtil.exibirMensagemSucesso("Somente fornecedores");	
		}else if(PerfilDePessoa.Funcionários.equals(perfil)){
			FacesUtil.exibirMensagemSucesso("Somente funcionários");	
		}
	}
	
	/*####### GETS DE PAGINAÇÃO LAZY DATATABLE  ##########*/
	public FiltroPessoa getFiltro() {
		return filtro;
	}
	public LazyDataModel<Pessoa> getModel() {
		return model;
	}

}
