package br.com.pontek.util.jsf;

import java.net.URL;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class CepWebService {
	private String tipoLogradouro = "";
	private int resultado = 0;

	private String logradouro;
	private String bairro;
	private String cep;
	private String cidade;
	private String uf;

	@SuppressWarnings("rawtypes")
	public void buscaCep(String cep) {
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep="+cep+"&formato=xml");
			Document document = getDocumento(url);

			Element root = document.getRootElement();

			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();

				if (element.getQualifiedName().equals("uf")) {
					this.uf=(element.getText());
					this.cep=(cep);
				}
				if (element.getQualifiedName().equals("cidade")) {
					this.cidade=(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					this.bairro=(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					 setTipoLogradouro(element.getText());
				}
				if (element.getQualifiedName().equals("logradouro")) {
					this.logradouro=(getTipoLogradouro()+ " "+ element.getText());
				}
				if (element.getQualifiedName().equals("resultado")) {
					setResultado(Integer.parseInt(element.getText()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(this.resultado==0){
				System.out.println("Cep: Não encontrado");
			}else{
				System.out.println("Cep: Encontrado");
			}
		}
	}

	public Document getDocumento(URL url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}

	public String getTipoLogradouro() {
		return tipoLogradouro;
	}
	public void setTipoLogradouro(String tipoLogradouro) {
		this.tipoLogradouro = tipoLogradouro;
	}
	public int getResultado() {
		return resultado;
	}
	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	//SOMENTE OS GETS ABAIXO
	public String getLogradouro() {
		return logradouro;
	}
	public String getBairro() {
		return bairro;
	}
	public String getCep() {
		return cep;
	}
	public String getCidade() {
		return cidade;
	}
	public String getUf() {
		return uf;
	}

}
