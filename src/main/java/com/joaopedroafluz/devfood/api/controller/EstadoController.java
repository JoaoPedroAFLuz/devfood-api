package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.EstadoInputDisassembler;
import com.joaopedroafluz.devfood.api.assembler.EstadoModelAssembler;
import com.joaopedroafluz.devfood.api.model.EstadoModel;
import com.joaopedroafluz.devfood.api.model.input.EstadoInput;
import com.joaopedroafluz.devfood.domain.model.Estado;
import com.joaopedroafluz.devfood.domain.repository.EstadoRepository;
import com.joaopedroafluz.devfood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
@RequiredArgsConstructor
public class EstadoController {

    private final EstadoService estadoService;
    private final EstadoRepository estadoRepository;
    private final EstadoModelAssembler estadoModelAssembler;
    private final EstadoInputDisassembler estadoInputDisassembler;


    @GetMapping
    public List<Estado> buscarTodos() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public Estado buscarPorId(@PathVariable Long estadoId) {
        return estadoService.buscarOuFalharPorId(estadoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody @Valid EstadoInput estadoInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

        estadoService.salvar(estado);

        return estadoModelAssembler.toModel(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
        Estado estadoAtual = estadoService.buscarOuFalharPorId(estadoId);

        BeanUtils.copyProperties(estado, estadoAtual, "id");

        return estadoService.salvar(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.excluir(estadoId);
    }
}
