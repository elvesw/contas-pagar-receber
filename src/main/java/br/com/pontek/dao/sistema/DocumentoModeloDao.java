package br.com.pontek.dao.sistema;

import java.util.List;

import br.com.pontek.dao.AbstractDao;
import br.com.pontek.model.sistema.DocumentoModelo;
import br.com.pontek.util.filtro.FiltroDocumentoModelo;

public  interface DocumentoModeloDao extends AbstractDao<DocumentoModelo, Integer>{

	/*PAGINAÇÃO LAZY DATATABLE*/
	List<DocumentoModelo> filtrados(FiltroDocumentoModelo filtro);
	Integer quantidadeFiltrados(FiltroDocumentoModelo filtro);
}
