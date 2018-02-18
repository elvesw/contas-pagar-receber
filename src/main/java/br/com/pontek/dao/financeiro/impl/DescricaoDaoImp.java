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
import br.com.pontek.dao.financeiro.DescricaoDao;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Descricao;
import br.com.pontek.util.filtro.FiltroDescricao;


@Repository(value = "descricaoDao")
public class DescricaoDaoImp extends AbstractDaoImpl<Descricao, Integer> implements DescricaoDao{

	public DescricaoDaoImp() {
		super(Descricao.class);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Descricao> filtrados(FiltroDescricao filtro) {
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
	public Integer quantidadeFiltrados(FiltroDescricao filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(FiltroDescricao filtro) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Descricao.class);
		
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
	

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listaPorNomeTipoDeLancamento(String nome, TipoDeLancamento tipoDeLancamento) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria( Descricao.class ); 
		criteria.setProjection(Projections.distinct( Projections.property("nome")));
		criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("tipoDeLancamento", tipoDeLancamento));
		return criteria.list();
	}
	
	
	@Override
	public Descricao buscarPorNomeTipolancamento(String nome, TipoDeLancamento tipoDeLancamento) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Descricao.class);
		criteria.add(Restrictions.ilike("nome",nome, MatchMode.EXACT));
		criteria.add(Restrictions.eq("tipoDeLancamento", tipoDeLancamento));
		if(!criteria.list().isEmpty())
			return (Descricao) criteria.uniqueResult();
		return null;
	}




}
