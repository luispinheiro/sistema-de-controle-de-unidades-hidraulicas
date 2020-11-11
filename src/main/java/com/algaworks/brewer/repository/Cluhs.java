package com.algaworks.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Cluh;
import com.algaworks.brewer.repository.helper.cluh.CluhsQueries;

@Repository
public interface Cluhs extends JpaRepository<Cluh, Long>, CluhsQueries{
	
//	@Query("from Cluh c join c.tag")
//	List<Cluh> findAll();

}
