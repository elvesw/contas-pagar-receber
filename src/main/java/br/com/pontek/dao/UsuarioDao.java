package br.com.pontek.dao;

import br.com.pontek.model.Usuario;

public interface UsuarioDao extends AbstractDao<Usuario, Integer> {

	Usuario buscarPorEmail(String email);
	Usuario verificaLoginSenha(String email, String senha);
}
