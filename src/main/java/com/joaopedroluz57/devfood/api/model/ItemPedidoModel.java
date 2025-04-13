package com.joaopedroluz57.devfood.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ItemPedidoModel {

    private Long produtoId;
    private String produtoNome;
    private BigDecimal precoUnitario;
    private Integer quantidade;
    private BigDecimal precoTotal;
    private String observacao;

}
