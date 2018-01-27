package br.com.pontek.controller.financeiro;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.pontek.enums.PerfilDePessoa;
import br.com.pontek.enums.StatusDeLancamento;
import br.com.pontek.enums.TipoDeLancamento;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.financeiro.Categoria;
import br.com.pontek.model.financeiro.Conta;
import br.com.pontek.model.financeiro.Lancamento;
import br.com.pontek.service.entidades.PessoaService;
import br.com.pontek.service.financeiro.LancamentoService;
import br.com.pontek.service.financeiro.ReciboService;
import br.com.pontek.util.dto.Recibo;
import br.com.pontek.util.filtro.FiltroPessoa;
import br.com.pontek.util.jsf.FacesUtil;

@ManagedBean(name = "reciboBean")
@Controller
@Scope("view")
public class ReciboBean {
	
	@Autowired private ReciboService reciboService;
	@Autowired private PessoaService pessoaService;
	@Autowired private LancamentoService lancamentoService;
	
	private Recibo recibo;
	private Lancamento lancamento;

	public ReciboBean() {

	}
	
	@PostConstruct
	private void init() {
		lancamento=new Lancamento();
		recibo=new Recibo();
		recibo=reciboService.carregarDadosEmpresa(recibo);
	}
	
	public void atualizarReciboViaAjax (){
		recibo = reciboService.updateReciboViaAjax(lancamento);
	}
	
	/**Função do autocomplete de nomes para o form cadastro*/	
	public List<Pessoa> autoCompleteNomes(String nome) {
			FiltroPessoa filtroPessoa = new FiltroPessoa(nome, PerfilDePessoa.Clientes);
			List<Pessoa> ListTempPessoa = pessoaService.filtrados(filtroPessoa);
	    return ListTempPessoa;
	 }
	/**Função do autocomplete de descrição já cadastrados*/	
	public List<String> autoCompleteDescricao(String nome) {
			List<String> lista = lancamentoService.listaDescricoesLancamentos(nome);
	    return lista;
	 }
	
	public boolean validaForm(Lancamento lancamento) {
		if((lancamento.getValor()==null)||(lancamento.getValor()==BigDecimal.ZERO)) {
			return true;
		}
		if((lancamento.getPessoa()==null)||(lancamento.getPessoa().getId()==null)) {
			return true;
		}
		if(StringUtils.isBlank(lancamento.getDescricao())) {
			return true;
		}
		return false;
	}
	
	
	public void salvar(ActionEvent actionevent) {
		try {
			lancamento.setTipoLancamento(TipoDeLancamento.ENTRADA);
			lancamento.setDataVencimento(new Date());
			Categoria categoria = new Categoria();
			categoria.setId(1);
			lancamento.setCategoria(categoria);
			Conta conta = new Conta();
			conta.setId(1);
			lancamento.setConta(conta);
			lancamento.setDataPagamento(new Date());
			
			lancamento.setObservacao("a"+System.currentTimeMillis());//a de avulso, que também é único
			lancamento.setStatusLancamento(StatusDeLancamento.Pago);
			lancamentoService.salvar(lancamento);//Salvar lançamento único
			FacesUtil.exibirMensagemSucesso("Lançamento salvo com sucesso!");
		} catch (Exception e) {
			FacesUtil.exibirMensagemErro("Erro : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
/*	########## GETS E SETS ##########*/
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public Recibo getRecibo() {
		return recibo;
	}
	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}
}
