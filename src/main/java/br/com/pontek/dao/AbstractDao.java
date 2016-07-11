package br.com.pontek.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

public interface AbstractDao<T extends Serializable, PK extends Serializable> {
	
	//Metodos de salvar
	void salvarEntity(T entity);
    void atualizarEntity(T entity);
    List<T> salvarAllEntitys(List<T> entitys);
    T salvarReturnEntity(T entity);
    
    //Metodos de excluir
    void excluirEntity(T entity);
    void excluirEntityPorId(PK id);
    void excluirAllEntitys(List<T> entitys);
 
    //Metodos de buscar
    T buscarEntity(PK id);
    List<T> listaDeEntitys();
    
    //Metodos auxiliares
    Long quantidadeDeEntitys();
    Integer ultimoIdDeEntity();
    void detachEntity(T entity);
    EntityManager getEm();
    
}
