package com.joaopedroluz57.devfood.infrastructure.repository;

import com.joaopedroluz57.devfood.domain.model.Estado;
import com.joaopedroluz57.devfood.domain.repository.EstadoRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Estado> todos() {
        return entityManager.createQuery("from Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscarPorId(Long id) {
        return entityManager.find(Estado.class, id);
    }

    @Transactional
    @Override
    public Estado adicionar(Estado estado) {
        return entityManager.merge(estado);
    }

    @Transactional
    @Override
    public void remover(Estado estado) {
        estado = buscarPorId(estado.getId());
        entityManager.remove(estado);
    }
}
