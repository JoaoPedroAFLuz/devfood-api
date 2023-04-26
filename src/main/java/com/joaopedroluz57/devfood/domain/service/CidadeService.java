package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.CidadeNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroluz57.devfood.domain.model.Cidade;
import com.joaopedroluz57.devfood.domain.model.Estado;
import com.joaopedroluz57.devfood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    public static final String MSG_CIDADE_EM_USO = "Cidade com o código %d não pode ser removida, pois está em uso.";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoService estadoService;

    public Cidade buscarOuFalharPorId(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }

    public Cidade salvar(Cidade cidade) {
        final Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoService.buscarOuFalharPorId(estadoId);

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public void excluir(Long cidadeId) {
        try {
            cidadeRepository.deleteById(cidadeId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(cidadeId);
        }
    }

}
