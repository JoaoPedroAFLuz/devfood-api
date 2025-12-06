package com.joaopedroafluz.devfood.api.model.input;

import com.joaopedroafluz.devfood.core.validation.TaxaFrete;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInput {

    @NotBlank
    private String nome;

    @TaxaFrete
    private BigDecimal taxaEntrega;

    @Valid
    @NotNull
    private CozinhaIdInput cozinha;

    @Valid
    @NotNull
    private EnderecoInput endereco;

}
