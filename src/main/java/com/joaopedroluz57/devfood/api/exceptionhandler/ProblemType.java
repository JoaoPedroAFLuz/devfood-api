package com.joaopedroluz57.devfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ERRO_NEGOCIO("Violação de regra de negócio", "/erro-negocio"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "/mensagem-incompreensivel"),
    PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido"),
    ERRO_DO_SISTEMA("Erro do sistema", "/erro-do-sistema"),
    DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos");

    private final String title;
    private final String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://devfood.com.br" + path;
    }

}
