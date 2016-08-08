package br.com.pontek.controller.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pontek.model.Pessoa;
import br.com.pontek.service.PessoaService;

@Component("pessoaConverter")
public class PessoaConverter implements Converter {

	@Autowired
	PessoaService pessoaService;
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pessoa pessoa = new Pessoa();
		if (value != null && !value.isEmpty()) {
			System.out.println("PessoaConverter.getAsObject() "+value);
			pessoa.setId(Integer.parseInt(value));
			pessoa=pessoaService.buscar(pessoa.getId());
		}
		return pessoa.getId() == null ? null : pessoa;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (value instanceof Pessoa){
				System.out.println("PessoaConverter.getAsString() : " +((Pessoa) value).getNome());
			}
			Integer id = ((Pessoa) value).getId();
			String retorno = (id == null ? null : id.toString());
			return retorno;
		}
		return "";
	}

}