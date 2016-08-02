package br.com.pontek.dao.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.ContaDao;
import br.com.pontek.model.Conta;


@Repository(value = "contaDao")
public class ContaDaoImp extends AbstractDaoImpl<Conta, Integer> implements ContaDao {

	public ContaDaoImp() {
		super(Conta.class);
	}


}
