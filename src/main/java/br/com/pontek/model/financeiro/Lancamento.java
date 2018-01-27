package br.com.pontek.model.financeiro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.util.DataUtil;

@Entity
@Table(name = "lancamento")
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@NotNull(message="Informe o tipo de lançamento")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_lancamento")
	private TipoDeLancamento tipoLancamento;//ENTRADA ou SAIDA

	@NotNull(message="Informe a situação de lançamento")
	@Enumerated(EnumType.STRING)
	@Column(name = "status_lancamento")
	private StatusDeLancamento statusLancamento=StatusDeLancamento.Pendente; //Pendente,Pago,Cancelado;
	
	@NotNull(message="Informe o valor")
    @Column(name="valor",scale=2,precision=12)  
    private BigDecimal valor;//VALOR LANÇADO
    
    @Column(name="valor_desconto",scale=2,precision=12)  
    private BigDecimal valorDesconto;//VALOR DESCONTO EM CIMA DO VALOR LANÇADO
    
    @Column(name="valor_acrescimo",scale=2,precision=12)  
    private BigDecimal valorAcrescimo;//VALOR ACRESCIMO EM CIMA DO VALOR LANÇADO
    
    /*Esse é um valor que pode ser obtido atraves do valor,valorDesconto e valorAcrescimo,
    peso na consiencia, de quebrar a normalização no DB na terceira forma normal 3FN, paranóia de faculdade, 
    eu até tinha feito com @Transient.
    Mais o custo de acessar o banco para fazer um soma total de valores é muito, fiz da forma que simples, foda-se!
    o importante é funcionar melhor*/
    @Column(name="valor_pago",scale=2,precision=12)  
    private BigDecimal valorPago;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "data_pagamento")
    private Date dataPagamento;//DATA PAGAMENTO
    
    @NotNull(message="Informe a data de vencimento")
    @Temporal(TemporalType.DATE)
    @Column(name = "data_vencimento")
    private Date dataVencimento;//DATA VENCIMENTO
    
    @Column(name = "descricao")
    private String descricao;//DESCRIÇÃO DO USUARIO
    
    @Column(name = "observacao")//INFORMAÇÃO SOBRE QUANTIDADES DE LANÇAMENTOS REPETIDOS
    private String observacao;//CODIGO UNICO DE OPERAÇÃO (u-lançamento único) (r-lançamentos repetidos) + data em Timestamp

    @Column(name = "motivo_cancelamento")
    private String motivoCancelamento;//MOTIVO DO CANCELAMENTO OBRIGATÓRIO
    
    @Column(name = "motivo_estorno")
    private String motivoEstorno;//MOTIVO PARA ESTORNAR OBRIGATÓRIO
    
    /* ####OUTRAS#### */
    @NotNull(message="A data de alteração é null")
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_alteracao")
	private Date dataAlteracao;//ULTIMA ALTERAÇÃO FEITA POR UM USUÁRIO
	@NotNull(message="A data de cadastro é null")
	@Column(name = "data_cadastro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;// DATA QUE FOI CRIADO O LANÇAMENTO
    
   
	@JoinColumn(name = "pessoa", referencedColumnName = "id")
	@ManyToOne(optional = true , fetch = FetchType.LAZY) /*Não colocar Cascate ALL*/
    private Pessoa pessoa;
	
	@JoinColumn(name = "categoria", referencedColumnName = "id")
	@ManyToOne(optional = true , fetch = FetchType.LAZY,cascade = CascadeType.DETACH) /*Não colocar Cascate ALL*/
    private Categoria categoria;
	
	@JoinColumn(name = "conta", referencedColumnName = "id")
	@ManyToOne(optional = true , fetch = FetchType.LAZY) /*Não colocar Cascate ALL*/
    private Conta conta;

	/*############# CONSTRUTOR ###################################*/
	public Lancamento() {
		if(conta==null) conta=new Conta();
		if(pessoa==null) pessoa = new Pessoa();
		if(categoria==null) categoria=new Categoria();
	}

	public Lancamento(Integer id, TipoDeLancamento tipoLancamento, StatusDeLancamento statusLancamento,
			BigDecimal valor, BigDecimal valorDesconto, BigDecimal valorAcrescimo, BigDecimal valorPago,
			Date dataPagamento, Date dataVencimento, String descricao, String observacao, String motivoCancelamento, Date dataAlteracao, Date dataCadastro, Pessoa pessoa,
			Categoria categoria, Conta conta) {
		super();
		this.id = id;
		this.tipoLancamento = tipoLancamento;
		this.statusLancamento = statusLancamento;
		this.valor = valor;
		this.valorDesconto = valorDesconto;
		this.valorAcrescimo = valorAcrescimo;
		this.valorPago = valorPago;
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
		this.descricao = descricao;
		this.observacao = observacao;
		this.motivoCancelamento = motivoCancelamento;
		this.dataAlteracao = dataAlteracao;
		this.dataCadastro = dataCadastro;
		this.pessoa = pessoa;
		this.categoria = categoria;
		this.conta = conta;
	}
	/*############# FUNÇÕES ###################################*/
	/**Função que valida se a data e vencimento esta em atrazo em relaçao a data de hoje usada no datatable */
	public boolean getLancamentoVencido(){
		if(this.statusLancamento==StatusDeLancamento.Pendente){
			//Se a data de hoje for maior , return true
			if(!DataUtil.comparaDataInicialDataFinal(this.dataVencimento, new Date())){
				return true;
			}	
		}
		return false;
	}
	/*############# GETS E SETS ###################################*/
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
	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}
	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}
	public BigDecimal getValorAcrescimo() {
		return valorAcrescimo;
	}
	public void setValorAcrescimo(BigDecimal valorAcrescimo) {
		this.valorAcrescimo = valorAcrescimo;
	}
	public BigDecimal getValorPago() {
		return valorPago;
	}
	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
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
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}
	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}
	public String getMotivoEstorno() {
		return motivoEstorno;
	}
	public void setMotivoEstorno(String motivoEstorno) {
		this.motivoEstorno = motivoEstorno;
	}
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
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
