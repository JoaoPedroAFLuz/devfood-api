package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.ProdutoInputDisassembler;
import com.joaopedroafluz.devfood.api.assembler.ProdutoModelAssembler;
import com.joaopedroafluz.devfood.api.model.ProdutoModel;
import com.joaopedroafluz.devfood.api.model.input.ProdutoInput;
import com.joaopedroafluz.devfood.domain.model.Produto;
import com.joaopedroafluz.devfood.domain.service.ProdutoService;
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
    public List<ProdutoModel> buscarTodos(@PathVariable Long restauranteId,
                                          @RequestParam(required = false) boolean incluirInativos) {
        if (incluirInativos) {
            return produtoService.buscarPorRestauranteId(restauranteId).stream()
                    .map(produtoModelAssembler::toModel)
                    .collect(Collectors.toList());
        }

        return produtoService.buscarAtivosPorRestauranteId(restauranteId).stream()
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

    @PutMapping("/{produtoId}")
    public ProdutoModel atualizar(@PathVariable Long restauranteId,
                                  @PathVariable Long produtoId,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscarOuFalharPorIdERestauranteId(produtoId, restauranteId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        Produto produtoPersistido = produtoService.salvar(restauranteId, produtoAtual);

        return produtoModelAssembler.toModel(produtoPersistido);
    }

}
