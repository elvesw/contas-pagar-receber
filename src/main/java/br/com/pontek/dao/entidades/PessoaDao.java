package br.com.pontek.dao.entidades;

import java.util.List;

import br.com.pontek.dao.AbstractDao;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.util.filtro.FiltroPessoa;

public interface PessoaDao extends AbstractDao<Pessoa, Integer> {


	/*PAGINAÇÃO LAZY DATATABLE*/
	List<Pessoa> filtrados(FiltroPessoa filtro);
	Integer quantidadeFiltrados(FiltroPessoa filtro);
	
	List<Pessoa> listaPorPerfil(boolean perfilCliente,boolean perfilFornecedor,boolean perfilFuncionario);
}
