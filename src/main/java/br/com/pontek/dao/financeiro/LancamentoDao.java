package br.com.pontek.dao.financeiro;

import java.math.BigDecimal;
import java.util.List;

import br.com.pontek.dao.AbstractDao;
import br.com.pontek.model.financeiro.Conta;
import br.com.pontek.model.financeiro.Lancamento;
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
	List<LancamentosPeriodo> historicoSeisMeses();
	
}
