package com.joaopedroafluz.devfood.domain.repository;

import com.joaopedroafluz.devfood.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

    @Query("FROM Pedido p " +
            "JOIN FETCH p.cliente " +
            "JOIN p.restaurante r " +
            "JOIN FETCH r.cozinha")
    List<Pedido> findAll();

    Optional<Pedido> findByCodigo(UUID codigoPedido);

}
