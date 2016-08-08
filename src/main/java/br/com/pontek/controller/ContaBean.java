package br.com.pontek.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.Conta;
import br.com.pontek.model.Lancamento;
import br.com.pontek.service.ContaService;
import br.com.pontek.util.FacesUtil;

@ManagedBean(name = "contaBean")
@Controller
@Scope("view")
public class ContaBean extends AbstractBean{

	private static final long serialVersionUID = 1L;

	@Autowired
	ContaService contaService;
	
	/*############# NOVO LANÇAMENTO #############*/
	private Conta conta = new Conta();
	/*############# FIM - NOVO LANÇAMENTO #############*/
	
	/*############# LISTAS #############*/
	private List<Conta> listaContas  = new ArrayList<>();
	private BigDecimal saldoGeral=BigDecimal.ZERO;
	private String viewAtiva = estadoDaView.LISTANDO.toString();
	/*############# FIM - LISTAS #############*/
	

	
	/*############# LANÇAR SANGRIA  OU SUPRIMENTO############*/
	private Conta contaSelecionada;
	/*############# FIM - LANÇAR SANGRI OU SUPRIMENTO#############*/
	

	//CONSTRUTOR
	public ContaBean() {
	}

	@PostConstruct
	private void inicializar(){
		listaContas=contaService.listaDeContas();
		for (Conta conta : listaContas) {
			BigDecimal saldoTemp=BigDecimal.ZERO;
			for (Lancamento l : conta.getListaLancamentos()) {
				if(l.getStatusLancamento().equals(StatusDeLancamento.Pago)){
					if(l.getTipoLancamento().equals(TipoDeLancamento.ENTRADA)){
						saldoTemp=saldoTemp.add(l.getValorPago());
					}else if(l.getTipoLancamento().equals(TipoDeLancamento.SAÍDA)){
						saldoTemp=saldoTemp.subtract(l.getValorPago());
					}
				}
			}
			saldoGeral=saldoGeral.add(saldoTemp);
			conta.setSaldo(saldoTemp);
		}//fim for
	}

	public void salvar() {
		try {
			contaService.salvar(conta);
			if(!listaContas.contains(conta)){
				listaContas.add(conta);				
			}
			FacesUtil.exibirMensagemSucesso("Salvo com sucesso!");
			this.reset();
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
		}
	}

	public void novo() {
		reset();
		carregaListas();
		viewAtiva=estadoDaView.INSERINDO.toString();
    } 
	
	public void voltar() {
		reset();
		viewAtiva=estadoDaView.LISTANDO.toString();
    } 

	public void editar(Conta conta){
		if(conta.getId()!=null){
			carregaListas();
			this.conta=conta;
			viewAtiva=estadoDaView.EDITANDO.toString();
		}
	}
	
	public void excluir(Conta conta) {
		try {
			contaService.excluir(conta);
			listaContas.remove(conta);
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro: "+ e.getMessage());
		}
	}
	
	private void reset(){
		conta = new Conta();
		viewAtiva=estadoDaView.LISTANDO.toString();
	}
	
	private void carregaListas(){
		if(listaContas.isEmpty()){
			listaContas=contaService.listaDeContas();
		}	
	}

	/*####### GETS E SETS##########*/
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public List<Conta> getListaContas() {
		return listaContas;
	}
	public void setListaContas(List<Conta> listaContas) {
		this.listaContas = listaContas;
	}
	public String getViewAtiva() {
		return viewAtiva;
	}
	public BigDecimal getSaldoGeral() {
		return saldoGeral;
	}
	public Conta getContaSelecionada() {
		return contaSelecionada;
	}
	public void setContaSelecionada(Conta contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

	/*####### MENSAGENS  ##########*/
	public void addMessageCadastroAtivo() {
		System.out.println("Metodo executado com sucesso");
	}



}
