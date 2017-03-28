package br.com.pontek.enums;

public enum FiltroData {
	Passado_mais_30_dias("Passado +30 dias"),
	Hoje("Hoje"),
	Ontem("Ontem"),
	Amanhã("Amanhã"),
	Está_semana("Está semana"),
	Próxima_semana("Próxima semana"),
	Últimos_7_dias("Últimos 7 dias"),
	Próximos_7_dias("Próximos 7 dias"),
	Últimos_14_dias("Últimos 14 dias"),
	Próximos_14_dias("Próximos 14 dias"),
	Últimos_30_dias("Últimos 30 dias"),
	Próximos_30_dias("Próximos 30 dias"),
	Esse_mês("Esse mês"),
	Mês_passado("Mês passado"),
	Próximo_mês("Próximo mês"),
	Esse_ano("Esse ano"),
	Últimos_12_meses("Últimos 12 meses"),
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
