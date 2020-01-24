package br.com.ciceroednilson.pessoa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.ciceroednilson.model.PessoaModel;
import br.com.ciceroednilson.repository.PessoaRepository;

@Named(value="exportarRegistrosXmlController")
@RequestScoped
public class ExportarRegistrosXmlController implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject transient
	PessoaRepository pessoaRepository;
	
	private StreamedContent arquivoDownload;
	
	/*
	 * REALIZA O DOWNLOAD DO ARQUIVO XML
	 * @return
	 */
	public StreamedContent getArquivoDownload() {
		
		this.DownloadArquivoXmlPessoa();
		
		return arquivoDownload;
	}
	
	/*
	 * GERANDO ARQUIVO XML PARA EXPORTAÇÃ.
	 * @return
	 */
	private File GerarXmlPessoas() {
		
		//MASCARA PARA FORMATAR A DATA QUE VAI SER ADICIONADA NO ARQUIVO XML
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		List<PessoaModel> pessoasModel = pessoaRepository.GetPessoas();
		
		//ELEMENTO RAIZ DO NOSSO ARQUIVO XML
		Element elementPessoas = new Element("Pessoas");
		
		Document documentoPessoas = new Document(elementPessoas);
		
		pessoasModel.forEach(pessoa -> {
			
			//MONTANDO AS TAGS DO XML COM OS SEUS VALORES
			Element elementPessoa = new Element("Pessoa");
			elementPessoa.addContent(new Element("codigo").setText(pessoa.getCodigo().toString()));
			elementPessoa.addContent(new Element("nome").setText(pessoa.getSexo()));
			elementPessoa.addContent(new Element("sexo").setText(pessoa.getSexo()));
			
			//FORMATANDO A DATA
			String dataCadastroFormatada = pessoa.getDataCadastro().format(dateTimeFormatter);
			
			elementPessoa.addContent(new Element("dataCadastro").setText(dataCadastroFormatada));
			
			elementPessoa.addContent(new Element("email").setText(pessoa.getEmail()));
			elementPessoa.addContent(new Element("email").setText(pessoa.getEmail()));
			elementPessoa.addContent(new Element("origemCadastro").setText(pessoa.getOrigemCadastro()));
			elementPessoa.addContent(new Element("usuarioCadastro").setText(pessoa.getUsuarioModel().getUsuario()));
			
			elementPessoas.addContent(elementPessoa);
		});
		
		XMLOutputter xmlGerado = new XMLOutputter();
		
		try {
			
			/* GERANDO O NOME DO ARQUIVO*/
			String nomeArquivo = "pessoas_".concat(java.util.UUID.randomUUID().toString()).concat(".xml");
			
			//CAMINHO ONDE VAI SER GERADO O ARQUIVO XML
			File arquivo = new File("/home/rodrigo/Documents".concat(nomeArquivo));
			
			FileWriter fileWriter = new FileWriter(arquivo);
			
			xmlGerado.output(documentoPessoas, fileWriter);
			
			return arquivo;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	/*
	 * PREPARA O ARQUIVO PARA DOWNLOAD
	 */
	private void DownloadArquivoXmlPessoa() {
		
		File arquivoXml = this.GerarXmlPessoas();
		
		InputStream inputStream;
		
		try {
			
			inputStream = new FileInputStream(arquivoXml.getPath());
			
			arquivoDownload = new DefaultStreamedContent(inputStream,"application/xml",arquivoXml.getName());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
