 package com.algaworks.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Cluh;
import com.algaworks.brewer.repository.Cluhs;

@Service
public class CadastroCluhService {

	@Autowired
	private Cluhs cluhs;
	
	@Transactional 
	public void salvar(Cluh cluh) {
		cluhs.save(cluh);
	}
	
	@Transactional
	public void excluir(Cluh cluh) {
//		try {
			cluhs.delete(cluh);
			cluhs.flush();
//		} catch (PersistenceException e) {
//			throw new ImpossivelExcluirEntidadeException("Impossível apagar Checklist. Já foi utilizada em alguma transação.");
//		}
	}
}

