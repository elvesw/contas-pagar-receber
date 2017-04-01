package br.com.pontek.dao.sistema.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.sistema.DocumentoDao;
import br.com.pontek.model.sistema.Documento;

@Repository(value = "documentoDao")
public class DocumentoDaoImpl extends AbstractDaoImpl<Documento, Integer> implements DocumentoDao {

	public DocumentoDaoImpl() {
		super(Documento.class);
	}

	
}
