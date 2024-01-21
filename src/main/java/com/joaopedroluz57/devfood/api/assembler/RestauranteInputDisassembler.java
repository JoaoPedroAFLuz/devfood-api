package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.domain.model.Cozinha;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import com.joaopedroluz57.devfood.domain.model.input.RestauranteInput;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {

    private final ModelMapper modelMapper;

    public RestauranteInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Restaurante fromModel(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyFromModel(RestauranteInput restauranteInput, Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha());

        modelMapper.map(restauranteInput, restaurante);
    }

}
