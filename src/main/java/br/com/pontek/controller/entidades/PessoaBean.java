package br.com.pontek.controller.entidades;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.controller.AbstractBean;
import br.com.pontek.enums.Estados;
import br.com.pontek.enums.PerfilDePessoa;
import br.com.pontek.enums.TipoResponsavel;
import br.com.pontek.exception.RelatorioException;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.sistema.Configuracao;
import br.com.pontek.service.entidades.PessoaService;
import br.com.pontek.service.financeiro.LancamentoService;
import br.com.pontek.service.sistema.ConfiguracaoService;
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
	@Autowired
	LancamentoService lancamentoService;
	@Autowired
	ConfiguracaoService configuracaoService;
	
	private Pessoa pessoa = new Pessoa();
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	
	/*Perfil setado pela pagina*/
	private PerfilDePessoa perfil;
	private Configuracao configuracao=new Configuracao();
	
	/*OBJETOS DE PAGINA��O LAZY DATATABLE*/
	private FiltroPessoa filtro  = new FiltroPessoa();
	private LazyDataModel<Pessoa> model;
	
	/*############# GERA��O DE RELAT�RIO #############*/
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
				//Relat�rio
				filtroRelatorio=filtro; 
				filtroRelatorio.setQuantidadeRegistros(getRowCount());
				filtroRelatorio.setPrimeiroRegistro(0);
				filtroRelatorio.setAscendente(true);
				return listaTemp;
			}
		};
	}
	
	/* ##FUN��ES DE RELAT�RIO PARA IMPRESS�O ############################################################################*/
	/**Fun��o para listar no formato PDF no navegador para impress�o
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
			 new RelatorioException("Erro ao gerar relat�rio :" +e);
		} 	
		return null;
	}/* ##FIM DAS FUN��ES DE RELAT�RIO PARA IMPRESS�O ############################################################################*/
	
	@PostConstruct
	private void init(){
		if(configuracao.getId()==null){
			configuracao=configuracaoService.carregar();
		}
	}
	public boolean exibirPerfil(){
		if(perfil.equals(PerfilDePessoa.Clientes)){
			return configuracao.isExibirOutrosPerfisNoCliente();
		}else if(perfil.equals(PerfilDePessoa.Fornecedores)){
			return configuracao.isExibirOutrosPerfisNoFornecedor();
		}else if(perfil.equals(PerfilDePessoa.Funcion�rios)){
			return configuracao.isExibirOutrosPerfisNoFuncionario();
		}
		return false;
	}
	
	public String salvar() {
			checkPerfil();
			try {
				Pessoa pessoaExiste=pessoaService.existeCadastro(pessoa);
			 if(pessoaExiste!=null){
				 FacesUtil.exibirMensagemErro("ID:"+pessoaExiste.getId()+" "+pessoaExiste.getNome()+ " j� existe, com perfil: "+pessoaExiste.perfis());
				 return null;
			 }
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
	
	/*Setando nome do respons�vel didatico*/ 
	public void confereRespFinanceiro(){
		if(TipoResponsavel.Mae.equals(pessoa.getTipoResponsavel())){			
				pessoa.setNomeResponsavel(pessoa.getNomeMae());
		}else if(TipoResponsavel.Pai.equals(pessoa.getTipoResponsavel())){
				pessoa.setNomeResponsavel(pessoa.getNomePai());
		}
	}

	/* ##(EXCLUIR) #####################*/
	public String excluir(Pessoa pessoa) {
		if(lancamentoService.existePessoaEmLancamentos(pessoa)){
			String infoPerfil="";
			if(filtro.getPerfil().equals(PerfilDePessoa.Clientes)){
				infoPerfil="cliente";
			}else if(filtro.getPerfil().equals(PerfilDePessoa.Fornecedores)){
				infoPerfil="fornecedor";
			}else if(filtro.getPerfil().equals(PerfilDePessoa.Funcion�rios)){
				infoPerfil="funcion�rio";
			}
			FacesUtil.exibirMensagemErro("O "+infoPerfil+" tem lancamentos financeiros, nessa caso desative o cadastro.");
			return null;
		}
		try {
			pessoaService.excluir(pessoa);
			FacesUtil.exibirMensagemSucesso("Exclu�do com sucesso!");
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
		return null;
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
		}else if(filtro.getPerfil().equals(PerfilDePessoa.Funcion�rios)){
			pessoa.setFuncionario(true);
		}
	}

	/* #####CEP WEB SERVI�E###### */
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
				FacesUtil.exibirMensagemAlerta("Servidor indispon�vel, colocar manualmente o endere�o");
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
	public TipoResponsavel[] getTiposDeResponsaveisEnums() {
		return TipoResponsavel.values();
	}
	public Estados[] getEstadosEnums() {
		return Estados.values();
	}
	/*############# PERFIL PAGINA ###############*/
	public PerfilDePessoa getPerfil() {
		return perfil;
	}
	public Configuracao getConfiguracao() {
		return configuracao;
	}

	/*####### GETS DE PAGINA��O LAZY DATATABLE  ##########*/
	public FiltroPessoa getFiltro() {
		return filtro;
	}
	public LazyDataModel<Pessoa> getModel() {
		return model;
	}
	
	/*####### MENSAGENS  ##########*/
	public void addMessageCadastroAtivo(boolean ativo) {
		if(ativo){
			FacesUtil.exibirMensagemSucesso("Somente cadastros ativos");
		}else{
			FacesUtil.exibirMensagemAlerta("Somente cadastros desativados");			
		}
	}

	/*preRenderView e seta o filtro conforme o perfil do cadastro*/
	public void tipoPagina(String nomePerfil){
		perfil=PerfilDePessoa.valueOf(nomePerfil);
		if(perfil!=null)
		filtro.setPerfil(perfil);
	}


}
