package br.com.pontek.dao.financeiro;

import java.util.List;

import br.com.pontek.dao.AbstractDao;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.util.filtro.FiltroCategoria;

public interface CategoriaDao extends AbstractDao<Categoria, Integer> {

	List<Categoria> listaCategoriasPorTipo(TipoDeLancamento tipo);
	
	/*PAGINA��O LAZY DATATABLE*/
	List<Categoria> filtrados(FiltroCategoria filtro);
	Integer quantidadeFiltrados(FiltroCategoria filtro);
}
