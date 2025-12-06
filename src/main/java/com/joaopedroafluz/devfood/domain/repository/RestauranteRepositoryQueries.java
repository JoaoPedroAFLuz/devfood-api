package com.joaopedroafluz.devfood.domain.repository;

import com.joaopedroafluz.devfood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    List<Restaurante> buscarPorNomeETaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> buscarPorNomeEFreteGratis(String nome);

}
