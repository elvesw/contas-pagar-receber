package br.com.pontek.dao.financeiro.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.financeiro.CategoriaDao;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Categoria;


@Repository(value = "categoriaDao")
public class CategoriaDaoImp extends AbstractDaoImpl<Categoria, Integer> implements CategoriaDao {

	public CategoriaDaoImp() {
		super(Categoria.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> listaCategoriasPorTipo(TipoDeLancamento tipo) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Categoria.class);
		criteria.add(Restrictions.eq("tipoDeLancamento", tipo));
		return criteria.list();
	}
}
