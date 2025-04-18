package com.joaopedroluz57.devfood.api.model;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
//@JsonFilter("pedidoFilter")
@ApiModel(value = "Pedido Resumido Model", description = "Representação resumida de um pedido")
public class PedidoResumidoModel {

    private UUID codigo;
    private BigDecimal subTotal;
    private BigDecimal taxaEntrega;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoModel restaurante;
//    private UsuarioModel cliente;
    private String nomeCliente;

}
