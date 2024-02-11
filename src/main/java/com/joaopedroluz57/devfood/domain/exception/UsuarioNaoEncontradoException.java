package com.joaopedroluz57.devfood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long usuarioId) {
        this(String.format("Não há um cadastro de usuário com o id: %d", usuarioId));
    }

}
