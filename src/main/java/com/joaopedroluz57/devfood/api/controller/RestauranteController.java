package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.domain.exception.EntidadeNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.repository.RestauranteRepository;
import com.joaopedroluz57.devfood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    CadastroRestauranteService cadastroRestauranteService;

    @GetMapping()
    public List<Restaurante> listar() {
        return restauranteRepository.buscarTodos();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        final Restaurante restaurante = restauranteRepository.buscarPorId(restauranteId);

        if (Objects.isNull(restaurante)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(restaurante);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        try {
            Restaurante restauranteAtual = restauranteRepository.buscarPorId(id);

            if (Objects.isNull(restauranteAtual)) {
                return ResponseEntity.notFound().build();
            }

            BeanUtils.copyProperties(restaurante, restauranteAtual, "id");

            restauranteAtual = cadastroRestauranteService.salvar(restauranteAtual);

            return ResponseEntity.ok().body(restauranteAtual);

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
