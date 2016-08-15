package br.com.pontek.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.pontek.dao.CategoriaDao;
import br.com.pontek.model.Categoria;


@Repository(value = "categoriaDao")
public class CategoriaDaoImp extends AbstractDaoImpl<Categoria, Integer> implements CategoriaDao {

	public CategoriaDaoImp() {
		super(Categoria.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> listaRaizes() {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Categoria.class);
		criteria.add(Restrictions.isNull("categoriaPai"));
		return criteria.list();
	}


}
