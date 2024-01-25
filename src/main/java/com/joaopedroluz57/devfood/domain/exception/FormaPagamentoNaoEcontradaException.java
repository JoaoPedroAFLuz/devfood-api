package com.joaopedroluz57.devfood.domain.exception;

public class FormaPagamentoNaoEcontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEcontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEcontradaException(Long formaPagamentoId) {
        this(String.format("Não há um cadastro de forma de pagamento com o id: %d", formaPagamentoId));
    }

}
