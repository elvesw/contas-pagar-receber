package br.com.pontek.util.dto;

import java.math.BigDecimal;

public class Recibo {
	
	private boolean via2=false;
	private BigDecimal valor;
	private String valorPorExtenso;
	private String descricao;
	private String dataPagamento;
	private String dataHoje;
	private String nome;
	private String documento;

	private String nomeEmpresa;
	private String telefoneEmpresa;
	private String emailEmpresa;
	private String enderecoEmpresa;
	private String cnpjEmpresa;
	private String logoEmpresa;
	
	
	
	public boolean isVia2() {
		return via2;
	}
	public void setVia2(boolean via2) {
		this.via2 = via2;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getValorPorExtenso() {
		return valorPorExtenso;
	}
	public void setValorPorExtenso(String valorPorExtenso) {
		this.valorPorExtenso = valorPorExtenso;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getDataHoje() {
		return dataHoje;
	}
	public void setDataHoje(String dataHoje) {
		this.dataHoje = dataHoje;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getTelefoneEmpresa() {
		return telefoneEmpresa;
	}
	public void setTelefoneEmpresa(String telefoneEmpresa) {
		this.telefoneEmpresa = telefoneEmpresa;
	}
	public String getEmailEmpresa() {
		return emailEmpresa;
	}
	public void setEmailEmpresa(String emailEmpresa) {
		this.emailEmpresa = emailEmpresa;
	}
	public String getEnderecoEmpresa() {
		return enderecoEmpresa;
	}
	public void setEnderecoEmpresa(String enderecoEmpresa) {
		this.enderecoEmpresa = enderecoEmpresa;
	}
	public String getCnpjEmpresa() {
		return cnpjEmpresa;
	}
	public void setCnpjEmpresa(String cnpjEmpresa) {
		this.cnpjEmpresa = cnpjEmpresa;
	}
	public String getLogoEmpresa() {
		return logoEmpresa;
	}
	public void setLogoEmpresa(String logoEmpresa) {
		this.logoEmpresa = logoEmpresa;
	}
	
	

}
