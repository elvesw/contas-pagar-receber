package br.com.pontek.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pontek.model.sistema.DocumentoModelo;
import br.com.pontek.service.sistema.DocumentoModeloService;

@Component("documentoModeloConverter")
public class DocumentoModeloConverter implements Converter {

	@Autowired
	DocumentoModeloService documentoModeloService;
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		DocumentoModelo documentoModelo = new DocumentoModelo();
		if (value != null && !value.isEmpty()) {
			System.out.println("DocumentoModeloConverter.getAsObject() "+value);
			documentoModelo.setId(Integer.parseInt(value));
			documentoModelo=documentoModeloService.buscar(documentoModelo.getId());
		}
		return documentoModelo.getId() == null ? null : documentoModelo;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			if (value instanceof DocumentoModelo){
				System.out.println("DocumentoModeloConverter.getAsString() : " +((DocumentoModelo) value).getNome());
			}
			Integer id = ((DocumentoModelo) value).getId();
			String retorno = (id == null ? null : id.toString());
			return retorno;
		}
		return "";
	}

}