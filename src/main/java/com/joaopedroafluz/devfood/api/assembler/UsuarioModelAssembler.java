package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.UsuarioModel;
import com.joaopedroafluz.devfood.domain.model.Usuario;
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
