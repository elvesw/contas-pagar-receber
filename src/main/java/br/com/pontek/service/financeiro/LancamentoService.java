package br.com.pontek.service.financeiro;

import java.math.BigDecimal;
import java.util.List;

import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.model.financeiro.Conta;
import br.com.pontek.model.financeiro.Lancamento;
import br.com.pontek.util.filtro.FiltroLancamento;
import br.com.pontek.util.jpa.LancamentosPeriodo;

public interface LancamentoService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(Lancamento lancamento);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(Lancamento lancamento);
    void excluirPorId(Integer lancamento_id);
    
/*### METODOS DE BUSCAR ###*/
    Lancamento buscar(Integer lancamento_id);
    List<Lancamento> listaDeMovimentos();
    List<String> listaDescricoesLancamentos(String descricao);
    
/*### Metodos de PAGINAÇÃO LAZY DATATABLE ###*/
    List<Lancamento> filtrados(FiltroLancamento filtro);
	Integer quantidadeFiltrados(FiltroLancamento filtro);

	BigDecimal somaEntradaPago(FiltroLancamento filtro);
	BigDecimal somaSaidaPago(FiltroLancamento filtro);
	BigDecimal somaSaldoAnterior(FiltroLancamento filtro);
	
	//conta pagar e receber
	BigDecimal somaValor(FiltroLancamento filtro);
	
	List<LancamentosPeriodo> historicoSeisMeses();
	
	//Faz o confere antes de excluir
	boolean existeCategoriaEmLancamentos(Categoria categoria);
	boolean existeContaEmLancamentos(Conta conta);
	boolean existePessoaEmLancamentos(Pessoa pessoa);
}
