package com.joaopedroafluz.devfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoModel {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private boolean ativo;

}
