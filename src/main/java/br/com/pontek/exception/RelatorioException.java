package br.com.pontek.exception;

public class RelatorioException extends Exception{

	private static final long serialVersionUID = 1L; 
	
	 private String msg; 
	 
	/** Capturar Exceptions de relatorios*/
	   public RelatorioException(String mensagem){     
	     super(mensagem);
	     this.msg = mensagem;     
	   }  
	   public String getMessage(){
	     return msg;     
	   }    

}
