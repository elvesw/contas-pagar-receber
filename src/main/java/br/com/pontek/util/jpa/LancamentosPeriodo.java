package br.com.pontek.util.jpa;

import java.math.BigDecimal;

public class LancamentosPeriodo {
	
	private String mesReferencia;
	private BigDecimal somaEntradasSomentePago=BigDecimal.ZERO;
	private BigDecimal somaEntradasSomentePendente=BigDecimal.ZERO;
	private BigDecimal somaEntradasSomenteCancelado=BigDecimal.ZERO;
	
	private BigDecimal somaSaidasSomentePago=BigDecimal.ZERO;
	private BigDecimal somaSaidasSomentePendente=BigDecimal.ZERO;
	private BigDecimal somaSaidasSomenteCancelado=BigDecimal.ZERO;


	 /*########### CONSTRUTORES ###########*/
	public LancamentosPeriodo() {
		
	}
	public LancamentosPeriodo(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
	 /*########### GETS E SETS ###########*/
	public String getMesReferencia() {
		return mesReferencia;
	}
	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}
	public BigDecimal getSomaEntradasSomentePago() {
		return somaEntradasSomentePago;
	}
	public void setSomaEntradasSomentePago(BigDecimal somaEntradasSomentePago) {
		this.somaEntradasSomentePago = somaEntradasSomentePago;
	}
	public BigDecimal getSomaEntradasSomentePendente() {
		return somaEntradasSomentePendente;
	}
	public void setSomaEntradasSomentePendente(BigDecimal somaEntradasSomentePendente) {
		this.somaEntradasSomentePendente = somaEntradasSomentePendente;
	}
	public BigDecimal getSomaEntradasSomenteCancelado() {
		return somaEntradasSomenteCancelado;
	}
	public void setSomaEntradasSomenteCancelado(BigDecimal somaEntradasSomenteCancelado) {
		this.somaEntradasSomenteCancelado = somaEntradasSomenteCancelado;
	}
	public BigDecimal getSomaSaidasSomentePago() {
		return somaSaidasSomentePago;
	}
	public void setSomaSaidasSomentePago(BigDecimal somaSaidasSomentePago) {
		this.somaSaidasSomentePago = somaSaidasSomentePago;
	}
	public BigDecimal getSomaSaidasSomentePendente() {
		return somaSaidasSomentePendente;
	}
	public void setSomaSaidasSomentePendente(BigDecimal somaSaidasSomentePendente) {
		this.somaSaidasSomentePendente = somaSaidasSomentePendente;
	}
	public BigDecimal getSomaSaidasSomenteCancelado() {
		return somaSaidasSomenteCancelado;
	}
	public void setSomaSaidasSomenteCancelado(BigDecimal somaSaidasSomenteCancelado) {
		this.somaSaidasSomenteCancelado = somaSaidasSomenteCancelado;
	}
	
/*	if(!somaTodasSaidas.equals(BigDecimal.ZERO))//valida para não da erro divisão por zero
		percentualPagoSaidas=somaSaidasSomentePago.divide(somaTodasSaidas,new MathContext(2, RoundingMode.HALF_UP)).multiply(new BigDecimal(100)).intValue();*/

}
