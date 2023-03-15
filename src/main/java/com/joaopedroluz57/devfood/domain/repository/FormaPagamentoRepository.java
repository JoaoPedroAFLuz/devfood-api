package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> todos();

    FormaPagamento porId(Long id);

    FormaPagamento adicionar(FormaPagamento formaPagamento);

    void remover(FormaPagamento formaPagamento);

}
