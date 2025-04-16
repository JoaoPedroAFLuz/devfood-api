package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long> {

    @Query("SELECT MAX(formaPagamento.dataAtualizacao) " +
            "FROM FormaPagamento formaPagamento ")
    OffsetDateTime getDataUltimaAtualizacao();

}
