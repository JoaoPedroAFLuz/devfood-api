package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.RestauranteModel;
import com.joaopedroafluz.devfood.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestauranteModelAssembler {

    private final CozinhaModelAssembler cozinhaModelAssembler;
    private final EnderecoModelAssembler enderecoModelAssembler;

    public RestauranteModel toModel(Restaurante restaurante) {
        var cozinhaModel = cozinhaModelAssembler.toModel(restaurante.getCozinha());

        var enderecoModel = restaurante.getEndereco() != null
                ? enderecoModelAssembler.toModel(restaurante.getEndereco())
                : null;

        return RestauranteModel.builder()
                .id(restaurante.getId())
                .nome(restaurante.getNome())
                .taxaEntrega(restaurante.getTaxaEntrega())
                .cozinha(cozinhaModel)
                .endereco(enderecoModel)
                .ativo(restaurante.isAtivo())
                .aberto(restaurante.isAberto())
                .build();
    }

}
