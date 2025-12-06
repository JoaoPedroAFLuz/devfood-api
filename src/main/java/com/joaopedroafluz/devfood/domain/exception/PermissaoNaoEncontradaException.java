package com.joaopedroafluz.devfood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format("Não há um cadastro de permissao com o id: %d", permissaoId));
    }

}
