package br.com.pontek.model.sistema;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.util.DataUtil;

@Entity
@Table(name = "documento")
public class Documento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	
	@NotEmpty(message="O nome é obrigatório")
	@Column(name="nome")
	private String nome;
	
	@Lob
	@NotEmpty(message="Texto é obrigatório")
	@Column(name="texto")
	private String texto;
	
	@Column(name="emitido_por")
	private String emitidoPor;
	
	/* ####OUTRAS#### */
	@NotNull
	@Column(name = "data_emissao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEmissao;
	
	@Lob
	@Column(name = "historico")
	private String historico="";//concatenando informações sobre esse doc, coloque um <br/> para listar depois
	
	@JoinColumn(name = "pessoa", referencedColumnName = "id")
	@ManyToOne(optional = false , fetch = FetchType.LAZY) /*Não colocar Cascate ALL*/
    private Pessoa pessoa;
	
	public Documento() {
		super();
	}
	public Documento(String nome, String texto) {
		super();
		this.nome = nome;
		this.texto = texto;
	}
	public Documento(String nome, String texto, Pessoa pessoa) {
		super();
		this.nome = nome;
		this.texto = texto;
		this.pessoa=pessoa;
	}
	
	public void atualizaHistorico(String up){
		this.historico=this.historico+"<br/>"+DataUtil.ddMMyyyy_HHmmss(new Date())+" <span>"+up+"</span>";
	}
	
	public Documento copia(){
		return new Documento(this.getNome(),this.getTexto(),this.pessoa);
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
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getEmitidoPor() {
		return emitidoPor;
	}
	public void setEmitidoPor(String emitidoPor) {
		this.emitidoPor = emitidoPor;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
		Documento other = (Documento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@PrePersist
	private void preIn(){
		this.atualizaHistorico("Emitido");
		this.dataEmissao=new Date();
	}
	@PreUpdate
	private void preUp(){
		this.atualizaHistorico("Salvo");
	}
}
