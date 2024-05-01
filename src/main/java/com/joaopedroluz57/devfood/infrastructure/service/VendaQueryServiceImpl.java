package com.joaopedroluz57.devfood.infrastructure.service;

import com.joaopedroluz57.devfood.domain.filter.VendaDiariaFilter;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import com.joaopedroluz57.devfood.domain.model.dto.VendaDiaria;
import com.joaopedroluz57.devfood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

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

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        return entityManager.createQuery(query).getResultList();
    }

}
