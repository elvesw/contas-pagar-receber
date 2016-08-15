package br.com.pontek.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.CategoriaDao;
import br.com.pontek.dao.LancamentoDao;
import br.com.pontek.model.Categoria;
import br.com.pontek.model.Lancamento;
import br.com.pontek.service.CategoriaService;


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
	public void excluir(Categoria categoria) {
		Set<Lancamento> lancamentos=categoria.getListaLancamentos();
		int cont=0;
		if(!lancamentos.isEmpty()){
			for (Lancamento l : lancamentos) {
				l.setCategoria(null);
				lancamentoDao.atualizarEntity(l);
				cont=cont+1;
			}
		}
		categoriaDao.excluirEntityPorId(categoria.getId());
		System.out.println("CategoriaServiceImp.excluir(): Total"
				+ " de lançamentos removidos dessa categoria: "+cont);
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
	public List<Categoria> listaRaizes() {
		return categoriaDao.listaRaizes();
	}



	

}
