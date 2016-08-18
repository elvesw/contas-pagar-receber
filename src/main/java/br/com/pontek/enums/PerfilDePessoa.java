package br.com.pontek.enums;

public enum PerfilDePessoa {
	Todos("Todos"),
	Clientes("Clientes"),
	Funcionários("Funcionários"),
	Fornecedores("Fornecedores");
	
	private String stringEnum;
	private PerfilDePessoa(String stringEnum) {
		this.stringEnum = stringEnum;
	}
	public String getStringEnum() {
		return stringEnum;
	}
	public void setStringEnum(String stringEnum) {
		this.stringEnum = stringEnum;
	}
	
}


