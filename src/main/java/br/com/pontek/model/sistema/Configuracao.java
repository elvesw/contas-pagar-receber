package br.com.pontek.model.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;

import br.com.pontek.model.entidades.Empresa;

@Entity
@Table(name = "configuracao")
public class Configuracao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	
	@Email(message="Email inválido")
	private String envioEmailParaResponder;
	@Email(message="Email inválido")
	private String envioEmailParaCopia;
	
	@JoinColumn(name = "empresa", referencedColumnName = "id")
	@OneToOne(optional=false)
    private Empresa empresa;
	
	/*######## CONSTRUTORES ########*/
	public Configuracao() {
		
	}
	/*######## GETS E SETS ########*/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEnvioEmailParaResponder() {
		return envioEmailParaResponder;
	}
	public void setEnvioEmailParaResponder(String envioEmailParaResponder) {
		this.envioEmailParaResponder = envioEmailParaResponder;
	}
	public String getEnvioEmailParaCopia() {
		return envioEmailParaCopia;
	}
	public void setEnvioEmailParaCopia(String envioEmailParaCopia) {
		this.envioEmailParaCopia = envioEmailParaCopia;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	/*######## hashCode e equals ########*/
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
		Configuracao other = (Configuracao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
