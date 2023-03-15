package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Estado;

import java.util.List;


public interface EstadoRepository {

    List<Estado> todos();

    Estado porId(Long id);

    Estado adicionar(Estado estado);

    void remover(Estado estado);

}

