package com.joaopedroafluz.devfood.domain.service;

import com.joaopedroafluz.devfood.domain.exception.EntidadeEmUsoException;
import com.joaopedroafluz.devfood.domain.exception.GrupoNaoEncontradoException;
import com.joaopedroafluz.devfood.domain.model.Grupo;
import com.joaopedroafluz.devfood.domain.model.Permissao;
import com.joaopedroafluz.devfood.domain.repository.GrupoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    private static final String MSG_GRUPO_EM_USO = "Grupo com o código %d não pode ser removido, pois está em uso";

    private final GrupoRepository grupoRepository;
    private final PermissaoService permissaoService;

    public GrupoService(GrupoRepository grupoRepository, PermissaoService permissaoService) {
        this.grupoRepository = grupoRepository;
        this.permissaoService = permissaoService;
    }


    public List<Grupo> buscarTodos() {
        return grupoRepository.findAll();
    }

    public Grupo buscarOuFalharPorId(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarOuFalharPorId(grupoId);
        Permissao permissao = permissaoService.buscarOuFalharPorId(permissaoId);

        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarOuFalharPorId(grupoId);
        Permissao permissao = permissaoService.buscarOuFalharPorId(permissaoId);

        grupo.removerPermissao(permissao);
    }

    @Transactional
    public void excluir(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(grupoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
        }
    }

}
