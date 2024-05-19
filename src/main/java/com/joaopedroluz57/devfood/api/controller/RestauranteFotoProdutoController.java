package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.FotoProdutoModelAssembler;
import com.joaopedroluz57.devfood.api.model.FotoProdutoModel;
import com.joaopedroluz57.devfood.api.model.input.FotoProdutoInput;
import com.joaopedroluz57.devfood.domain.exception.EntidadeNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.model.FotoProduto;
import com.joaopedroluz57.devfood.domain.model.Produto;
import com.joaopedroluz57.devfood.domain.service.ArmazenamentoFotoService;
import com.joaopedroluz57.devfood.domain.service.FotoProdutoService;
import com.joaopedroluz57.devfood.domain.service.ProdutoService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    private final ProdutoService produtoService;
    private final FotoProdutoService fotoProdutoService;
    private final ArmazenamentoFotoService armazenamentoFotoService;
    private final FotoProdutoModelAssembler fotoProdutoModelAssembler;

    public RestauranteFotoProdutoController(ProdutoService produtoService,
                                            FotoProdutoService fotoProdutoService,
                                            ArmazenamentoFotoService armazenamentoFotoService,
                                            FotoProdutoModelAssembler fotoProdutoModelAssembler) {
        this.produtoService = produtoService;
        this.fotoProdutoService = fotoProdutoService;
        this.armazenamentoFotoService = armazenamentoFotoService;
        this.fotoProdutoModelAssembler = fotoProdutoModelAssembler;
    }


    @GetMapping
    public FotoProdutoModel buscarDadosFotoProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = fotoProdutoService
                .buscarOuFalharPorProdutoIdEPorRestauranteId(restauranteId, produtoId);

        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<InputStreamResource> buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
       try {
           FotoProduto fotoProduto = fotoProdutoService
                   .buscarOuFalharPorProdutoIdEPorRestauranteId(restauranteId, produtoId);

           InputStream inputStream = armazenamentoFotoService.recuperar(fotoProduto.getNomeArquivo());

           return ResponseEntity.ok()
                   .contentType(MediaType.IMAGE_PNG)
                   .body(new InputStreamResource(inputStream));
       } catch (EntidadeNaoEncontradaException e) {
           return ResponseEntity.notFound().build();
       }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

        FotoProduto fotoPersistida = fotoProdutoService.salvarFoto(fotoProduto,
                fotoProdutoInput.getArquivo().getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoPersistida);
    }

}
