package br.com.pontek.service.sistema;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontek.model.autenticacao.Usuario;
import br.com.pontek.model.entidades.Empresa;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.service.autenticacao.UsuarioService;
import br.com.pontek.service.entidades.EmpresaService;
import br.com.pontek.util.DataUtil;

@Service(value="replaceDocumentoService")
public class ReplaceDocumentoService {
	
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private UsuarioService usuarioService;

	private Empresa empresa = new Empresa();
	private Usuario usuario = new Usuario();
	
	
	public String gerarDocumentoPessoa(Pessoa pessoa,String texto){
		empresa = empresaService.carregarDados();
		usuario = usuarioService.usuarioLogado();
		
		String textoTemp=texto;
		textoTemp=textoTemp.replace("#pessoaNome#", pessoa.getNome()!=null?pessoa.getNome():"");
		textoTemp=textoTemp.replace("#pessoaNomePai#", pessoa.getNomePai()!=null?pessoa.getNomePai():"SEM NOME PAI");
		textoTemp=textoTemp.replace("#pessoaNomeMae#", pessoa.getNomeMae()!=null?pessoa.getNomeMae():"SEM NOME MÃE");
		
		textoTemp=textoTemp.replace("#pessoaDataNasc#", pessoa.getDataNascimento()!=null?DataUtil.ddMMyyyy(pessoa.getDataNascimento()):"SEM DATA NASCIMENTO");
		textoTemp=textoTemp.replace("#pessoaDataNascExtenso#", pessoa.getDataNascimento()!=null?DataUtil.extenso(pessoa.getDataNascimento()):"SEM DATA NASCIMENTO");
		
		textoTemp=textoTemp.replace("#pessoaCpf#",pessoa.CpfOuCnpj()?"SEM CPF":pessoa.getCpfCnpj());
		textoTemp=textoTemp.replace("#pessoaCnpj#",pessoa.CpfOuCnpj()?pessoa.getCpfCnpj():"SEM CNPJ");
		textoTemp=textoTemp.replace("#pessoaTelefone#", pessoa.getTelefone()!=null?pessoa.getTelefone():"SEM TELEFONE");
		textoTemp=textoTemp.replace("#pessoaEmail#", pessoa.getEmail()!=null?pessoa.getEmail():"SEM EMAIL");
		textoTemp=textoTemp.replace("#pessoaRespFinanceiro#", pessoa.getEhResponsavelFinanceiro()?"SOU RESPONSÁVEL":pessoa.getNomeResponsavel());
		textoTemp=textoTemp.replace("#pessoaRespFinanceiroCpf#", pessoa.getCpfResponsavel()!=null?pessoa.getCpfResponsavel():"SEM CPF RESPONSÁVEL");

		textoTemp=textoTemp.replace("#pessoaLogradouro#", pessoa.getLogradouro()!=null?pessoa.getLogradouro():"");
		textoTemp=textoTemp.replace("#pessoaBairro#", pessoa.getBairro()!=null?pessoa.getBairro():"");
		textoTemp=textoTemp.replace("#pessoaNumero#", pessoa.getNumero()!=null?pessoa.getNumero():"");
		textoTemp=textoTemp.replace("#pessoaComplemento#", pessoa.getComplemento()!=null?pessoa.getComplemento():"");
		textoTemp=textoTemp.replace("#pessoaCep#", pessoa.getCep()!=null?pessoa.getCep():"");
		textoTemp=textoTemp.replace("#pessoaCidade#", pessoa.getCidade()!=null?pessoa.getCidade():"");
		textoTemp=textoTemp.replace("#pessoaUf#", pessoa.getUf()!=null?pessoa.getUf():"");
		
		//Data e hora
		textoTemp=textoTemp.replace("#dataAtual#", DataUtil.ddMMyyyy(new Date()));
		textoTemp=textoTemp.replace("#dataAtualExtenso#", DataUtil.extenso(new Date()));
		textoTemp=textoTemp.replace("#horarioAtual#", DataUtil.HHmmss(new Date()));
		textoTemp=textoTemp.replace("#anoAtual#", DataUtil.yyyy(new Date())); 
		
		//Usuario Logado
		textoTemp=textoTemp.replace("#usuarioNome#", usuario.getNome());
		textoTemp=textoTemp.replace("#usuarioEmail#", usuario.getEmail());
		
		//Dados da escola
		textoTemp=textoTemp.replace("#empresaCnpj#", empresa.getCnpj()!=null?empresa.getCnpj():"SEM CNPJ");
		textoTemp=textoTemp.replace("#empresaRazaoSocial#", empresa.getRazaoSocial()!=null?empresa.getRazaoSocial():"SEM RAZÃO SOCIAL");
		textoTemp=textoTemp.replace("#empresaNome#", empresa.getNomeEmpresa()!=null?empresa.getNomeEmpresa():"SEM NOME EMPRESA");
		textoTemp=textoTemp.replace("#empresaInsEstadual#", empresa.getInsEstadual()!=null?empresa.getInsEstadual():"SEM INSCRIÇÃO ESTADUAL");
		textoTemp=textoTemp.replace("#empresaInsMunicipal#", empresa.getInsMunicipal()!=null?empresa.getInsMunicipal():"SEM INSCRIÇÃO MUNICIPAL");
		
		textoTemp=textoTemp.replace("#empresaLogradouro#", empresa.getLogradouro()!=null?empresa.getLogradouro():"SEM LOGRADOURO");
		textoTemp=textoTemp.replace("#empresaBairro#", empresa.getBairro()!=null?empresa.getLogradouro():"SEM LOGRADOURO");
		textoTemp=textoTemp.replace("#empresaNumero#", empresa.getNumero()!=null?empresa.getNumero():"SEM NÚMERO");
		textoTemp=textoTemp.replace("#empresaComplemento#", empresa.getComplemento()!=null?empresa.getComplemento():"");
		textoTemp=textoTemp.replace("#empresaCep#", empresa.getCep()!=null?empresa.getCep():"SEM CEP");
		textoTemp=textoTemp.replace("#empresaCidade#", empresa.getCidade()!=null?empresa.getCidade():"SEM CIDADE");
		textoTemp=textoTemp.replace("#empresaUf#", empresa.getUf()!=null?empresa.getUf():"SEM UF");
		
		textoTemp=textoTemp.replace("#empresaTelefone1#", empresa.getTelefone1()!=null?empresa.getTelefone1():"SEM TELEFONE 1");
		textoTemp=textoTemp.replace("#empresaTelefone2#", empresa.getTelefone2()!=null?empresa.getTelefone2():"SEM TELEFONE 2");
		
		textoTemp=textoTemp.replace("#empresaSecretario#", empresa.getNomeSecretario()!=null?empresa.getNomeSecretario():"SEM SECRETÁRIO");
		textoTemp=textoTemp.replace("#empresaDiretor#", empresa.getNomeDiretor()!=null?empresa.getNomeDiretor():"SEM DIRETOR");

		return textoTemp;
	}
	
}
