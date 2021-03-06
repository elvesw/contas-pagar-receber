package br.com.pontek.service.entidades;

import java.util.List;

import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.util.filtro.FiltroPessoa;

public interface PessoaService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(Pessoa pessoa);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(Pessoa pessoa);
    void excluirPorId(Integer pessoa_id);
    
/*### METODOS DE BUSCAR ###*/
    Boolean existe(Pessoa pessoa);
    Pessoa existeCadastro(Pessoa pessoa);
    Pessoa buscar(Integer pessoa_id);
    List<Pessoa> listaDePessoas();
    Pessoa buscarPorNomeExato(String nome);
    
/*### Metodos de PAGINA��O LAZY DATATABLE ###*/
    List<Pessoa> filtrados(FiltroPessoa filtro);
	Integer quantidadeFiltrados(FiltroPessoa filtro);
	
	
	List<Pessoa> listaPorPerfil(boolean perfilCliente,boolean perfilFornecedor,boolean perfilFuncionario);
	List<Pessoa> listaComTermoDeBuscaPorPerfil(String termoDeBusca,boolean perfilCliente,boolean perfilFornecedor,boolean perfilFuncionario);
}
