package br.com.pontek.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("cpfCnpjValidator")
public class CpfCnpjValidator implements Validator {
	
	private static final String CPF_PATTERN = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}";
	private static final String CNPJ_PATTERN = "[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}\\/?[0-9]{4}\\-?[0-9]{2}";
	
	private Pattern patternCpf;
	private Pattern patternCnpj;
	private Matcher matcher;

	public CpfCnpjValidator(){
		patternCpf = Pattern.compile(CPF_PATTERN);
		patternCnpj = Pattern.compile(CNPJ_PATTERN);
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component,Object value) throws ValidatorException {
		matcher = patternCpf.matcher(value.toString());
		if(!value.toString().isEmpty()){
			if(!matcher.matches()){
				matcher = patternCnpj.matcher(value.toString());
				if(!matcher.matches()){
					FacesMessage msg = 	new FacesMessage("Documento inválido.", "Formato inválido.");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);				
				}
			}
			
		}

		if(!value.toString().isEmpty()){
			String valor = value.toString();
			valor=valor.replaceAll("[.-]", "");
			valor=valor.replaceAll("[/]", "");
			if(valor.length()==11){//CPF
				if((!validateCPF(value.toString())) || (isCPFPadrao(value.toString()))){
					FacesMessage msg = 	new FacesMessage("CPF inválido", "Formato inválido.");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);
				}
				
			}else if(valor.length()==14){//CNPJ
				if((!validateCNPJ(value.toString())) || (isCNPJPadrao(value.toString()))){
					FacesMessage msg = 	new FacesMessage("CNPJ inválido", "Formato inválido.");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);
				}
			}
		}
	}
	
	/*###########################################*/	
	/**
    * @param cpf String valor a ser testado
    * @return boolean indicando se o usuário entrou com um CPF padrão
    */
    private static boolean isCPFPadrao(String cpf) {
    	cpf=cpf.replaceAll("[.-]","");//remove a mascara
         if (cpf.equals("11111111111") 
	        || cpf.equals("22222222222")
		    || cpf.equals("33333333333")
		    || cpf.equals("44444444444")
		    || cpf.equals("55555555555")
		    || cpf.equals("66666666666")
		    || cpf.equals("77777777777")
		    || cpf.equals("88888888888")
		    || cpf.equals("99999999999")){
              return true;
         }
     return false;
    }
	/**
     * Valida o CPF.
     * @param cpf Numero de CPF sem pontuacao.
     * @return true para CPF valido ou o contrario.
     */
    private boolean validateCPF(String cpf) {
    	cpf=cpf.replaceAll("[.-]", "");//remove a mascara
    	System.out.println("CPF VALI: "+ cpf);
        if (cpf.length() == 11) {
            if (cpf.equals(isCpfValido(cpf.substring(0, 9)))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gera digitos validadores.
     *
     * @param digitsBase 9 digitos iniciais.
     * @return CPF com digitos validadores sem pontuacao.
     */
    private String isCpfValido(String digitsBase) {

        StringBuilder sbCpfNumber = new StringBuilder(digitsBase);
        int total = 0;
        int multiple = digitsBase.length() + 1;
        for (char digit : digitsBase.toCharArray()) {
            long parcial = Integer.parseInt(String.valueOf(digit)) * (multiple--);
            total += parcial;
        }
        int resto = Integer.parseInt(String.valueOf(Math.abs(total % 11)));
        if (resto < 2) {
            resto = 0;
        } else {
            resto = 11 - resto;
        }
        sbCpfNumber.append(resto);
        if (sbCpfNumber.length() < 11) {
            return isCpfValido(sbCpfNumber.toString());
        }
        return sbCpfNumber.toString();
    }
    
	/*###########################################*/	
    /**
     * @param cpf String valor a ser testado
     * @return boolean indicando se o usuário entrou com um CPF padrão
     */
     private static boolean isCNPJPadrao(String cnpf) {
    	 cnpf = cnpf.replaceAll("[.-]", "");//remove a mascara
    	 cnpf = cnpf.replaceAll("[/]", "");//remove a mascara
          if (cnpf.equals("11111111111111") 
 	        || cnpf.equals("22222222222222")
 		    || cnpf.equals("33333333333333")
 		    || cnpf.equals("44444444444444")
 		    || cnpf.equals("55555555555555")
 		    || cnpf.equals("66666666666666")
 		    || cnpf.equals("77777777777777")
 		    || cnpf.equals("88888888888888")
 		    || cnpf.equals("99999999999999")){
               return true;
          }
      return false;
     }
     
    /**
     * Valida o CNPJ.
     * @param cpf Numero de CNPJ.
     * @return true para CNPJ valido ou o contrario.
     */
    private static boolean validateCNPJ(String CNPJ) {
		CNPJ = CNPJ.replaceAll("[.-]", "");//remove a mascara
		CNPJ = CNPJ.replaceAll("[/]", "");//remove a mascara
		
		if (isCnpjValido(CNPJ) == true) {
			if (CNPJ != null && !CNPJ.equals("") && CNPJ.length() == 14)
				return true;
		}

		return false;
    }
    
    /**
     * Gera digitos validadores.
     *
     * @param cnpj 14 digitos iniciais.
     * @return CNPJ com digitos validadores com ou sem pontuação.
     */
    private static boolean isCnpjValido(String cnpj) {
		if (!cnpj.substring(0, 1).equals("")) {
			try {
				cnpj = cnpj.replace('.', ' ');  // onde há ponto coloca espaço
				cnpj = cnpj.replace('/', ' ');  // onde há barra coloca espaço
				cnpj = cnpj.replace('-', ' ');  // onde há traço coloca espaço
				cnpj = cnpj.replaceAll(" ", "");// retira espaço
				int soma = 0, dig;
				String cnpj_calc = cnpj.substring(0, 12);

				if (cnpj.length() != 14) {
					return false;
				}
				char[] chr_cnpj = cnpj.toCharArray();
				/* Primeira parte */
				for (int i = 0; i < 4; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
						soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer
						.toString(dig);
				/* Segunda parte */
				soma = 0;
				for (int i = 0; i < 5; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
						soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
					}
				}
				dig = 11 - (soma % 11);
				cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer
						.toString(dig);
				return cnpj.equals(cnpj_calc);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
}

}
