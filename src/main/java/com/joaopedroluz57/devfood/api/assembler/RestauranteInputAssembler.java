package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.input.RestauranteInput;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputAssembler {

    private final ModelMapper modelMapper;

    public RestauranteInputAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestauranteInput toInput(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteInput.class);
    }

}
