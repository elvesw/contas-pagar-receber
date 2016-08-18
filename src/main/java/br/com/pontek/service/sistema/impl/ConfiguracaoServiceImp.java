package br.com.pontek.service.sistema.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.model.sistema.Configuracao;
import br.com.pontek.service.sistema.ConfiguracaoService;


@Service
public class ConfiguracaoServiceImp implements ConfiguracaoService ,Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	@Transactional
	public void salvar(Configuracao configuracao) {
		
	}

	@Override
	@Transactional
	public void excluir(Configuracao configuracao) {
		
	}

	@Override
	@Transactional
	public void excluirPorId(Integer id) {
		
	}

	@Override
	@Transactional(readOnly=true)
	public Configuracao buscar(Integer id) {
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Configuracao> listaTodos() {
		return null;
	}

}
