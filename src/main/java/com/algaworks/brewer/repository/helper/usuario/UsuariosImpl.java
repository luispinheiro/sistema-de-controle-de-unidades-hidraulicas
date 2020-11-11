package com.algaworks.brewer.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Grupo;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class UsuariosImpl implements UsuariosQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @Override
    public Optional<Usuario> porEmailEAtivo(String email) {
        return manager.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
                .setParameter("email", email).getResultList().stream().findFirst();
    }

    @Override
    public List<String> permissoes(Usuario usuario) {
        return manager.createQuery(
                "select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario",
                String.class).setParameter("usuario", usuario).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
        Root<Usuario> usuarioEntity = query.from(Usuario.class);
        Fetch<Usuario, Grupo> grupoJoinRoot = usuarioEntity.fetch("grupos");

        Predicate[] filtros = adicionarFiltro(filtro, usuarioEntity);
        query.where(filtros);

        TypedQuery<Usuario> typedQuery =  (TypedQuery<Usuario>) paginacaoUtil.prepararOrdem(query, usuarioEntity, pageable);
        typedQuery = (TypedQuery<Usuario>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);

        return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario buscarComGrupos(Long codigo) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
        Root<Usuario> usuarioEntity = query.from(Usuario.class);
        Fetch<Usuario, Grupo> grupoJoinRoot = usuarioEntity.fetch("grupos");
        query.where(builder.equal(usuarioEntity.get("codigo"), codigo));

        TypedQuery<Usuario> typedQuery = manager.createQuery(query);
        return (Usuario) typedQuery.getSingleResult();
    }

    private Long total(UsuarioFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Usuario> usuarioEntity = query.from(Usuario.class);
        Predicate[] filtros = adicionarFiltro(filtro, usuarioEntity);

        query.where(filtros);
        query.select(builder.count(usuarioEntity));
        return manager.createQuery(query).getSingleResult();
    }

    private Predicate[] adicionarFiltro(UsuarioFilter filtro, Root<Usuario> usuarioEntity) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);

        if (filtro != null) {
            if (!StringUtils.isEmpty(filtro.getNome())) {
                predicateList.add(builder.like(usuarioEntity.get("nome"), "%"+filtro.getNome()+"%"));
            }

            if (!StringUtils.isEmpty(filtro.getEmail())) {
                predicateList.add(builder.like(usuarioEntity.get("email"), "%" + filtro.getEmail() + "%"));
            }

            if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
                for (Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {

                    Subquery<Grupo> subquery = query.subquery(Grupo.class);
                    Root<Usuario> usuarioSubquery = subquery.correlate(usuarioEntity);
                    Join<Usuario, Grupo> grupoJoin = usuarioSubquery.join("grupos");
                    subquery.select(usuarioSubquery.get("codigo"));
                    subquery.where(builder.equal(grupoJoin.get("codigo"), codigoGrupo));
                    predicateList.add(usuarioEntity.in(subquery));
                }
            }
        }
        return predicateList.toArray(new Predicate[predicateList.size()]);
    }

}
