package br.com.pontek.enums;

public enum Estados {
	
	GO("Goiás"),
	AC("Acre"),
	AL("Alagoas"),
	AP("Amapá"),
	AM("Amazonas"),
	BA("Bahia"),
	CE("Ceará"),
	DF("Distrito Federal"),
	ES("Espírito Santo"),
	MA("Maranhão"),
	MT("Mato Grosso"),
	MS("Mato Grosso do Sul"),
	MG("Minas Gerais"),
	PA("Pará"),
	PB("Paraíba"),
	PR("Paraná"),
	PE("Pernambuco"),
	PI("Piauí"),
	RJ("Rio de Janeiro"),
	RN("Rio Grande do Norte"),
	RS("Rio Grande do Sul"),
	RO("Rondônia"),
	RR("Roraima"),
	SC("Santa Catarina"),
	SP("São Paulo"),
	SE("Sergipe"),
	TO("Tocantins");
	
	private String stringEnum;
	private Estados(String stringEnum) {
		this.stringEnum = stringEnum;
	}
	public String getStringEnum() {
		return stringEnum;
	}
	public void setStringEnum(String stringEnum) {
		this.stringEnum = stringEnum;
	}
}
