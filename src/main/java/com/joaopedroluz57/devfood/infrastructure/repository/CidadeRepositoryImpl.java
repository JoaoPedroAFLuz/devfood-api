package com.joaopedroluz57.devfood.infrastructure.repository;

import com.joaopedroluz57.devfood.domain.model.Cidade;
import com.joaopedroluz57.devfood.domain.repository.CidadeRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cidade> buscarTodas() {
        return entityManager.createQuery("from Cidade", Cidade.class).getResultList();
    }

    @Override
    public Cidade buscarPorId(Long id) {
        return entityManager.find(Cidade.class, id);
    }

    @Override
    public List<Cidade> buscarPorEstadoId(Long estadoId) {
        return entityManager
                .createQuery("from Cidade where estado.id = :estadoId", Cidade.class)
                .setParameter("estadoId", estadoId)
                .getResultList();
    }

    @Transactional
    @Override
    public Cidade salvar(Cidade cidade) {
        return entityManager.merge(cidade);
    }

    @Transactional
    @Override
    public void remover(Cidade cidade) {
        cidade = buscarPorId(cidade.getId());
        entityManager.remove(cidade);
    }
}
