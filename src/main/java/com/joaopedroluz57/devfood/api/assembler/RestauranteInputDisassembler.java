package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.model.input.RestauranteInput;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {

    public Restaurante fromModel(RestauranteInput restauranteInput) {
        Restaurante restaurante = new Restaurante();

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaEntrega(restauranteInput.getTaxaEntrega());
        restaurante.setCozinha(cozinha);

        return restaurante;
    }

}
