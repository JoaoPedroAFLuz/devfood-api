package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.input.RestauranteInput;
import com.joaopedroafluz.devfood.domain.model.Restaurante;
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
