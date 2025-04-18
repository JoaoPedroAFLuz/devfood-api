package com.joaopedroluz57.devfood.api.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value = "Estado Input", description = "Representação de uma estado input")
public class EstadoIdInput {

    @NotNull
    @ApiModelProperty(value = "Id de um estado", example = "1", required = true)
    private Long id;

}
