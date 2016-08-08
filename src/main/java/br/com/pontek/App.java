package br.com.pontek;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import br.com.pontek.service.CategoriaService;
import br.com.pontek.service.LancamentoService;
import br.com.pontek.service.PessoaService;

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

		System.out.println("u"+System.currentTimeMillis());
	}
	
}