package com.joaopedroafluz.devfood.api.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel("Cozinha Input")
public class CozinhaInput {

    @NotBlank
    @ApiModelProperty(value = "Nome de uma cozinha", example = "Brasileira", required = true)
    private String nome;

}
