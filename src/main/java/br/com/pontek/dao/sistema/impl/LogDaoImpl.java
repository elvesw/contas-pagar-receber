package br.com.pontek.dao.sistema.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.sistema.LogDao;
import br.com.pontek.model.sistema.Log;

@Repository(value = "logDao")
public class LogDaoImpl extends AbstractDaoImpl<Log, Integer> implements LogDao{

	public LogDaoImpl() {
		super(Log.class);
	}
}
