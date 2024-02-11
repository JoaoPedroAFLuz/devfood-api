package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.UsuarioModel;
import com.joaopedroluz57.devfood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler {

    private final ModelMapper modelMapper;

    public UsuarioModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioModel toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }

}
