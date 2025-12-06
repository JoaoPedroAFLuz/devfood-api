package com.joaopedroafluz.devfood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "estados")
@ApiModel(value = "Estado Model", description = "Representação de um estado")
public class EstadoModel extends RepresentationModel<EstadoModel> {

    @ApiModelProperty(value = "Id de um estado", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome de um estado", example = "Bahia")
    private String nome;

    @ApiModelProperty(value = "Sigla de um estado", example = "BA")
    private String sigla;

}
