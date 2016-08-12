package br.com.pontek.exception;


public class ValidacaoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String msg; 

    public ValidacaoException(String mensagem) {
        super(mensagem);
        this.msg = mensagem;     
    }
      
	   public String getMessage(){
	     return msg;     
	   }    
}