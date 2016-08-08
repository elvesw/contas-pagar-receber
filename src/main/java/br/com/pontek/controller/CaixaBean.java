package br.com.pontek.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.model.Lancamento;
import br.com.pontek.service.LancamentoService;
import br.com.pontek.util.DataUtil;
import br.com.pontek.util.FacesUtil;
import br.com.pontek.util.filtro.FiltroLancamento;

@ManagedBean(name = "caixaBean")
@Controller
@Scope("view")
public class CaixaBean extends AbstractBean{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired private LancamentoService lancamentoService;
	
	/*########### LAZY DATATABLE ##############*/
	private FiltroLancamento filtro= new FiltroLancamento(FiltroData.Sem_filtro_de_data, FiltroStatus.Somente_pagos, 
															FiltroTipoData.Data_de_pagamento, FiltroTipoLancamento.Todos);
	private LazyDataModel<Lancamento> model;
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	private BigDecimal somaSaldoAnterior=BigDecimal.ZERO;
	private BigDecimal somaEntradaPago=BigDecimal.ZERO;
	private BigDecimal somaSaidaPago=BigDecimal.ZERO;
	private BigDecimal saldoFinal=BigDecimal.ZERO;
	/*########### FIM - LAZY DATATABLE ##############*/
	
	
	/*############# ESTORNAR LANÇAMENTOS #############*/
	private List<Lancamento> listaLancamentosSelecionados = new ArrayList<>();
	private String motivoEstornar;
	/*############# FIM - ESTORNAR LANÇAMENTOS #############*/

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
				List<Lancamento> listaTemp = lancamentoService.filtrados(filtro);
				
				//SOMAS
				somaEntradaPago=lancamentoService.somaEntradaPago(filtro);
				somaSaidaPago=lancamentoService.somaSaidaPago(filtro);
				saldoFinal=BigDecimal.ZERO;
				saldoFinal=saldoFinal.add(somaEntradaPago).subtract(somaSaidaPago);

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
	
	/*############# FUNÇÕES PRIVATE #############*/
	private void reset(){
		listaLancamentosSelecionados.clear();
		this.getListaLancamentosSelecionados();
		viewAtiva=estadoDaView.LISTANDO.toString();
		motivoEstornar=null;
	}
	
	/*####### FUNÇÕES ESTORNAR LANÇAMENTOS SELECIONADOS ##########*/
	/*Função que muda o status para canceldo dos lançamentos selecionados*/
	public void estornar() {
		for (Lancamento l : listaLancamentosSelecionados) {
			l.setMotivoEstorno(this.motivoEstornar);
			l.setStatusLancamento(StatusDeLancamento.Pendente);
			l.setDataPagamento(null);
			lancamentoService.salvar(l);
			FacesUtil.exibirMensagemSucesso("ID "+l.getId()+" OBS:"+l.getObservacao()+" Valor: R$ "+l.getValorPago());
		}
		FacesUtil.exibirMensagemSucesso("Estornado com sucesso");
		reset();
    }
	
	/*Função que altera o estado da view durante o estorno dos lançamentos selecionados*/
	public void estornando(){
		viewAtiva=estadoDaView.DETALHANDO.toString();

	}
	
	/*####### FUNÇÕES PAGAR LANÇAMENTOS SELECIONADOS ##########*/
	
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

	public String getViewAtiva() {
		return viewAtiva;
	}
	public void setViewAtiva(String viewAtiva) {
		this.viewAtiva = viewAtiva;
	}
	/*####### GETS DE PAGINAÇÃO LAZY DATATABLE  ##########*/
	public FiltroLancamento getFiltro() {
		return filtro;
	}
	public LazyDataModel<Lancamento> getModel() {
		return model;
	}
	public BigDecimal getSomaSaldoAnterior() {
		return somaSaldoAnterior;
	}
	public BigDecimal getSomaEntradaPago() {
		return somaEntradaPago;
	}
	public BigDecimal getSomaSaidaPago() {
		return somaSaidaPago;
	}
	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	/*############# ESTORNAR GETS e SETS #############*/
	public List<Lancamento> getListaLancamentosSelecionados() {
		return listaLancamentosSelecionados;
	}
	public void setListaLancamentosSelecionados(List<Lancamento> listaLancamentosSelecionados) {
		this.listaLancamentosSelecionados = listaLancamentosSelecionados;
	}
	public String getMotivoEstornar() {
		return motivoEstornar;
	}
	public void setMotivoEstornar(String motivoEstornar) {
		this.motivoEstornar = motivoEstornar;
	}
	/*############# FIM - ESTORNAR GETS e SETS #############*/


}
