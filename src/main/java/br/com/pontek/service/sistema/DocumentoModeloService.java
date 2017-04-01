package br.com.pontek.service.sistema;

import java.util.List;

import br.com.pontek.model.sistema.DocumentoModelo;

public interface  DocumentoModeloService{
	
	/*### METODOS DE SALVAR ###*/
    void salvar(DocumentoModelo documento);
    
    /*### METODOS DE EXCLUIR ###*/
    void excluir(DocumentoModelo documento);
    void excluirPorId(Integer documento_id);
    
    /*### METODOS DE BUSCAR ###*/
    DocumentoModelo buscar(Integer documento_id);
    List<DocumentoModelo> listaDeDocumentos();
}
