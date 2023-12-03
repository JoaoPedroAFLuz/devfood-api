package com.joaopedroluz57.devfood.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public abstract class UsuarioMixin {

    @JsonIgnore
    private LocalDateTime dataCadastro;

}
