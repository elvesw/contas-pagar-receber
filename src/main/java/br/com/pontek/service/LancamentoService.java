package br.com.pontek.service;

import java.util.List;

import br.com.pontek.model.Lancamento;

public interface LancamentoService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(Lancamento lancamento);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(Lancamento lancamento);
    void excluirPorId(Integer lancamento_id);
    
/*### METODOS DE BUSCAR ###*/
    Lancamento buscar(Integer lancamento_id);
    List<Lancamento> listaDeMovimentos();
}
