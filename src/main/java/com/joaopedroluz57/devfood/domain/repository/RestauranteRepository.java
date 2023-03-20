package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries {

    List<Restaurante> consultarPorNome(String nome, Long cozinhaId);

    List<Restaurante> findByTaxaEntregaBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

}
