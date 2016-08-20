package br.com.pontek.service.entidades.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.entidades.EmpresaDao;
import br.com.pontek.model.entidades.Empresa;
import br.com.pontek.service.entidades.EmpresaService;

@Service
public class EmpresaServiceImp implements EmpresaService, Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private EmpresaDao empresaDao;

	@Override
	@Transactional
	public void salvar(Empresa empresa) {
			empresa.setId(1);
			empresaDao.atualizarEntity(empresa);
	}

	@Override
	@Transactional(readOnly=true)
	public Empresa carregarDados(){
		return empresaDao.buscarEntity(1);
	}

}
