package br.com.pontek.dao.sistema;

import java.util.List;

import br.com.pontek.dao.AbstractDao;
import br.com.pontek.model.sistema.Documento;
import br.com.pontek.util.filtro.FiltroDocumento;

public  interface DocumentoDao extends AbstractDao<Documento, Integer>{
	
	/*PAGINAÇÃO LAZY DATATABLE*/
	List<Documento> filtrados(FiltroDocumento filtro);
	Integer quantidadeFiltrados(FiltroDocumento filtro);

}
