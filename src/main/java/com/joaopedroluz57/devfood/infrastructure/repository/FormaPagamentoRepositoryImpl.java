package com.joaopedroluz57.devfood.infrastructure.repository;

import com.joaopedroluz57.devfood.domain.model.FormaPagamento;
import com.joaopedroluz57.devfood.domain.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FormaPagamento> todos() {
        return entityManager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
    }

    @Override
    public FormaPagamento porId(Long id) {
        return entityManager.find(FormaPagamento.class, id);
    }

    @Transactional
    @Override
    public FormaPagamento adicionar(FormaPagamento formaPagamento) {
        return entityManager.merge(formaPagamento);
    }

    @Transactional
    @Override
    public void remover(FormaPagamento formaPagamento) {
        entityManager.remove(formaPagamento);
    }
}
