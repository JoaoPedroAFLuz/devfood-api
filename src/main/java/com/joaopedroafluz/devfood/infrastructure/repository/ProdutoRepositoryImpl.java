package com.joaopedroafluz.devfood.infrastructure.repository;

import com.joaopedroafluz.devfood.domain.model.FotoProduto;
import com.joaopedroafluz.devfood.domain.repository.ProdutoRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public FotoProduto saveProductPhoto(FotoProduto fotoProduto) {
        return entityManager.merge(fotoProduto);
    }

    @Override
    @Transactional
    public void deleteProductPhoto(FotoProduto fotoProduto) {
        entityManager.remove(fotoProduto);
    }

}
