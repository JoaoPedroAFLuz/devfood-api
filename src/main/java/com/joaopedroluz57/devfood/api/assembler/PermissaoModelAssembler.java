package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.PermissaoModel;
import com.joaopedroluz57.devfood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler {

    private final ModelMapper modelMapper;

    public PermissaoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

}
