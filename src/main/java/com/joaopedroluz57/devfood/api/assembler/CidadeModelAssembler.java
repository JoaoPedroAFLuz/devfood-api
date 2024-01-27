package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.CidadeResumidaModel;
import com.joaopedroluz57.devfood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler {

    private final ModelMapper modelMapper;

    public CidadeModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CidadeResumidaModel toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeResumidaModel.class);
    }

}
