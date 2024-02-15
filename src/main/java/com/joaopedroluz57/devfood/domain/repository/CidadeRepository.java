package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Cidade;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {

    List<Cidade> findByEstadoId(Long id);

}

