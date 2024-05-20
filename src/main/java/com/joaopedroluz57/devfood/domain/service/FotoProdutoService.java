package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.FotoProdutoNaoEncontrada;
import com.joaopedroluz57.devfood.domain.model.FotoProduto;
import com.joaopedroluz57.devfood.domain.model.NovaFoto;
import com.joaopedroluz57.devfood.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class FotoProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ArmazenamentoFotoService armazenamentoFotoService;

    public FotoProdutoService(ProdutoRepository produtoRepository,
                              ArmazenamentoFotoService armazenamentoFotoService) {
        this.produtoRepository = produtoRepository;
        this.armazenamentoFotoService = armazenamentoFotoService;
    }


    public FotoProduto buscarOuFalharPorRestauranteIdEPorProdutoId(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoProdutoByProdutoIdAndRestauranteId(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontrada(produtoId, restauranteId));
    }

    public Optional<FotoProduto> buscarPorProdutoIdEPorRestauranteId(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoProdutoByProdutoIdAndRestauranteId(restauranteId, produtoId);
    }

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream dadosArquivo) {
        Long produtoId = fotoProduto.getProduto().getId();
        Long restauranteId = fotoProduto.getRestauranteId();

        String nomeArquivoExistente = null;
        String novoNomeArquivo = armazenamentoFotoService.gerarNomeArquivo(fotoProduto.getNomeArquivo());

        fotoProduto.setNomeArquivo(novoNomeArquivo);

        Optional<FotoProduto> fotoExistente = buscarPorProdutoIdEPorRestauranteId(restauranteId, produtoId);

        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.deleteProductPhoto(fotoExistente.get());
        }

        FotoProduto fotoPersistida = produtoRepository.saveProductPhoto(fotoProduto);

        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .inputStream(dadosArquivo)
                .nomeArquivo(novoNomeArquivo)
                .build();

        armazenamentoFotoService.substituir(nomeArquivoExistente, novaFoto);

        return fotoPersistida;
    }

    @Transactional
    public void remover(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalharPorRestauranteIdEPorProdutoId(restauranteId, produtoId);

        produtoRepository.deleteProductPhoto(fotoProduto);
        produtoRepository.flush();

        armazenamentoFotoService.remover(fotoProduto.getNomeArquivo());
    }

}
