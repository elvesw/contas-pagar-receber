package br.com.pontek.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.CategoriaDao;
import br.com.pontek.model.Categoria;
import br.com.pontek.service.CategoriaService;


@Service
public class CategoriaServiceImp implements CategoriaService ,Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private CategoriaDao categoriaDao;

	@Override
	@Transactional
	public void salvar(Categoria categoria) {
		categoriaDao.salvarEntity(categoria);
	}

	@Override
	@Transactional
	public void excluir(Categoria categoria) {
		categoriaDao.excluirEntity(categoria);
	}

	@Override
	@Transactional
	public void excluirPorId(Integer categoria_id) {
		categoriaDao.excluirEntityPorId(categoria_id);
	}

	@Override
	@Transactional(readOnly=true)
	public Categoria buscar(Integer categoria_id) {
		return categoriaDao.buscarEntity(categoria_id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Categoria> listaDeCategorias() {
		return categoriaDao.listaDeEntitys();
	}



	

}
