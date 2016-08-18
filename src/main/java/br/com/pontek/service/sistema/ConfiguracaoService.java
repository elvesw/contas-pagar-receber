package br.com.pontek.service.sistema;

import java.util.List;

import br.com.pontek.model.sistema.Configuracao;

public interface  ConfiguracaoService{
	/*###METODOS BÁSICOS###*/
    void salvar(Configuracao configuracao);
    void excluir(Configuracao configuracao);
    void excluirPorId(Integer id);
    Configuracao buscar(Integer id);
    List<Configuracao> listaTodos();
	
}
