package br.com.ciceroednilson.pessoa.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ciceroednilson.model.PessoaModel;
import br.com.ciceroednilson.repository.PessoaRepository;

@Named(value="consultarPessoaCarouselController")
@ViewScoped
public class ConsultarPessoaCorouselController implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject transient
	private PessoaRepository pessoaRepository;
	
	@Produces
	private List<PessoaModel> pessoas;
	
	public List<PessoaModel> getPessoas() {
		return pessoas;
	}
	
	@PostConstruct
	private void init() {
		
		this.pessoas = pessoaRepository.GetPessoas();
	}
	
}
