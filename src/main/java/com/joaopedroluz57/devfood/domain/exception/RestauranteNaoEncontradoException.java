package com.joaopedroluz57.devfood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long estadoId) {
        this(String.format("Não há um cadastro de restaurante com o id: %d.", estadoId));
    }

}
