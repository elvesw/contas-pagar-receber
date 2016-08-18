package br.com.pontek.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractDaoImpl<T extends Serializable, PK extends Serializable> implements AbstractDao<T, PK> {

	@PersistenceContext
	private EntityManager em;

	private final Class<T> entityType;

	/**
	 * @param type
	 *            Entity type
	 */
	public AbstractDaoImpl(final Class<T> type) {
		this.entityType = type;
	}

	/**
	 * @return Entity manager
	 */
	public final EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	/**
	 * Returns entity type.
	 * @return Entity type
	 */
	public final Class<T> getType() {
		return entityType;
	}



	// Metodos de salvar
	public final void salvarEntity(final T entity) {
		 em.persist(entity);
	}

	public final void atualizarEntity(final T entity) {
		em.merge(entity);
	}

	public final T salvarReturnEntity(final T entity) {
		return em.merge(entity);
	}

	public final List<T> salvarAllEntitys(final List<T> entitys) {
		List<T> result = new ArrayList<T>();
		if (entitys == null) {
			return result;
		}
		for (T entity : entitys) {
			result.add(em.merge(entity));
		}
		return result;
	}

	// Metodos de excluir
	public final void excluirEntity(final T entity) {
		em.remove(entity);
		em.flush();
	}

	public final void excluirAllEntitys(final List<T> entitys) {
		if (entitys != null) {
			for (T e : entitys) {
				em.remove(e);
			}
		}
	}

	public final void excluirEntityPorId(final PK id) {
		em.remove(em.find(entityType, id));
		em.flush();
	}

	// Metodos de buscar
	public final T buscarEntity(final PK id) {
		return em.find(entityType, id);

	}
	public final List<T> listaDeEntitys() {
		return em.createQuery("from " + entityType.getName(), entityType).getResultList();
	}

    //Metodos auxiliares
	public final Long quantidadeDeEntitys() {
		return (Long) em.createQuery("select count(*) from " + entityType.getName()).getSingleResult();
	}
	public final Integer ultimoIdDeEntity() {
		Integer id = (Integer) em.createQuery("SELECT max (id) FROM " + entityType.getName(), Integer.class)
				.getSingleResult();
		return id;
	}
	public final void detachEntity(T entity) {
		em.detach(entity);
		em.flush();
	}

}
