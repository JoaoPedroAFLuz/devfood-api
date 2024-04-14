package com.joaopedroluz57.devfood.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum StatusPedido {

    CRIADO("Criado"),
    CONFIRMADO("Confirmado", CRIADO),
    A_CAMINHO("A caminho", CONFIRMADO),
    ENTREGUE("Entregue", A_CAMINHO),
    CANCELADO("Cancelado", CRIADO, CONFIRMADO);

    private final String descricao;
    private final List<StatusPedido> statusAnteriores;

    StatusPedido(String descricao, StatusPedido... statusAnteriores) {
        this.descricao = descricao;
        this.statusAnteriores = Arrays.asList(statusAnteriores);
    }

    public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
        return !novoStatus.statusAnteriores.contains(this);
    }

}
