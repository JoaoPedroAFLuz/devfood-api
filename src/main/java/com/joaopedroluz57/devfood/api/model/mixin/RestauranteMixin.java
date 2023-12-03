package com.joaopedroluz57.devfood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.model.Endereco;
import com.joaopedroluz57.devfood.domain.model.FormaPagamento;
import com.joaopedroluz57.devfood.domain.model.Produto;

import java.time.OffsetDateTime;
import java.util.List;

public abstract class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento;

    @JsonIgnore
    private List<Produto> produtos;

    @JsonIgnore
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    private OffsetDateTime dataAtualizacao;


}
