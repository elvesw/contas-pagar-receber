package br.com.pontek.enums;

public enum FiltroStatus {
	Pagos_e_pendentes("Pagos e pendentes"),
	Somente_pendentes("Somente pendentes"),
	Somente_pagos("Somente pagos"),
	Somente_cancelados("Somente cancelados");
	
	private String stringEnum;
	private FiltroStatus(String stringEnum) {
		this.stringEnum = stringEnum;
	}
	public String getStringEnum() {
		return stringEnum;
	}
	public void setStringEnum(String stringEnum) {
		this.stringEnum = stringEnum;
	}
	
}
