package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.ProdutoNaoEncontradoException;
import com.joaopedroluz57.devfood.domain.model.Produto;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteService restauranteService;

    public ProdutoService(ProdutoRepository produtoRepository, RestauranteService restauranteService) {
        this.produtoRepository = produtoRepository;
        this.restauranteService = restauranteService;
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

    public Produto salvar(Long restaurantId, Produto produto) {
        Restaurante restaurante = restauranteService.buscarOuFalharPorId(restaurantId);

        produto.setRestaurante(restaurante);

        return produtoRepository.save(produto);
    }

}
