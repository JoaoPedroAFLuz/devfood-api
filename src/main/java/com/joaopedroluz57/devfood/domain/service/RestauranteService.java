package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroluz57.devfood.domain.exception.RestauranteNaoEncontradoException;
import com.joaopedroluz57.devfood.domain.model.*;
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
    private final CidadeService cidadeService;
    private final FormaPagamentoService formaPagamentoService;
    private final UsuarioService usuarioService;

    public RestauranteService(RestauranteRepository restauranteRepository,
                              CozinhaService cozinhaService,
                              CidadeService cidadeService,
                              FormaPagamentoService formaPagamentoService,
                              UsuarioService usuarioService) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaService = cozinhaService;
        this.cidadeService = cidadeService;
        this.formaPagamentoService = formaPagamentoService;
        this.usuarioService = usuarioService;
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
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.buscarOuFalharPorId(cozinhaId);
        Cidade cidade = cidadeService.buscarOuFalharPorId(cidadeId);

        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public FormaPagamento associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalharPorId(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalharPorId(formaPagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);

        return formaPagamento;
    }

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalharPorId(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalharPorId(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void ativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalharPorId(restauranteId);

        restaurante.ativar();
    }

    @Transactional
    public void ativar(List<Long> restauranteIds) {
        for (Long restauranteId : restauranteIds) {
            ativar(restauranteId);
        }
    }

    @Transactional
    public void desativar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalharPorId(restauranteId);

        restaurante.desativar();
    }

    @Transactional
    public void abrir(Long restauranteId) {
        Restaurante restaurante = buscarOuFalharPorId(restauranteId);

        restaurante.abrir();
    }

    @Transactional
    public void fechar(Long restauranteId) {
        Restaurante restaurante = buscarOuFalharPorId(restauranteId);

        restaurante.fechar();
    }

    @Transactional
    public void adicionarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalharPorId(restauranteId);
        Usuario usuario = usuarioService.buscarOuFalharPorId(usuarioId);

        restaurante.adicionarResponsavel(usuario);
    }

    @Transactional
    public void removerResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalharPorId(restauranteId);
        Usuario usuario = usuarioService.buscarOuFalharPorId(usuarioId);

        restaurante.removerResponsavel(usuario);
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
