package br.com.pontek;
import static java.nio.file.FileSystems.getDefault;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import br.com.pontek.service.entidades.PessoaService;
import br.com.pontek.service.financeiro.CategoriaService;
import br.com.pontek.service.financeiro.LancamentoService;

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
		System.out.println("App.main(): "+LocalDate.now().atStartOfDay().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
		
			app.verificaPathTomcat();
	}
	
	void verificaPathTomcat(){
		Path path = Paths.get("c://files//avaliacao//7PASSOS.png");
		System.out.println("App.verificaPathTomcat()"+path);
		System.out.println("SISTEMA OPERACIONAL: "+System.getProperties().get("os.name")); 
		
	}
	
}