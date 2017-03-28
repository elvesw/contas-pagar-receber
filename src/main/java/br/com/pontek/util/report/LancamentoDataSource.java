package br.com.pontek.util.report;

import java.util.ArrayList;
import java.util.List;

import br.com.pontek.model.financeiro.Lancamento;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class LancamentoDataSource implements JRDataSource {

	private List<Lancamento> listaLancamentos = new ArrayList<Lancamento>();
	private int index = -1;

	public LancamentoDataSource() {
		super();
	}

	public void prepareDataSource(List<Lancamento> lista) {
		this.listaLancamentos = lista;	
	}

	 @Override
	public Object getFieldValue(JRField field) throws JRException {
		 Lancamento lancamento = listaLancamentos.get(index);
		if (field.getName().equals("id")) {
			return lancamento.getId();
		} else if (field.getName().equals("nomePessoa")){
			return (lancamento.getPessoa()!=null?lancamento.getPessoa().getNome():"");
		}else if (field.getName().equals("nomeConta")){
			return (lancamento.getConta()!=null?lancamento.getConta().getNome():"");
		} else if (field.getName().equals("valor")) {
			return lancamento.getValor();
		}else if (field.getName().equals("valorAcrescimo")) {
			return lancamento.getValorAcrescimo();
		}else if (field.getName().equals("valorDesconto")) {
			return lancamento.getValorDesconto();
		}else if (field.getName().equals("valorPago")) {
			return lancamento.getValorPago();
		}else if (field.getName().equals("statusLancamento")) {
			return lancamento.getStatusLancamento().toString();
		}else if (field.getName().equals("tipoLancamento")) {
			return lancamento.getTipoLancamento().toString();
		}else if(field.getName().equals("descricao")){
			return lancamento.getDescricao();
		}else if(field.getName().equals("dataVencimento")){
			return lancamento.getDataVencimento();
		}else if(field.getName().equals("dataPagamento")){
			return lancamento.getDataPagamento();
		}else if(field.getName().equals("categoriaNome")){
			return lancamento.getCategoria().getNome(); //up 07/12/16, add categoriaNome no relatorio
		}
		return null;
	}

	 @Override
	public boolean next() throws JRException {
		if (index < listaLancamentos.size() - 1) {
			index++;
			return true;
		}
		return false;
	}
}
