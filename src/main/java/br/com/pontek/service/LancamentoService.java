package br.com.pontek.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.pontek.model.Lancamento;
import br.com.pontek.util.filtro.FiltroLancamento;

public interface LancamentoService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(Lancamento lancamento);
    List<Lancamento> salvarLista(List<Lancamento> lista);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(Lancamento lancamento);
    void excluirPorId(Integer lancamento_id);
    
/*### METODOS DE BUSCAR ###*/
    Lancamento buscar(Integer lancamento_id);
    List<Lancamento> listaDeMovimentos();
    
/*### Metodos de PAGINAÇÃO LAZY DATATABLE ###*/
    List<Lancamento> filtrados(FiltroLancamento filtro);
	Integer quantidadeFiltrados(FiltroLancamento filtro);

	BigDecimal somaEntradaPago(FiltroLancamento filtro);
	BigDecimal somaSaidaPago(FiltroLancamento filtro);
	BigDecimal somaSaldoAnterior(FiltroLancamento filtro);
	
	//conta pagar e receber
	BigDecimal somaValor(FiltroLancamento filtro);
}
