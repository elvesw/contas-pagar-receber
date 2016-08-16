package br.com.pontek.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import br.com.pontek.model.Categoria;
import br.com.pontek.model.Conta;
import br.com.pontek.model.Lancamento;
import br.com.pontek.util.filtro.FiltroLancamento;
import br.com.pontek.util.jpa.LancamentosPeriodo;

public interface LancamentoDao extends AbstractDao<Lancamento, Integer> {


	/*PAGINAÇÃO LAZY DATATABLE*/
	List<Lancamento> filtrados(FiltroLancamento filtro);
	Integer quantidadeFiltrados(FiltroLancamento filtro);

	//bean Caixa
	BigDecimal somaEntradaPago(FiltroLancamento filtro);
	BigDecimal somaSaidaPago(FiltroLancamento filtro);
	BigDecimal somaSaldoAnterior(FiltroLancamento filtro);
	
	//bean ContasPagar ContasReceber
	BigDecimal somaValor(FiltroLancamento filtro);
	
	//Conta
	BigDecimal saldoPorConta(Conta conta);
	
	//bean Dashboard
/*	Map<Categoria,BigDecimal> saldoMesPorCategoriaEntrada();
	Map<Categoria,BigDecimal> saldoMesPorCategoriaSaida();*/
	
	List<LancamentosPeriodo> historicoSeisMeses();
	
}
