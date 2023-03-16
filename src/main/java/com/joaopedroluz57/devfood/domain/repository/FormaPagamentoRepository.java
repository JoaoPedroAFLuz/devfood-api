package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> buscarTodas();

    FormaPagamento buscarPorId(Long id);

    FormaPagamento salvar(FormaPagamento formaPagamento);

    void remover(FormaPagamento formaPagamento);

}
