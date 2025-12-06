package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.UsuarioModelAssembler;
import com.joaopedroafluz.devfood.api.model.UsuarioModel;
import com.joaopedroafluz.devfood.domain.model.Usuario;
import com.joaopedroafluz.devfood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {

    private final RestauranteService restauranteService;
    private final UsuarioModelAssembler usuarioModelAssembler;


    @GetMapping
    public CollectionModel<UsuarioModel> buscarTodos(@PathVariable Long restauranteId) {
        final Set<Usuario> resposaveis = restauranteService.buscarOuFalharPorId(restauranteId).getResposaveis();

        return usuarioModelAssembler.toCollectionModel(resposaveis)
                .add(linkTo(methodOn(RestauranteUsuarioController.class).buscarTodos(restauranteId)).withSelfRel());
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
