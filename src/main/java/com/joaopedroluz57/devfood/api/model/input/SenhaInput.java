package com.joaopedroluz57.devfood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SenhaInput {

    @NotBlank
    @Size(min = 8, message = "A senha atual deve ter no mínimo 8 caracteres")
    private String senhaAtual;

    @NotBlank
    @Size(min = 8, message = "A nova senha deve ter no mínimo 8 caracteres")
    private String novaSenha;

}
