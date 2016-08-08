package br.com.pontek.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.pontek.dao.UsuarioDao;
import br.com.pontek.model.Usuario;


@Repository(value = "usuarioDao")
public class UsuarioDaoImp extends AbstractDaoImpl<Usuario, Integer> implements UsuarioDao {

	public UsuarioDaoImp() {
		super(Usuario.class);
	}

	@Override
	public Usuario verificaLoginSenha(String email, String senha) {
		
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Usuario.class);
		
		criteria.add(Restrictions.like("email", email));
		criteria.add(Restrictions.like("senha", senha));
		Usuario userTemp = (Usuario) criteria.uniqueResult();
		return userTemp;
	}

	@Override
	public Usuario buscarPorEmail(String email) {
		Session session = (Session) getEm().getDelegate();
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.like("email", email));
		Usuario userTemp = (Usuario) criteria.uniqueResult();
		return userTemp;
	}

}
