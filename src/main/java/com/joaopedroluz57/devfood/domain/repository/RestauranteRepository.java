package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> buscarTodos();

    Restaurante buscarPorId(Long id);

    Restaurante salvar(Restaurante restaurante);

    void remover(Long id);

}
