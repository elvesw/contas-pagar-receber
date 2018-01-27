package br.com.pontek.util.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("moedaConverter")
public class MoedaConverter implements Converter {

	public BigDecimal getAsObject(FacesContext fc, UIComponent component,String value) {
		if (value == null || "".equals(value)) {
		    return BigDecimal.ZERO;
		}
		//10,00 ~> 10.00 --- 1.000,00 ~> 1000.00
		String valor = value.replace(".", "").replace(",", ".");
		return new BigDecimal(valor);
	}

	public String getAsString(FacesContext fc, UIComponent component, Object value) {
		if (value instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) value;
            //10.00 ~> 10,00 --- 1000.00 ~> 1.000,00
            return new DecimalFormat("#,##0.00").format(bd);
        }
        return "";
	}
}