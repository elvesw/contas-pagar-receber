package br.com.pontek.service;

import java.util.List;

import br.com.pontek.model.Pessoa;
import br.com.pontek.util.filtro.FiltroPessoa;

public interface PessoaService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(Pessoa pessoa);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(Pessoa pessoa);
    void excluirPorId(Integer pessoa_id);
    
/*### METODOS DE BUSCAR ###*/
    Pessoa buscar(Integer pessoa_id);
    List<Pessoa> listaDePessoas();
    
/*### Metodos de PAGINAÇÃO LAZY DATATABLE ###*/
    List<Pessoa> filtrados(FiltroPessoa filtro);
	Integer quantidadeFiltrados(FiltroPessoa filtro);
}
