package br.com.pontek.model.financeiro;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.pontek.enums.TipoDeLancamento;

/**Um model auxiliar somente para armazenar as descrições de lançamentos 
 * e com a possibilidade de exclusão de algumas sem relevancia.*/
@Entity
@Table(name = "descricao")
public class Descricao implements Serializable {
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Basic(optional = false)
		@Column(name = "id")
		private Integer id;
		@NotEmpty(message="Informe o nome")
		@Column(name="nome")
		private String nome;
		@NotNull(message="Informe o tipo")
		@Enumerated(EnumType.STRING)
		@Column(name = "tipo_lancamento")
		private TipoDeLancamento tipoDeLancamento;
		
		public Descricao() {
		
		}

		public Descricao(String nome, TipoDeLancamento tipoDeLancamento) {
			this.nome = nome;
			this.tipoDeLancamento = tipoDeLancamento;
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
		public TipoDeLancamento getTipoDeLancamento() {
			return tipoDeLancamento;
		}
		public void setTipoDeLancamento(TipoDeLancamento tipoDeLancamento) {
			this.tipoDeLancamento = tipoDeLancamento;
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
			Descricao other = (Descricao) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
}
