package br.com.ciceroednilson.repository;

import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.ciceroednilson.model.PessoaModel;
import br.com.ciceroednilson.repository.entity.PessoaEntity;
import br.com.ciceroednilson.repository.entity.UsuarioEntity;
import br.com.ciceroednilson.uteis.Uteis;

public class PessoaRepository {

	@Inject
	PessoaEntity pessoaEntity;
	
	EntityManager entityManager;
	
	/***
	 * MÉTODO RESPONSÁVEL POR SALVAR UMA NOVA PESSOA
	 * @param pessoaModel
	 */
	
	public void SalvarNovoRegistro (PessoaModel pessoaModel) {
		
		entityManager = Uteis.JpaEntityManager();
		
		pessoaEntity = new PessoaEntity();
		pessoaEntity.setDataCadastro(LocalDateTime.now());
		pessoaEntity.setEmail(pessoaModel.getEmail());
		pessoaEntity.setEndereco(pessoaModel.getEndereco());
		pessoaEntity.setNome(pessoaModel.getNome());
		pessoaEntity.setOrigemCadastro(pessoaModel.getOrigemCadastro());
		pessoaEntity.setSexo(pessoaModel.getSexo());
		
		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, pessoaModel.getUsuarioModel().getCodigo());
		
		pessoaEntity.setUsuarioEntity(usuarioEntity);
		
		entityManager.persist(pessoaEntity);
	}
}
