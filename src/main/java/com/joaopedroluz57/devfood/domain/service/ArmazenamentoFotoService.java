package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.model.FotoRecuperada;
import com.joaopedroluz57.devfood.domain.model.NovaFoto;

import java.util.UUID;

public interface ArmazenamentoFotoService {

    FotoRecuperada recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    default void substituir(String nomeArquivo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivo != null) {
            this.remover(nomeArquivo);
        }
    }

    default String gerarNomeArquivo(String nomeArquivo) {
        return  UUID.randomUUID() + "_" + nomeArquivo;
    }

}
