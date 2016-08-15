package br.com.pontek.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name="id")
	private Integer id;
	@Column(name="nome")
	private String nome;
	
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
	
}
