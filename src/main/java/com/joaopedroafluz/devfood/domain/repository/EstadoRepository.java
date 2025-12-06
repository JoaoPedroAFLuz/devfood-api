package com.joaopedroafluz.devfood.domain.repository;

import com.joaopedroafluz.devfood.domain.model.Estado;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {

}

