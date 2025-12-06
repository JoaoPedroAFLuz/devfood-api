package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.input.UsuarioInput;
import com.joaopedroafluz.devfood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {

    private final ModelMapper modelMapper;

    public UsuarioInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Usuario toDomainObject(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }

}
