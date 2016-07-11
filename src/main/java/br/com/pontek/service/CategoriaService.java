package br.com.pontek.service;

import java.util.List;

import br.com.pontek.model.Categoria;

public interface CategoriaService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(Categoria categoria);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(Categoria categoria);
    void excluirPorId(Integer categoria_id);
    
/*### METODOS DE BUSCAR ###*/
    Categoria buscar(Integer categoria_id);
    List<Categoria> listaDeCategorias();
}
