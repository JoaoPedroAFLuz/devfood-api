package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.CidadeModelAssembler;
import com.joaopedroluz57.devfood.api.assembler.CidadeModelDisassembler;
import com.joaopedroluz57.devfood.api.model.CidadeModel;
import com.joaopedroluz57.devfood.api.model.input.CidadeInput;
import com.joaopedroluz57.devfood.domain.exception.EstadoNaoEncontradoException;
import com.joaopedroluz57.devfood.domain.exception.NegocioException;
import com.joaopedroluz57.devfood.domain.model.Cidade;
import com.joaopedroluz57.devfood.domain.repository.CidadeRepository;
import com.joaopedroluz57.devfood.domain.service.CidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private final CidadeRepository cidadeRepository;
    private final CidadeService cidadeService;
    private final CidadeModelAssembler cidadeModelAssembler;
    private final CidadeModelDisassembler cidadeModelDisassembler;

    public CidadeController(CidadeRepository cidadeRepository,
                            CidadeService cidadeService,
                            CidadeModelAssembler cidadeModelAssembler,
                            CidadeModelDisassembler cidadeModelDisassembler) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeService = cidadeService;
        this.cidadeModelAssembler = cidadeModelAssembler;
        this.cidadeModelDisassembler = cidadeModelDisassembler;
    }


    @GetMapping
    public List<CidadeModel> buscarTodas() {
        return cidadeRepository.findAll().stream()
                .map(cidadeModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscarPorId(@PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscarOuFalharPorId(cidadeId);

        return cidadeModelAssembler.toModel(cidade);
    }

    @GetMapping("/por-estado")
    public List<CidadeModel> buscarPorEstado(Long estadoId) {
        return cidadeRepository.findByEstadoId(estadoId).stream()
                .map(cidadeModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeModelDisassembler.fromModel(cidadeInput);

            Cidade cidadePersistida = cidadeService.salvar(cidade);

            return cidadeModelAssembler.toModel(cidadePersistida);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cidadeService.buscarOuFalharPorId(cidadeId);

            cidadeModelDisassembler.copyFromModel(cidadeInput, cidadeAtual);

            Cidade cidadePersistida = cidadeService.salvar(cidadeAtual);

            return cidadeModelAssembler.toModel(cidadePersistida);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }

}
