package br.com.pontek.dao.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.LancamentoDao;
import br.com.pontek.model.Lancamento;


@Repository(value = "lancamentoDao")
public class LancamentoDaoImp extends AbstractDaoImpl<Lancamento, Integer> implements LancamentoDao {

	public LancamentoDaoImp() {
		super(Lancamento.class);
	}


}
