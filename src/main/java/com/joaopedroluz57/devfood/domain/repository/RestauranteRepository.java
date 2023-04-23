package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository
        extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    @Query("from Restaurante r "
            + "join fetch r.cozinha "
            + "order by r.id asc ")
    List<Restaurante> findAll();

    List<Restaurante> consultarPorNome(String nome, Long cozinhaId);

    List<Restaurante> findByTaxaEntregaBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

}
