package br.com.pontek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.service.LancamentoService;
import br.com.pontek.util.jpa.LancamentosPeriodo;

@ManagedBean(name = "dashboardBean")
@Controller
@Scope("view")
public class DashboardBean {

	@Autowired
	LancamentoService lancamentoService;
	
	private List<LancamentosPeriodo> listaSomas=new ArrayList<LancamentosPeriodo>();
	private BarChartModel graficoBarRecebimentos;
	private BarChartModel graficoBarPagamentos;

	public DashboardBean() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		listaSomas=lancamentoService.historicoSeisMeses();
		gerarGraficoBarEntradas();
		gerarGraficoBarSaidas();
	}

	private void gerarGraficoBarEntradas(){
		graficoBarRecebimentos=new BarChartModel();
		 ChartSeries pagosEntrada = new ChartSeries("Total pago");
		 ChartSeries pendenteEntrada = new ChartSeries("Total pendente");
		 
		 for (LancamentosPeriodo p : listaSomas) {
	        pagosEntrada.set(p.getMesReferencia(),p.getSomaEntradasSomentePago());
	        pendenteEntrada.set(p.getMesReferencia(),p.getSomaEntradasSomentePendente());
		 }
		 graficoBarRecebimentos.addSeries(pagosEntrada);
		 graficoBarRecebimentos.addSeries(pendenteEntrada);
		 graficoBarRecebimentos.setTitle("Recebimentos");
	     graficoBarRecebimentos.setLegendPosition("n");
	     graficoBarRecebimentos.setSeriesColors("58B158,FFCB7D");
	     graficoBarRecebimentos.setStacked(true);
	     graficoBarRecebimentos.setBarMargin(30);
	     graficoBarRecebimentos.setDatatipFormat("<span style='display:none;'>%s</span><span>%s</span>");
	     Axis yAxis = graficoBarRecebimentos.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setTickFormat("R$ %.2f");
	}
	private void gerarGraficoBarSaidas(){
		graficoBarPagamentos=new BarChartModel();
		 ChartSeries pagosEntrada = new ChartSeries("Total pago");
		 ChartSeries pendenteEntrada = new ChartSeries("Total pendente");
		 
		 for (LancamentosPeriodo p : listaSomas) {
	        pagosEntrada.set(p.getMesReferencia(),p.getSomaSaidasSomentePago());
	        pendenteEntrada.set(p.getMesReferencia(),p.getSomaSaidasSomentePendente());
		 }
		 graficoBarPagamentos.addSeries(pagosEntrada);
		 graficoBarPagamentos.addSeries(pendenteEntrada);
		 graficoBarPagamentos.setTitle("Pagamentos");
	     graficoBarPagamentos.setLegendPosition("n");
	     graficoBarPagamentos.setSeriesColors("0996DD,FFCB7D");
	     graficoBarPagamentos.setStacked(true);
	     graficoBarPagamentos.setBarMargin(30);
	     graficoBarPagamentos.setDatatipFormat("<span style='display:none;'>%s</span><span>%s</span>");
	     Axis yAxis = graficoBarPagamentos.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setTickFormat("R$ %.2f");
	}
 

	/*########### GETS E SETS ###########*/
	public BarChartModel getGraficoBarRecebimentos() {
		return graficoBarRecebimentos;
	}
	public void setGraficoBarRecebimentos(BarChartModel graficoBarRecebimentos) {
		this.graficoBarRecebimentos = graficoBarRecebimentos;
	}
	public BarChartModel getGraficoBarPagamentos() {
		return graficoBarPagamentos;
	}
	public void setGraficoBarPagamentos(BarChartModel graficoBarPagamentos) {
		this.graficoBarPagamentos = graficoBarPagamentos;
	}	
}
