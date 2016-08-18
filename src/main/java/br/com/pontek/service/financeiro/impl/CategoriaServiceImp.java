package br.com.pontek.service.financeiro.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.financeiro.CategoriaDao;
import br.com.pontek.dao.financeiro.LancamentoDao;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.service.financeiro.CategoriaService;


@Service
public class CategoriaServiceImp implements CategoriaService ,Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private CategoriaDao categoriaDao;
	@Autowired
	LancamentoDao lancamentoDao;

	@Override
	@Transactional
	public void salvar(Categoria categoria) {
		if(categoria.getId()!=null){
			categoriaDao.atualizarEntity(categoria);
		}else{
			categoriaDao.salvarEntity(categoria);			
		}
	}

	@Override
	@Transactional
	public void excluir(Categoria categoria){
		categoriaDao.excluirEntityPorId(categoria.getId());
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

	@Override
	@Transactional(readOnly=true)
	public List<Categoria> listaCategoriasPorTipo(TipoDeLancamento tipo) {
		return categoriaDao.listaCategoriasPorTipo(tipo);
	}
}
