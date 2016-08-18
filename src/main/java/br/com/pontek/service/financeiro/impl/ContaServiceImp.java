package br.com.pontek.service.financeiro.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.financeiro.ContaDao;
import br.com.pontek.model.financeiro.Conta;
import br.com.pontek.service.financeiro.ContaService;


@Service
public class ContaServiceImp   implements ContaService ,Serializable{

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
	public List<Conta> listaTodos() {
		return contaDao.listaDeEntitys();
	}

}
