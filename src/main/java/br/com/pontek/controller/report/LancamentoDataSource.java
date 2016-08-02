package br.com.pontek.controller.report;

import java.util.ArrayList;
import java.util.List;

import br.com.pontek.model.Lancamento;
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
		} else if (field.getName().equals("nomePessoa")) {
			return lancamento.getPessoa().getNome();
		} else if (field.getName().equals("valor")) {
			return lancamento.getValor();
		}else if (field.getName().equals("valorAcrescimo")) {
			return lancamento.getValorAcrescimo();
		}else if (field.getName().equals("valorDesconto")) {
			return lancamento.getValorDesconto();
		}else if (field.getName().equals("valorPago")) {
			return lancamento.getValorPago();
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
