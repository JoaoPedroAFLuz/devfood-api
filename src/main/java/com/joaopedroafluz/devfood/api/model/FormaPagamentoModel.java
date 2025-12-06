package com.joaopedroafluz.devfood.api.model;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoModel {

    private Long id;
    private String descricao;
    private OffsetDateTime dataAtualizacao;

}
