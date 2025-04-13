package com.joaopedroluz57.devfood.api.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidadeResumidaModel {

    private Long id;
    private String nome;
    private String estado;

}
