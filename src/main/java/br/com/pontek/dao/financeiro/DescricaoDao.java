package br.com.pontek.dao.financeiro;

import java.util.List;

import br.com.pontek.dao.AbstractDao;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Descricao;
import br.com.pontek.util.filtro.FiltroDescricao;

public interface DescricaoDao  extends AbstractDao<Descricao, Integer> {

	  List<String> listaPorNomeTipoDeLancamento(String nome, TipoDeLancamento tipoDeLancamento);
	  Descricao buscarPorNomeTipolancamento(String nome, TipoDeLancamento tipoDeLancamento);
	  
	  /*PAGINAÇÃO LAZY DATATABLE*/
		List<Descricao> filtrados(FiltroDescricao filtro);
		Integer quantidadeFiltrados(FiltroDescricao filtro);
}
