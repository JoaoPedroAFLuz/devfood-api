package com.joaopedroafluz.devfood.domain.service;

import com.joaopedroafluz.devfood.domain.exception.CidadeNaoEncontradaException;
import com.joaopedroafluz.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroafluz.devfood.domain.model.Cidade;
import com.joaopedroafluz.devfood.domain.model.Estado;
import com.joaopedroafluz.devfood.domain.repository.CidadeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CidadeService {

    public static final String MSG_CIDADE_EM_USO = "Cidade com o código %d não pode ser removida, pois está em uso";

    private final CidadeRepository cidadeRepository;

    private final EstadoService estadoService;

    public CidadeService(CidadeRepository cidadeRepository, EstadoService estadoService) {
        this.cidadeRepository = cidadeRepository;
        this.estadoService = estadoService;
    }


    public Cidade buscarOuFalharPorId(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {
        final Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoService.buscarOuFalharPorId(estadoId);

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);
            cidadeRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(cidadeId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }

}
