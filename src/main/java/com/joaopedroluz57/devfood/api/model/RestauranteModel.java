package com.joaopedroluz57.devfood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.joaopedroluz57.devfood.api.view.RestauranteView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModel {

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private Long id;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaEntrega;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel cozinha;

    private EnderecoModel endereco;
    private Boolean ativo;
    private Boolean aberto;

}
