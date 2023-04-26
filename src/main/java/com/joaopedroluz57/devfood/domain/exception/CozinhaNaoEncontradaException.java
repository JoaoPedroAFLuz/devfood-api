package com.joaopedroluz57.devfood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long estadoId) {
        this(String.format("Não há um cadastro de cozinha com o id: %d.", estadoId));
    }

}
