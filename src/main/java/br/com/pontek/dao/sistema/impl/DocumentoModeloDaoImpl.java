package br.com.pontek.dao.sistema.impl;

import org.springframework.stereotype.Repository;

import br.com.pontek.dao.AbstractDaoImpl;
import br.com.pontek.dao.sistema.DocumentoModeloDao;
import br.com.pontek.model.sistema.DocumentoModelo;

@Repository(value = "documentoModeloDao")
public class DocumentoModeloDaoImpl extends AbstractDaoImpl<DocumentoModelo, Integer> implements DocumentoModeloDao {

	public DocumentoModeloDaoImpl() {
		super(DocumentoModelo.class);
	}

	
}
