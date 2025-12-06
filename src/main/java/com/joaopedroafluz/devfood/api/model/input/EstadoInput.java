package com.joaopedroafluz.devfood.api.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ApiModel("Estado Input")
public class EstadoInput {

    @NotBlank
    @ApiModelProperty(value = "Nome de uma estado", example = "Bahia", required = true)
    private String nome;

    @NotBlank
    @ApiModelProperty(value = "Sigla de uma estado", example = "BA", required = true)
    private String sigla;

}
