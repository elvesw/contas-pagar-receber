package br.com.pontek.util.filtro;

import br.com.pontek.enums.TipoDeLancamento;

public class FiltroDescricao extends FiltroBaseAbstract{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private TipoDeLancamento tipoDeLancamento=TipoDeLancamento.SA�DA;
	
	public FiltroDescricao(String nome, TipoDeLancamento tipoDeLancamento) {
		this.nome = nome;
		this.tipoDeLancamento = tipoDeLancamento;
	}
	public FiltroDescricao() {

	}
	/*########### GETS E SETS ###################*/
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public TipoDeLancamento getTipoDeLancamento() {
		return tipoDeLancamento;
	}
	public void setTipoDeLancamento(TipoDeLancamento tipoDeLancamento) {
		this.tipoDeLancamento = tipoDeLancamento;
	}	
}
