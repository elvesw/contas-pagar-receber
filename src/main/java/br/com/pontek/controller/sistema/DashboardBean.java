package br.com.pontek.controller.sistema;

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

import br.com.pontek.enums.PerfilDePessoa;
import br.com.pontek.service.entidades.PessoaService;
import br.com.pontek.service.financeiro.LancamentoService;
import br.com.pontek.util.filtro.FiltroPessoa;
import br.com.pontek.util.jpa.LancamentosPeriodo;

@ManagedBean(name = "dashboardBean")
@Controller
@Scope("view")
public class DashboardBean {

	@Autowired
	LancamentoService lancamentoService;
	@Autowired
	PessoaService pessoaService;
	
	//Quantidades de cliente ativos
	private Integer qtdClientesAtivos=0;
	
	//Histórico seis meses
	private List<LancamentosPeriodo> listaSomas=new ArrayList<LancamentosPeriodo>();
	private BarChartModel graficoBarRecebimentos;
	private BarChartModel graficoBarPagamentos;

	public DashboardBean() {
	}

	@PostConstruct
	public void init() {
		//Quantidades de cliente ativos
		qtdClientesAtivos=pessoaService.quantidadeFiltrados(new FiltroPessoa(null, PerfilDePessoa.Clientes));
		//Histórico seis meses
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
	     graficoBarRecebimentos.setBarMargin(15);
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
	     graficoBarPagamentos.setBarMargin(15);
	     graficoBarPagamentos.setDatatipFormat("<span style='display:none;'>%s</span><span>%s</span>");
	     Axis yAxis = graficoBarPagamentos.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setTickFormat("R$ %.2f");
	}
	

	/*########### GETS E SETS ###########*/
	public BarChartModel getGraficoBarRecebimentos() {
		return graficoBarRecebimentos;
	}
	public BarChartModel getGraficoBarPagamentos() {
		return graficoBarPagamentos;
	}
	public Integer getQtdClientesAtivos() {
		return qtdClientesAtivos;
	}

}
