package br.com.pontek;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.model.financeiro.Lancamento;
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

	@SuppressWarnings({ "resource" })
	public static void main(String[] args) throws ParseException {
		final ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

		final App app = context.getBean(App.class);
		//System.out.println("App.main(): " + LocalDate.now().atStartOfDay().atZone(ZoneId.of("America/Sao_Paulo")).toInstant());

		app.printObservacoesLancamentos();
	}

	public void checkAccess() {
		Path file = Paths.get("C:\\imagens\\thumbnail.140x129.Capturadetela2014-01-0213.00.07.png");

	
		try {
			Boolean isRegularFile = Files.isRegularFile(file);
			System.out.println("Arquivo : isRegularFile: " + isRegularFile);

			Boolean isHidden;
			isHidden = Files.isHidden(file);

			System.out.println("Arquivo : isHidden: " + isHidden);

			Boolean isReadable = Files.isReadable(file);
			System.out.println("Arquivo : isReadable: " + isReadable);

			Boolean isExecutable = Files.isExecutable(file);
			System.out.println("Arquivo : isExecutable: " + isExecutable);

			Boolean isSymbolicLink = Files.isSymbolicLink(file);
			System.out.println("Arquivo : isSymbolicLink: " + isSymbolicLink);

			Path directory = Paths.get("C:\\imagens2");

			Boolean isDirectory = Files.isDirectory(directory);
			System.out.println("Arquivo : isDirectory: " + isDirectory);

			Boolean isWritable = Files.isWritable(directory);
			System.out.println("Arquivo : isWritable: " + isWritable);

			Files.delete(file);
		} catch (IOException e){
			e.printStackTrace();
		}

	}

	public void verificaData() {

		Pessoa p = pessoaService.buscar(3);

		Date data = new Date();

		System.out.println("new Date: " + data);

		System.out.println("Data cadastro: " + p.getDataCadastro());

		System.out.println("Data nascimento: " + p.getDataNascimento());

	}
	
	public void printDescricoesLancamentos() {
		List<String> listaLancamentos = new ArrayList<>();
		listaLancamentos=lancamentoService.listaDescricoesLancamentos("ME");
		for (String l : listaLancamentos) {
			System.out.println(l);
		}
	}
	
	public void printObservacoesLancamentos() {
		List<Lancamento> listaLancamentos = new ArrayList<>();
		listaLancamentos=lancamentoService.listaDeMovimentos();
		for (Lancamento l : listaLancamentos) {
			System.out.println(l.getObservacao());
		}
	}

}