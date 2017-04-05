package br.com.pontek.util.filtro;

public class FiltroDocumento extends FiltroBaseAbstract{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	
	public FiltroDocumento(String nome) {
		super();
		this.nome = nome;

	}
	public FiltroDocumento() {

	}
	
	/*GETS E SETS*/
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
