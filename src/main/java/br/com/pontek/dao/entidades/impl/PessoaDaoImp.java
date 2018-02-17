package br.com.pontek.dao.entidades.impl;

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
import br.com.pontek.dao.entidades.PessoaDao;
import br.com.pontek.enums.PerfilDePessoa;
import br.com.pontek.model.entidades.Pessoa;
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
		}else if(filtro.getPerfil().equals(PerfilDePessoa.Fornecedores)){
			criteria.add(Restrictions.eq("fornecedor", true));
		}else if(filtro.getPerfil().equals(PerfilDePessoa.Funcionários)){
			criteria.add(Restrictions.eq("funcionario", true));
		}
		return criteria;
	}
	/*FIM - PAGINAÇÃO LAZY DATATABLE*/


	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> listaPorPerfil(boolean perfilCliente, boolean perfilFornecedor, boolean perfilFuncionario) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Pessoa.class);
		Conjunction conjunction = Restrictions.conjunction();
		
		conjunction.add(Restrictions.eq("cadastroAtivo",true));
		/*Lógica doida, fiz pelo rumo e funcionou*/
		conjunction.add(Restrictions.or(Restrictions.eq("cliente",perfilCliente?true:null),
										Restrictions.eq("fornecedor",perfilFornecedor?true:null),
										Restrictions.eq("funcionario",perfilFuncionario?true:null)));
		criteria.add(conjunction);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> listaComTermoDeBuscaPorPerfil(String termoDeBusca, boolean perfilCliente,boolean perfilFornecedor, boolean perfilFuncionario) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Pessoa.class);
		Conjunction conjunction = Restrictions.conjunction();
		
		conjunction.add(Restrictions.eq("cadastroAtivo",true));
		if (StringUtils.isNotEmpty(termoDeBusca)) {
			criteria.add(Restrictions.ilike("nome", termoDeBusca, MatchMode.ANYWHERE));
		}
		/*Lógica doida, fiz pelo rumo e funcionou*/
		conjunction.add(Restrictions.or(Restrictions.eq("cliente",perfilCliente?true:null),
										Restrictions.eq("fornecedor",perfilFornecedor?true:null),
										Restrictions.eq("funcionario",perfilFuncionario?true:null)));
		criteria.add(conjunction);
		return criteria.list();
	}


	@Override
	public Boolean existe(Pessoa pessoa) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Pessoa.class);
		
		criteria.add(Restrictions.ilike("nome",pessoa.getNome(), MatchMode.EXACT));
		criteria.add(Restrictions.neOrIsNotNull("id", pessoa.getId()));
		Integer count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		if(count>0) 
			return true;
		
		return false;
	}


	@Override
	public Pessoa existeCadastro(Pessoa pessoa) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Pessoa.class);
		criteria.add(Restrictions.ilike("nome",pessoa.getNome(), MatchMode.EXACT));
		criteria.add(Restrictions.neOrIsNotNull("id", pessoa.getId()));
		if(!criteria.list().isEmpty())
			return (Pessoa) criteria.uniqueResult();
		return null;
	}


	@Override
	public Pessoa buscarPorNomeExato(String nome) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Pessoa.class);
		criteria.add(Restrictions.ilike("nome",nome, MatchMode.EXACT));
		if(!criteria.list().isEmpty())
			return (Pessoa) criteria.uniqueResult();
		return null;
	}






	
}
