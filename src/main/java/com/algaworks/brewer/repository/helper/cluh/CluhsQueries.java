package com.algaworks.brewer.repository.helper.cluh;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Cluh;
import com.algaworks.brewer.repository.filter.CluhFilter;

public interface CluhsQueries {

	public Page<Cluh> filtrar(CluhFilter filtro, Pageable pageable);
}
