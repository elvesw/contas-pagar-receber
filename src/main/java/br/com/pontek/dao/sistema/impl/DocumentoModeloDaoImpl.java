package br.com.pontek.dao.sistema.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.sistema.DocumentoModeloDao;
import br.com.pontek.model.sistema.DocumentoModelo;
import br.com.pontek.util.filtro.FiltroDocumentoModelo;

@Repository(value = "documentoModeloDao")
public class DocumentoModeloDaoImpl extends AbstractDaoImpl<DocumentoModelo, Integer> implements DocumentoModeloDao {

	public DocumentoModeloDaoImpl() {
		super(DocumentoModelo.class);
	}
	
	/*PAGINAÇÃO LAZY DATATABLE*/
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoModelo> filtrados(FiltroDocumentoModelo filtro) {
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
	public Integer quantidadeFiltrados(FiltroDocumentoModelo filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}
	private Criteria criarCriteriaParaFiltro (FiltroDocumentoModelo filtro){
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(DocumentoModelo.class);
		if (StringUtils.isNotEmpty(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		return criteria;
	}
	/*FIM - PAGINAÇÃO LAZY DATATABLE*/

	
}
