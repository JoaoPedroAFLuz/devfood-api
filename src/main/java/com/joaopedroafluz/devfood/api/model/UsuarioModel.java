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
@Relation(collectionRelation = "usuarios")
@ApiModel(value = "Cidade Model", description = "Representação de um usuário")
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    @ApiModelProperty(value = "Id de um usuário", example = "1")
    private Long id;

    @ApiModelProperty(value = "Id de um usuário", example = "João")
    private String nome;

    @ApiModelProperty(value = "Id de um usuário", example = "joao@email.com")
    private String email;

}
