package br.com.pontek.service.sistema.impl;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.sistema.ConfiguracaoDao;
import br.com.pontek.model.sistema.Configuracao;
import br.com.pontek.service.sistema.ConfiguracaoService;


@Service
public class ConfiguracaoServiceImp implements ConfiguracaoService ,Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	ConfiguracaoDao configuracaoDao;
	
	@Override
	@Transactional
	public void salvar(Configuracao configuracao) {
		configuracao.setDataAlteracao(new Date());
		if(configuracao.getId()!=null){
			configuracaoDao.atualizarEntity(configuracao);			
		}else{
			configuracaoDao.salvarEntity(configuracao);			
		}
	}

	@Override
	@Transactional(readOnly=true)
	public Configuracao carregar() {
		return configuracaoDao.buscarEntity(1);
	}

}
