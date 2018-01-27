package br.com.pontek.service.financeiro;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontek.model.entidades.Empresa;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.financeiro.Lancamento;
import br.com.pontek.service.entidades.EmpresaService;
import br.com.pontek.util.DataUtil;
import br.com.pontek.util.dto.Recibo;
import br.com.pontek.util.jsf.CurrencyWriter;

@Service(value="reciboService")
public class ReciboService {
	
	@Autowired private EmpresaService empresaService;
	private Empresa empresa=new Empresa();
	
	private Recibo recibo;
	
	//caixaBean
		public Recibo gerarRecibo (Lancamento lancamento) {
			recibo=new Recibo();
			recibo.setValor(lancamento.getValor());
			CurrencyWriter cw=CurrencyWriter.getInstance();
			recibo.setValorPorExtenso(cw.write(lancamento.getValor()));
			recibo.setDescricao(lancamento.getDescricao());
			recibo.setDataPagamento(DataUtil.ddMMyyyy(lancamento.getDataPagamento()));
			if(lancamento.getPessoa().getEhResponsavelFinanceiro()){
				recibo.setNome(lancamento.getPessoa().getNome());
				if(StringUtils.isNotEmpty(lancamento.getPessoa().getCpfCnpj()))
					recibo.setDocumento(lancamento.getPessoa().getCpfCnpj().length()>14?" CNPJ:"+lancamento.getPessoa().getCpfCnpj():" CPF:"+lancamento.getPessoa().getCpfCnpj());
			}else{
				recibo.setNome(lancamento.getPessoa().getNomeResponsavel());
				if(StringUtils.isNotEmpty(lancamento.getPessoa().getCpfResponsavel()))
					recibo.setDocumento(" CPF:"+lancamento.getPessoa().getCpfResponsavel()+", responsável por "+lancamento.getPessoa().getNome()+" ");
			}
			return carregarDadosEmpresa(recibo);
		}
		
		//reciboBean
		public Recibo novoLancamentoComRecibo (Pessoa pessoa, BigDecimal valor, String descricao){
			recibo=new Recibo();
			recibo.setValor(valor);
			CurrencyWriter cw=CurrencyWriter.getInstance();
			recibo.setValorPorExtenso(cw.write(valor));
			recibo.setDescricao(descricao);
			recibo.setDataPagamento(DataUtil.ddMMyyyy(new Date()));
			if(pessoa.getEhResponsavelFinanceiro()){
				recibo.setNome(pessoa.getNome());
				if(StringUtils.isNotEmpty(pessoa.getCpfCnpj()))
					recibo.setDocumento(pessoa.getCpfCnpj().length()>14?" CNPJ:"+pessoa.getCpfCnpj():" CPF:"+pessoa.getCpfCnpj());
			}else{
				recibo.setNome(pessoa.getNomeResponsavel());
				if(StringUtils.isNotEmpty(pessoa.getCpfResponsavel()))
					recibo.setDocumento(" CPF:"+pessoa.getCpfResponsavel());
			}
			return carregarDadosEmpresa(recibo);
		}
		
		//lancamentoBean
		public Recibo pagarComRecibo (Lancamento lancamento){
			recibo=new Recibo();
			return carregarDadosEmpresa(recibo);
		}
		
		//DADOS DA EMPRESA
		public Recibo carregarDadosEmpresa(Recibo recibo) {
			//if(empresa.getId()==null)
				empresa=empresaService.carregarDados();	
			recibo.setNomeEmpresa(empresa.getNomeEmpresa());
			recibo.setTelefoneEmpresa("Fone: "+empresa.getTelefone1());
			recibo.setEmailEmpresa("Email: "+empresa.getEmail());
			recibo.setEnderecoEmpresa(empresa.getLogradouro()+" "+empresa.getNumero()+" "+empresa.getBairro()+" "+empresa.getCidade()+" - "+empresa.getUf());
			recibo.setDataHoje(empresa.getCidade()+", "+DataUtil.extenso(new Date()));
			recibo.setCnpjEmpresa(empresa.getCnpj());
			recibo.setLogoEmpresa(empresa.getLogo());
			return recibo;
		}
		
		//ATUALIZA RECIBO VIA AJAX
		public Recibo updateReciboViaAjax (Lancamento lancamento) {
			//lancamento.setValor(formatarValor(lancamento.getValor()));
			recibo=new Recibo();
			CurrencyWriter cw=CurrencyWriter.getInstance();
			if((lancamento.getValor()!=null)&&(lancamento.getValor()!=BigDecimal.ZERO)){
				lancamento.setValor(formatarValor(lancamento.getValor()));
				recibo.setValorPorExtenso(cw.write(lancamento.getValor()));				
			}
			recibo.setValor(lancamento.getValor());
			recibo.setDescricao(lancamento.getDescricao());
			recibo.setDataPagamento(DataUtil.ddMMyyyy(new Date()));
			if(lancamento.getPessoa()!=null) {
				if(lancamento.getPessoa().getEhResponsavelFinanceiro()){
					recibo.setNome(lancamento.getPessoa().getNome());
					if(StringUtils.isNotEmpty(lancamento.getPessoa().getCpfCnpj()))
						recibo.setDocumento(lancamento.getPessoa().getCpfCnpj().length()>14?" CNPJ:"+lancamento.getPessoa().getCpfCnpj():" CPF:"+lancamento.getPessoa().getCpfCnpj());
				}else{
					recibo.setNome(lancamento.getPessoa().getNomeResponsavel());
					if(StringUtils.isNotEmpty(lancamento.getPessoa().getCpfResponsavel()))
						recibo.setDocumento(" (CPF:"+lancamento.getPessoa().getCpfResponsavel()+"), responsável por "+lancamento.getPessoa().getNome()+" ");
				}
			}
			return carregarDadosEmpresa(recibo);
		}
		
		/**Formatando 2 casas decimais e Validando o valor de BigDecimal caso seja null*/
		private BigDecimal formatarValor(BigDecimal valor) {
			valor=valor==null?new BigDecimal(0):valor;
			valor = valor.setScale(2, BigDecimal.ROUND_UP);
			return valor;
		}
		
}
