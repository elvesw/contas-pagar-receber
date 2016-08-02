package br.com.pontek.service;

import java.util.List;

import br.com.pontek.model.Conta;

public interface ContaService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(Conta conta);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(Conta conta);
    void excluirPorId(Integer id);
    
/*### METODOS DE BUSCAR ###*/
    Conta buscar(Integer id);
    List<Conta> listaDeContas();
}
