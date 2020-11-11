package com.algaworks.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.brewer.model.Tag;
import com.algaworks.brewer.repository.helper.tag.TagsQueries;

public interface Tags extends JpaRepository<Tag, Long>, TagsQueries{

	public Optional<Tag> findByCodigoIgnoreCase(String codigo);
}
