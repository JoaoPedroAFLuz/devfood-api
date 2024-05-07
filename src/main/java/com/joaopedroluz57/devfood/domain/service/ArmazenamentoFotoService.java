package com.joaopedroluz57.devfood.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

public interface ArmazenamentoFotoService {

    void armazenar(NovaFoto novaFoto);

    @Getter
    @Setter
    @Builder
    class NovaFoto {

        private String nomeArquivo;
        private InputStream inputStream;

    }

}
