package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.GrupoInputDisassembler;
import com.joaopedroluz57.devfood.api.assembler.GrupoModelAssembler;
import com.joaopedroluz57.devfood.api.model.GrupoModel;
import com.joaopedroluz57.devfood.api.model.input.GrupoInput;
import com.joaopedroluz57.devfood.domain.model.Grupo;
import com.joaopedroluz57.devfood.domain.service.GrupoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoService grupoService;
    private final GrupoModelAssembler grupoModelAssembler;
    private final GrupoInputDisassembler grupoInputDisassembler;

    public GrupoController(GrupoService grupoService,
                           GrupoModelAssembler grupoModelAssembler,
                           GrupoInputDisassembler grupoInputDisassembler) {
        this.grupoService = grupoService;
        this.grupoModelAssembler = grupoModelAssembler;
        this.grupoInputDisassembler = grupoInputDisassembler;
    }

    @GetMapping
    public List<GrupoModel> buscarTodos() {
        return grupoService.buscarTodos().stream()
                .map(grupoModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{grupoId}")
    public GrupoModel buscarPorId(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalharPorId(grupoId);

        return grupoModelAssembler.toModel(grupo);
    }

    @PostMapping
    public GrupoModel cadastrar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

        Grupo grupoPersistido = grupoService.salvar(grupo);

        return grupoModelAssembler.toModel(grupoPersistido);
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = grupoService.buscarOuFalharPorId(grupoId);

        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

        Grupo grupoPersistido = grupoService.salvar(grupoAtual);

        return grupoModelAssembler.toModel(grupoPersistido);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
        grupoService.excluir(grupoId);
    }

}
