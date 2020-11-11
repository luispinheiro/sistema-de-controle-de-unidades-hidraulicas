package com.algaworks.brewer.repository.helper.tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Tag;
import com.algaworks.brewer.repository.filter.TagFilter;

public interface TagsQueries {

	public Page<Tag> filtrar(TagFilter filtro, Pageable pageable);
}
