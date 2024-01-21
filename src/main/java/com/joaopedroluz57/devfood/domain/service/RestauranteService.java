package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroluz57.devfood.domain.exception.RestauranteNaoEncontradoException;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.repository.RestauranteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RestauranteService {

    public static final String MSG_CIDADE_EM_USO = "Restaurante com o código %d não pode ser removido, pois está em uso";

    private final RestauranteRepository restauranteRepository;
    private final CozinhaService cozinhaService;

    public RestauranteService(RestauranteRepository restauranteRepository, CozinhaService cozinhaService) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaService = cozinhaService;
    }

    public List<Restaurante> buscarTodos() {
        return restauranteRepository.findAll();
    }

    public Restaurante buscarOuFalharPorId(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }

    public List<Restaurante> buscarPorNome(String nome, Long cozinhaId) {
        return restauranteRepository.consultarPorNome(nome, cozinhaId);
    }

    public List<Restaurante> buscarPorTaxaEntrega(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaEntregaBetween(taxaInicial, taxaFinal);
    }

    public List<Restaurante> buscarPorNomeETaxaEntrega(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.buscarPorNomeETaxaEntrega(nome, taxaInicial, taxaFinal);
    }

    public List<Restaurante> buscarPorNomeEFreteGratis(String nome) {
        return restauranteRepository.buscarPorNomeEFreteGratis(nome);
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaService.buscarOuFalharPorId(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void excluir(Long restauranteId) {
        try {
            restauranteRepository.deleteById(restauranteId);
            restauranteRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(restauranteId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, restauranteId));
        }
    }

}
