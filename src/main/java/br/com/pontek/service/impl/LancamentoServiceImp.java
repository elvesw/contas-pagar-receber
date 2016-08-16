package br.com.pontek.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.LancamentoDao;
import br.com.pontek.model.Lancamento;
import br.com.pontek.service.LancamentoService;
import br.com.pontek.util.filtro.FiltroLancamento;
import br.com.pontek.util.jpa.LancamentosPeriodo;


@Service
public class LancamentoServiceImp implements LancamentoService ,Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private LancamentoDao lancamentoDao;

	@Override
	@Transactional
	public void salvar(Lancamento lancamento) {
		if(lancamento.getValor()!=null)
			lancamento.setValorPago(lancamento.getValor());
		if(lancamento.getValorAcrescimo()!=null)
			lancamento.setValorPago(lancamento.getValorPago().add(lancamento.getValorAcrescimo()));
		if(lancamento.getValorDesconto()!=null)
			lancamento.setValorPago(lancamento.getValorPago().subtract(lancamento.getValorDesconto()));
		
		if(lancamento.getId()!=null){
			lancamento.setDataAlteracao(new Date());
			lancamentoDao.atualizarEntity(lancamento);
		}else{
			lancamento.setDataCadastro(new Date());
			lancamento.setDataAlteracao(new Date());
			lancamentoDao.salvarEntity(lancamento);
		}
	}

	@Override
	@Transactional
	public List<Lancamento> salvarLista(List<Lancamento> lista) {
			if(!lista.isEmpty()){
				return lancamentoDao.salvarAllEntitys(lista);				
			}
			return null;
	}
	
	@Override
	@Transactional
	public void excluir(Lancamento lancamento) {
		lancamentoDao.excluirEntityPorId(lancamento.getId());
	}

	@Override
	@Transactional
	public void excluirPorId(Integer lancamento_id) {
		lancamentoDao.excluirEntityPorId(lancamento_id);
	}

	@Override
	@Transactional(readOnly=true)
	public Lancamento buscar(Integer lancamento_id) {
		return lancamentoDao.buscarEntity(lancamento_id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Lancamento> listaDeMovimentos() {
		return lancamentoDao.listaDeEntitys();
	}

	/*Metodos de PAGINAÇÃO LAZY DATATABLE*/
	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> filtrados(FiltroLancamento filtro) {
		return lancamentoDao.filtrados(filtro);
	}
	@Override
	@Transactional(readOnly = true)
	public Integer quantidadeFiltrados(FiltroLancamento filtro) {
		return lancamentoDao.quantidadeFiltrados(filtro);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal somaEntradaPago(FiltroLancamento filtro) {
		return lancamentoDao.somaEntradaPago(filtro);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal somaSaidaPago(FiltroLancamento filtro) {
		return lancamentoDao.somaSaidaPago(filtro);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal somaSaldoAnterior(FiltroLancamento filtro) {
		return lancamentoDao.somaSaldoAnterior(filtro);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal somaValor(FiltroLancamento filtro) {
		return lancamentoDao.somaValor(filtro);
	}

	@Override
	@Transactional(readOnly = true)
	public List<LancamentosPeriodo> historicoSeisMeses() {
		return lancamentoDao.historicoSeisMeses();
	}


}
