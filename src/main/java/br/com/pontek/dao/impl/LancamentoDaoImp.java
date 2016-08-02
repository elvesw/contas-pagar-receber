package br.com.pontek.dao.impl;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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

import br.com.pontek.dao.LancamentoDao;
import br.com.pontek.enums.FiltroData;
import br.com.pontek.enums.FiltroStatus;
import br.com.pontek.enums.FiltroTipoData;
import br.com.pontek.enums.FiltroTipoLancamento;
import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.Lancamento;
import br.com.pontek.util.DataUtil;
import br.com.pontek.util.filtro.FiltroLancamento;

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

	private Criteria criarCriteriaParaFiltro(FiltroLancamento filtro) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Lancamento.class);
		criteria.createAlias("pessoa","p");//alias para pessoa
		Conjunction conjunction = Restrictions.conjunction();
		
		/*Termo para busca OK*/
		if(StringUtils.isNotEmpty(filtro.getTermoParaBusca())){
			conjunction.add(Restrictions.or(Restrictions.and(Restrictions.isNotNull("p.nome"),(Restrictions.ilike("p.nome", filtro.getTermoParaBusca(), MatchMode.ANYWHERE))),
											Restrictions.and(Restrictions.isNotNull("descricao"),(Restrictions.ilike("descricao", filtro.getTermoParaBusca(), MatchMode.ANYWHERE)))));
		}
		
		/*Tipo de data e data inicial e final OK*/
		if (filtro.getFiltroTipoData() != null) {
			
			/*Setando o tipo de data*/
			String tipoDeData = "";
			if (filtro.getFiltroTipoData().equals(FiltroTipoData.Data_de_vencimento)) {
				tipoDeData = "dataVencimento";
			} else if (filtro.getFiltroTipoData().equals(FiltroTipoData.Data_de_pagamento)) {
				tipoDeData = "dataPagamento";
			} else if (filtro.getFiltroTipoData().equals(FiltroTipoData.Data_de_cadastro)) {
				tipoDeData = "dataCadastro";
			}
			
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

	@Override
	public BigDecimal somaTotal(FiltroLancamento filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.sum("valor"))
        .add(Restrictions.isNotNull("valor"));  
		return ((BigDecimal) criteria.uniqueResult());
	}

	@Override
	public BigDecimal somaTotalPago(FiltroLancamento filtro) {
		//VALOR
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.sum("valor"))
        .add(Restrictions.isNotNull("valor"))
        .add(Restrictions.eq("statusLancamento", StatusDeLancamento.Pago)); 
		BigDecimal valorTemp=BigDecimal.ZERO;
		if(criteria.uniqueResult()!=null)
		valorTemp=((BigDecimal) criteria.uniqueResult());
		//DESCONTO
		criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.sum("valorDesconto"))
        .add(Restrictions.isNotNull("valorDesconto"))
        .add(Restrictions.eq("statusLancamento", StatusDeLancamento.Pago)); 
		BigDecimal valorDescontoTemp =BigDecimal.ZERO;
		if(criteria.uniqueResult()!=null)
		valorDescontoTemp=((BigDecimal) criteria.uniqueResult());
		//ACRESCIMO
		criteria = criarCriteriaParaFiltro(filtro);
		criteria.setProjection(Projections.sum("valorAcrescimo"))
        .add(Restrictions.isNotNull("valorAcrescimo"))
        .add(Restrictions.eq("statusLancamento", StatusDeLancamento.Pago)); 
		BigDecimal valorAcrescimoTemp = BigDecimal.ZERO;
		if(criteria.uniqueResult()!=null)
		valorAcrescimoTemp=((BigDecimal) criteria.uniqueResult());
		//RESULTADO
		valorTemp=valorTemp.add(valorAcrescimoTemp).subtract(valorDescontoTemp);
		return valorTemp;
	}

}
