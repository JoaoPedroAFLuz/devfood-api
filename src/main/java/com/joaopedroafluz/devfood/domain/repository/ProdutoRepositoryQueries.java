package com.joaopedroafluz.devfood.domain.repository;

import com.joaopedroafluz.devfood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

    FotoProduto saveProductPhoto(FotoProduto fotoProduto);

    void deleteProductPhoto(FotoProduto fotoProduto);

}
