package br.com.pontek.controller.financeiro;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import br.com.pontek.enums.FiltroData;
import br.com.pontek.enums.FiltroStatus;
import br.com.pontek.enums.FiltroTipoData;
import br.com.pontek.enums.FiltroTipoLancamento;
import br.com.pontek.enums.FrequenciaDeLancamento;
import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.exception.RelatorioException;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.model.financeiro.Conta;
import br.com.pontek.model.financeiro.Lancamento;
import br.com.pontek.service.entidades.PessoaService;
import br.com.pontek.service.financeiro.CategoriaService;
import br.com.pontek.service.financeiro.ContaService;
import br.com.pontek.service.financeiro.LancamentoService;
import br.com.pontek.util.DataUtil;
import br.com.pontek.util.filtro.FiltroLancamento;
import br.com.pontek.util.jsf.FacesUtil;
import br.com.pontek.util.report.LancamentoDataSource;
import br.com.pontek.util.report.RelatorioUtil;

@ManagedBean(name = "lancamentoBean")
@Controller
@Scope("view")
public class LancamentoBean extends AbstractBean{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired private LancamentoService lancamentoService;
	@Autowired private PessoaService pessoaService;
	@Autowired private CategoriaService categoriaService;
	@Autowired private ContaService contaService;
	/*########### PERFIL ##############*/
	private TipoDeLancamento tipoLancamentoPagina;
	private String tituloRelatorio="";
	private String nomePDFRelatorio="";
	/*########### LAZY DATATABLE ##############*/
	private FiltroLancamento filtro= new FiltroLancamento(FiltroData.Passado_mais_30_dias, 
														FiltroStatus.Somente_pendentes,
														FiltroTipoData.Data_de_vencimento);
	private LazyDataModel<Lancamento> model;
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	private BigDecimal somaTodosValorPago;
	private BigDecimal somaSomentePago;
	private String tituloDataTableFiltro="";
	/*########### FIM - LAZY DATATABLE ##############*/
	
	/*############# NOVO LAN�AMENTO #############*/
	private Lancamento lancamento = new Lancamento();
	private BigDecimal valorPago=BigDecimal.ZERO;//valor para fazer update na tela, o valor pago real vai ser por @prepersist
	private FrequenciaDeLancamento frequenciaDeLancamentos=FrequenciaDeLancamento.Mensal;
	private Integer ocorrencias=1;
	private boolean repetir=false;
	private boolean repetirAcrescimosDescontos=false;
	private boolean pago=false;
	/*############# FIM - NOVO LAN�AMENTO #############*/
	
	/*############# CANCELAR LAN�AMENTOS #############*/
	private List<Lancamento> listaLancamentosSelecionados = new ArrayList<>();
	private String motivoCancelar;
	/*############# FIM - CANCELAR LAN�AMENTOS #############*/
	
	/*############# LISTAS #############*/
	private List<Conta> listaContas  = new ArrayList<>();
	private List<Categoria> listaCategorias  = new ArrayList<>();
	private List<Pessoa> listaPessoas = new ArrayList<>();
	/*############# FIM - LISTAS #############*/
	
	/*############# GERA��O DE RELAT�RIO #############*/
	private FiltroLancamento filtroRelatorio = new FiltroLancamento();


	//CONSTRUTOR
	public LancamentoBean() {
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
				List<Lancamento> listaTemp = lancamentoService.filtrados(filtro);
				//Personalizando t�tulo no Datatable
				tituloDataTableFiltro=DataUtil.intervaloDeFiltroLancamento(filtro);
				//Relat�rio
				filtroRelatorio=filtro; 
				filtroRelatorio.setQuantidadeRegistros(getRowCount());
				filtroRelatorio.setPrimeiroRegistro(0);
				filtroRelatorio.setAscendente(true);
				//Somas
				somaSomentePago=lancamentoService.somaEntradaPago(filtro);
				somaTodosValorPago=lancamentoService.somaValor(filtro);
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
	
	/*############# FUN��ES GERAL #############*/
	
	
	/*############ 1� BLOCO FUN��ES PARA O DATATABLE #######*/
	/**Fun��o para listar no formato PDF no navegador para impress�o*/
	public String verPDF(ActionEvent actionEvent) throws Exception{
		List<Lancamento> listaTemp = lancamentoService.filtrados(filtroRelatorio);
		if(listaTemp.isEmpty()){
			FacesUtil.exibirMensagemErro("Lista vazia");
			return null;
		}
		try {
			Map<String, Object> parametros = new HashMap<String,Object>();
				parametros.put("titulo",tituloRelatorio);//perfil
				parametros.put("intervaloData",DataUtil.intervaloDeFiltroLancamento(filtroRelatorio));
			LancamentoDataSource lancamentoDS = new LancamentoDataSource();
				lancamentoDS.prepareDataSource(listaTemp);
			RelatorioUtil relatorio=new RelatorioUtil();
				relatorio.verPdfBrowser(lancamentoDS,parametros,"Lancamentos.jrxml",nomePDFRelatorio+DataUtil.ddMMyy(new Date()));
		} catch (Exception e) {
			 FacesUtil.exibirMensagemErro("Erro : "+ e.getMessage());
			 new RelatorioException("Erro ao gerar relat�rio :" +e);
		} 	
		return null;
	}
	
	
	/*####### FUN��ES PAGAR LAN�AMENTOS SELECIONADOS ##########*/
	/**Fun��o que muda o status para pago dos lan�amentos selecionados pelo multiSelect do datatable*/
	public void pagar(){
		BigDecimal somaTotalTemp=BigDecimal.ZERO;
		for (Lancamento l : listaLancamentosSelecionados) {
			l.setStatusLancamento(StatusDeLancamento.Pago);
			l.setDataPagamento(new Date());
			l.setMotivoCancelamento(null);
			lancamentoService.salvar(l);
			somaTotalTemp=somaTotalTemp.add(l.getValorPago());//Soma Tempor�ria
		}
		FacesUtil.exibirMensagemSucesso("Soma total: R$"+somaTotalTemp+ " foi pago com data de hoje.");
		reset();	
	}
	
	
	/*####### FUN��ES CANCELAR LAN�AMENTOS SELECIONADOS ##########*/
	/**Fun��o que muda o status para cancelado dos lan�amentos selecionados pelo multiSelect do datatable*/
	public void cancelar() {
		BigDecimal somaTotalTemp=BigDecimal.ZERO;
		for (Lancamento l : listaLancamentosSelecionados) {
			l.setMotivoCancelamento(this.motivoCancelar);
			l.setStatusLancamento(StatusDeLancamento.Cancelado);
			l.setDataPagamento(null);
			lancamentoService.salvar(l);
			somaTotalTemp=somaTotalTemp.add(l.getValorPago());//Soma Tempor�ria
		}
		FacesUtil.exibirMensagemSucesso("Soma total: R$"+somaTotalTemp+ " foram cancelados.");
		reset();
    }
	
	
	/**Fun��o que altera o estado da view durante o cancelamento dos lan�amentos selecionados pelo multiSelect do datatable*/
	public void cancelando(){
		viewAtiva=estadoDaView.DETALHANDO.toString();
	}
	
	
	/*####### FUN��ES PARA O FILTRO ##########*/
	/**Fun��o que altera o estado da view ,deixando o filtro dispon�vel*/
	public void filtrando(){
		viewAtiva=estadoDaView.FILTRO.toString();
	}
	
	
	/**Fun��o filtrar, somente para validar o filtro, o resolver o bug do primeiro registro*/
	public void filtrar(){
		this.filtro.setBtnFiltro(true);//pela btn do filtro
	}
	
	
	/**Reset filtro da view e altera o estado da view ,deixando o datatable dispon�vel*/
	public void filtroReset(){
		filtro.setFiltroTipoData(FiltroTipoData.Data_de_vencimento);
		filtro.setFitroStatus(FiltroStatus.Somente_pendentes);
		filtro.setFitroData(FiltroData.Passado_mais_30_dias);
		filtro.setTermoParaBusca(null);
		viewAtiva=estadoDaView.LISTANDO.toString();
	}
	/*############ FIM - 1� BLOCO FUN��ES PARA O DATATABLE #######*/
	
	
	
	
	/*############ 2� BLOCO FUN��ES PARA FORM DE CADASTRO #######*/
	/**Fun��o do autocomplete de nomes para o form cadastro*/	
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
	
	
	/**Fun��o que confere os objetos usados no cadastro e persiste no banco de dados um ou mais lan�amentos*/
	public String salvar() {
		/*Pode lan�ar um valor zerado, como se for uma bolsa, um desconto 100% mais que precisa ficar registrado*/
		try {
			if(lancamento.getValorDesconto()!=null){
				if(lancamento.getValorDesconto().compareTo(lancamento.getValor()) > 0){
					FacesUtil.exibirMensagemErro("N�o permitido, Valor de desconto � MAIOR que o valor principal");
					return null;
				}				
			}
			if(this.getValorPago().compareTo(BigDecimal.ZERO) < 0){
				FacesUtil.exibirMensagemErro("N�o permitido, Valor total � MENOR que zero");
				return null;
			}
			if(viewAtiva.equals(estadoDaView.EDITANDO.toString())){
				lancamentoService.salvar(lancamento);
				FacesUtil.exibirMensagemSucesso("Altera��o feita com sucesso!");
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
					//Se marcar como pagar, demais pagamentos v�o como Pendente
					if(pago){
						if(i==0){
							lancamentoTemp.setStatusLancamento(StatusDeLancamento.Pago);
						}else{
							lancamentoTemp.setStatusLancamento(StatusDeLancamento.Pendente);
							lancamentoTemp.setDataPagamento(null);
						}
					}
					//Confere se � para repetir Acrescimos e Descontos
					if(repetirAcrescimosDescontos){
						if(i>0){
							lancamentoTemp.setValorAcrescimo(null);
							lancamentoTemp.setValorDesconto(null);	
						}
					}
					lancamentoService.salvar(lancamentoTemp);//Salvar cada lan�amento repetido
				}
			}else{
				lancamento.setObservacao(obs);
				lancamento.setStatusLancamento(pago ? StatusDeLancamento.Pago:StatusDeLancamento.Pendente);
				lancamentoService.salvar(lancamento);//Salvar lan�amento �nico
			}
			FacesUtil.exibirMensagemSucesso("Lan�amento "+obs+" salvo com sucesso!");
			reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**Fun��o para inserir lan�amento, faz o reset dos componentes de cadastro,
	 *  carrega a listas e muda o estado da view para INSERINDO(Muda o t�tulo e mostra os componentes de repetir no cadastro)*/
	public void novo() {
		reset();
		carregaListas();
		viewAtiva=estadoDaView.INSERINDO.toString();
    } 
	
	
	/**Fun��o que recebe um lan�amento do datatable,
	 *  carrega a listas e muda o estado da view para EDITANDO(Muda o t�tulo e esconde os componentes de repetir no cadastro)
	 *  @param lancamento selecionado pelo datatable*/
	public void editar(Lancamento lancamento){
		if(lancamento.getId()!=null){
			carregaListas();
			this.lancamento=lancamento;
			viewAtiva=estadoDaView.EDITANDO.toString();
		}
	}
	
	
	/** Faz o reset dos objetos usados no cadastro*/
	private void reset(){
		valorPago=BigDecimal.ZERO;
		lancamento = new Lancamento();
		lancamento.setTipoLancamento(tipoLancamentoPagina);//perfil
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
	
	
	/**Carrega listas usadas no cadastro, n�o foi no PosConstruct por existir a 
	 * possibilidade de entrar na p�gina sem entrar no cadastro fazendo uma chamada desnecess�ria*/
	private void carregaListas(){
		if(listaCategorias.isEmpty()){
			listaCategorias=categoriaService.listaCategoriasPorTipo(tipoLancamentoPagina);//perfil
		}
		if(listaPessoas.isEmpty()){
			listaPessoas=pessoaService.listaDePessoas();
		}
		if(listaContas.isEmpty()){
			listaContas=contaService.listaTodos();
		}
	}
	/*############ FIM - 2� BLOCO PARA FORM DE CADASTRO #######*/
	
	
	/*############ 3� BLOCO FUN��ES OUTRAS  #######*/
	
	
	/*############# FUN�OES PARA PERFIL DA VIEW #############*/
	/**Aplica as configura��es conforme a chamada do preRenderView*/
	private void checkTipoPagina(){
		if(tipoLancamentoPagina.equals(TipoDeLancamento.ENTRADA)){
			filtro.setFiltroTipoLancamento(FiltroTipoLancamento.Somente_entrada);
			tituloRelatorio="Lan�amentos a receber";
			nomePDFRelatorio="ListaReceber";
			tituloDataTableFiltro=DataUtil.intervaloDeFiltroLancamento(filtro).replace("Lan�amentos ", " ");
		}else if(tipoLancamentoPagina.equals(TipoDeLancamento.SA�DA)){
			filtro.setFiltroTipoLancamento(FiltroTipoLancamento.Somente_sa�da);
			tituloRelatorio="Lan�amentos a pagar";
			nomePDFRelatorio="ListaPagar";
			tituloDataTableFiltro=DataUtil.intervaloDeFiltroLancamento(filtro).replace("Lan�amentos ", " ");
		}
	}
	
	
	/**Chamada do preRenderView para o bean diferenciar se o 
	 * TipoDeLancamento de lan�amentos da view � de ENTRADA ou SA�DA
	 * @param EntradaOuSaida  ENTRADA ou SA�DA*/
	public void tipoPagina(String EntradaOuSaida){
		 System.out.println("Valor EntradaOuSaida: "+EntradaOuSaida);
		tipoLancamentoPagina=TipoDeLancamento.valueOf(EntradaOuSaida);
		if(tipoLancamentoPagina!=null){
			 System.out.println("Valor tipoLancamentoPagina: "+tipoLancamentoPagina);
			 checkTipoPagina();
		}
	}
	
	
	/**Fun��o que valida se a data e vencimento esta em atrazo em rela�ao a data de hoje usada no datatable
	 * @param dtVencimento data de vencimento para comparar com a data de hoje
	 * @param status para verificar se o status � pendente */
	public boolean verificaLancamentoVencido(Date dtVencimento,StatusDeLancamento status){
		if(status==StatusDeLancamento.Pendente){
			//Se a data de hoje for maior , return true
			if(!DataUtil.comparaDataInicialDataFinal(dtVencimento, new Date())){
				return true;
			}	
		}
		return false;
	}
	
	
	/**Fun��o que faz o reset nos componentes e coloca o estado da view selecionada como Listando*/
	public void voltar() {
		reset();
    } 
	/*############ FIM - 3� BLOCO OUTRAS #######*/
	
	
	
	
	
	/*####### ENUMS  e OUTROS ##########*/
	public FrequenciaDeLancamento[] getFrequenciaDeLancamentoEnum() {
		return FrequenciaDeLancamento.values();
	}
	public Integer [] getNumeroParaRepetir(){
		Integer[] quantidades={2,3,4,5,6,7,8,9,10,11,12};
		return quantidades;
	}
	public FiltroData[] getFiltroDataEnums() {
		return FiltroData.values();
	}
	public FiltroStatus[] getFiltroStatusEnums(){
		FiltroStatus[] arrayStatus = {FiltroStatus.Somente_pendentes,FiltroStatus.Somente_pagos,FiltroStatus.Pagos_e_pendentes};
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
	public BigDecimal getValorPago(){
		if(lancamento.getValor()!=null)valorPago=lancamento.getValor();
		if(lancamento.getValorAcrescimo()!=null)valorPago=valorPago.add(lancamento.getValorAcrescimo());
		if(lancamento.getValorDesconto()!=null)valorPago=valorPago.subtract(lancamento.getValorDesconto());
		return valorPago;
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
	/*####### GETS DE PAGINA��O LAZY DATATABLE  ##########*/
	public FiltroLancamento getFiltro() {
		return filtro;
	}
	public LazyDataModel<Lancamento> getModel() {
		return model;
	}
	public BigDecimal getSomaTodosValorPago() {
		return somaTodosValorPago;
	}
	public BigDecimal getSomaSomentePago() {
		return somaSomentePago;
	}
	public String getTituloDataTableFiltro() {
		return tituloDataTableFiltro;
	}
	/*####### GETS DE LISTAS  ##########*/
	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}
	public List<Conta> getListaContas() {
		return listaContas;
	}
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
}
