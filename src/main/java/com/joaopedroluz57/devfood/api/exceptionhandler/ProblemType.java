package com.joaopedroluz57.devfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ERRO_NEGOCIO("Violação de regra de negócio", "/erro-negocio"),
    ENTIDADE_NAO_ENCONTRADA("Entidade não encontrada", "/entidade-nao-encontrada"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "/mensagem-incompreensivel"),
    PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido");

    private final String title;
    private final String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://devfood.com.br" + path;
    }

}
