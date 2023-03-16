package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Cozinha;

import java.util.List;


public interface CozinhaRepository {

    List<Cozinha> buscarTodas();

    Cozinha buscarPorId(Long id);

    Cozinha salvar(Cozinha cozinha);

    void remover(Cozinha cozinha);

}

