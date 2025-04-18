package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.CidadeModel;
import com.joaopedroluz57.devfood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler {

    private final ModelMapper modelMapper;

    public CidadeModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CidadeModel toModel(Cidade cidade) {
        var cidadeResumidaModel = modelMapper.map(cidade, CidadeModel.class);
        cidadeResumidaModel.setEstado(cidade.getEstado().getNome());

        return cidadeResumidaModel;
    }

}
