package com.joaopedroluz57.devfood.domain.enums;

import lombok.Getter;

@Getter
public enum StatusPedido {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado"),
    A_CAMINHO("A caminho"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

}
