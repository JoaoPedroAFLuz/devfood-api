package com.joaopedroluz57.devfood.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@Builder
public class NovaFoto {

    private String nomeArquivo;
    private String tipoArquivo;
    private InputStream inputStream;

}
