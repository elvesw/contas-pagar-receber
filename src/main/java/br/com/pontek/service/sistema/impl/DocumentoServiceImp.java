package br.com.pontek.service.sistema.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.sistema.DocumentoDao;
import br.com.pontek.model.sistema.Documento;
import br.com.pontek.service.sistema.DocumentoService;
import br.com.pontek.util.filtro.FiltroDocumento;


@Service
public class DocumentoServiceImp implements DocumentoService ,Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	DocumentoDao documentoDao;
	
	@Override
	@Transactional
	public void salvar(Documento documento) {
		if(documento.getId()!=null){
			documentoDao.atualizarEntity(documento);
		}else{
			documentoDao.salvarEntity(documento);			
		}
	}
	
	@Override
	@Transactional
	public Documento salvarRetorno(Documento documento) {
		if(documento.getId()!=null){
			return documentoDao.salvarReturnEntity(documento);
		}else{
			return documentoDao.salvarReturnEntity(documento);			
		}
	}

	@Override
	@Transactional
	public void excluir(Documento documento) {
		documentoDao.excluirEntityPorId(documento.getId());
	}

	@Override
	@Transactional
	public void excluirPorId(Integer documento_id) {
		documentoDao.excluirEntityPorId(documento_id);
	}

	@Override
	@Transactional
	public Documento buscar(Integer documento_id) {
		return documentoDao.buscarEntity(documento_id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Documento> listaDeDocumentos() {
		return documentoDao.listaDeEntitys();
	}

	/*Metodos de PAGINAÇÃO LAZY DATATABLE*/
	@Override
	@Transactional(readOnly = true)
	public List<Documento> filtrados(FiltroDocumento filtro) {
		return documentoDao.filtrados(filtro);
	}
	@Override
	@Transactional(readOnly = true)
	public Integer quantidadeFiltrados(FiltroDocumento filtro) {
		return documentoDao.quantidadeFiltrados(filtro);
	}
	

}
