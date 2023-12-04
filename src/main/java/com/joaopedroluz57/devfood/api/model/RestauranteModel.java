package com.joaopedroluz57.devfood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {

    private Long id;
    private String nome;
    private BigDecimal taxaEntrega;
    private CozinhaModel cozinha;

}
