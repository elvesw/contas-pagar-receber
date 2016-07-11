package br.com.pontek.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.enums.TipoDeLancamento;

@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@Enumerated
	@Column(name = "tipo_lancamento")
	private TipoDeLancamento tipoLancamento;

	@Enumerated
	@Column(name = "status_lancamento")
	private StatusDeLancamento statusLancamento;
	
    @Column(name="valor",scale=2,precision=12)  
    private BigDecimal valor; 
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_pagamento")
    private Date dataPagamento;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_vencimento")
    private Date dataVencimento;
    
    @Column(name = "descricao")
    private String descricao;
    
   
	@JoinColumn(name = "pessoa", referencedColumnName = "id")
	@ManyToOne() /*Não colocar Cascate ALL*/
    private Pessoa pessoa;
	
	@JoinColumn(name = "categoria", referencedColumnName = "id")
	@ManyToOne() /*Não colocar Cascate ALL*/
    private Categoria categoria;
	
	@JoinColumn(name = "centro_de_custo", referencedColumnName = "id")
	@ManyToOne() /*Não colocar Cascate ALL*/
    private CentroDeCusto centroDeCusto;

	/*############# CONSTRUTOR ###################################*/
	public Lancamento() {

	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public TipoDeLancamento getTipoLancamento() {
		return tipoLancamento;
	}
	public void setTipoLancamento(TipoDeLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento;
	}
	public StatusDeLancamento getStatusLancamento() {
		return statusLancamento;
	}
	public void setStatusLancamento(StatusDeLancamento statusLancamento) {
		this.statusLancamento = statusLancamento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public CentroDeCusto getCentroDeCusto() {
		return centroDeCusto;
	}
	public void setCentroDeCusto(CentroDeCusto centroDeCusto) {
		this.centroDeCusto = centroDeCusto;
	}

	/*################################################*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
