package br.com.pontek.service.financeiro;

import java.util.List;

import br.com.pontek.model.financeiro.Conta;

public interface ContaService {
	/*###METODOS BÁSICOS###*/
    void salvar(Conta conta);
    void excluir(Conta conta);
    void excluirPorId(Integer id);
    Conta buscar(Integer id);
    List<Conta> listaTodos();

}
