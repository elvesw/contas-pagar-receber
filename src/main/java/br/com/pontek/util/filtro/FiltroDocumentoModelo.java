package br.com.pontek.util.filtro;

public class FiltroDocumentoModelo extends FiltroBaseAbstract{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	
	public FiltroDocumentoModelo(String nome) {
		super();
		this.nome = nome;
	}
	public FiltroDocumentoModelo() {

	}
	
	/*GETS E SETS*/
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
