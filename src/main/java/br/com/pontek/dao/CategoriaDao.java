package br.com.pontek.dao;

import java.util.List;

import br.com.pontek.model.Categoria;

public interface CategoriaDao extends AbstractDao<Categoria, Integer> {

	List<Categoria> listaRaizes();
}
