package br.com.pontek.dao.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.CentroDeCustoDao;
import br.com.pontek.model.CentroDeCusto;


@Repository(value = "centroDeCustoDao")
public class CentroDeCustoDaoImp extends AbstractDaoImpl<CentroDeCusto, Integer> implements CentroDeCustoDao {

	public CentroDeCustoDaoImp() {
		super(CentroDeCusto.class);
	}


}
