package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.PermissaoNaoEncontradaException;
import com.joaopedroluz57.devfood.domain.model.Permissao;
import com.joaopedroluz57.devfood.domain.repository.PermissaoRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;

    public PermissaoService(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    public Permissao buscarOuFalharPorId(Long permissaoId) {
        return permissaoRepository.findById(permissaoId).
                orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }

}
