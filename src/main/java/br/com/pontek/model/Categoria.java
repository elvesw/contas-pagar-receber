package br.com.pontek.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Column(name="nome")
	private String nome;

	
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name = "categoria_pai",insertable=false,updatable=false)
	Categoria categoriaPai;
	
	@OneToMany 
	@JoinColumn(name = "categoria_pai") 
	private List<Categoria> listaFilhos = new LinkedList<Categoria>(); 

	@OneToMany(mappedBy = "categoria")
	@OrderBy("id ASC")
	private Set<Lancamento> listaLancamentos;

	/*############# CONSTRUTOR ###################################*/
	public Categoria() {

	}
	


	public Categoria(String nome, Categoria categoriaPai) {
		super();
		this.nome = nome;
		this.categoriaPai = categoriaPai;
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
	
	public Categoria getCategoriaPai() {
		return categoriaPai;
	}
	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}
	public List<Categoria> getListaFilhos() {
		return listaFilhos;
	}
	public void setListaFilhos(List<Categoria> listaFilhos) {
		this.listaFilhos = listaFilhos;
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
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
