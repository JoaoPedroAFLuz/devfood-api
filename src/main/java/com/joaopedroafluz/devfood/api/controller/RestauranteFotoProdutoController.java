package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.FotoProdutoModelAssembler;
import com.joaopedroafluz.devfood.api.model.FotoProdutoModel;
import com.joaopedroafluz.devfood.api.model.input.FotoProdutoInput;
import com.joaopedroafluz.devfood.domain.exception.EntidadeNaoEncontradaException;
import com.joaopedroafluz.devfood.domain.model.FotoProduto;
import com.joaopedroafluz.devfood.domain.model.FotoRecuperada;
import com.joaopedroafluz.devfood.domain.model.Produto;
import com.joaopedroafluz.devfood.domain.service.ArmazenamentoFotoService;
import com.joaopedroafluz.devfood.domain.service.FotoProdutoService;
import com.joaopedroafluz.devfood.domain.service.ProdutoService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteFotoProdutoController {

    private final ProdutoService produtoService;
    private final FotoProdutoService fotoProdutoService;
    private final ArmazenamentoFotoService armazenamentoFotoService;
    private final FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscarDados(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = fotoProdutoService
                .buscarOuFalharPorRestauranteIdEPorProdutoId(restauranteId, produtoId);

        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }

    @GetMapping
    public ResponseEntity<?> buscar(@PathVariable Long restauranteId,
                                    @PathVariable Long produtoId,
                                    @RequestHeader(name = "Accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {
            FotoProduto fotoProduto = fotoProdutoService
                    .buscarOuFalharPorRestauranteIdEPorProdutoId(restauranteId, produtoId);

            MediaType mediaTypeDaFotoProduto = MediaType.parseMediaType(fotoProduto.getTipoArquivo());
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeDaFotoProduto, mediaTypesAceitas);

            FotoRecuperada fotoRecuperada = armazenamentoFotoService.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header("Location", fotoRecuperada.getUrl().toString())
                        .build();
            }

            return ResponseEntity.ok()
                    .contentType(mediaTypeDaFotoProduto)
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizar(@PathVariable Long restauranteId,
                                      @PathVariable Long produtoId,
                                      @Valid FotoProdutoInput fotoProdutoInput,
                                      @ApiParam(value = "teste") @RequestPart MultipartFile arquivo) throws IOException {
        Produto produto = produtoService.buscarOuFalharPorIdERestauranteId(produtoId, restauranteId);

        FotoProduto fotoProduto = FotoProduto.builder()
                .id(produto.getId())
                .produto(produto)
                .nomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename())
                .descricao(fotoProdutoInput.getDescricao())
                .tipoArquivo(fotoProdutoInput.getArquivo().getContentType())
                .tamanho(fotoProdutoInput.getArquivo().getSize())
                .build();

        FotoProduto fotoPersistida = fotoProdutoService.salvar(fotoProduto,
                fotoProdutoInput.getArquivo().getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoPersistida);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        fotoProdutoService.remover(restauranteId, produtoId);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeDaFotoProduto, List<MediaType> mediaTypesAceitas)
            throws HttpMediaTypeNotAcceptableException {
        boolean mediaTypeCompativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeDaFotoProduto));

        if (!mediaTypeCompativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

}
