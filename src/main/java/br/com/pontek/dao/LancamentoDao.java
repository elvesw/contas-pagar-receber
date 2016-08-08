package br.com.pontek.dao;

import java.math.BigDecimal;
import java.util.List;

import br.com.pontek.model.Conta;
import br.com.pontek.model.Lancamento;
import br.com.pontek.util.filtro.FiltroLancamento;

public interface LancamentoDao extends AbstractDao<Lancamento, Integer> {


	/*PAGINAÇÃO LAZY DATATABLE*/
	List<Lancamento> filtrados(FiltroLancamento filtro);
	Integer quantidadeFiltrados(FiltroLancamento filtro);

	//caixa
	BigDecimal somaEntradaPago(FiltroLancamento filtro);
	BigDecimal somaSaidaPago(FiltroLancamento filtro);
	BigDecimal somaSaldoAnterior(FiltroLancamento filtro);
	
	//conta pagar e receber
	BigDecimal somaValor(FiltroLancamento filtro);
	
	//Conta
	BigDecimal saldoPorConta(Conta conta);
	
}
