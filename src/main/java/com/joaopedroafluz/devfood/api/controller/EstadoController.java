package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.EstadoInputDisassembler;
import com.joaopedroafluz.devfood.api.assembler.EstadoModelAssembler;
import com.joaopedroafluz.devfood.api.model.EstadoModel;
import com.joaopedroafluz.devfood.api.model.input.EstadoInput;
import com.joaopedroafluz.devfood.domain.repository.EstadoRepository;
import com.joaopedroafluz.devfood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;
    private final EstadoRepository estadoRepository;
    private final EstadoModelAssembler estadoModelAssembler;
    private final EstadoInputDisassembler estadoInputDisassembler;


    @GetMapping
    public CollectionModel<EstadoModel> buscarTodos() {
        final var estados = estadoRepository.findAll();

        return estadoModelAssembler.toCollectionModel(estados)
                .add(linkTo(EstadoController.class).withSelfRel());
    }

    @GetMapping("/{estadoId}")
    public EstadoModel buscarPorId(@PathVariable Long estadoId) {
        final var estado = estadoService.buscarOuFalharPorId(estadoId);

        return estadoModelAssembler.toModel(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        final var estado = estadoInputDisassembler.toDomainObject(estadoInput);

        estadoService.salvar(estado);

        return estadoModelAssembler.toModel(estado);
    }

    @PutMapping("/{estadoId}")
    public EstadoModel atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        final var estadoAtual = estadoService.buscarOuFalharPorId(estadoId);

        copyProperties(estadoInput, estadoAtual, "id");

        estadoService.salvar(estadoAtual);

        return estadoModelAssembler.toModel(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.excluir(estadoId);
    }
}
