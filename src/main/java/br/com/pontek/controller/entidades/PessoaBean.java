package br.com.pontek.controller.entidades;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.controller.AbstractBean;
import br.com.pontek.enums.PerfilDePessoa;
import br.com.pontek.exception.RelatorioException;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.service.entidades.PessoaService;
import br.com.pontek.util.DataUtil;
import br.com.pontek.util.filtro.FiltroPessoa;
import br.com.pontek.util.jsf.CepWebService;
import br.com.pontek.util.jsf.FacesUtil;
import br.com.pontek.util.report.PessoaDataSource;
import br.com.pontek.util.report.RelatorioUtil;

@ManagedBean(name = "pessoaBean")
@Controller
@Scope("view")
public  class PessoaBean extends AbstractBean{
	private static final long serialVersionUID = 1L;

	@Autowired
	private PessoaService pessoaService;
	
	private Pessoa pessoa = new Pessoa();
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	
	/*Perfil setado pela pagina*/
	PerfilDePessoa perfil;
	
	/*OBJETOS DE PAGINAÇÃO LAZY DATATABLE*/
	private FiltroPessoa filtro  = new FiltroPessoa();
	private LazyDataModel<Pessoa> model;
	
	/*############# GERAÇÃO DE RELATÓRIO #############*/
	private FiltroPessoa filtroRelatorio = new FiltroPessoa();

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
				//Relatório
				filtroRelatorio=filtro; 
				filtroRelatorio.setQuantidadeRegistros(getRowCount());
				filtroRelatorio.setPrimeiroRegistro(0);
				filtroRelatorio.setAscendente(true);
				return listaTemp;
			}
		};
	}
	
	/* ##FUNÇÕES DE RELATÓRIO PARA IMPRESSÃO ############################################################################*/
	/**Função para listar no formato PDF no navegador para impressão
	 * @throws RelatorioException */
	public String verPDF(ActionEvent actionEvent) throws RelatorioException {
		List<Pessoa> listaTemp = pessoaService.filtrados(filtroRelatorio);
		if(listaTemp.isEmpty()){
			FacesUtil.exibirMensagemErro("Lista vazia");
			return null;
		}
		try {
			String tituloRelatorio=filtroRelatorio.getPerfil().getStringEnum();
			Map<String, Object> parametros = new HashMap<String,Object>();
				parametros.put("titulo","Lista de "+tituloRelatorio);
			PessoaDataSource pessoaDS = new PessoaDataSource();
			pessoaDS.prepareDataSource(listaTemp);
			RelatorioUtil relatorio=new RelatorioUtil();
				relatorio.verPdfBrowser(pessoaDS,parametros,"RelatorioPessoas.jrxml",tituloRelatorio+DataUtil.ddMMyy(new Date()));
		} catch (Exception e) {
			 FacesUtil.exibirMensagemErro("Erro : "+ e.getMessage());
			 new RelatorioException("Erro ao gerar relatório :" +e);
		} 	
		return null;
	}/* ##FIM DAS FUNÇÕES DE RELATÓRIO PARA IMPRESSÃO ############################################################################*/
	

	public String salvar() {
			checkPerfil();
			try {
			pessoaService.salvar(pessoa);
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso!");
			reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
		return null;
	}

	public void novo() {
		reset();
		viewAtiva = estadoDaView.INSERINDO.toString();
    } 
	public void voltar() {
		reset();
		viewAtiva = estadoDaView.LISTANDO.toString();
    } 

	public void editar(Pessoa pessoa){
		if(pessoa.getId()!=null){
			this.pessoa=pessoa;
			viewAtiva = estadoDaView.EDITANDO.toString();
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
		pessoa = new Pessoa();
		checkPerfil();
	}
	
	private void checkPerfil(){
		if(filtro.getPerfil().equals(PerfilDePessoa.Clientes)){
			pessoa.setCliente(true);
		}else if(filtro.getPerfil().equals(PerfilDePessoa.Fornecedores)){
			pessoa.setFornecedor(true);
		}else if(filtro.getPerfil().equals(PerfilDePessoa.Funcionários)){
			pessoa.setFuncionario(true);
		}
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
	public String getViewAtiva() {
		return viewAtiva;
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

	/*####### GETS DE PAGINAÇÃO LAZY DATATABLE  ##########*/
	public FiltroPessoa getFiltro() {
		return filtro;
	}
	public LazyDataModel<Pessoa> getModel() {
		return model;
	}

	/*preRenderView e seta o filtro conforme o perfil do cadastro*/
	public void tipoPagina(String nomePerfil){
		perfil=PerfilDePessoa.valueOf(nomePerfil);
		if(perfil!=null)
		filtro.setPerfil(perfil);
	}


}
