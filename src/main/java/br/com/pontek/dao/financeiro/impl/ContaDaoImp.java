package br.com.pontek.dao.financeiro.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.financeiro.ContaDao;
import br.com.pontek.model.financeiro.Conta;


@Repository(value = "contaDao")
public class ContaDaoImp extends AbstractDaoImpl<Conta, Integer> implements ContaDao {

	public ContaDaoImp() {
		super(Conta.class);
	}


}
