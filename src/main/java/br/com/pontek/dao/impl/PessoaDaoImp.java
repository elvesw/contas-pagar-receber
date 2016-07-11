package br.com.pontek.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.pontek.dao.PessoaDao;
import br.com.pontek.enums.PerfilDePessoa;
import br.com.pontek.model.Pessoa;
import br.com.pontek.util.filtro.FiltroPessoa;


@Repository(value = "pessoaDao")
public class PessoaDaoImp extends AbstractDaoImpl<Pessoa, Integer> implements PessoaDao {

	public PessoaDaoImp() {
		super(Pessoa.class);
	}

	
	/*PAGINAÇÃO LAZY DATATABLE*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> filtrados(FiltroPessoa filtro) {
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
	public Integer quantidadeFiltrados(FiltroPessoa filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}
	private Criteria criarCriteriaParaFiltro (FiltroPessoa filtro){
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Pessoa.class);
		if (StringUtils.isNotEmpty(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		//caso for verificar situação do cadastro
		criteria.add(Restrictions.eq("cadastroAtivo", filtro.isCadastroAtivo()));
		
		if(filtro.getPerfil().equals(PerfilDePessoa.Clientes)){
			criteria.add(Restrictions.eq("cliente", true));
			criteria.add(Restrictions.eq("fornecedor", false));
			criteria.add(Restrictions.eq("funcionario", false));
		}else if(filtro.getPerfil().equals(PerfilDePessoa.Fornecedores)){
			criteria.add(Restrictions.eq("cliente", false));
			criteria.add(Restrictions.eq("fornecedor", true));
			criteria.add(Restrictions.eq("funcionario", false));
		}else if(filtro.getPerfil().equals(PerfilDePessoa.Funcionários)){
			criteria.add(Restrictions.eq("cliente", false));
			criteria.add(Restrictions.eq("fornecedor", false));
			criteria.add(Restrictions.eq("funcionario", true));
		}

		
		return criteria;
	}
	/*FIM - PAGINAÇÃO LAZY DATATABLE*/

}
