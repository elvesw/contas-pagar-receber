package br.com.pontek;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import br.com.pontek.service.CategoriaService;
import br.com.pontek.service.LancamentoService;
import br.com.pontek.service.PessoaService;
import br.com.pontek.util.jpa.LancamentosPeriodo;

@Component
public class App {
	static Logger logger = LoggerFactory.getLogger(App.class);
	
	@Autowired
	CategoriaService categoriaService;
	@Autowired
	PessoaService pessoaService;
	@Autowired
	LancamentoService lancamentoService;

	@SuppressWarnings({ "resource", "unused"})
	public static void main(String[] args) throws ParseException {
		final ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

		final App app = context.getBean(App.class);
		System.out.println("App.main(): "+LocalDate.now().atStartOfDay().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());

		//System.out.println("u"+System.currentTimeMillis());
		
		app.historicoSeisMeses();
	}
	
	void historicoSeisMeses(){
		List<LancamentosPeriodo> listaSomas=lancamentoService.historicoSeisMeses();
		/*for (LancamentosPeriodo p : listaSomas) {
			System.err.println("########################");
			System.err.println("mesReferencia           : "+p.getMesReferencia());
			System.err.println("somaEntradasSomentePago : "+p.getSomaEntradasSomentePago());
			System.err.println("somaTodasEntradas       : "+p.getSomaTodasEntradas());
			System.err.println("somaSaidasSomentePago   : "+p.getSomaSaidasSomentePago());
			System.err.println("somaTodasSaidas         : "+p.getSomaTodasSaidas());
			System.err.println("percentualPagoEntradas  : "+p.getPercentualPagoEntradas());
			System.err.println("percentualPagoSaidas    : "+p.getPercentualPagoSaidas());
			System.err.println("########################");
		}*/
	}
	
}