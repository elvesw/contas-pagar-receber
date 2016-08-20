package br.com.pontek.service.sistema;

import br.com.pontek.model.sistema.Configuracao;

public interface  ConfiguracaoService{
	
	/*### METODOS DE SALVAR ###*/
    void salvar(Configuracao configuracao);
   
    /*### METODOS DE BUSCAR ###*/
    Configuracao carregar();
	
}
