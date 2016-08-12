package br.com.pontek.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.ContaDao;
import br.com.pontek.model.Conta;
import br.com.pontek.service.ContaService;


@Service
public class ContaServiceImp implements ContaService ,Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ContaDao contaDao;

	
	@Override
	@Transactional
	public void salvar(Conta conta) {
		if(conta.getId()!=null){
			contaDao.atualizarEntity(conta);
		}else{
			contaDao.salvarEntity(conta);
		}
	}

	@Override
	@Transactional
	public void excluir(Conta conta) {
		contaDao.excluirEntityPorId(conta.getId());
	}

	@Override
	@Transactional
	public void excluirPorId(Integer id) {
		contaDao.excluirEntityPorId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Conta buscar(Integer id) {
		return contaDao.buscarEntity(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Conta> listaDeContas() {
		return contaDao.listaDeEntitys();
	}



	

}
