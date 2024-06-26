package com.joaopedroluz57.devfood.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
public class Email {

    @Singular
    private Set<String> destinatarios;

    @NonNull
    private String assunto;

    @NonNull
    private String corpo;

    @Singular("variavel")
    private Map<String, Object> variaveis;

}
