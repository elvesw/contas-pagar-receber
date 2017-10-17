package br.com.pontek.service.sistema.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.sistema.LogDao;
import br.com.pontek.model.sistema.Log;
import br.com.pontek.service.sistema.LogService;

@Service
public class LogServiceImp implements LogService {
	
	@Autowired
	private LogDao logDao;

	@Override
	@Transactional
	public void salvar(String nome, String evento) {
		logDao.salvarEntity(new Log(nome, evento, new Date()));
	}
}
