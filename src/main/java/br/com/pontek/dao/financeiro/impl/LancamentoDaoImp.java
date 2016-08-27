package br.com.pontek.dao.financeiro.impl;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.financeiro.LancamentoDao;
import br.com.pontek.enums.FiltroData;
import br.com.pontek.enums.FiltroStatus;
import br.com.pontek.enums.FiltroTipoData;
import br.com.pontek.enums.FiltroTipoLancamento;
import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.model.financeiro.Conta;
import br.com.pontek.model.financeiro.Lancamento;
import br.com.pontek.util.DataUtil;
import br.com.pontek.util.filtro.FiltroLancamento;
import br.com.pontek.util.jpa.LancamentosPeriodo;

@Repository(value = "lancamentoDao")
public class LancamentoDaoImp extends AbstractDaoImpl<Lancamento, Integer> implements LancamentoDao {

	public LancamentoDaoImp() {
		super(Lancamento.class);
	}

	/* PAGINAÇÃO LAZY DATATABLE */
	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> filtrados(FiltroLancamento filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}else if(filtro.getFiltroTipoData().equals(FiltroTipoData.Data_de_vencimento)){
			criteria.addOrder(Order.desc("dataVencimento"));
		}else if(filtro.getFiltroTipoData().equals(FiltroTipoData.Data_de_pagamento)){
			criteria.addOrder(Order.desc("dataPagamento"));
		}else if(filtro.getFiltroTipoData().equals(FiltroTipoData.Data_de_cadastro)){
			criteria.addOrder(Order.desc("dataCadastro"));
		}
		return criteria.list();
	}

	@Override
	public Integer quantidadeFiltrados(FiltroLancamento filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.rowCount());
		return ((Number) criteria.uniqueResult()).intValue();
	}

	//Seta o tipo de data escolhido
	private String tipoDeData(FiltroTipoData tipo){
		String tipoDeData = "";
		if (tipo.equals(FiltroTipoData.Data_de_vencimento)) {
			tipoDeData = "dataVencimento";
		} else if (tipo.equals(FiltroTipoData.Data_de_pagamento)) {
			tipoDeData = "dataPagamento";
		} else if (tipo.equals(FiltroTipoData.Data_de_cadastro)) {
			tipoDeData = "dataCadastro";
		}
		return tipoDeData;
	}
	
	private Criteria criarCriteriaParaFiltro(FiltroLancamento filtro) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Lancamento.class);
		
		Conjunction conjunction = Restrictions.conjunction();
		
		/*Termo para busca OK*/
		if(StringUtils.isNotEmpty(filtro.getTermoParaBusca())){
			Criterion porDescricao = Restrictions.ilike("descricao", filtro.getTermoParaBusca(), MatchMode.ANYWHERE);
			
			Criteria c = criteria;//funcionou depois de criar um novo criteria para o alias
			c.createAlias("pessoa", "p");
			Criterion porNomePessoa=Restrictions.ilike("p.nome", filtro.getTermoParaBusca(), MatchMode.ANYWHERE);
			
			LogicalExpression orExp = Restrictions.or(porDescricao,porNomePessoa);

			conjunction.add(orExp);
		}
	
		/*Tipo de data e data inicial e final OK*/
		if (filtro.getFiltroTipoData() != null) {
			
			/*Setando o tipo de data*/
			String tipoDeData = tipoDeData(filtro.getFiltroTipoData());
			
			 LocalDate dataHoje = LocalDate.now();
			if (filtro.getFitroData() != null) {
				if (filtro.getFitroData().equals(FiltroData.Hoje)) {
					conjunction.add(Restrictions.eq(tipoDeData, DataUtil.localDateParaDate(dataHoje)));
				} else if (filtro.getFitroData().equals(FiltroData.Ontem)) {
					conjunction.add(Restrictions.eq(tipoDeData, DataUtil.localDateParaDate(dataHoje.plusDays(-1))));
				}else if (filtro.getFitroData().equals(FiltroData.Amanhã)) {
					conjunction.add(Restrictions.eq(tipoDeData, DataUtil.localDateParaDate(dataHoje.plusDays(+1))));
				}else if (filtro.getFitroData().equals(FiltroData.Está_semana)) {
					LocalDate inicio = dataHoje.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
					LocalDate fim = dataHoje.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(inicio)));
					conjunction.add(Restrictions.le(tipoDeData, DataUtil.localDateParaDate(fim)));
				}else if (filtro.getFitroData().equals(FiltroData.Próxima_semana)){
					LocalDate inicio = dataHoje.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
					LocalDate fim = inicio.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(inicio)));
					conjunction.add(Restrictions.le(tipoDeData,DataUtil.localDateParaDate(fim)));
				}else if (filtro.getFitroData().equals(FiltroData.Últimos_7_dias)) {
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(dataHoje.plusDays(-7))));
					conjunction.add(Restrictions.le(tipoDeData,DataUtil.localDateParaDate(dataHoje)));
				}else if (filtro.getFitroData().equals(FiltroData.Próximos_7_dias)) {
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(dataHoje)));
					conjunction.add(Restrictions.le(tipoDeData,DataUtil.localDateParaDate(dataHoje.plusDays(+7))));
				}else if (filtro.getFitroData().equals(FiltroData.Últimos_14_dias)) {
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(dataHoje.plusDays(-14))));
					conjunction.add(Restrictions.le(tipoDeData,DataUtil.localDateParaDate(dataHoje)));
				}else if (filtro.getFitroData().equals(FiltroData.Próximos_14_dias)) {
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(dataHoje)));
					conjunction.add(Restrictions.le(tipoDeData,DataUtil.localDateParaDate(dataHoje.plusDays(+14))));
				}else if (filtro.getFitroData().equals(FiltroData.Últimos_30_dias)) {
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(dataHoje.plusDays(-30))));
					conjunction.add(Restrictions.le(tipoDeData,DataUtil.localDateParaDate(dataHoje)));
				}else if (filtro.getFitroData().equals(FiltroData.Próximos_30_dias)) {
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(dataHoje)));
					conjunction.add(Restrictions.le(tipoDeData,DataUtil.localDateParaDate(dataHoje.plusDays(+30))));
				}else if (filtro.getFitroData().equals(FiltroData.Esse_mês)) {
					 LocalDate primeiroDiaMes = dataHoje.with(TemporalAdjusters.firstDayOfMonth());
					 LocalDate ultimoDiaMes = dataHoje.with(TemporalAdjusters.lastDayOfMonth());
					 conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(primeiroDiaMes)));
					 conjunction.add(Restrictions.le(tipoDeData, DataUtil.localDateParaDate(ultimoDiaMes)));
				}else if (filtro.getFitroData().equals(FiltroData.Mês_passado)) {
					 LocalDate dataMesPassado = dataHoje.plusMonths(-1);
					 LocalDate primeiroDiaMes = dataMesPassado.with(TemporalAdjusters.firstDayOfMonth());
					 LocalDate ultimoDiaMes = dataMesPassado.with(TemporalAdjusters.lastDayOfMonth());
					 conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(primeiroDiaMes)));
					 conjunction.add(Restrictions.le(tipoDeData, DataUtil.localDateParaDate(ultimoDiaMes)));
				}else if (filtro.getFitroData().equals(FiltroData.Próximo_mês)) {
					LocalDate dataProximoMes = dataHoje.plusMonths(+1);
					LocalDate primeiroDiaMes = dataProximoMes.with(TemporalAdjusters.firstDayOfMonth());
					LocalDate ultimoDiaMes = dataProximoMes.with(TemporalAdjusters.lastDayOfMonth());
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(primeiroDiaMes)));
					conjunction.add(Restrictions.le(tipoDeData, DataUtil.localDateParaDate(ultimoDiaMes)));
				}else if (filtro.getFitroData().equals(FiltroData.Passado_mais_30_dias)) {
					conjunction.add(Restrictions.le(tipoDeData,DataUtil.localDateParaDate(dataHoje.plusDays(+30))));
				}else if (filtro.getFitroData().equals(FiltroData.Esse_ano)) {
					conjunction.add(Restrictions.ge(tipoDeData, DataUtil.localDateParaDate(dataHoje.with(TemporalAdjusters.firstDayOfYear()))));
					conjunction.add(Restrictions.le(tipoDeData, DataUtil.localDateParaDate(dataHoje.with(TemporalAdjusters.lastDayOfYear()))));
				}else if (filtro.getFitroData().equals(FiltroData.Últimos_12_meses)) {
					conjunction.add(Restrictions.ge(tipoDeData,DataUtil.localDateParaDate(dataHoje.plusYears(-1))));
					conjunction.add(Restrictions.le(tipoDeData, DataUtil.localDateParaDate(dataHoje)));
				}else if (filtro.getFitroData().equals(FiltroData.Sem_filtro_de_data)) {
					conjunction.add(Restrictions.isNotNull(tipoDeData));//Todos sem que a data esteja null
				}
			}
		}
		//StatusDeLancamento OK
		if (filtro.getFitroStatus() != null) {
			if (filtro.getFitroStatus().equals(FiltroStatus.Somente_pendentes)) {
				conjunction.add(Restrictions.eq("statusLancamento", StatusDeLancamento.Pendente));
			} else if (filtro.getFitroStatus().equals(FiltroStatus.Somente_pagos)) {
				conjunction.add(Restrictions.eq("statusLancamento", StatusDeLancamento.Pago));
			} else if (filtro.getFitroStatus().equals(FiltroStatus.Somente_cancelados)) {
				conjunction.add(Restrictions.eq("statusLancamento", StatusDeLancamento.Cancelado));
			} else if (filtro.getFitroStatus().equals(FiltroStatus.Pagos_e_pendentes)) {
				conjunction.add(Restrictions.or(Restrictions.eq("statusLancamento", StatusDeLancamento.Pendente),
												Restrictions.eq("statusLancamento", StatusDeLancamento.Pago)));
			}
		}

		// TipoDeLancamento OK
		if (filtro.getFiltroTipoLancamento() != null) {
			if (filtro.getFiltroTipoLancamento().equals(FiltroTipoLancamento.Somente_entrada)) {
				conjunction.add(Restrictions.eq("tipoLancamento", TipoDeLancamento.ENTRADA));
			} else if (filtro.getFiltroTipoLancamento().equals(FiltroTipoLancamento.Somente_saída)) {
				conjunction.add(Restrictions.eq("tipoLancamento", TipoDeLancamento.SAÍDA));
			} else if (filtro.getFiltroTipoLancamento().equals(FiltroTipoLancamento.Todos)) {
				conjunction.add(Restrictions.or(Restrictions.eq("tipoLancamento", TipoDeLancamento.ENTRADA),
												Restrictions.eq("tipoLancamento", TipoDeLancamento.SAÍDA)));
			}
		}
		criteria.add(conjunction);
		return criteria;
	}
	/* FIM - PAGINAÇÃO LAZY DATATABLE */
	
	//Criteria para somar valores
	private BigDecimal somaTotal(FiltroLancamento filtro,TipoDeLancamento tipo) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.sum("valorPago"))
		.add(Restrictions.isNotNull("valorPago"))
		.add(Restrictions.eq("statusLancamento", StatusDeLancamento.Pago))
		.add(Restrictions.eq("tipoLancamento", tipo));
		if(criteria.uniqueResult()!=null)
		return ((BigDecimal) criteria.uniqueResult());
		return BigDecimal.ZERO;
	}
	
	@Override
	public BigDecimal somaEntradaPago(FiltroLancamento filtro) {
		return somaTotal(filtro,TipoDeLancamento.ENTRADA);
	}

	@Override
	public BigDecimal somaSaidaPago(FiltroLancamento filtro) {
		return somaTotal(filtro,TipoDeLancamento.SAÍDA);
	}

	@Override
	public BigDecimal somaSaldoAnterior(FiltroLancamento filtro) {
		/*if(filtro.getFitroData().equals(FiltroData.Sem_filtro_de_data)){
			return BigDecimal.ZERO;
		}*/
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.min("dataPagamento"))
		.add(Restrictions.isNotNull("dataPagamento"));
		Date dataMinina=((Date) criteria.uniqueResult());
		
		Session session = (Session) getEm().getDelegate();
		Criteria criteria2 = session.createCriteria(Lancamento.class);
		criteria2.setProjection(Projections.sum("valorPago"))
		.add(Restrictions.isNotNull("valorPago"))
		.add(Restrictions.lt("dataPagamento",dataMinina))
		.add(Restrictions.eq("statusLancamento", StatusDeLancamento.Pago))
		.add(Restrictions.eq("tipoLancamento", TipoDeLancamento.ENTRADA));
		BigDecimal saldoAnteriorEntrada=BigDecimal.ZERO;
		if(criteria2.uniqueResult()!=null)
			saldoAnteriorEntrada= ((BigDecimal) criteria2.uniqueResult());
		
		criteria2 = session.createCriteria(Lancamento.class);
		criteria2.setProjection(Projections.sum("valorPago"))
		.add(Restrictions.isNotNull("valorPago"))
		.add(Restrictions.lt("dataPagamento",dataMinina))
		.add(Restrictions.eq("statusLancamento", StatusDeLancamento.Pago))
		.add(Restrictions.eq("tipoLancamento", TipoDeLancamento.SAÍDA));
		BigDecimal saldoAnteriorSaida=BigDecimal.ZERO;
		if(criteria2.uniqueResult()!=null)
			saldoAnteriorSaida= ((BigDecimal) criteria2.uniqueResult());
		
		BigDecimal saldoAnterior=BigDecimal.ZERO;
		saldoAnterior=saldoAnterior.add(saldoAnteriorEntrada);
		saldoAnterior=saldoAnterior.subtract(saldoAnteriorSaida);
		return saldoAnterior;
	}


	/*######################################*/
	
	//Soma valor pago e Não pago
	@Override
	public BigDecimal somaValor(FiltroLancamento filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.sum("valorPago"))
		.add(Restrictions.isNotNull("valorPago"));
		if(criteria.uniqueResult()!=null)
		return ((BigDecimal) criteria.uniqueResult());
		return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal saldoPorConta(Conta conta) {
		BigDecimal somaEntrada=BigDecimal.ZERO;
		BigDecimal somaSaida=BigDecimal.ZERO;
		
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Lancamento.class);
		criteria.setProjection(Projections.sum("valorPago"))
		.add(Restrictions.isNotNull("valorPago"))
		.add(Restrictions.eq("tipoLancamento", TipoDeLancamento.ENTRADA))
		.add(Restrictions.eq("conta",conta));
		if(criteria.uniqueResult()!=null)
			somaEntrada= ((BigDecimal) criteria.uniqueResult());
		
		criteria = session.createCriteria(Lancamento.class);
		criteria.setProjection(Projections.sum("valorPago"))
		.add(Restrictions.isNotNull("valorPago"))
		.add(Restrictions.eq("tipoLancamento", TipoDeLancamento.SAÍDA))
		.add(Restrictions.eq("conta",conta));
		if(criteria.uniqueResult()!=null)
			somaSaida= ((BigDecimal) criteria.uniqueResult());
		
		return somaEntrada.subtract(somaSaida);
	}

	@Override
	public List<LancamentosPeriodo> historicoSeisMeses() {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria;
		List<LancamentosPeriodo> listaSomas=new ArrayList<LancamentosPeriodo>();
		LancamentosPeriodo periodo;
		LocalDate dataHoje = LocalDate.now();
		
		
		 for (int i=-5;i<1;i++){
		 LocalDate mesReferencia = dataHoje.plusMonths(i);
		 LocalDate primeiroDiaMes = mesReferencia.with(TemporalAdjusters.firstDayOfMonth());
		 LocalDate ultimoDiaMes = mesReferencia.with(TemporalAdjusters.lastDayOfMonth());
		 periodo=new LancamentosPeriodo(mesReferencia.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt")));
		 
		 /*somaEntradasSomentePago no intervalo*/
		 criteria = session.createCriteria(Lancamento.class);
		 criteria.setProjection(Projections.sum("valorPago"))
		 .add(Restrictions.isNotNull("valorPago"))
		 .add(Restrictions.ge("dataVencimento",DataUtil.localDateParaDate(primeiroDiaMes)))
		 .add(Restrictions.le("dataVencimento",DataUtil.localDateParaDate(ultimoDiaMes)))
		 .add(Restrictions.eq("statusLancamento",StatusDeLancamento.Pago))
		 .add(Restrictions.eq("tipoLancamento",TipoDeLancamento.ENTRADA));
		 periodo.setSomaEntradasSomentePago(criteria.uniqueResult()!=null?(BigDecimal)criteria.uniqueResult():BigDecimal.ZERO);
		 
		 /*somaEntradasSomentePendente no intervalo*/
		 criteria = session.createCriteria(Lancamento.class);
		 criteria.setProjection(Projections.sum("valorPago"))
		 .add(Restrictions.isNotNull("valorPago"))
		 .add(Restrictions.ge("dataVencimento",DataUtil.localDateParaDate(primeiroDiaMes)))
		 .add(Restrictions.le("dataVencimento",DataUtil.localDateParaDate(ultimoDiaMes)))
		 .add(Restrictions.eq("statusLancamento",StatusDeLancamento.Pendente))
		 .add(Restrictions.eq("tipoLancamento",TipoDeLancamento.ENTRADA));
		 periodo.setSomaEntradasSomentePendente(criteria.uniqueResult()!=null?(BigDecimal)criteria.uniqueResult():BigDecimal.ZERO);
		 
		/* somaSaidasSomentePago no intervalo*/
		 criteria = session.createCriteria(Lancamento.class);
		 criteria.setProjection(Projections.sum("valorPago"))
		 .add(Restrictions.isNotNull("valorPago"))
		 .add(Restrictions.ge("dataVencimento",DataUtil.localDateParaDate(primeiroDiaMes)))
		 .add(Restrictions.le("dataVencimento",DataUtil.localDateParaDate(ultimoDiaMes)))
		 .add(Restrictions.eq("statusLancamento",StatusDeLancamento.Pago))
		 .add(Restrictions.eq("tipoLancamento",TipoDeLancamento.SAÍDA));
		 periodo.setSomaSaidasSomentePago(criteria.uniqueResult()!=null?(BigDecimal)criteria.uniqueResult():BigDecimal.ZERO);
		 
		 /*somaSaidasSomentePendente no intervalo*/
		 criteria = session.createCriteria(Lancamento.class);
		 criteria.setProjection(Projections.sum("valorPago"))
		 .add(Restrictions.isNotNull("valorPago"))
		 .add(Restrictions.ge("dataVencimento",DataUtil.localDateParaDate(primeiroDiaMes)))
		 .add(Restrictions.le("dataVencimento",DataUtil.localDateParaDate(ultimoDiaMes)))
		 .add(Restrictions.eq("statusLancamento",StatusDeLancamento.Pendente))
		 .add(Restrictions.eq("tipoLancamento",TipoDeLancamento.SAÍDA));
		 periodo.setSomaSaidasSomentePendente(criteria.uniqueResult()!=null?(BigDecimal)criteria.uniqueResult():BigDecimal.ZERO);
		 
		 listaSomas.add(periodo);
		 }//fim for
		
		return listaSomas;
	}

	@Override
	public boolean existeCategoriaEmLancamentos(Categoria categoria) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Lancamento.class);
		criteria.add(Restrictions.eq("categoria",categoria));
		if(!criteria.list().isEmpty()) return true;
		return false;
	}

	@Override
	public boolean existeContaEmLancamentos(Conta conta) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Lancamento.class);
		criteria.add(Restrictions.eq("conta",conta));
		if(!criteria.list().isEmpty()) return true;
		return false;
	}

	@Override
	public boolean existePessoaEmLancamentos(Pessoa pessoa) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Lancamento.class);
		criteria.add(Restrictions.eq("pessoa",pessoa));
		if(!criteria.list().isEmpty()) return true;
		return false;
	}

}
