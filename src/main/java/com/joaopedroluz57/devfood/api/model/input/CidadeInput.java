package com.joaopedroluz57.devfood.api.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "Cidade Input", description = "Representação de uma cidade input")
public class CidadeInput {

    @NotBlank
    @ApiModelProperty(value = "Nome de uma cidade", example = "Vitória da Conquista", required = true)
    private String nome;

    @Valid
    @NotNull
    private EstadoIdInput estado;

}
