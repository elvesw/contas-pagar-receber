package br.com.pontek.util.filtro;

import br.com.pontek.enums.FiltroData;
import br.com.pontek.enums.FiltroStatus;
import br.com.pontek.enums.FiltroTipoData;
import br.com.pontek.enums.FiltroTipoLancamento;

public class FiltroLancamento extends FiltroBaseAbstract{

	private static final long serialVersionUID = 1L;
	private String termoParaBusca;
	private FiltroData fitroData;
	private FiltroStatus fitroStatus;
	private FiltroTipoData filtroTipoData;
	private FiltroTipoLancamento filtroTipoLancamento;
	
	private boolean  btnFiltro = false;//PARA BUG DO PRIMEIRO REGISTRO DA LISTA
	
	/* ########### CONSTRUTORES ###########*/
	public FiltroLancamento(FiltroData fitroData, FiltroStatus fitroStatus, FiltroTipoData filtroTipoData,
			FiltroTipoLancamento filtroTipoLancamento) {
		this.fitroData = fitroData;
		this.fitroStatus = fitroStatus;
		this.filtroTipoData = filtroTipoData;
		this.filtroTipoLancamento = filtroTipoLancamento;
	}
	public FiltroLancamento(FiltroData fitroData, FiltroStatus fitroStatus, FiltroTipoData filtroTipoData) {
		this.fitroData = fitroData;
		this.fitroStatus = fitroStatus;
		this.filtroTipoData = filtroTipoData;
	}
	public FiltroLancamento() {

	}

	public String getTermoParaBusca() {
		return termoParaBusca;
	}
	public void setTermoParaBusca(String termoParaBusca) {
		this.termoParaBusca = termoParaBusca;
	}
	public FiltroData getFitroData() {
		return fitroData;
	}
	public void setFitroData(FiltroData fitroData) {
		this.fitroData = fitroData;
	}
	public FiltroStatus getFitroStatus() {
		return fitroStatus;
	}
	public void setFitroStatus(FiltroStatus fitroStatus) {
		this.fitroStatus = fitroStatus;
	}
	public FiltroTipoData getFiltroTipoData() {
			//Resolver a de cancelados com data de pagamento que claro é null nesse caso
			if((fitroStatus.equals(FiltroStatus.Somente_cancelados)&&(filtroTipoData.equals(FiltroTipoData.Data_de_pagamento)))){
				filtroTipoData=FiltroTipoData.Data_de_vencimento;
			}
		return filtroTipoData;
	}
	public void setFiltroTipoData(FiltroTipoData filtroTipoData) {
		this.filtroTipoData = filtroTipoData;
	}
	public FiltroTipoLancamento getFiltroTipoLancamento() {
		return filtroTipoLancamento;
	}
	public void setFiltroTipoLancamento(FiltroTipoLancamento filtroTipoLancamento) {
		this.filtroTipoLancamento = filtroTipoLancamento;
	}
	public boolean isBtnFiltro() {
		return btnFiltro;
	}
	public void setBtnFiltro(boolean btnFiltro) {
		this.btnFiltro = btnFiltro;
	}
}
