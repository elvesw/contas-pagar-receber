package br.com.pontek.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.LancamentoDao;
import br.com.pontek.model.Lancamento;
import br.com.pontek.service.LancamentoService;


@Service
public class LancamentoServiceImp implements LancamentoService ,Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private LancamentoDao lancamentoDao;

	@Override
	@Transactional
	public void salvar(Lancamento lancamento) {
		lancamentoDao.salvarEntity(lancamento);
	}

	@Override
	@Transactional
	public void excluir(Lancamento lancamento) {
		lancamentoDao.excluirEntity(lancamento);
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



}
