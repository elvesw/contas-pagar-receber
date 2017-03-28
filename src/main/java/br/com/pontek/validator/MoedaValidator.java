package br.com.pontek.validator;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("moedaValidator")
public class MoedaValidator implements Validator {
	
	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
		BigDecimal valor = (BigDecimal) value;
	       if(value == null)
				return;				
	        if (valor.intValue() <= 0) {
	    	   FacesMessage message = new FacesMessage("Preencha com o valor válido maior que zero");
	           message.setSeverity(FacesMessage.SEVERITY_ERROR);
	           throw new ValidatorException(message);
	       }

	}
	
}
