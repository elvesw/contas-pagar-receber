package br.com.pontek.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.PessoaDao;
import br.com.pontek.model.Lancamento;
import br.com.pontek.model.Pessoa;
import br.com.pontek.service.PessoaService;
import br.com.pontek.util.filtro.FiltroPessoa;


@Service
public class PessoaServiceImp implements PessoaService ,Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private PessoaDao pessoaDao;

	@Override
	@Transactional
	public void salvar(Pessoa pessoa) {
		if(pessoa.getId()!=null){
			pessoa.setUltimaAlteracao(new Date());
			pessoaDao.atualizarEntity(pessoa);
		}else{
			pessoa.setDataCadastro(new Date());
			pessoa.setUltimaAlteracao(new Date());
			pessoaDao.salvarEntity(pessoa);	
		}
	}

	@Override
	@Transactional
	public void excluir(Pessoa pessoa) {
		pessoaDao.excluirEntityPorId(pessoa.getId());
	}

	@Override
	@Transactional
	public void excluirPorId(Integer pessoa_id) {
		pessoaDao.excluirEntityPorId(pessoa_id);
	}

	@SuppressWarnings("unused")
	private Pessoa validaExcluir(Pessoa pessoa){
		if(!pessoa.getListaLancamentos().isEmpty()){
			Set<Lancamento>lista=pessoa.getListaLancamentos();
			for (Lancamento l : lista){
				l.setPessoa(null);
				l.setDescricao(l.getDescricao()+"");
			}
		}
		return pessoa;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Pessoa buscar(Integer pessoa_id) {
		return pessoaDao.buscarEntity(pessoa_id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Pessoa> listaDePessoas() {
		return pessoaDao.listaDeEntitys();
	}

	/*Metodos de PAGINA��O LAZY DATATABLE*/
	@Override
	@Transactional(readOnly = true)
	public List<Pessoa> filtrados(FiltroPessoa filtro) {
		return pessoaDao.filtrados(filtro);
	}
	@Override
	@Transactional(readOnly = true)
	public Integer quantidadeFiltrados(FiltroPessoa filtro) {
		return pessoaDao.quantidadeFiltrados(filtro);
	}

	

}
