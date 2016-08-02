package br.com.pontek.enums;

public enum FiltroTipoData {
	Data_de_vencimento("Data de vencimento"),
	Data_de_pagamento("Data de pagamento"),
	Data_de_cadastro("Data de cadastro");
	
	private String stringEnum;
	private FiltroTipoData(String stringEnum) {
		this.stringEnum = stringEnum;
	}
	public String getStringEnum() {
		return stringEnum;
	}
	public void setStringEnum(String stringEnum) {
		this.stringEnum = stringEnum;
	}
}
	
