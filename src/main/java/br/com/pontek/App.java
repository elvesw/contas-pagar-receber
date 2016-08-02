package br.com.pontek;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import br.com.pontek.enums.FrequenciaDeLancamento;
import br.com.pontek.model.Categoria;
import br.com.pontek.model.Pessoa;
import br.com.pontek.service.CategoriaService;
import br.com.pontek.service.LancamentoService;
import br.com.pontek.service.PessoaService;
import br.com.pontek.util.DataUtil;

@Component
public class App {
	static Logger logger = LoggerFactory.getLogger(App.class);
	
	@Autowired
	CategoriaService categoriaService;
	@Autowired
	PessoaService pessoaService;
	@Autowired
	LancamentoService lancamentoService;

	@SuppressWarnings({ "resource"})
	public static void main(String[] args) throws ParseException {
		final ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

		final App app = context.getBean(App.class);

		System.out.println("###########################");
		logger.debug("debug log");
		logger.info("info log");
		logger.warn("warn log");
		logger.error("error log");
		
		app.inserirDados();
		System.out.println("u"+System.currentTimeMillis());
	}
	
	void consulta(){
		List<Categoria> listaCategorias = new ArrayList<Categoria>();
		listaCategorias=categoriaService.listaDeCategorias();
		
		if(!listaCategorias.isEmpty()){
			for (Categoria c : listaCategorias) {
				System.out.println("id: "+c.getId() +"nome: "+c.getNome());
			}
		}else{
			System.out.println("lista vazia!");
		}
	}
	
	void inserirDados(){

		for (int i = 0; i < 10; i++) {
			DataUtil.geraDataVencimentoParcela(new Date(), i, FrequenciaDeLancamento.Diário);
		}
		System.out.println("#####################");
		for (int i = 0; i < 10; i++) {
			DataUtil.geraDataVencimentoParcela(new Date(), i, FrequenciaDeLancamento.Semanal);
		}
		System.out.println("#####################");
		for (int i = 0; i < 10; i++) {
			DataUtil.geraDataVencimentoParcela(new Date(), i, FrequenciaDeLancamento.Quinzenal);
		}
		System.out.println("#####################");
		for (int i = 0; i < 10; i++) {
			DataUtil.geraDataVencimentoParcela(new Date(), i, FrequenciaDeLancamento.Mensal);
		}
	}

}