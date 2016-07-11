package br.com.pontek;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import br.com.pontek.model.Categoria;
import br.com.pontek.model.Pessoa;
import br.com.pontek.service.CategoriaService;
import br.com.pontek.service.PessoaService;

@Component
public class App {
	static Logger logger = LoggerFactory.getLogger(App.class);
	
	@Autowired
	CategoriaService categoriaService;
	@Autowired
	PessoaService pessoaService;

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
		Pessoa p = new Pessoa();
		p.setNome("Elves app");
		pessoaService.salvar(p);
		
		List<Pessoa> lista = new ArrayList<Pessoa>();
		lista=pessoaService.listaDePessoas();
		if(!lista.isEmpty()){
			for (Pessoa p1 : lista) {
				System.out.println("id: "+p1.getId() +"nome: "+p1.getNome());
			}
		}else{
			System.out.println("lista vazia!");
		}
	}

}