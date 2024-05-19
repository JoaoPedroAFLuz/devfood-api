package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.ProdutoNaoEncontradoException;
import com.joaopedroluz57.devfood.domain.model.FotoProduto;
import com.joaopedroluz57.devfood.domain.model.NovaFoto;
import com.joaopedroluz57.devfood.domain.model.Produto;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteService restauranteService;
    private final ArmazenamentoFotoService armazenamentoFotoService;

    public ProdutoService(ProdutoRepository produtoRepository,
                          RestauranteService restauranteService,
                          ArmazenamentoFotoService armazenamentoFotoService) {
        this.produtoRepository = produtoRepository;
        this.restauranteService = restauranteService;
        this.armazenamentoFotoService = armazenamentoFotoService;
    }


    public List<Produto> buscarPorRestauranteId(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId);
    }

    public List<Produto> buscarAtivosPorRestauranteId(Long restauranteId) {
        return produtoRepository.findByRestauranteIdAndAtivoIsTrue(restauranteId);
    }

    public Produto buscarOuFalharPorIdERestauranteId(Long produtoId, Long restauranteId) {
        return produtoRepository.findByIdAndRestauranteId(produtoId, restauranteId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId, restauranteId));
    }

    public Optional<FotoProduto> buscarFotoProdutoOuFalharPorIdERestauranteId(Long produtoId, Long restauranteId) {
        return produtoRepository.findFotoProdutoByProdutoIdAndRestauranteId(produtoId, restauranteId);
    }

    @Transactional
    public Produto salvar(Long restaurantId, Produto produto) {
        Restaurante restaurante = restauranteService.buscarOuFalharPorId(restaurantId);

        produto.setRestaurante(restaurante);

        return produtoRepository.save(produto);
    }

    @Transactional
    public FotoProduto salvarFoto(FotoProduto fotoProduto, InputStream dadosArquivo) {
        Long produtoId = fotoProduto.getProduto().getId();
        Long restauranteId = fotoProduto.getRestauranteId();
        String novoNomeArquivo = armazenamentoFotoService.gerarNomeArquivo(fotoProduto.getNomeArquivo());
        String nomeArquivoExistente = null;

        fotoProduto.setNomeArquivo(novoNomeArquivo);

        Optional<FotoProduto> fotoExistente = buscarFotoProdutoOuFalharPorIdERestauranteId(produtoId, restauranteId);

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

}
