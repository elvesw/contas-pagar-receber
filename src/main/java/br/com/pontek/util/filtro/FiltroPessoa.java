package br.com.pontek.util.filtro;

import br.com.pontek.enums.PerfilDePessoa;

public class FiltroPessoa extends FiltroBaseAbstract{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private boolean cadastroAtivo=true;
	private PerfilDePessoa perfil=PerfilDePessoa.Todos;
	
	public FiltroPessoa(String nome, PerfilDePessoa perfil) {
		super();
		this.nome = nome;
		this.perfil = perfil;
	}
	public FiltroPessoa() {

	}
	
	/*GETS E SETS*/
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isCadastroAtivo() {
		return cadastroAtivo;
	}
	public void setCadastroAtivo(boolean cadastroAtivo) {
		this.cadastroAtivo = cadastroAtivo;
	}
	public PerfilDePessoa getPerfil() {
		return perfil;
	}
	public void setPerfil(PerfilDePessoa perfil) {
		this.perfil = perfil;
	}
	
}
