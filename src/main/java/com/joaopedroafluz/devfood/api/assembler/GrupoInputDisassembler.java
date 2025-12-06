package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.input.GrupoInput;
import com.joaopedroafluz.devfood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDisassembler {

    private final ModelMapper modelMapper;

    public GrupoInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }

}
