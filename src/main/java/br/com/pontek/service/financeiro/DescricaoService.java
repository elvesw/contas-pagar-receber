package br.com.pontek.service.financeiro;

import java.util.List;

import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Descricao;
import br.com.pontek.util.filtro.FiltroDescricao;

public interface DescricaoService {
	/*###METODOS BÁSICOS###*/
    void salvar(Descricao descricao);
    void excluir(Descricao descricao);
    Descricao buscarPorNomeTipolancamento(String nome, TipoDeLancamento tipoDeLancamento);
    List<Descricao> listaTodos();
    List<String> listaPorNomeTipoDeLancamento(String nome, TipoDeLancamento tipoDeLancamento);

    
	/*PAGINAÇÃO LAZY DATATABLE*/
	List<Descricao> filtrados(FiltroDescricao filtro);
	Integer quantidadeFiltrados(FiltroDescricao filtro);
}
