package com.joaopedroafluz.devfood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long produtoId, Long restauranteId) {
        this(String.format("Não há um cadastro de produto com o id: %d para o restaurante com o id: %d ",
                produtoId,
                restauranteId));
    }

}
