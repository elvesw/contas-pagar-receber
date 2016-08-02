package br.com.pontek.dao;

import java.math.BigDecimal;
import java.util.List;

import br.com.pontek.model.Lancamento;
import br.com.pontek.util.filtro.FiltroLancamento;

public interface LancamentoDao extends AbstractDao<Lancamento, Integer> {


	/*PAGINAÇÃO LAZY DATATABLE*/
	List<Lancamento> filtrados(FiltroLancamento filtro);
	Integer quantidadeFiltrados(FiltroLancamento filtro);
	//
	BigDecimal somaTotal(FiltroLancamento filtro);
	BigDecimal somaTotalPago(FiltroLancamento filtro);
	
}
