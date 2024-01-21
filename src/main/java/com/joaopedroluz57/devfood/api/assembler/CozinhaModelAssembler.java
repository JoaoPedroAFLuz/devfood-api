package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.CozinhaModel;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssembler {

    private final ModelMapper modelMapper;

    public CozinhaModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CozinhaModel toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaModel.class);
    }

}
