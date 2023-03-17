package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> buscarTodos();

    Estado buscarPorId(Long id);

    Estado salvar(Estado estado);

    void remover(Estado estado);

}

