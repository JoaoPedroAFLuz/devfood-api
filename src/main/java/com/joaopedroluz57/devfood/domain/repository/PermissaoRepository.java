package com.joaopedroluz57.devfood.domain.repository;

import com.joaopedroluz57.devfood.domain.model.Permissao;

import java.util.List;

public interface PermissaoRepository {

    List<Permissao> buscarTodas();

    Permissao buscarPorId(Long id);

    Permissao salvar(Permissao permissao);

    void remover(Permissao permissao);

}
