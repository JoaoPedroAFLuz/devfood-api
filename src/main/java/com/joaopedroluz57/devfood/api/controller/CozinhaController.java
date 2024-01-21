package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.CozinhaInputDisassembler;
import com.joaopedroluz57.devfood.api.assembler.CozinhaModelAssembler;
import com.joaopedroluz57.devfood.api.model.CozinhaModel;
import com.joaopedroluz57.devfood.api.model.input.CozinhaInput;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.repository.CozinhaRepository;
import com.joaopedroluz57.devfood.domain.service.CozinhaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;
    private final CozinhaService cozinhaService;
    private final CozinhaInputDisassembler cozinhaInputDisassembler;
    private final CozinhaModelAssembler cozinhaModelAssembler;

    public CozinhaController(CozinhaRepository cozinhaRepository,
                             CozinhaService cozinhaService,
                             CozinhaInputDisassembler cozinhaInputDisassembler,
                             CozinhaModelAssembler cozinhaModelAssembler) {
        this.cozinhaRepository = cozinhaRepository;
        this.cozinhaService = cozinhaService;
        this.cozinhaInputDisassembler = cozinhaInputDisassembler;
        this.cozinhaModelAssembler = cozinhaModelAssembler;
    }

    @GetMapping
    public List<CozinhaModel> buscarTodos() {
        return cozinhaRepository.findAll().stream()
                .map(cozinhaModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscarPorId(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaService.buscarOuFalharPorId(cozinhaId);

        return cozinhaModelAssembler.toModel(cozinha);
    }

    @GetMapping("/por-nome")
    public List<CozinhaModel> buscarPorNome(String nome) {
        return cozinhaRepository.findByNomeContaining(nome).stream()
                .map(cozinhaModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);

        Cozinha cozinhaPersistida = cozinhaService.salvar(cozinha);

        return cozinhaModelAssembler.toModel(cozinhaPersistida);
    }

    @PutMapping("/{id}")
    public CozinhaModel atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cozinhaService.buscarOuFalharPorId(id);

        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        Cozinha cozinhaPersistida = cozinhaService.salvar(cozinhaAtual);

        return cozinhaModelAssembler.toModel(cozinhaPersistida);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cozinhaService.excluir(id);
    }

}
