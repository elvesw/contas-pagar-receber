package br.com.pontek.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.enums.FiltroData;
import br.com.pontek.enums.FiltroStatus;
import br.com.pontek.enums.FiltroTipoData;
import br.com.pontek.enums.FiltroTipoLancamento;
import br.com.pontek.enums.FrequenciaDeLancamento;
import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.Categoria;
import br.com.pontek.model.Conta;
import br.com.pontek.model.Lancamento;
import br.com.pontek.model.Pessoa;
import br.com.pontek.service.CategoriaService;
import br.com.pontek.service.ContaService;
import br.com.pontek.service.LancamentoService;
import br.com.pontek.service.PessoaService;
import br.com.pontek.util.DataUtil;
import br.com.pontek.util.FacesUtil;
import br.com.pontek.util.filtro.FiltroLancamento;

@ManagedBean(name = "caixaBean")
@Controller
@Scope("view")
public class CaixaBean extends AbstractBean{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired private LancamentoService lancamentoService;
	@Autowired private PessoaService pessoaService;
	@Autowired private CategoriaService categoriaService;
	@Autowired private ContaService contaService;
	
	/*########### LAZY DATATABLE ##############*/
	private FiltroLancamento filtro= new FiltroLancamento(FiltroData.Sem_filtro_de_data, FiltroStatus.Somente_pagos, 
															FiltroTipoData.Data_de_pagamento, FiltroTipoLancamento.Todos);
	private LazyDataModel<Lancamento> model;
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	private BigDecimal somaTotal;
	private BigDecimal somaTotalPago;
	/*########### FIM - LAZY DATATABLE ##############*/
	
	/*############# NOVO LANÇAMENTO #############*/
	private Lancamento lancamento = new Lancamento();
	private FrequenciaDeLancamento frequenciaDeLancamentos=FrequenciaDeLancamento.Mensal;
	private Integer ocorrencias=1;
	private boolean repetir=false;
	private boolean repetirAcrescimosDescontos=false;
	private boolean pago=false;
	/*############# FIM - NOVO LANÇAMENTO #############*/
	
	/*############# CANCELAR LANÇAMENTOS #############*/
	private List<Lancamento> listaLancamentosSelecionados = new ArrayList<>();
	private String motivoCancelar;
	/*############# FIM - CANCELAR LANÇAMENTOS #############*/
	
	/*############# LISTAS #############*/
	private List<Conta> listaContas  = new ArrayList<>();
	private List<Categoria> listaCategorias  = new ArrayList<>();
	private List<Pessoa> listaPessoas = new ArrayList<>();
	/*############# FIM - LISTAS #############*/



	//CONSTRUTOR
	public CaixaBean() {
		model = new LazyDataModel<Lancamento>() {
			private static final long serialVersionUID = 1L;
			public List<Lancamento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
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
				somaTotal=lancamentoService.somaTotal(filtro);//SOMA TOTAL
				somaTotalPago=lancamentoService.somaTotalPago(filtro);//SOMA TOTAL PAGO
				List<Lancamento> listaTemp = lancamentoService.filtrados(filtro);
					System.out.println("TermoParaBusca       : "+filtro.getTermoParaBusca());
					System.out.println("PrimeiroRegistro     : "+filtro.getPrimeiroRegistro());
					System.out.println("QuantidadeRegistros  : "+filtro.getQuantidadeRegistros());
					System.out.println("Ascendente           : "+filtro.isAscendente());
					System.out.println("PropriedadeOrdenacao : "+filtro.getPropriedadeOrdenacao());
					System.out.println("RowCount             : "+getRowCount());
					System.out.println("QtdNaListaTemp       : "+listaTemp.size());
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
	
	/*############# FUNÇÕES #############*/
	
	/*####### FUNÇÕES de SALVAR/EDITAR/EXCLUIR ##########*/
	/**Função do autocomplete de nomes*/	
	public List<Pessoa> autoCompleteNomes(String nome) {
			List<Pessoa> lista = listaPessoas;
	        List<Pessoa> filtroLista = new ArrayList<Pessoa>();
	        for (Integer i = 0; i < lista.size(); i++) {
	        	Pessoa temp = lista.get(i);
	            if(temp.getNome().toLowerCase().startsWith(nome)){
	            	filtroLista.add(temp);
	            }
	        }
	    return filtroLista;
	 }
	public String salvar() {
		/*Pode lançar um valor zerado, como se for uma bolsa, um desconto 100% mais que precisa ficar registrado*/
		try {
			if(lancamento.getValorDesconto()!=null){
				if(lancamento.getValorDesconto().compareTo(lancamento.getValor()) > 0){
					FacesUtil.exibirMensagemErro("Não permitido, Valor de desconto é MAIOR que o valor principal");
					return null;
				}				
			}
			if(lancamento.getValorPago().compareTo(BigDecimal.ZERO) < 0){
				FacesUtil.exibirMensagemErro("Não permitido, Valor total é MENOR que zero");
				return null;
			}
			if(viewAtiva.equals(estadoDaView.EDITANDO.toString())){
				lancamentoService.salvar(lancamento);
				FacesUtil.exibirMensagemSucesso("Alteração feita com sucesso!");
				viewAtiva=estadoDaView.LISTANDO.toString();
				reset();
				return null;
			}
			Long timestamp = System.currentTimeMillis();
			String parcela = "";
			String obs= ocorrencias==1?"u"+timestamp:"r"+timestamp;	
			if(repetir){
				Lancamento lancamentoTemp = lancamento;
				for (int i = 0; i < ocorrencias; i++) {
					parcela="("+(i+1)+"/"+ocorrencias+")";
					lancamentoTemp = new Lancamento(null, lancamento.getTipoLancamento(), lancamento.getStatusLancamento(), 
							lancamento.getValor(), lancamento.getValorDesconto(), lancamento.getValorAcrescimo(), lancamento.getValorPago(), 
							lancamento.getDataPagamento(), DataUtil.geraDataVencimentoParcela(lancamento.getDataVencimento(), i, frequenciaDeLancamentos), 
							lancamento.getDescricao(), obs+parcela, null, null, null, lancamento.getPessoa(), lancamento.getCategoria(), lancamento.getConta());
					//Se marcar como pagar, demais pagamentos vão como Pendente
					if(pago){
						if(i==0){
							lancamentoTemp.setStatusLancamento(StatusDeLancamento.Pago);
						}else{
							lancamentoTemp.setStatusLancamento(StatusDeLancamento.Pendente);
							lancamentoTemp.setDataPagamento(null);
						}
					}
					//Confere se é para repetir Acrescimos e Descontos
					if(repetirAcrescimosDescontos){
						if(i>0){
							lancamentoTemp.setValorAcrescimo(null);
							lancamentoTemp.setValorDesconto(null);	
						}
					}
					lancamentoService.salvar(lancamentoTemp);//Salvar cada lançamento repetido
				}
			}else{
				lancamento.setObservacao(obs);
				lancamento.setStatusLancamento(pago ? StatusDeLancamento.Pago:StatusDeLancamento.Pendente);
				lancamentoService.salvar(lancamento);//Salvar lançamento único
			}
			FacesUtil.exibirMensagemSucesso("Lançamento "+obs+" salvo com sucesso!");
			reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
		return null;
	}
	
	public void novo() {
		reset();
		carregaListas();
		viewAtiva=estadoDaView.INSERINDO.toString();
    } 
	
	public void editar(Lancamento lancamento){
		if(lancamento.getId()!=null){
			carregaListas();
			this.lancamento=lancamento;
			viewAtiva=estadoDaView.EDITANDO.toString();
		}
	}
	/*Função vai ficar desabilitada, porque não vamos excluir, somente cancelar*/
	public void excluir(Lancamento lancamento) {
		try {
			lancamentoService.excluir(lancamento);
			FacesUtil.exibirMensagemSucesso("Excluído com sucesso!");
			reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
	}
	
	/*############# FUNÇÕES PRIVATE #############*/
	private void reset(){
		lancamento = new Lancamento();
		lancamento.setTipoLancamento(TipoDeLancamento.ENTRADA);
		lancamento.setDataVencimento(new Date());
		lancamento.setStatusLancamento(StatusDeLancamento.Pendente);
		frequenciaDeLancamentos=FrequenciaDeLancamento.Mensal;
	    ocorrencias=1;
		repetir=false;
		pago=false;
		listaLancamentosSelecionados.clear();
		this.getListaLancamentosSelecionados();
		viewAtiva=estadoDaView.LISTANDO.toString();
		motivoCancelar=null;
	}
	
	private void carregaListas(){
		if(listaCategorias.isEmpty()){
			listaCategorias=categoriaService.listaDeCategorias();
		}
		if(listaPessoas.isEmpty()){
			listaPessoas=pessoaService.listaDePessoas();
		}
		if(listaContas.isEmpty()){
			listaContas=contaService.listaDeContas();
		}
	}
	
	/*####### FUNÇÕES CANCELAR LANÇAMENTOS SELECIONADOS ##########*/
	/*Função que muda o status para canceldo dos lançamentos selecionados*/
	public void cancelar() {
		for (Lancamento l : listaLancamentosSelecionados) {
			l.setMotivoEstorno(this.motivoCancelar);
			l.setStatusLancamento(StatusDeLancamento.Pendente);
			l.setDataPagamento(null);
			lancamentoService.salvar(l);
			FacesUtil.exibirMensagemSucesso("ID "+l.getId()+" OBS:"+l.getObservacao()+" Valor: R$ "+l.getValorPago());
		}
		FacesUtil.exibirMensagemSucesso("Estornado com sucesso");
		reset();
    }
	
	/*Função que altera o estado da view durante o cancelamento dos lançamentos selecionados*/
	public void cancelando(){
		viewAtiva=estadoDaView.DETALHANDO.toString();

	}
	
	/*####### FUNÇÕES PAGAR LANÇAMENTOS SELECIONADOS ##########*/
	/*Função que muda o status para pago dos lançamentos selecionados*/
	public void pagar(){
		System.out.println("ContasReceberBean.pagar()");
		for (Lancamento l : listaLancamentosSelecionados) {
			l.setStatusLancamento(StatusDeLancamento.Pago);
			l.setDataPagamento(new Date());
			l.setMotivoCancelamento(null);
			lancamentoService.salvar(l);
			FacesUtil.exibirMensagemSucesso("ID "+l.getId()+" OBS:"+l.getObservacao()+" Valor: R$ "+l.getValorPago());
		}
		FacesUtil.exibirMensagemSucesso("Pagos com data de hoje");
		reset();	
	}
	
	/*####### FUNÇÕES OUTROS ##########*/
	/*Função que valida se a data e vencimento esta em atrazo em relaçao a data de hoje*/
	public boolean verificaLancamentoVencido(Date dtVencimento,StatusDeLancamento status){
		if(status==StatusDeLancamento.Pendente){
			//Se a data de hoje for maior , return true
			if(!DataUtil.comparaDataInicialDataFinal(dtVencimento, new Date())){
				return true;
			}	
		}
		return false;
	}
	/*Função que reset os componentes e coloca a view selecionada como Listando*/
	public void voltar() {
		reset();
    } 
	
	/*####### FUNÇÕES FILTRO ##########*/
	/*Função que altera o estado da view ,deixando o filtro disponível*/
	public void filtrando(){
		viewAtiva=estadoDaView.FILTRO.toString();
	}
	public void filtroReset(){
		filtro.setFiltroTipoData(FiltroTipoData.Data_de_pagamento);
		filtro.setFiltroTipoLancamento(FiltroTipoLancamento.Todos);
		filtro.setFitroStatus(FiltroStatus.Somente_pagos);
		filtro.setFitroData(FiltroData.Sem_filtro_de_data);
		filtro.setTermoParaBusca(null);
		viewAtiva=estadoDaView.LISTANDO.toString();
	}
	/**Função filtrar, somente para validar o filtro, o resolver o bug do primeiro registro*/
	public void filtrar(){
		this.filtro.setBtnFiltro(true);//pela btn do filtro
	}
	/*############# FIM - FUNÇÕES #############*/
	
	
	/*####### ENUMS  e OUTROS ##########*/
	public FrequenciaDeLancamento[] getFrequenciaDeLancamentoEnum() {
		return FrequenciaDeLancamento.values();
	}
	public Integer [] getNumeroParaRepetir(){
		Integer[] quantidades={2,3,4,5,6,7,8,9,10,11,12,24,36};
		return quantidades;
	}
	public FiltroData[] getFiltroDataEnums() {
		return FiltroData.values();
	}
	public FiltroStatus[] getFiltroStatusEnums(){
		FiltroStatus[] arrayStatus = {FiltroStatus.Somente_pagos,FiltroStatus.Somente_cancelados};
		return arrayStatus;
	}
	public FiltroTipoData[] getFiltroTipoDataEnums() {
		return FiltroTipoData.values();
	}
	public FiltroTipoLancamento[] getFiltroTipoLancamentoEnums() {
		return FiltroTipoLancamento.values();
	}
	
	/*####### GETS E SETS##########*/
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public String getViewAtiva() {
		return viewAtiva;
	}
	public void setViewAtiva(String viewAtiva) {
		this.viewAtiva = viewAtiva;
	}
	public FrequenciaDeLancamento getFrequenciaDeLancamentos() {
		return frequenciaDeLancamentos;
	}
	public void setFrequenciaDeLancamentos(FrequenciaDeLancamento frequenciaDeLancamentos) {
		this.frequenciaDeLancamentos = frequenciaDeLancamentos;
	}
	public Integer getOcorrencias() {
		return ocorrencias;
	}
	public void setOcorrencias(Integer ocorrencias) {
		this.ocorrencias = ocorrencias;
	}
	public boolean isRepetir() {
		return repetir;
	}
	public void setRepetir(boolean repetir) {
		this.repetir = repetir;
	}
	public boolean isRepetirAcrescimosDescontos() {
		return repetirAcrescimosDescontos;
	}
	public void setRepetirAcrescimosDescontos(boolean repetirAcrescimosDescontos) {
		this.repetirAcrescimosDescontos = repetirAcrescimosDescontos;
	}
	public boolean isPago() {
		pago=(lancamento.getStatusLancamento()==StatusDeLancamento.Pago ? true:false);
		return pago;
	}
	public void setPago(boolean pago) {
		lancamento.setStatusLancamento(pago ? StatusDeLancamento.Pago:StatusDeLancamento.Pendente);
		this.pago = pago;
	}
	/*####### GETS DE PAGINAÇÃO LAZY DATATABLE  ##########*/
	public FiltroLancamento getFiltro() {
		return filtro;
	}
	public LazyDataModel<Lancamento> getModel() {
		return model;
	}
	public BigDecimal getSomaTotal() {
		return somaTotal;
	}
	public BigDecimal getSomaTotalPago() {
		return somaTotalPago;
	}

	/*####### GETS DE LISTAS  ##########*/
	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}
	public List<Conta> getListaContas() {
		return listaContas;
	}
	/*####### FIM - GETS DE LISTAS  ##########*/
	
	/*############# CANCELAR GETS e SETS #############*/
	public List<Lancamento> getListaLancamentosSelecionados() {
		return listaLancamentosSelecionados;
	}
	public void setListaLancamentosSelecionados(List<Lancamento> listaLancamentosSelecionados) {
		this.listaLancamentosSelecionados = listaLancamentosSelecionados;
	}
	public String getMotivoCancelar() {
		return motivoCancelar;
	}
	public void setMotivoCancelar(String motivoCancelar) {
		this.motivoCancelar = motivoCancelar;
	}
	/*############# FIM - CANCELAR GETS e SETS #############*/


}
