package com.joaopedroafluz.devfood.domain.exception;

public class FotoProdutoNaoEncontrada extends EntidadeNaoEncontradaException {

    public FotoProdutoNaoEncontrada(String message) {
        super(message);
    }

    public FotoProdutoNaoEncontrada(Long produtoId, Long restauranteId) {
        this(String.format(
                "Não há um cadastro de foto para o produto com o código: %d do restaurante com o código: %d ",
                restauranteId, produtoId));
    }

}
