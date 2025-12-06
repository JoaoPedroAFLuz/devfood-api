package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.ResourceUriHelper;
import com.joaopedroafluz.devfood.api.assembler.CidadeInputDisassembler;
import com.joaopedroafluz.devfood.api.assembler.CidadeModelAssembler;
import com.joaopedroafluz.devfood.api.model.CidadeModel;
import com.joaopedroafluz.devfood.api.model.input.CidadeInput;
import com.joaopedroafluz.devfood.api.openapi.controller.CidadeControllerOpenApi;
import com.joaopedroafluz.devfood.domain.exception.EstadoNaoEncontradoException;
import com.joaopedroafluz.devfood.domain.exception.NegocioException;
import com.joaopedroafluz.devfood.domain.repository.CidadeRepository;
import com.joaopedroafluz.devfood.domain.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeService cidadeService;
    private final CidadeRepository cidadeRepository;
    private final CidadeModelAssembler cidadeModelAssembler;
    private final CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public CollectionModel<CidadeModel> buscarTodas() {
        final var cidades = cidadeRepository.findAll();

        return cidadeModelAssembler.toCollectionModel(cidades);
    }

    @GetMapping("/{cidadeId}")
    public CidadeModel buscarPorId(@PathVariable Long cidadeId) {
        final var cidade = cidadeService.buscarOuFalharPorId(cidadeId);

        return cidadeModelAssembler.toModel(cidade);
    }

    @GetMapping("/por-estado")
    public List<CidadeModel> buscarPorEstado(@RequestParam Long estadoId) {
        return cidadeRepository.findByEstadoId(estadoId).stream()
                .map(cidadeModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            final var cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            cidadeService.salvar(cidade);

            final var cidadeModel = cidadeModelAssembler.toModel(cidade);

            ResourceUriHelper.addUriInResponseHeader(cidade.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            final var cidade = cidadeService.buscarOuFalharPorId(cidadeId);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidade);

            return cidadeModelAssembler.toModel(cidade);
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
