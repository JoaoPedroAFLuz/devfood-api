package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.ProdutoInputDisassembler;
import com.joaopedroluz57.devfood.api.assembler.ProdutoModelAssembler;
import com.joaopedroluz57.devfood.api.model.ProdutoModel;
import com.joaopedroluz57.devfood.api.model.input.ProdutoInput;
import com.joaopedroluz57.devfood.domain.model.Produto;
import com.joaopedroluz57.devfood.domain.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoModelAssembler produtoModelAssembler;
    private final ProdutoInputDisassembler produtoInputDisassembler;

    public RestauranteProdutoController(ProdutoService produtoService,
                                        ProdutoModelAssembler produtoModelAssembler,
                                        ProdutoInputDisassembler produtoInputDisassembler) {
        this.produtoService = produtoService;
        this.produtoModelAssembler = produtoModelAssembler;
        this.produtoInputDisassembler = produtoInputDisassembler;
    }


    @GetMapping
    public List<ProdutoModel> buscarTodos(@PathVariable Long restauranteId) {
        return produtoService.buscarPorRestauranteId(restauranteId)
                .stream()
                .map(produtoModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscarPorId(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
        Produto produto = produtoService.buscarOuFalharPorIdERestauranteId(produtoId, restauranteId);

        return produtoModelAssembler.toModel(produto);
    }

    @PostMapping
    public ProdutoModel adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);

        Produto produtoPersistido = produtoService.salvar(restauranteId, produto);

        return produtoModelAssembler.toModel(produtoPersistido);
    }

    // TODO: rota de atualização de produto

}
