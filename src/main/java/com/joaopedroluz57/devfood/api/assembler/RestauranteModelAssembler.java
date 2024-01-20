package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.RestauranteModel;
import com.joaopedroluz57.devfood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler {

    private final ModelMapper modelMapper;

    public RestauranteModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestauranteModel toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteModel.class);
    }

}
