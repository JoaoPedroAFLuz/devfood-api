package com.joaopedroluz57.devfood.api.openapi.model;

import com.joaopedroluz57.devfood.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@ApiModel(value = "Restaurante Básico Model", description = "Representa um restaurante")
public class RestauranteBasicoModelOpenApi {

    @ApiModelProperty(value = "Id de um restaurante", example = "1", position = 1)
    private Long id;

    @ApiModelProperty(value = "Nome de um restaurante", example = "Lanchonete do Brasil Brasileiro", position = 2)
    private String nome;

    @ApiModelProperty(value = "Taxa de entrega de um restaurante", example = "10", position = 3)
    private BigDecimal taxaEntrega;

    @ApiModelProperty(position = 4)
    private CozinhaModel cozinha;

}
