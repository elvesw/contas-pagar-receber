package br.com.pontek.service.autenticacao;

import java.util.List;

import br.com.pontek.model.autenticacao.Usuario;


public interface UsuarioService {
	
/*### METODOS DE SALVAR ###*/
    void salvar(Usuario usuario);
    
/*### METODOS DE EXCLUIR ###*/
    void excluir(Usuario usuario);
    void excluirPorId(Integer usuario_id);
    
/*### METODOS DE BUSCAR ###*/
    Usuario buscar(Integer usuario_id);
    List<Usuario> listaDeUsuarios();
    
	Usuario verificaLoginSenha(String email, String senha);
	Usuario buscarPorEmail(String email);
	Usuario usuarioLogado();
    
}
