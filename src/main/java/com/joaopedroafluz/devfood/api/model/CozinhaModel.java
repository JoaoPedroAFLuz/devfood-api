package com.joaopedroafluz.devfood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.joaopedroafluz.devfood.api.view.RestauranteView;
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
@Relation(collectionRelation = "cozinhas")
@ApiModel(value = "Cozinha Model", description = "Representação de uma cozinha")
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

    @JsonView(RestauranteView.Resumo.class)
    @ApiModelProperty(value = "Id de uma cozinha", example = "1")
    private Long id;

    @JsonView(RestauranteView.Resumo.class)
    @ApiModelProperty(value = "Nome de uma cozinha", example = "Brasileira")
    private String nome;

}
