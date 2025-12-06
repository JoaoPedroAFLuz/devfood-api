package com.joaopedroafluz.devfood.domain.repository;

import com.joaopedroafluz.devfood.domain.model.Cidade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {

    List<Cidade> findByEstadoId(Long id);

}

