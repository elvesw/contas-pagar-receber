package br.com.pontek.service.financeiro;

import java.util.List;

import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.util.filtro.FiltroCategoria;

public interface CategoriaService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(Categoria categoria);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(Categoria categoria);
    void excluirPorId(Integer categoria_id);
    
/*### METODOS DE BUSCAR ###*/
    Categoria buscar(Integer categoria_id);
    List<Categoria> listaDeCategorias();
    
    List<Categoria> listaCategoriasPorTipo(TipoDeLancamento tipo);
    
	/*PAGINAÇÃO LAZY DATATABLE*/
	List<Categoria> filtrados(FiltroCategoria filtro);
	Integer quantidadeFiltrados(FiltroCategoria filtro);
}
