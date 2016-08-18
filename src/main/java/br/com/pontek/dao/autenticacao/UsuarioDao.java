package br.com.pontek.dao.autenticacao;

import br.com.pontek.dao.AbstractDao;
import br.com.pontek.model.autenticacao.Usuario;

public interface UsuarioDao extends AbstractDao<Usuario, Integer> {

	Usuario buscarPorEmail(String email);
	Usuario verificaLoginSenha(String email, String senha);
}
