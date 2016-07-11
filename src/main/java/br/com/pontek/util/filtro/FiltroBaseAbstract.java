package br.com.pontek.util.filtro;

import java.io.Serializable;

public abstract class FiltroBaseAbstract implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int primeiroRegistro; //a partir de qual registro começa , padrão zero '0'
	private int quantidadeRegistros;//quantos registro serão buscados
	private String propriedadeOrdenacao;// campo que será ordenado, nome da propriedade do objeto
	private boolean ascendente;// true:ascendente , false:descendente
	
	
	public int getPrimeiroRegistro() {
		return primeiroRegistro;
	}
	public void setPrimeiroRegistro(int primeiroRegistro) {
		this.primeiroRegistro = primeiroRegistro;
	}
	public int getQuantidadeRegistros() {
		return quantidadeRegistros;
	}
	public void setQuantidadeRegistros(int quantidadeRegistros) {
		this.quantidadeRegistros = quantidadeRegistros;
	}
	public String getPropriedadeOrdenacao() {
		return propriedadeOrdenacao;
	}
	public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
		this.propriedadeOrdenacao = propriedadeOrdenacao;
	}
	public boolean isAscendente() {
		return ascendente;
	}
	public void setAscendente(boolean ascendente) {
		this.ascendente = ascendente;
	}
	
	

}
