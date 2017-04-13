package br.com.pontek.model.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import br.com.pontek.enums.TipoResponsavel;
import br.com.pontek.model.financeiro.Lancamento;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="id")
	private Integer id;
	@NotEmpty(message="O nome é obrigatório")
	@Column(name="nome")
	private String nome;
	@Column(name = "data_nasc")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;
	@Column(name = "eh_responsavel_financeiro")
	private Boolean ehResponsavelFinanceiro=true;//caso false tem que colocar o nome e cpf de quem é
    @Column(name = "observacoes")
    private String observacoes;
    
    @Column(name = "naturalidade")
    private String naturalidade;
    @Column(name = "naturalidade_uf")
    private String naturalidadeUf;

	/*####FILIAÇÃO#### */
    @Column(name = "nome_pai")
    private String nomePai;
    @Column(name = "nome_mae")
    private String nomeMae;
    
    /*####RESPONSAVEL FINANCEIRO#### */
    @Enumerated(EnumType.STRING)
	@Column(name = "tipo_responsavel")
	private TipoResponsavel tipoResponsavel;
    @Column(name = "nome_responsavel")
    private String nomeResponsavel;
    @CPF
    @Column(name = "cpf_responsavel")
    private String cpfResponsavel;
    
	/* ####PERFIL#### */
	@Column(name="cliente")
	private Boolean cliente=false;
	@Column(name="fornecedor")
	private Boolean fornecedor=false;
	@Column(name="funcionario")
	private Boolean funcionario=false;
	
	
	/* ####DOCUMENTOS#### */
	@Column(name="cpf_cnpj")
	private String cpfCnpj;

	/* ####CONTATOS#### */
	@Column(name = "telefone")
	private String telefone;
	@Email(message="Email inválido")
	@Column(name = "email")
	private String email;
    
	/* ####ENDEREÇO#### */
	@Column(name = "logradouro")
	private String logradouro;
	@Column(name = "bairro")
	private String bairro;
	@Column(name = "numero")
	private String numero;
	@Column(name = "complemento")
	private String complemento;
	@Column(name = "cep")
	private String cep;
	@Column(name = "cidade")
	private String cidade;
	@Column(name = "uf")
	private String uf;
	
	/* ####OUTRAS#### */
	@Column(name = "ultima_alteracao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimaAlteracao;
	@Column(name = "data_cadastro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	@Column(name = "cadastro_ativo")
	private boolean cadastroAtivo=true;

	
	@OneToMany(cascade = CascadeType.ALL ,mappedBy = "pessoa", orphanRemoval = true)
	@OrderBy("id ASC")
	private Set<Lancamento> listaLancamentos;

	/*############# CONSTRUTOR ###################################*/
	public Pessoa() {
		
	}
	
	/**return true é cnpj e false é cpf*/
	public Boolean CpfOuCnpj(){
		if(StringUtils.isNotBlank(this.cpfCnpj))
			return this.getCpfCnpj().length()>14;
		return false;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Boolean getEhResponsavelFinanceiro() {
		return ehResponsavelFinanceiro;
	}
	public void setEhResponsavelFinanceiro(Boolean ehResponsavelFinanceiro) {
		this.ehResponsavelFinanceiro = ehResponsavelFinanceiro;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
    public String getNaturalidade() {
		return naturalidade;
	}
	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}
	public String getNaturalidadeUf() {
		return naturalidadeUf;
	}
	public void setNaturalidadeUf(String naturalidadeUf) {
		this.naturalidadeUf = naturalidadeUf;
	}
	public Boolean getCliente() {
		return cliente;
	}
	public void setCliente(Boolean cliente) {
		this.cliente = cliente;
	}
	public Boolean getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Boolean fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Boolean getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Boolean funcionario) {
		this.funcionario = funcionario;
	}
	public String getNomePai() {
		return nomePai;
	}
	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	public TipoResponsavel getTipoResponsavel() {
		return tipoResponsavel;
	}
	public void setTipoResponsavel(TipoResponsavel tipoResponsavel) {
		this.tipoResponsavel = tipoResponsavel;
	}

	public String getNomeResponsavel() {
		confereRespFinanceiro();
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public String getCpfResponsavel() {
		return cpfResponsavel;
	}
	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public boolean isCadastroAtivo() {
		return cadastroAtivo;
	}
	public void setCadastroAtivo(boolean cadastroAtivo) {
		this.cadastroAtivo = cadastroAtivo;
	}
	public Set<Lancamento> getListaLancamentos() {
		return listaLancamentos;
	}
	public void setListaLancamentos(Set<Lancamento> listaLancamentos) {
		this.listaLancamentos = listaLancamentos;
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
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@PrePersist
	@PreUpdate
	private void confereRespFinanceiro(){
		if(TipoResponsavel.Mae.equals(this.tipoResponsavel)){			
			this.setNomeResponsavel(this.getNomeMae());
		}else if(TipoResponsavel.Pai.equals(this.tipoResponsavel)){
			this.setNomeResponsavel(this.getNomePai());
		}
	}
	
}
