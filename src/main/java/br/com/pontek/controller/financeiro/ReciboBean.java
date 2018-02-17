package br.com.pontek.controller.financeiro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.enums.FiltroData;
import br.com.pontek.enums.FiltroStatus;
import br.com.pontek.enums.FiltroTipoData;
import br.com.pontek.enums.FiltroTipoLancamento;
import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.model.financeiro.Conta;
import br.com.pontek.model.financeiro.Lancamento;
import br.com.pontek.model.sistema.Configuracao;
import br.com.pontek.service.entidades.PessoaService;
import br.com.pontek.service.financeiro.LancamentoService;
import br.com.pontek.service.financeiro.ReciboService;
import br.com.pontek.service.sistema.ConfiguracaoService;
import br.com.pontek.util.dto.Recibo;
import br.com.pontek.util.filtro.FiltroLancamento;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "reciboBean")
@Controller
@Scope("view")
public class ReciboBean {
	
	@Autowired private ReciboService reciboService;
	@Autowired private PessoaService pessoaService;
	@Autowired private LancamentoService lancamentoService;
	@Autowired private ConfiguracaoService  configuracaoService;
	
	/*########### LAZY DATATABLE ##############*/
	private FiltroLancamento filtro= new FiltroLancamento(FiltroData.Sem_filtro_de_data, 
														FiltroStatus.Somente_pendentes,
														FiltroTipoData.Data_de_vencimento);
	private LazyDataModel<Lancamento> model;
	private Lancamento lancamentoSelecionado;
	/*########### RECIBO ##############*/
	private Recibo recibo;
	private Lancamento lancamento;
	/*########### PERFIL ##############*/
	private TipoDeLancamento tipoLancamentoPagina;
	private Configuracao configuracao;


	public ReciboBean() {
		model = new LazyDataModel<Lancamento>() {
			private static final long serialVersionUID = 1L;
			public List<Lancamento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
				List<Lancamento> listaTemp = new ArrayList<>();
				/*Valida se existe nome de pessoa*/
				if(StringUtils.isNotEmpty(lancamento.getPessoa().getNome())){
					filtro.setTermoParaBusca(lancamento.getPessoa().getNome());
				}else {
					return listaTemp;
				}
				
				if((filtro.isBtnFiltro()==true)){//RESOLVENDO O BUG DA CONSULTA
					filtro.setPrimeiroRegistro(0);//se for pela consulta
					filtro.setBtnFiltro(false);//pela consulta
				}else{
					filtro.setPrimeiroRegistro(first);
				}
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				setRowCount(lancamentoService.quantidadeFiltrados(filtro));
				listaTemp = lancamentoService.filtrados(filtro);
				return listaTemp;
			}
		    @Override
		    public Lancamento getRowData(String rowKey) {
		        Integer intRowKey = Integer.parseInt(rowKey);
		        for(Lancamento event : model) {
		            if(event.getId().equals(intRowKey)) {
		                return event;
		            }
		        }
		        return null;
		    }
		    @Override
		    public Object getRowKey(Lancamento event) {
		        return event.getId();
		    }
		};
	}
	
	@PostConstruct
	public void init() {
		lancamentoSelecionado=new Lancamento();
		lancamento=new Lancamento();
		lancamento.setObservacao("a"+System.currentTimeMillis());
		recibo=new Recibo();
		recibo=reciboService.carregarDadosEmpresa(recibo);
		configuracao = new Configuracao();
		configuracao=configuracaoService.carregar();
	}
	
	public void atualizarReciboViaAjax (){
		if(lancamentoSelecionado!=null) {
			if(lancamento.getId()!=lancamentoSelecionado.getId()) {
				lancamento = lancamentoSelecionado;
				FacesUtil.exibirMensagemAlerta("Lancamento "+lancamento.getObservacao()+" selecionado da lista");				
			}
		}
		Boolean checkBox2via=recibo.isVia2();
		recibo = reciboService.updateReciboViaAjax(lancamento);
		recibo.setVia2(checkBox2via);//manter o estado do checkbox
	}
	
	/**Fun��o do autocomplete de nomes para o form cadastro*/	
	public List<Pessoa> autoCompleteNomes(String nome) {
		List<Pessoa> ListTempPessoa = new ArrayList<>();
				//Busca por termo de busca e perfil de acordo com as configura��es do sistema.
				if(tipoLancamentoPagina.equals(TipoDeLancamento.ENTRADA)){
					ListTempPessoa=pessoaService.listaComTermoDeBuscaPorPerfil(nome,configuracao.isExibirClientesEmLancamentosEntrada(), 
							configuracao.isExibirFornecedoresEmLancamentosEntrada(),
							configuracao.isExibirFuncionariosEmLancamentosEntrada());				
				}
				else if(tipoLancamentoPagina.equals(TipoDeLancamento.SA�DA)){
					ListTempPessoa=pessoaService.listaComTermoDeBuscaPorPerfil(nome,configuracao.isExibirClientesEmLancamentosSaida(), 
							configuracao.isExibirFornecedoresEmLancamentosSaida(),
							configuracao.isExibirFuncionariosEmLancamentosSaida());				
				}
	    return ListTempPessoa;
	 }
	/**Fun��o do autocomplete de descri��o j� cadastrados*/	
	public List<String> autoCompleteDescricao(String nome) {
			List<String> lista = lancamentoService.listaDescricoesLancamentos(nome);
	    return lista;
	 }
	
	public boolean getValidaForm() {
		if((lancamento.getValor()==null)||(lancamento.getValor()==BigDecimal.ZERO)) {
			return true;
		}
		if((lancamento.getPessoa()==null)||(lancamento.getPessoa().getId()==null)) {
			return true;
		}
		if(StringUtils.isBlank(lancamento.getDescricao())) {
			return true;
		}
		return false;
	}
	
	
	public void salvar(ActionEvent actionevent) {
		try {
			if(lancamento.getId()==null) {
				//lancamento.setTipoLancamento(TipoDeLancamento.ENTRADA);
				lancamento.setDataVencimento(new Date());
				Categoria categoria = new Categoria();
				categoria.setId(1);
				lancamento.setCategoria(categoria);
				Conta conta = new Conta();
				conta.setId(1);
				lancamento.setConta(conta);
			}
			lancamento.setDataPagamento(new Date());
			lancamento.setStatusLancamento(StatusDeLancamento.Pago);
			lancamentoService.salvar(lancamento);//Salvar lan�amento �nico
			FacesUtil.exibirMensagemSucesso("Lan�amento salvo com sucesso!");
			init();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
/*	########## FUN��ES DE PERFIL DA P�GINA ##########*/
	/**Chamada do preRenderView para o bean diferenciar se o 
	 * TipoDeLancamento de lan�amentos da view � de ENTRADA ou SA�DA
	 * @param EntradaOuSaida  ENTRADA ou SA�DA*/
	public void tipoPagina(String EntradaOuSaida){
		tipoLancamentoPagina=TipoDeLancamento.valueOf(EntradaOuSaida);
		if(tipoLancamentoPagina!=null){
			lancamento.setTipoLancamento(tipoLancamentoPagina);//set tipo em lan�amento
			 if(tipoLancamentoPagina.equals(TipoDeLancamento.ENTRADA)){
					filtro.setFiltroTipoLancamento(FiltroTipoLancamento.Somente_entrada);
			}else if(tipoLancamentoPagina.equals(TipoDeLancamento.SA�DA)){
					filtro.setFiltroTipoLancamento(FiltroTipoLancamento.Somente_sa�da);
			}
		}
	}
	
	/**Define qual include ser� apresentado na view include_avulso_recibo_entrada.xhtml ou include_avulso_recibo_saida.xhtml*/
	public boolean confirmaTipoDePaginaParaView() {
		if(tipoLancamentoPagina.equals(TipoDeLancamento.ENTRADA)){
			return true;		
		}
			return false;
	}
	
	/**Seta o t�tulo da p�gina de acordo com o tipo de lan�amento*/
	public String getTitlePage() {
		String titlePage = "";
		if(tipoLancamentoPagina.equals(TipoDeLancamento.ENTRADA)){
			titlePage="Financeiro Receber - Recibo Avulso via Bean";		
		}
		else if(tipoLancamentoPagina.equals(TipoDeLancamento.SA�DA)){
			titlePage="Financeiro Pagar - Recibo Avulso via Bean";	
		}
		return titlePage;
	}
	
/*	########## GETS E SETS ##########*/
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public Recibo getRecibo() {
		return recibo;
	}
	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}
	/*####### GETS DE PAGINA��O LAZY DATATABLE  ##########*/
	public FiltroLancamento getFiltro() {
		return filtro;
	}
	public LazyDataModel<Lancamento> getModel() {
		return model;
	}
	public Lancamento getLancamentoSelecionado() {
		return lancamentoSelecionado;
	}
	public void setLancamentoSelecionado(Lancamento lancamentoSelecionado) {
		this.lancamentoSelecionado = lancamentoSelecionado;
	}
	/*########### PERFIL ##############*/

	
}
