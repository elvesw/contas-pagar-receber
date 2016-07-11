package br.com.pontek.dao;

import java.util.List;

import br.com.pontek.model.Pessoa;
import br.com.pontek.util.filtro.FiltroPessoa;

public interface PessoaDao extends AbstractDao<Pessoa, Integer> {


	/*PAGINAÇÃO LAZY DATATABLE*/
	List<Pessoa> filtrados(FiltroPessoa filtro);
	Integer quantidadeFiltrados(FiltroPessoa filtro);
}
