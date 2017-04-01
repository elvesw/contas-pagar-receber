package br.com.pontek.service.sistema.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.sistema.DocumentoDao;
import br.com.pontek.model.sistema.Documento;
import br.com.pontek.service.sistema.DocumentoService;


@Service
public class DocumentoServiceImp implements DocumentoService ,Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	DocumentoDao documentoDao;
	
	@Override
	@Transactional
	public void salvar(Documento documento) {
		if(documento.getId()!=null){
			documento.atualizaHistorico("Salvo");
			documentoDao.atualizarEntity(documento);
		}else{
			documento.atualizaHistorico("Emitido");
			documentoDao.salvarEntity(documento);			
		}
	}
	
	@Override
	@Transactional
	public Documento salvarRetorno(Documento documento) {
		if(documento.getId()!=null){
			documento.atualizaHistorico("Salvo");
			return documentoDao.salvarReturnEntity(documento);
		}else{
			documento.atualizaHistorico("Emitido");
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
		Documento documento = documentoDao.buscarEntity(documento_id);
		documento.atualizaHistorico("Visualizado");
		return documentoDao.salvarReturnEntity(documento);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Documento> listaDeDocumentos() {
		return documentoDao.listaDeEntitys();
	}

	

}
