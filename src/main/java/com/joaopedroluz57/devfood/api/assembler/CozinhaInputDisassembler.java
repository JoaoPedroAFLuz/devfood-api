package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.input.CozinhaInput;
import com.joaopedroluz57.devfood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDisassembler {

    private final ModelMapper modelMapper;

    public CozinhaInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }

}
