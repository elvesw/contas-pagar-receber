package br.com.pontek.service;

import java.util.List;

import br.com.pontek.model.CentroDeCusto;

public interface CentroDeCustoService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(CentroDeCusto centroDeCusto);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(CentroDeCusto centroDeCusto);
    void excluirPorId(Integer id);
    
/*### METODOS DE BUSCAR ###*/
    CentroDeCusto buscar(Integer id);
    List<CentroDeCusto> listaDeCentroDeCustos();
}
