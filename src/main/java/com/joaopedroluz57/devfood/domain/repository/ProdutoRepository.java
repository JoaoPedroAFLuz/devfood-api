package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends CustomJpaRepository <Produto, Long> {

    List<Produto> findByRestauranteId(Long restauranteId);

    List<Produto> findByRestauranteIdAndAtivoIsTrue(Long restauranteId);

    Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);

}
