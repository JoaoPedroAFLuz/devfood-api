package com.joaopedroafluz.devfood.domain.repository;

import com.joaopedroafluz.devfood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findByNomeContaining(String nome);

}

