package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Tag;
import com.algaworks.brewer.repository.Tags;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.service.exception.NomeTagJaCadastradoException;

@Service
public class CadastroTagService {

	@Autowired
	private Tags tags;
	
	@Transactional
	public Tag salvar(Tag tag) {
		Optional<Tag> tagOptional = tags.findByCodigoIgnoreCase(tag.getCodigo());
		if (tagOptional.isPresent()) {
			throw new NomeTagJaCadastradoException("Nome da tag já cadastrado");
		}
		
		return tags.saveAndFlush(tag);
	}
	
	@Transactional
	public void excluir(Tag tag) {
		try {
			tags.delete(tag);
			tags.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar tag. Já foi usada em alguma venda.");
		}
	}
}
