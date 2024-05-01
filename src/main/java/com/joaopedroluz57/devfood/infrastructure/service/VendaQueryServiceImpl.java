package com.joaopedroluz57.devfood.infrastructure.service;

import com.joaopedroluz57.devfood.domain.enums.StatusPedido;
import com.joaopedroluz57.devfood.domain.filter.VendaDiariaFilter;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import com.joaopedroluz57.devfood.domain.model.dto.VendaDiaria;
import com.joaopedroluz57.devfood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.*;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    private final EntityManager entityManager;

    public VendaQueryServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        Root<Pedido> root = query.from(Pedido.class);

        Expression<Date> functionDateDataCriacao = builder.function(
                "date", Date.class, root.get("dataCriacao")
        );

        CompoundSelection<VendaDiaria> selection = builder.construct(VendaDiaria.class,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")),
                functionDateDataCriacao
        );

        ArrayList<Predicate> predicates = new ArrayList<>();
        List<StatusPedido> statusList = Arrays.asList(
                StatusPedido.CONFIRMADO, StatusPedido.A_CAMINHO, StatusPedido.ENTREGUE
        );

        predicates.add(builder.in(root.get("status")).value(statusList));

        if (Objects.nonNull(filtro.getRestauranteId())) {
            predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));
        }

        if (Objects.nonNull(filtro.getDataCriacaoInicio())) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
        }

        if (Objects.nonNull(filtro.getDataCriacaoFim())) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
        }

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);

        return entityManager.createQuery(query).getResultList();
    }

}
