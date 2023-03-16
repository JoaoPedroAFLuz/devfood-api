package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Cidade;

import java.util.List;


public interface CidadeRepository {

    List<Cidade> todos();

    Cidade buscarPorId(Long id);

    Cidade adicionar(Cidade cidade);

    void remover(Cidade cidade);

}

