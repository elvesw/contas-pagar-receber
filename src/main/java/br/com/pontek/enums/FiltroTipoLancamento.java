package br.com.pontek.enums;

public enum FiltroTipoLancamento {
	Todos("Todos"),
	Somente_entrada("Somente entrada"),
	Somente_sa�da("Somente sa�da");
	
	
	private String stringEnum;
	private FiltroTipoLancamento(String stringEnum) {
		this.stringEnum = stringEnum;
	}
	public String getStringEnum() {
		return stringEnum;
	}
	public void setStringEnum(String stringEnum) {
		this.stringEnum = stringEnum;
	}
}
