package com.joaopedroafluz.devfood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

}
