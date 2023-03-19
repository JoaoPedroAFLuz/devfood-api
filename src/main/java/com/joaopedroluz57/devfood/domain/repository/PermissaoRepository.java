package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
