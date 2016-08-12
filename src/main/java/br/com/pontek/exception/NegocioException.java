package br.com.pontek.exception;

public class NegocioException extends Exception{

	private static final long serialVersionUID = 1L; 
	
	 private String msg; 
	 
	   public NegocioException(String mensagem){     
	     super(mensagem);
	     this.msg = mensagem;     
	   }  
	   public String getMessage(){
	     return msg;     
	   }    

}
