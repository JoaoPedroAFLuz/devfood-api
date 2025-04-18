package com.joaopedroluz57.devfood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Cidade Model", description = "Representação de uma cidade")
public class CidadeModel {

    @ApiModelProperty(value = "Id de uma cidade", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome de uma cidade", example = "Vitória da Conquista")
    private String nome;

    @ApiModelProperty(value = "Sigla de uma cidade", example = "BA")
    private String estado;

}
