package br.com.pontek.service.sistema;

import java.util.List;

import br.com.pontek.model.sistema.Documento;
import br.com.pontek.util.filtro.FiltroDocumento;

public interface  DocumentoService{
	
	/*### METODOS DE SALVAR ###*/
    void salvar(Documento documento);
    Documento salvarRetorno(Documento documento);
    
    /*### METODOS DE EXCLUIR ###*/
    void excluir(Documento documento);
    void excluirPorId(Integer documento_id);
    
    /*### METODOS DE BUSCAR ###*/
    Documento buscar(Integer documento_id);
    List<Documento> listaDeDocumentos();
    
    /*### Metodos de PAGINAÇÃO LAZY DATATABLE ###*/
    List<Documento> filtrados(FiltroDocumento filtro);
	Integer quantidadeFiltrados(FiltroDocumento filtro);
}
