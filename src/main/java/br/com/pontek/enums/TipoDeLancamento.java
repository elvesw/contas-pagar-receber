package br.com.pontek.enums;

public enum TipoDeLancamento {
	ENTRADA("Entrada"),
	SAÍDA("Saída");

	private String tipoDelancamento_enum;

	private TipoDeLancamento(String tipoDelancamento_enum) {
		this.tipoDelancamento_enum = tipoDelancamento_enum;
	}
	public String getTipoDelancamento_enum() {
		return tipoDelancamento_enum;
	}

	public void setTipoDelancamento_enum(String tipoDelancamento_enum) {
		this.tipoDelancamento_enum = tipoDelancamento_enum;
	}
}


