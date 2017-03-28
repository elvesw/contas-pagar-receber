package br.com.pontek.enums;

public enum FiltroData {
	Passado_mais_30_dias("Passado +30 dias"),
	Hoje("Hoje"),
	Ontem("Ontem"),
	Amanh�("Amanh�"),
	Est�_semana("Est� semana"),
	Pr�xima_semana("Pr�xima semana"),
	�ltimos_7_dias("�ltimos 7 dias"),
	Pr�ximos_7_dias("Pr�ximos 7 dias"),
	�ltimos_14_dias("�ltimos 14 dias"),
	Pr�ximos_14_dias("Pr�ximos 14 dias"),
	�ltimos_30_dias("�ltimos 30 dias"),
	Pr�ximos_30_dias("Pr�ximos 30 dias"),
	Esse_m�s("Esse m�s"),
	M�s_passado("M�s passado"),
	Pr�ximo_m�s("Pr�ximo m�s"),
	Esse_ano("Esse ano"),
	�ltimos_12_meses("�ltimos 12 meses"),
	Sem_filtro_de_data("Sem filtro de data");
	
	private String stringEnum;
	private FiltroData(String stringEnum) {
		this.stringEnum = stringEnum;
	}
	public String getStringEnum() {
		return stringEnum;
	}
	public void setStringEnum(String stringEnum) {
		this.stringEnum = stringEnum;
	}
	
}
