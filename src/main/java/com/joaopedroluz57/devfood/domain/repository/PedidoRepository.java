package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Pedido;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends CustomJpaRepository<Pedido, Long> {

    @Query("FROM Pedido p " +
            "JOIN FETCH p.cliente " +
            "JOIN p.restaurante r " +
            "JOIN FETCH r.cozinha")
    List<Pedido> findAll();

}
