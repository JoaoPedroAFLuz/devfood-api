package com.joaopedroluz57.devfood.domain.exception;

import java.util.UUID;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(Long pedidoId) {
        this(String.format("Não há um cadastro de pedido com o id: %d", pedidoId));
    }

    public PedidoNaoEncontradoException(UUID codigoPedido) {
        this(String.format("Não há um cadastro de pedido com o código: %s", codigoPedido));
    }

}
