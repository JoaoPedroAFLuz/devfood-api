package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.CozinhaInputDisassembler;
import com.joaopedroafluz.devfood.api.assembler.CozinhaModelAssembler;
import com.joaopedroafluz.devfood.api.model.CozinhaModel;
import com.joaopedroafluz.devfood.api.model.input.CozinhaInput;
import com.joaopedroafluz.devfood.api.openapi.controller.CozinhaControllerOpenApi;
import com.joaopedroafluz.devfood.domain.model.Cozinha;
import com.joaopedroafluz.devfood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    private final CozinhaService cozinhaService;
    private final CozinhaInputDisassembler cozinhaInputDisassembler;
    private final CozinhaModelAssembler cozinhaModelAssembler;

    @GetMapping
    public Page<CozinhaModel> buscarPaginada(Pageable pageable) {
        return cozinhaService.buscarPaginada(pageable)
                .map(cozinhaModelAssembler::toModel);
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscarPorId(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaService.buscarOuFalharPorId(cozinhaId);

        return cozinhaModelAssembler.toModel(cozinha);
    }

    @GetMapping("/por-nome")
    public List<CozinhaModel> buscarPorNome(String nome) {
        return cozinhaService.buscarPorNome(nome).stream()
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

    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cozinhaService.buscarOuFalharPorId(cozinhaId);

        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        Cozinha cozinhaPersistida = cozinhaService.salvar(cozinhaAtual);

        return cozinhaModelAssembler.toModel(cozinhaPersistida);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.excluir(cozinhaId);
    }

}
