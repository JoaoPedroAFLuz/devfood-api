package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.CozinhaModel;
import com.joaopedroluz57.devfood.api.model.RestauranteModel;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler {

    public RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaEntrega(restaurante.getTaxaEntrega());
        restauranteModel.setCozinha(cozinhaModel);

        return restauranteModel;
    }

}
