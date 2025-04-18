package com.joaopedroluz57.devfood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("Page")
public class PageModelOpenApi<T> {

    @ApiModelProperty(value = "Conteúdo da página", position = 1)
    private List<T> content;

    @ApiModelProperty(value = "Número da página (começa em 0)", example = "0", position = 2)
    private int number;

    @ApiModelProperty(value = "Quantidade de itens por página", example = "10", position = 3)
    private int size;

    @ApiModelProperty(value = "Quantidade total de páginas", example = "1", position = 4)
    private int totalPages;

    @ApiModelProperty(value = "Quantidade total de itens", example = "1", position = 5)
    private int totalElements;

}
