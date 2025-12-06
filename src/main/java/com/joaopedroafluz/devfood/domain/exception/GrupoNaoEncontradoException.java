package com.joaopedroafluz.devfood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long grupoId) {
        this(String.format("Não há um cadastro de grupo com o id: %d", grupoId));
    }

}
