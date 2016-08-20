package br.com.pontek.service.entidades;

import br.com.pontek.model.entidades.Empresa;

public interface EmpresaService {
	
	/*### METODOS DE SALVAR ###*/
    void salvar(Empresa empresa);
   
    /*### METODOS DE BUSCAR ###*/
    Empresa carregarDados();
}
