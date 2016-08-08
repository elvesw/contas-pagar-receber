package br.com.pontek.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.pontek.dao.UsuarioDao;
import br.com.pontek.model.Usuario;
import br.com.pontek.service.UsuarioService;


@Service
public class UsuarioServiceImp implements UsuarioService ,Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	@Transactional
	public void salvar(Usuario usuario) {
		if(usuario.getId()!=null){
			usuarioDao.atualizarEntity(usuario);
		}else{
			usuarioDao.salvarEntity(usuario);	
		}
	}

	@Override
	@Transactional
	public void excluir(Usuario usuario) {
		usuarioDao.excluirEntityPorId(usuario.getId());
	}

	@Override
	@Transactional
	public void excluirPorId(Integer usuario_id) {
		usuarioDao.excluirEntityPorId(usuario_id);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario buscar(Integer usuario_id) {
		return usuarioDao.buscarEntity(usuario_id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuario> listaDeUsuarios() {
		return usuarioDao.listaDeEntitys();
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario verificaLoginSenha(String email, String senha) {
		return usuarioDao.verificaLoginSenha(email, senha);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario buscarPorEmail(String email) {
		return usuarioDao.buscarPorEmail(email);
	}

}
