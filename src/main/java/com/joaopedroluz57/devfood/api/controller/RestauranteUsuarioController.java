package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.UsuarioModelAssembler;
import com.joaopedroluz57.devfood.api.model.UsuarioModel;
import com.joaopedroluz57.devfood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {

    private final RestauranteService restauranteService;
    private final UsuarioModelAssembler usuarioModelAssembler;

    public RestauranteUsuarioController(RestauranteService restauranteService,
                                        UsuarioModelAssembler usuarioModelAssembler) {
        this.restauranteService = restauranteService;
        this.usuarioModelAssembler = usuarioModelAssembler;
    }


    @GetMapping
    public List<UsuarioModel> buscarTodos(@PathVariable Long restauranteId) {
        return restauranteService.buscarOuFalharPorId(restauranteId).getResposaveis().stream()
                .map(usuarioModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.adicionarResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        restauranteService.removerResponsavel(restauranteId, usuarioId);
    }

}
