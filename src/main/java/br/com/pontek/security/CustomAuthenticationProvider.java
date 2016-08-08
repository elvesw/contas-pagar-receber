package br.com.pontek.security;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import br.com.pontek.model.Usuario;
import br.com.pontek.service.UsuarioService;
import br.com.pontek.util.MD5;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UsuarioService usuarioService;


	public CustomAuthenticationProvider() {
		super();
	}

	@Override
	public Authentication authenticate(final Authentication authentication)
			throws AuthenticationException {
		System.out.println("ANTES - NOME :" +authentication.getName());
		
		WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
		String ip = details.getRemoteAddress();
		System.out.println("ENDEREÇO IP: "+ip);

		String email = authentication.getName();
		String senha = authentication.getCredentials().toString();

		senha = convertMD5(senha);
		
		Usuario user = usuarioService.verificaLoginSenha(email, senha);
		verificarLoginESenha(user);
		verificaUsuarioAtivo(user);


		if (user != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
			UserDetails userDetails = new User(email,senha,grantedAuthorities);
			return new UsernamePasswordAuthenticationToken(userDetails, senha, grantedAuthorities);
		} else {
			return null;
		}
		
		
	}
	
	private String convertMD5(String senha){
		try {
			senha = MD5.convertPasswordToMD5(senha);
		} catch (NoSuchAlgorithmException e) {
			throw new BadCredentialsException("Erro: "+ e.getMessage());
		}
		return senha;
	}
	
	private void verificarLoginESenha(Usuario usuario) {
		if (usuario == null) {
			throw new BadCredentialsException("Login e/ou senha inválidos");
		}
	}
	private void verificaUsuarioAtivo(Usuario usuario){
		if (!usuario.isCadastroAtivo()) {
			throw new BadCredentialsException("Usuário desativado");
		}
	}


	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	
}
