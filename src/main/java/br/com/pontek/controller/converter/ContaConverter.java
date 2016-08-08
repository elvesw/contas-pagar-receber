package br.com.pontek.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pontek.model.Conta;
import br.com.pontek.service.ContaService;

@Component("contaConverter")
public class ContaConverter implements Converter {

	@Autowired
	ContaService contaService;
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Conta conta = new Conta();
		if (value != null && !value.isEmpty()) {
			conta.setId(Integer.parseInt(value));
			conta=contaService.buscar(conta.getId());
		}
		return conta.getId() == null ? null : conta;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (value instanceof Conta){
				System.out.println("ContaConverter.getAsString() : " +((Conta) value).getNome());
			}
			Integer id = ((Conta) value).getId();
			String retorno = (id == null ? null : id.toString());
			return retorno;
		}
		return "";
	}

}