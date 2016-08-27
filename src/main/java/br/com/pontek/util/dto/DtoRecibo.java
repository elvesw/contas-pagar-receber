package br.com.pontek.util.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import br.com.pontek.model.financeiro.Lancamento;
import br.com.pontek.util.DataUtil;
import br.com.pontek.util.jsf.CurrencyWriter;

public class DtoRecibo {
	
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
	
	
	public DtoRecibo(Lancamento lancamento) {
		this.valor = lancamento.getValor();
		CurrencyWriter cw=CurrencyWriter.getInstance();
		this.valorPorExtenso = cw.write(valor);
		this.descricao = lancamento.getDescricao();
		this.dataPagamento = DataUtil.ddMMyyyy(lancamento.getDataPagamento());
		this.dataHoje =DataUtil.extenso(new Date());
		if(lancamento.getPessoa().getEhResponsavelFinanceiro()){
			this.nome=lancamento.getPessoa().getNome();
			if(StringUtils.isNotEmpty(lancamento.getPessoa().getCpfCnpj()))
				this.documento=lancamento.getPessoa().getCpfCnpj().length()>14?" CNPJ:"+lancamento.getPessoa().getCpfCnpj():" CPF:"+lancamento.getPessoa().getCpfCnpj();
		}else{
			this.nome=lancamento.getPessoa().getNomeResponsavel();
			if(StringUtils.isNotEmpty(lancamento.getPessoa().getCpfResponsavel()))
				this.documento=" CPF:"+lancamento.getPessoa().getCpfResponsavel();
		}
	}
	

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
