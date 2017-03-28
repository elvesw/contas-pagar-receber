package br.com.pontek.enums;

public enum FiltroTipoLancamento {
	Todos("Todos"),
	Somente_entrada("Somente entrada"),
	Somente_saída("Somente saída");
	
	
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
