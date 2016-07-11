package br.com.pontek.dao.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.CategoriaDao;
import br.com.pontek.model.Categoria;


@Repository(value = "categoriaDao")
public class CategoriaDaoImp extends AbstractDaoImpl<Categoria, Integer> implements CategoriaDao {

	public CategoriaDaoImp() {
		super(Categoria.class);
	}


}
