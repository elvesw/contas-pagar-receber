package br.com.pontek.service.sistema;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import br.com.pontek.model.autenticacao.Usuario;
import br.com.pontek.model.entidades.Empresa;
import br.com.pontek.model.entidades.Pessoa;
import br.com.pontek.service.autenticacao.UsuarioService;
import br.com.pontek.service.entidades.EmpresaService;
import br.com.pontek.util.DataUtil;
import org.apache.commons.lang3.StringUtils;

@Service(value="replaceDocumentoService")
@Scope("session")
public class ReplaceDocumentoService {
	
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private UsuarioService usuarioService;

	private Empresa empresa = new Empresa();
	private Usuario usuario = new Usuario();
	
	/*@PostConstruct
	private void init(){
		empresa = empresaService.carregarDados();
		usuario = usuarioService.usuarioLogado();
	}*/
	
	private void carregarDados(){
		if(empresa.getId()==null) empresa = empresaService.carregarDados();
		if(usuario.getId()==null) usuario = usuarioService.usuarioLogado();
	}
	
	
	public String gerarDocumentoPessoa(Pessoa pessoa,String texto){
		carregarDados();
		String textoTemp=texto;
		textoTemp=textoTemp.replace("#pessoaNome#", StringUtils.isNotBlank(pessoa.getNome())?pessoa.getNome():"<span style=\"color:#ff0000;\">SEM NOME</span>");
		textoTemp=textoTemp.replace("#pessoaNomePai#", StringUtils.isNotBlank(pessoa.getNomePai())?pessoa.getNomePai():"<span style=\"color:#ff0000;\">SEM NOME PAI</span>");
		textoTemp=textoTemp.replace("#pessoaNomeMae#", StringUtils.isNotBlank(pessoa.getNomeMae())?pessoa.getNomeMae():"<span style=\"color:#ff0000;\">SEM NOME MÃE</span>");
		
		textoTemp=textoTemp.replace("#pessoaDataNasc#", StringUtils.isNotBlank(DataUtil.ddMMyyyy(pessoa.getDataNascimento()))?DataUtil.ddMMyyyy(pessoa.getDataNascimento()):"<span style=\"color:#ff0000;\">SEM DATA NASCIMENTO</span>");
		textoTemp=textoTemp.replace("#pessoaDataNascExtenso#", StringUtils.isNotBlank(DataUtil.extenso(pessoa.getDataNascimento()))?DataUtil.extenso(pessoa.getDataNascimento()):"<span style=\"color:#ff0000;\">SEM DATA NASCIMENTO</span>");
		
		textoTemp=textoTemp.replace("#pessoaCpf#",pessoa.CpfOuCnpj()?"<span style=\"color:#ff0000;\">SEM CPF</span>":pessoa.getCpfCnpj());
		textoTemp=textoTemp.replace("#pessoaCnpj#",pessoa.CpfOuCnpj()?pessoa.getCpfCnpj():"<span style=\"color:#ff0000;\">SEM CNPJ</span>");
		textoTemp=textoTemp.replace("#pessoaTelefone#", StringUtils.isNotBlank(pessoa.getTelefone())?pessoa.getTelefone():"<span style=\"color:#ff0000;\">SEM TELEFONE</span>");
		
		textoTemp=textoTemp.replace("#pessoaEmail#", StringUtils.isNotBlank(pessoa.getEmail())?pessoa.getEmail():"<span style=\"color:#ff0000;\">SEM EMAIL</span>");
		textoTemp=textoTemp.replace("#pessoaRespFinanceiro#", pessoa.getEhResponsavelFinanceiro()?"SOU RESPONSÁVEL":pessoa.getNomeResponsavel());
		textoTemp=textoTemp.replace("#pessoaRespFinanceiroCpf#", StringUtils.isNotBlank(pessoa.getCpfResponsavel())?pessoa.getCpfResponsavel():"<span style=\"color:#ff0000;\">SEM CPF RESPONSÁVEL</span>");

		textoTemp=textoTemp.replace("#pessoaLogradouro#", StringUtils.isNotBlank(pessoa.getLogradouro())?pessoa.getLogradouro():"");
		textoTemp=textoTemp.replace("#pessoaBairro#", StringUtils.isNotBlank(pessoa.getBairro())?pessoa.getBairro():"");
		textoTemp=textoTemp.replace("#pessoaNumero#", StringUtils.isNotBlank(pessoa.getNumero())?pessoa.getNumero():"");
		textoTemp=textoTemp.replace("#pessoaComplemento#", StringUtils.isNotBlank(pessoa.getComplemento())?pessoa.getComplemento():"");
		textoTemp=textoTemp.replace("#pessoaCep#", StringUtils.isNotBlank(pessoa.getCep())?pessoa.getCep():"");
		textoTemp=textoTemp.replace("#pessoaCidade#", StringUtils.isNotBlank(pessoa.getCidade())?pessoa.getCidade():"");
		textoTemp=textoTemp.replace("#pessoaUf#", StringUtils.isNotBlank(pessoa.getUf())?pessoa.getUf():"");
		
		textoTemp=textoTemp.replace("#pessoaNatural#", StringUtils.isNotBlank(pessoa.getNaturalidade())?pessoa.getNaturalidade():"");
		textoTemp=textoTemp.replace("#pessoaNaturalUf#", StringUtils.isNotBlank(pessoa.getNaturalidadeUf())?pessoa.getNaturalidadeUf():"");
		
		//Data e hora
		textoTemp=textoTemp.replace("#dataAtual#", DataUtil.ddMMyyyy(new Date()));
		textoTemp=textoTemp.replace("#dataAtualExtenso#", DataUtil.extenso(new Date()));
		textoTemp=textoTemp.replace("#horarioAtual#", DataUtil.HHmmss(new Date()));
		textoTemp=textoTemp.replace("#anoAtual#", DataUtil.yyyy(new Date())); 
		
		//Usuario Logado
		textoTemp=textoTemp.replace("#usuarioNome#", usuario.getNome());
		textoTemp=textoTemp.replace("#usuarioEmail#", usuario.getEmail());
		
		//Dados da escola
		textoTemp=textoTemp.replace("#empresaCnpj#", StringUtils.isNotBlank(empresa.getCnpj())?empresa.getCnpj():"<span style=\"color:#ff0000;\">SEM CNPJ</span>");
		textoTemp=textoTemp.replace("#empresaRazaoSocial#", StringUtils.isNotBlank(empresa.getRazaoSocial())?empresa.getRazaoSocial():"<span style=\"color:#ff0000;\">SEM RAZÃO SOCIAL</span>");
		textoTemp=textoTemp.replace("#empresaNome#", StringUtils.isNotBlank(empresa.getNomeEmpresa())?empresa.getNomeEmpresa():"<span style=\"color:#ff0000;\">SEM NOME EMPRESA</span>");
		textoTemp=textoTemp.replace("#empresaNomeMaiuscula#", StringUtils.isNotBlank(empresa.getNomeEmpresa())?empresa.getNomeEmpresa().toUpperCase():"<span style=\"color:#ff0000;\">SEM NOME EMPRESA</span>");
		textoTemp=textoTemp.replace("#empresaInsEstadual#", StringUtils.isNotBlank(empresa.getInsEstadual())?empresa.getInsEstadual():"<span style=\"color:#ff0000;\">SEM INSCRIÇÃO ESTADUAL</span>");
		textoTemp=textoTemp.replace("#empresaInsMunicipal#", StringUtils.isNotBlank(empresa.getInsMunicipal())?empresa.getInsMunicipal():"<span style=\"color:#ff0000;\">SEM INSCRIÇÃO MUNICIPAL</span>");
		
		textoTemp=textoTemp.replace("#empresaLogradouro#", StringUtils.isNotBlank(empresa.getLogradouro())?empresa.getLogradouro():"<span style=\"color:#ff0000;\">SEM LOGRADOURO</span>");
		textoTemp=textoTemp.replace("#empresaBairro#", StringUtils.isNotBlank(empresa.getBairro())?empresa.getBairro():"<span style=\"color:#ff0000;\">SEM BAIRRO</span>");
		textoTemp=textoTemp.replace("#empresaNumero#", StringUtils.isNotBlank(empresa.getNumero())?empresa.getNumero():"<span style=\"color:#ff0000;\">SEM NÚMERO</span>");
		textoTemp=textoTemp.replace("#empresaComplemento#", StringUtils.isNotBlank(empresa.getComplemento())?empresa.getComplemento():"");
		textoTemp=textoTemp.replace("#empresaCep#", StringUtils.isNotBlank(empresa.getCep())?empresa.getCep():"<span style=\"color:#ff0000;\">SEM CEP</span>");
		textoTemp=textoTemp.replace("#empresaCidade#", StringUtils.isNotBlank(empresa.getCidade())?empresa.getCidade():"<span style=\"color:#ff0000;\">SEM CIDADE</span>");
		textoTemp=textoTemp.replace("#empresaUf#", StringUtils.isNotBlank(empresa.getUf())?empresa.getUf():"<span style=\"color:#ff0000;\">SEM ESTADO</span>");
		
		textoTemp=textoTemp.replace("#empresaTelefone1#", StringUtils.isNotBlank(empresa.getTelefone1())?empresa.getTelefone1():"<span style=\"color:#ff0000;\">SEM TELEFONE 1</span>");
		textoTemp=textoTemp.replace("#empresaTelefone2#", StringUtils.isNotBlank(empresa.getTelefone2())?empresa.getTelefone2():"<span style=\"color:#ff0000;\">SEM TELEFONE 2</span>");
		
		textoTemp=textoTemp.replace("#empresaSecretario#", StringUtils.isNotBlank(empresa.getNomeSecretario())?empresa.getNomeSecretario():"<span style=\"color:#ff0000;\">SEM SECRETÁRIO(a)</span>");
		textoTemp=textoTemp.replace("#empresaDiretor#", StringUtils.isNotBlank(empresa.getNomeDiretor())?empresa.getNomeDiretor():"<span style=\"color:#ff0000;\">SEM DIRETOR(a)</span>");

		textoTemp=textoTemp.replace("#empresaMensagem1#", StringUtils.isNotBlank(empresa.getMsg1())?empresa.getMsg1():"");
		textoTemp=textoTemp.replace("#empresaMensagem2#", StringUtils.isNotBlank(empresa.getMsg2())?empresa.getMsg2():"");
		textoTemp=textoTemp.replace("#empresaMensagem3#", StringUtils.isNotBlank(empresa.getMsg3())?empresa.getMsg3():"");
		textoTemp=textoTemp.replace("#empresaMensagem4#", StringUtils.isNotBlank(empresa.getMsg4())?empresa.getMsg4():"");
		return textoTemp;
	}

	/*##### GETS E SETS ########*/
	public Empresa getEmpresa() {
		return empresa;
	}
	public Usuario getUsuario() {
		return usuario;
	}

}
