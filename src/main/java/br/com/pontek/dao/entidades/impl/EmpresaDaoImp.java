package br.com.pontek.dao.entidades.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.entidades.EmpresaDao;
import br.com.pontek.model.entidades.Empresa;

@Repository(value = "empresaDao")
public class EmpresaDaoImp extends AbstractDaoImpl<Empresa, Integer> implements EmpresaDao {

	public EmpresaDaoImp() {
		super(Empresa.class);
	}


}
