package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping()
    public List<Restaurante> listar() {
        return restauranteRepository.buscarTodos();
    }

    @GetMapping("/{restauranteId}")
    public Restaurante buscar(@PathVariable Long restauranteId) {
        return restauranteRepository.buscarPorId(restauranteId);
    }

}
