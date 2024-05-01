package com.joaopedroluz57.devfood.infrastructure.repository;

import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.repository.RestauranteRepository;
import com.joaopedroluz57.devfood.domain.repository.RestauranteRepositoryQueries;
import com.joaopedroluz57.devfood.infrastructure.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> buscarPorNomeETaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {

        // Consulta com Criteria API
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        final Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (Objects.nonNull(taxaInicial)) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaEntrega"), taxaInicial));
        }

        if (Objects.nonNull(taxaFinal)) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaEntrega"), taxaFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(criteria)
                .getResultList();

        //  Consulta dinâmica com JPQL
//        var jpql = new StringBuilder();
//
//        jpql.append("from Restaurante where 0 = 0 ");
//
//        var parametros = new HashMap<String, Object>();
//
//        if (StringUtils.hasLength(nome)) {
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if (Objects.nonNull(taxaInicial)) {
//            jpql.append("and taxaEntrega >= :taxaInicial ");
//            parametros.put("taxaInicial", taxaInicial);
//
//        }
//
//        if (Objects.nonNull(taxaFinal)) {
//            jpql.append("and taxaEntrega <= :taxaFinal ");
//            parametros.put("taxaFinal", taxaFinal);
//        }
//
//        TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
//
//        parametros.forEach(query::setParameter);
//
//        return query.getResultList();
    }

    @Override
    public List<Restaurante> buscarPorNomeEFreteGratis(String nome) {
        return restauranteRepository.findAll(
                RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome))
        );
    }

}
