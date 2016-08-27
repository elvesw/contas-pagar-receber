package br.com.pontek.model.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@Column(name = "nome_empresa")
	private String nomeEmpresa;
	@Column(name = "razao_social")
	private String razaoSocial;
	@Column(name = "telefone1")
	private String telefone1;
	@Column(name = "telefone2")
	private String telefone2;
	@Email(message="Email incorreto")
	@Column(name = "email")
	private String email;
	@Column(name = "site")
	private String site;

	@Column(name = "logo")
	private String logo;
	
	@CNPJ
	@Column(name = "cnpj")
	private String cnpj;
	@Column(name = "ins_estadual")
	private String insEstadual;
	@Column(name = "ins_municipal")
	private String insMunicipal;

	@Column(name = "msg1")
	private String msg1;
	@Column(name = "msg2")
	private String msg2;
	@Column(name = "msg3")
	private String msg3;
	@Column(name = "msg4")
	private String msg4;

	@Column(name = "ultima_alteracao")
	@Temporal(TemporalType.DATE)
	private Date ultima_alteracao;

	/* ######ENDEREÇO######### */
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
	
	/* ###### CONFIGURAÇÕES ######### */
	public Empresa() {

	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getTelefone1() {
		return telefone1;
	}
	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}
	public String getTelefone2() {
		return telefone2;
	}
	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getInsEstadual() {
		return insEstadual;
	}
	public void setInsEstadual(String insEstadual) {
		this.insEstadual = insEstadual;
	}
	public String getInsMunicipal() {
		return insMunicipal;
	}
	public void setInsMunicipal(String insMunicipal) {
		this.insMunicipal = insMunicipal;
	}
	public String getMsg1() {
		return msg1;
	}
	public void setMsg1(String msg1) {
		this.msg1 = msg1;
	}
	public String getMsg2() {
		return msg2;
	}
	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}
	public String getMsg3() {
		return msg3;
	}
	public void setMsg3(String msg3) {
		this.msg3 = msg3;
	}
	public String getMsg4() {
		return msg4;
	}
	public void setMsg4(String msg4) {
		this.msg4 = msg4;
	}
	public Date getUltima_alteracao() {
		return ultima_alteracao;
	}
	public void setUltima_alteracao(Date ultima_alteracao) {
		this.ultima_alteracao = ultima_alteracao;
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
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
