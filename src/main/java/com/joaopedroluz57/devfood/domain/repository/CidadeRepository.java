package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Cidade;

import java.util.List;


public interface CidadeRepository {

    List<Cidade> buscarTodas();

    Cidade buscarPorId(Long id);

    Cidade salvar(Cidade cidade);

    void remover(Cidade cidade);

}

