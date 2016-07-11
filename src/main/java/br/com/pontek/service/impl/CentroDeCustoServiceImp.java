package br.com.pontek.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.CentroDeCustoDao;
import br.com.pontek.model.CentroDeCusto;
import br.com.pontek.service.CentroDeCustoService;


@Service
public class CentroDeCustoServiceImp implements CentroDeCustoService ,Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private CentroDeCustoDao centroDeCustoDao;

	
	@Override
	@Transactional
	public void salvar(CentroDeCusto centroDeCusto) {
		centroDeCustoDao.salvarEntity(centroDeCusto);
	}

	@Override
	@Transactional
	public void excluir(CentroDeCusto centroDeCusto) {
		centroDeCustoDao.excluirEntity(centroDeCusto);
	}

	@Override
	@Transactional
	public void excluirPorId(Integer id) {
		centroDeCustoDao.excluirEntityPorId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public CentroDeCusto buscar(Integer id) {
		return centroDeCustoDao.buscarEntity(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<CentroDeCusto> listaDeCentroDeCustos() {
		return centroDeCustoDao.listaDeEntitys();
	}



	

}
