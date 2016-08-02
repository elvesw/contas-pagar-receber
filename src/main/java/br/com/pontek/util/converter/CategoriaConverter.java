package br.com.pontek.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pontek.model.Categoria;
import br.com.pontek.service.CategoriaService;

@Component("categoriaConverter")
public class CategoriaConverter implements Converter {

	@Autowired
	CategoriaService categoriaService;
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria categoria = new Categoria();
		if (value != null && !value.isEmpty()) {
			System.out.println("CategoriaConverter.getAsObject() "+value);
			categoria.setId(Integer.parseInt(value));
			categoria=categoriaService.buscar(categoria.getId());
		}
		return categoria.getId() == null ? null : categoria;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (value instanceof Categoria){
				System.out.println("CategoriaConverter.getAsString() : " +((Categoria) value).getNome());
			}
			Integer id = ((Categoria) value).getId();
			String retorno = (id == null ? null : id.toString());
			return retorno;
		}
		return "";
	}

}