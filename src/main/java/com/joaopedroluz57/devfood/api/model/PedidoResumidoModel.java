package com.joaopedroluz57.devfood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class PedidoResumidoModel {

    private UUID codigo;
    private BigDecimal subTotal;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoModel restaurante;
    private UsuarioModel cliente;

}
