package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.GrupoModel;
import com.joaopedroafluz.devfood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GrupoModelAssembler {

    private final ModelMapper modelMapper;

    public GrupoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GrupoModel toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

}
