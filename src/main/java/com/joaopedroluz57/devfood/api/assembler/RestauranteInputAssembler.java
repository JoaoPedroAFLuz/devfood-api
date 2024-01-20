package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.model.input.CozinhaIdInput;
import com.joaopedroluz57.devfood.domain.model.input.RestauranteInput;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputAssembler {

    public RestauranteInput toInput(Restaurante restaurante) {
        RestauranteInput restauranteInput = new RestauranteInput();

        CozinhaIdInput cozinhaIdInput = new CozinhaIdInput();
        cozinhaIdInput.setId(restaurante.getCozinha().getId());

        restauranteInput.setNome(restaurante.getNome());
        restauranteInput.setTaxaEntrega(restaurante.getTaxaEntrega());
        restauranteInput.setCozinha(cozinhaIdInput);

        return restauranteInput;
    }

}
