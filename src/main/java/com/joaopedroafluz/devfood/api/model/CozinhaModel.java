package com.joaopedroafluz.devfood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.joaopedroafluz.devfood.api.view.RestauranteView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Cozinha Model", description = "Representação de uma cozinha")
public class CozinhaModel {

    @JsonView(RestauranteView.Resumo.class)
    @ApiModelProperty(value = "Id de uma cozinha", example = "1")
    private Long id;

    @JsonView(RestauranteView.Resumo.class)
    @ApiModelProperty(value = "Nome de uma cozinha", example = "Brasileira")
    private String nome;

}
