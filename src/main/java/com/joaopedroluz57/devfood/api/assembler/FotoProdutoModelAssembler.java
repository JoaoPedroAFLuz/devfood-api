package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.FotoProdutoModel;
import com.joaopedroluz57.devfood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler {

    private final ModelMapper modelMapper;

    public FotoProdutoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoModel.class);
    }

}
