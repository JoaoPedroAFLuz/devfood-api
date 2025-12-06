package com.joaopedroafluz.devfood.domain.repository;

import com.joaopedroafluz.devfood.domain.model.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {
}
