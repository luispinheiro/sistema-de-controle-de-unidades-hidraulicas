 package com.algaworks.brewer.repository.helper.cluh;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cluh;
import com.algaworks.brewer.repository.filter.CluhFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class CluhsImpl implements CluhsQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cluh> filtrar(CluhFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cluh> query = builder.createQuery(Cluh.class);
		Root<Cluh> cluhEntity = query.from(Cluh.class);
		cluhEntity.fetch("tag"); //Aqui ele faz o fecth e tráz a junto as tags relacionadas na mesma query
		cluhEntity.fetch("usuario");//Traz o usuário relacionado na mesma query
		Predicate[] filtros = adicionarFiltro(filtro, cluhEntity);

		query.select(cluhEntity).where(filtros); 
		
		TypedQuery<Cluh> typedQuery =  (TypedQuery<Cluh>) paginacaoUtil.prepararOrdem(query, cluhEntity, pageable);
		typedQuery = (TypedQuery<Cluh>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	private Long total(CluhFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Cluh> cluhEntity = query.from(Cluh.class);
		
		query.select(criteriaBuilder.count(cluhEntity));
		query.where(adicionarFiltro(filtro, cluhEntity));
		
		return manager.createQuery(query).getSingleResult();
	}

	private Predicate[] adicionarFiltro(CluhFilter filtro, Root<Cluh> cluhEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		//retirei esta parte pois estava fazendo um select a mais, o fetch anterior resolve.
		//Join<Cluh, Usuario> joinsuario = cluhEntity.join("usuario", JoinType.INNER);
		//joinsuario.alias("u");
		
		if (filtro != null) {
			
			if (filtro.getDataDe() != null) {
				LocalDateTime dataDe = LocalDateTime.of(filtro.getDataDe(), LocalTime.of(0, 0));
				predicateList.add(builder.greaterThanOrEqualTo(cluhEntity.get("dataCriacao"), dataDe));
			}
			
			if (filtro.getDataAte() != null) {
				LocalDateTime dataAte = LocalDateTime.of(filtro.getDataAte(), LocalTime.of(0, 0));
				predicateList.add(builder.lessThanOrEqualTo(cluhEntity.get("dataCriacao"), dataAte));;
			}
		
			if (!StringUtils.isEmpty(filtro.getNomeUsuario())) {
				predicateList.add(builder.like(cluhEntity.get("usuario").get("nome"), filtro.getNomeUsuario()));
			}
			
			if (!StringUtils.isEmpty(filtro.getTurnoUsuario())) {
				Expression<String> likeTurno = cluhEntity.get("usuario").get("turno");
			
				predicateList.add(builder.like(likeTurno, filtro.getTurnoUsuario()));
			}
			
			
			if (filtro.getNivelDe() != null) {
				predicateList.add(builder.ge(cluhEntity.get("nivel"), filtro.getNivelDe()));
			}

			if (filtro.getNivelAte() != null) {
				predicateList.add(builder.le(cluhEntity.get("nivel"), filtro.getNivelAte()));
			}
			
			if (filtro.getAbastecidoDe() != null) {
				predicateList.add(builder.ge(cluhEntity.get("abastecimento"), filtro.getAbastecidoDe()));
			}

			if (filtro.getAbastecidoAte() != null) {
				predicateList.add(builder.le(cluhEntity.get("abastecimento"), filtro.getAbastecidoAte()));
			}

			
			if (isTagPresente(filtro)) {
				predicateList.add(builder.equal(cluhEntity.get("tag"), filtro.getTag()));
			}
			
			if (filtro.getCondicao() != null) {
				predicateList.add(builder.equal(cluhEntity.get("condicao"), filtro.getCondicao()));
			}
			
			if (filtro.getObservacao() != null) {
				predicateList.add(builder.like(cluhEntity.get("observacao"), "%" + filtro.getObservacao() + "%"));
			}

		}
		
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);

	}
	
	private boolean isTagPresente(CluhFilter filtro) {
		return filtro.getTag() != null && filtro.getTag().getCodigo() != null;
	}

}
