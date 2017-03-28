package br.com.pontek.enums;

public enum StatusDeLancamento {
	Pendente("Pendente"),
	Pago("Pago"),
	Cancelado("Cancelado");
	
	private String statusDeLancamento_enum;
	
	private StatusDeLancamento(String statusDeLancamento_enum) {
		this.statusDeLancamento_enum = statusDeLancamento_enum;
	}

	public String getStatusDeLancamento_enum() {
		return statusDeLancamento_enum;
	}

	public void setStatusDeLancamento_enum(String statusDeLancamento_enum) {
		this.statusDeLancamento_enum = statusDeLancamento_enum;
	}

}


