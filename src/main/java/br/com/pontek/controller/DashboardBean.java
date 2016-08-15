package br.com.pontek.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.enums.FiltroData;
import br.com.pontek.enums.FiltroStatus;
import br.com.pontek.enums.FiltroTipoData;
import br.com.pontek.enums.FiltroTipoLancamento;
import br.com.pontek.model.Categoria;
import br.com.pontek.model.Lancamento;
import br.com.pontek.service.LancamentoService;
import br.com.pontek.util.filtro.FiltroLancamento;

@ManagedBean(name = "dashboardBean")
@Controller
@Scope("view")
public class DashboardBean {

	@Autowired
	LancamentoService lancamentoService;

	private BigDecimal somaValorPagoEntrada=BigDecimal.ZERO;
	private BigDecimal somaValorTotalEntrada=BigDecimal.ZERO;
	private BigDecimal somaValorPagoSaida=BigDecimal.ZERO;
	private BigDecimal somaValorTotalSaida=BigDecimal.ZERO;
	private Integer percentualValorPagoEntrada=0;
	private Integer percentualValorPagoSaida=0;


	public DashboardBean() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		somaValorPagoEntrada=lancamentoService.somaEntradaPago(new FiltroLancamento(FiltroData.Esse_mês, FiltroStatus.Somente_pagos, FiltroTipoData.Data_de_vencimento, FiltroTipoLancamento.Somente_entrada));
		somaValorTotalEntrada=lancamentoService.somaValor(new FiltroLancamento(FiltroData.Esse_mês, FiltroStatus.Pagos_e_pendentes, FiltroTipoData.Data_de_vencimento, FiltroTipoLancamento.Somente_entrada));
		somaValorPagoSaida=lancamentoService.somaSaidaPago(new FiltroLancamento(FiltroData.Esse_mês, FiltroStatus.Somente_pagos, FiltroTipoData.Data_de_vencimento, FiltroTipoLancamento.Somente_saída));
		somaValorTotalSaida=lancamentoService.somaValor(new FiltroLancamento(FiltroData.Esse_mês, FiltroStatus.Pagos_e_pendentes, FiltroTipoData.Data_de_vencimento, FiltroTipoLancamento.Somente_saída));
		if(!somaValorTotalEntrada.equals(BigDecimal.ZERO))
		percentualValorPagoEntrada=somaValorPagoEntrada.divide(somaValorTotalEntrada,new MathContext(2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100)).intValue();
		if(!somaValorTotalSaida.equals(BigDecimal.ZERO))
		percentualValorPagoSaida=somaValorPagoSaida.divide(somaValorTotalSaida,new MathContext(2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100)).intValue();
		System.err.println("somaValorPagoEntrada : "+somaValorPagoEntrada);
		System.err.println("somaValorTotalEntrada : "+somaValorTotalEntrada);
		System.err.println("somaValorPagoSaida : "+somaValorPagoSaida);
		System.err.println("somaValorTotalSaida : "+somaValorTotalSaida);
	}


	/*private void gerarGraficos() {
		Map<Categoria,BigDecimal> somasPorCategoria;
		for (Map.Entry<Categoria,BigDecimal> map : somasPorCategoria.entrySet()) {
			  System.out.println("Categoria: "+map.getKey()+" valor: "+map.getValue());
		}
		pizzaEntradas.setTitle("Entradas");
		pizzaSaidas.setTitle("Saidas");
	}*/

	/*########### GETS E SETS ###########*/
	public BigDecimal getSomaValorPagoEntrada() {
		return somaValorPagoEntrada;
	}
	public BigDecimal getSomaValorTotalEntrada() {
		return somaValorTotalEntrada;
	}
	public BigDecimal getSomaValorPagoSaida() {
		return somaValorPagoSaida;
	}
	public BigDecimal getSomaValorTotalSaida() {
		return somaValorTotalSaida;
	}
	public Integer getPercentualValorPagoEntrada() {
		return percentualValorPagoEntrada;
	}
	public Integer getPercentualValorPagoSaida() {
		return percentualValorPagoSaida;
	}
}
