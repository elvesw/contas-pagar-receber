package br.com.pontek.controller;

import java.io.Serializable;

public abstract class AbstractBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	   public enum estadoDaView {
	        LISTANDO,
	        INSERINDO,
	        EDITANDO,
	        EXCLUINDO,
	        DETALHANDO,
	        FILTRO; 
	    }

}
