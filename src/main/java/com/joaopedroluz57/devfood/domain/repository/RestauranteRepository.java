package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> todos();

    Restaurante buscarPorId(Long id);

    Restaurante adicionar(Restaurante restaurante);

    void remover(Restaurante restaurante);

}
