package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.FotoProdutoModelAssembler;
import com.joaopedroluz57.devfood.api.assembler.ProdutoInputDisassembler;
import com.joaopedroluz57.devfood.api.assembler.ProdutoModelAssembler;
import com.joaopedroluz57.devfood.api.model.FotoProdutoModel;
import com.joaopedroluz57.devfood.api.model.ProdutoModel;
import com.joaopedroluz57.devfood.api.model.input.FotoProdutoInput;
import com.joaopedroluz57.devfood.api.model.input.ProdutoInput;
import com.joaopedroluz57.devfood.domain.model.FotoProduto;
import com.joaopedroluz57.devfood.domain.model.Produto;
import com.joaopedroluz57.devfood.domain.service.ProdutoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoModelAssembler produtoModelAssembler;
    private final ProdutoInputDisassembler produtoInputDisassembler;
    private final FotoProdutoModelAssembler fotoProdutoModelAssembler;

    public RestauranteProdutoController(ProdutoService produtoService,
                                        ProdutoModelAssembler produtoModelAssembler,
                                        ProdutoInputDisassembler produtoInputDisassembler,
                                        FotoProdutoModelAssembler fotoProdutoModelAssembler) {
        this.produtoService = produtoService;
        this.produtoModelAssembler = produtoModelAssembler;
        this.produtoInputDisassembler = produtoInputDisassembler;
        this.fotoProdutoModelAssembler = fotoProdutoModelAssembler;
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

    @PutMapping(path = "/{produtoId}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId,
                                          @PathVariable Long produtoId,
                                          @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
        Produto produto = produtoService.buscarOuFalharPorIdERestauranteId(produtoId, restauranteId);

        FotoProduto fotoProduto = FotoProduto.builder()
                .id(produto.getId())
                .produto(produto)
                .nomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename())
                .descricao(fotoProdutoInput.getDescricao())
                .tipoArquivo(fotoProdutoInput.getArquivo().getContentType())
                .tamanho(fotoProdutoInput.getArquivo().getSize())
                .build();

        FotoProduto fotoPersistida = produtoService.salvarFoto(fotoProduto,
                fotoProdutoInput.getArquivo().getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoPersistida);
    }

}
