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
@Relation(collectionRelation = "cidades")
@ApiModel(value = "Cidade Model", description = "Representação de uma cidade")
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @ApiModelProperty(value = "Id de uma cidade", example = "1")
    private Long id;

    @ApiModelProperty(value = "Nome de uma cidade", example = "Vitória da Conquista")
    private String nome;

    private EstadoModel estado;

}
