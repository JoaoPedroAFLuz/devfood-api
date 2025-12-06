package com.joaopedroafluz.devfood.domain.repository;

import com.joaopedroafluz.devfood.domain.model.FotoProduto;
import com.joaopedroafluz.devfood.domain.model.Produto;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoRepositoryQueries {

    List<Produto> findByRestauranteId(Long restauranteId);

    List<Produto> findByRestauranteIdAndAtivoIsTrue(Long restauranteId);

    Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);

    @Query("SELECT fp " +
            "FROM FotoProduto fp " +
            "JOIN fp.produto p " +
            "WHERE fp.id = :produtoId " +
            "AND p.restaurante.id = :restauranteId")
    Optional<FotoProduto> findFotoProdutoByProdutoIdAndRestauranteId(Long restauranteId, Long produtoId);

}
