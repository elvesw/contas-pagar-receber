package br.com.pontek.service.financeiro.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.financeiro.DescricaoDao;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Descricao;
import br.com.pontek.service.financeiro.DescricaoService;
import br.com.pontek.util.filtro.FiltroDescricao;


@Service
public class DescricaoServiceImp   implements DescricaoService ,Serializable{

	private static final long serialVersionUID = 1L;
	@Autowired
	private DescricaoDao descricaoDao;
	
	@Override
	@Transactional
	public void salvar(Descricao descricao) {
		if(descricaoDao.buscarPorNomeTipolancamento(descricao.getNome(),descricao.getTipoDeLancamento())==null)
			descricaoDao.salvarEntity(descricao);
	}
	@Override
	@Transactional
	public void excluir(Descricao descricao) {
		descricaoDao.excluirEntityPorId(descricao.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Descricao> listaTodos() {
		return descricaoDao.listaDeEntitys();
	}
	@Override
	@Transactional(readOnly = true)
	public List<String> listaPorNomeTipoDeLancamento(String nome, TipoDeLancamento tipoDeLancamento) {
		return descricaoDao.listaPorNomeTipoDeLancamento(nome, tipoDeLancamento);
	}
	@Override
	@Transactional(readOnly=true)
	public List<Descricao> filtrados(FiltroDescricao filtro) {
		return descricaoDao.filtrados(filtro);
	}
	@Override
	@Transactional(readOnly=true)
	public Integer quantidadeFiltrados(FiltroDescricao filtro) {
		return descricaoDao.quantidadeFiltrados(filtro);
	}
	@Override
	@Transactional(readOnly=true)
	public Descricao buscarPorNomeTipolancamento(String nome, TipoDeLancamento tipoDeLancamento) {
		return descricaoDao.buscarPorNomeTipolancamento(nome,tipoDeLancamento);
	}
}
