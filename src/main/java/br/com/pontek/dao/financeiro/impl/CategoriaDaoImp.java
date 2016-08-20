package br.com.pontek.dao.financeiro.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.financeiro.CategoriaDao;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.util.filtro.FiltroCategoria;


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

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> filtrados(FiltroCategoria filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		return criteria.list();
	}
	
	@Override
	public Integer quantidadeFiltrados(FiltroCategoria filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(FiltroCategoria filtro) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Categoria.class);
		
		Conjunction conjunction = Restrictions.conjunction();
		
		/*Busca por nome*/
		if(StringUtils.isNotEmpty(filtro.getNome())){
			conjunction.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		/*Busca por tipo*/
		if(filtro.getTipoDeLancamento()!=null){
			conjunction.add(Restrictions.eq("tipoDeLancamento",filtro.getTipoDeLancamento()));
		}
		criteria.add(conjunction);
		return criteria;
	}
}
