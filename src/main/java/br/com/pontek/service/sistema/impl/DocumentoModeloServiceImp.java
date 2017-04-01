package br.com.pontek.service.sistema.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.sistema.DocumentoModeloDao;
import br.com.pontek.model.sistema.DocumentoModelo;
import br.com.pontek.service.sistema.DocumentoModeloService;


@Service
public class DocumentoModeloServiceImp implements DocumentoModeloService ,Serializable{

	private static final long serialVersionUID = 1L;

	@Autowired
	DocumentoModeloDao documentoModeloDao;
	

	@Override
	@Transactional
	public void salvar(DocumentoModelo relatorio) {
		if(relatorio.getId()!=null){
			documentoModeloDao.atualizarEntity(relatorio);
		}else{
			documentoModeloDao.salvarEntity(relatorio);			
		}
	}

	@Override
	@Transactional
	public void excluir(DocumentoModelo relatorio) {
		documentoModeloDao.excluirEntityPorId(relatorio.getId());
	}

	@Override
	@Transactional
	public void excluirPorId(Integer relatorio_id) {
		documentoModeloDao.excluirEntityPorId(relatorio_id);
	}

	@Override
	@Transactional(readOnly=true)
	public DocumentoModelo buscar(Integer relatorio_id) {
		return documentoModeloDao.buscarEntity(relatorio_id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<DocumentoModelo> listaDeDocumentos() {
		return documentoModeloDao.listaDeEntitys();
	}

}
