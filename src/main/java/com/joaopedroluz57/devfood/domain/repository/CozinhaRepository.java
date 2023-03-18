package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> buscarTodas();

    Cozinha buscarPorId(Long id);

    List<Cozinha> buscarPorNome(String nome);

    Cozinha salvar(Cozinha cozinha);

    void remover(Long id);

}

