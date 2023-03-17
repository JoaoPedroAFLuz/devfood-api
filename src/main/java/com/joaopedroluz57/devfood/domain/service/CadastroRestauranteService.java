package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroluz57.devfood.domain.exception.EntidadeNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.repository.CozinhaRepository;
import com.joaopedroluz57.devfood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaRepository.buscarPorId(cozinhaId);

        if (Objects.isNull(cozinha)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não há um cadastro de cozinha com o id: %d.", cozinhaId)
            );
        }

        restaurante.setCozinha(cozinha);

        return restauranteRepository.salvar(restaurante);
    }

    public void excluir(Long id) {
        try {
            final Restaurante restaurante = restauranteRepository.buscarPorId(id);

            if (Objects.isNull(restaurante)) {
                throw new EmptyResultDataAccessException(1);
            }

            restauranteRepository.remover(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Restaurante com id %d não encontrado.", id)
            );
        }
    }

}
