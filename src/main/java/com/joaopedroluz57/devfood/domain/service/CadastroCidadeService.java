package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.EntidadeNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.model.Cidade;
import com.joaopedroluz57.devfood.domain.model.Estado;
import com.joaopedroluz57.devfood.domain.repository.CidadeRepository;
import com.joaopedroluz57.devfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        final Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoRepository.buscarPorId(estadoId);

        if (Objects.isNull(estado)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não foi encontrado um estado com o id: %d.", estadoId)
            );
        }

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public void excluir(Long id) {
        try {
            cidadeRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não há um cadastro de cidade com o id: %d.", id)
            );
        }
    }

}
