package br.com.pontek.util.report;

import java.util.ArrayList;
import java.util.List;

import br.com.pontek.model.entidades.Pessoa;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class PessoaDataSource implements JRDataSource {

	private List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
	private int index = -1;

	public PessoaDataSource() {
		super();
	}

	public void prepareDataSource(List<Pessoa> lista) {
		this.listaPessoas = lista;	
	}

	 @Override
	public Object getFieldValue(JRField field) throws JRException {
		 Pessoa pessoa = listaPessoas.get(index);
		if (field.getName().equals("id")) {
			return pessoa.getId();
		}else if (field.getName().equals("nomePessoa")){
			return pessoa.getNome();
		}else if (field.getName().equals("perfil")){
			String perfil="";
			if(pessoa.getCliente()){
				perfil=perfil+"cliente";
			}if(pessoa.getFuncionario()){
				perfil=perfil.isEmpty()?perfil+"funcionário":perfil+",funcionário";
			}if(pessoa.getFornecedor()){
				perfil=perfil.isEmpty()?perfil+"fornecedor":perfil+",fornecedor";
			}
			return perfil;
		}else if (field.getName().equals("cpfCnpj")){
			return pessoa.getCpfCnpj();
		}else if (field.getName().equals("telefone")){
			return pessoa.getTelefone();
		}else if (field.getName().equals("email")){
			return pessoa.getEmail();
		}else if (field.getName().equals("dataCadastro")){
			return pessoa.getDataCadastro();
		}
		return null;
	}

	 @Override
	public boolean next() throws JRException {
		if (index < listaPessoas.size() - 1) {
			index++;
			return true;
		}
		return false;
	}
}
