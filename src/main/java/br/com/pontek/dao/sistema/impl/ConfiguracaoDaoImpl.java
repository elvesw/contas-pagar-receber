package br.com.pontek.dao.sistema.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.sistema.ConfiguracaoDao;
import br.com.pontek.model.sistema.Configuracao;

@Repository(value = "configuracaoDao")
public class ConfiguracaoDaoImpl extends AbstractDaoImpl<Configuracao, Integer> implements ConfiguracaoDao {

	public ConfiguracaoDaoImpl() {
		super(Configuracao.class);
	}

	
}
