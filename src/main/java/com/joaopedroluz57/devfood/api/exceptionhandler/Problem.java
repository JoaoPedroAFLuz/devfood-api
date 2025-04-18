package com.joaopedroluz57.devfood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {


    @ApiModelProperty(example = "400", position = 1)
    private final Integer status;

    @ApiModelProperty(example = "https://devfood.com.br/dados-invalidos", position = 2)
    private final String type;

    @ApiModelProperty(example = "Dados inválidos", position = 3)
    private final String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente",
            position = 4)
    private final String detail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente",
            position = 5)
    private final String userMessage;

    @ApiModelProperty(example = "2025-04-17T00:23:27.760554Z", position = 6)
    private final OffsetDateTime timestamp;

    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 7)
    private final List<Object> objects;

    @Getter
    @Builder
    @ApiModel("Objeto de erro")
    public static class Object {


        @ApiModelProperty(example = "nome")
        private final String name;

        @ApiModelProperty(example = "Nome da cidade é obrigatório")
        private final String userMessage;

    }

}
