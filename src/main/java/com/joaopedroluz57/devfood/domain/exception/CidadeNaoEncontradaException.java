package com.joaopedroluz57.devfood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long estadoId) {
        this(String.format("Não há um cadastro de cidade com o id: %d", estadoId));
    }

}
