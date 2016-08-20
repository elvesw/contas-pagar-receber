package br.com.pontek.model.sistema;

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

@Entity
@Table(name = "configuracao")
public class Configuracao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	
	//CONFIGURAÇÕES DE EMAIL
	@Email(message="Email inválido")
	@Column(name = "email_smpt")
	private String emailSmtp;
	
	@Column(name = "senha_email_smtp")
	private String senhaEmailSmtp;
	
	@Email(message="Email inválido")
	@Column(name = "email_empresa_para_responder")
	private String emailEmpresaParaResponder;
	
	@Email(message="Email inválido")
	@Column(name = "email_empresa_para_copia")
	private String emailEmpresaParaCopia;
	
	
	//CONFIGURAÇÕES DE LANÇAMENTOS
	@Column(name = "exibir_clientes_em_lancamentos_entrada")
	private boolean exibirClientesEmLancamentosEntrada=false;
	
	@Column(name = "exibir_funcionarios_em_lancamentos_entrada")
	private boolean exibirFuncionariosEmLancamentosEntrada=false;
	
	@Column(name = "exibir_fornecedores_em_lancamentos_entrada")
	private boolean exibirFornecedoresEmLancamentosEntrada=false;
	
	@Column(name = "exibir_clientes_em_lancamentos_saida")
	private boolean exibirClientesEmLancamentosSaida=false;
	
	@Column(name = "exibir_funcionarios_em_lancamentos_saida")
	private boolean exibirFuncionariosEmLancamentosSaida=false;
	
	@Column(name = "exibir_fornecedores_em_lancamentos_saida")
	private boolean exibirFornecedoresEmLancamentosSaida=false;
	
	
	//CONFIGURAÇÕES DE CADASTRO
	@Column(name = "exibir_outros_perfis_no_cliente")
	private boolean exibirOutrosPerfisNoCliente=false;
	
	@Column(name = "exibir_outros_perfis_no_fornecedor")
	private boolean exibirOutrosPerfisNoFornecedor=false;
	
	@Column(name = "exibir_outros_perfis_no_funcionario")
	private boolean exibirOutrosPerfisNoFuncionario=false;
	
	
	//OUTROS
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_alteracao")
	private Date dataAlteracao;//ULTIMA ALTERAÇÃO FEITA POR UM USUÁRIO
	

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
	public String getEmailSmtp() {
		return emailSmtp;
	}
	public void setEmailSmtp(String emailSmtp) {
		this.emailSmtp = emailSmtp;
	}
	public String getSenhaEmailSmtp() {
		return senhaEmailSmtp;
	}
	public void setSenhaEmailSmtp(String senhaEmailSmtp) {
		this.senhaEmailSmtp = senhaEmailSmtp;
	}
	public String getEmailEmpresaParaResponder() {
		return emailEmpresaParaResponder;
	}
	public void setEmailEmpresaParaResponder(String emailEmpresaParaResponder) {
		this.emailEmpresaParaResponder = emailEmpresaParaResponder;
	}
	public String getEmailEmpresaParaCopia() {
		return emailEmpresaParaCopia;
	}
	public void setEmailEmpresaParaCopia(String emailEmpresaParaCopia) {
		this.emailEmpresaParaCopia = emailEmpresaParaCopia;
	}
	public boolean isExibirClientesEmLancamentosEntrada() {
		return exibirClientesEmLancamentosEntrada;
	}
	public void setExibirClientesEmLancamentosEntrada(boolean exibirClientesEmLancamentosEntrada) {
		this.exibirClientesEmLancamentosEntrada = exibirClientesEmLancamentosEntrada;
	}
	public boolean isExibirFuncionariosEmLancamentosEntrada() {
		return exibirFuncionariosEmLancamentosEntrada;
	}
	public void setExibirFuncionariosEmLancamentosEntrada(boolean exibirFuncionariosEmLancamentosEntrada) {
		this.exibirFuncionariosEmLancamentosEntrada = exibirFuncionariosEmLancamentosEntrada;
	}
	public boolean isExibirFornecedoresEmLancamentosEntrada() {
		return exibirFornecedoresEmLancamentosEntrada;
	}
	public void setExibirFornecedoresEmLancamentosEntrada(boolean exibirFornecedoresEmLancamentosEntrada) {
		this.exibirFornecedoresEmLancamentosEntrada = exibirFornecedoresEmLancamentosEntrada;
	}
	public boolean isExibirClientesEmLancamentosSaida() {
		return exibirClientesEmLancamentosSaida;
	}
	public void setExibirClientesEmLancamentosSaida(boolean exibirClientesEmLancamentosSaida) {
		this.exibirClientesEmLancamentosSaida = exibirClientesEmLancamentosSaida;
	}
	public boolean isExibirFuncionariosEmLancamentosSaida() {
		return exibirFuncionariosEmLancamentosSaida;
	}
	public void setExibirFuncionariosEmLancamentosSaida(boolean exibirFuncionariosEmLancamentosSaida) {
		this.exibirFuncionariosEmLancamentosSaida = exibirFuncionariosEmLancamentosSaida;
	}
	public boolean isExibirFornecedoresEmLancamentosSaida() {
		return exibirFornecedoresEmLancamentosSaida;
	}
	public void setExibirFornecedoresEmLancamentosSaida(boolean exibirFornecedoresEmLancamentosSaida) {
		this.exibirFornecedoresEmLancamentosSaida = exibirFornecedoresEmLancamentosSaida;
	}
	public boolean isExibirOutrosPerfisNoCliente() {
		return exibirOutrosPerfisNoCliente;
	}
	public void setExibirOutrosPerfisNoCliente(boolean exibirOutrosPerfisNoCliente) {
		this.exibirOutrosPerfisNoCliente = exibirOutrosPerfisNoCliente;
	}
	public boolean isExibirOutrosPerfisNoFornecedor() {
		return exibirOutrosPerfisNoFornecedor;
	}
	public void setExibirOutrosPerfisNoFornecedor(boolean exibirOutrosPerfisNoFornecedor) {
		this.exibirOutrosPerfisNoFornecedor = exibirOutrosPerfisNoFornecedor;
	}
	public boolean isExibirOutrosPerfisNoFuncionario() {
		return exibirOutrosPerfisNoFuncionario;
	}
	public void setExibirOutrosPerfisNoFuncionario(boolean exibirOutrosPerfisNoFuncionario) {
		this.exibirOutrosPerfisNoFuncionario = exibirOutrosPerfisNoFuncionario;
	}
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
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
