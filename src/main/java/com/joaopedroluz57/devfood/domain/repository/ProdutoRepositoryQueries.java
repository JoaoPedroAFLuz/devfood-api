package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto saveProductPhoto(FotoProduto fotoProduto);

    void deleteProductPhoto(FotoProduto fotoProduto);

}
