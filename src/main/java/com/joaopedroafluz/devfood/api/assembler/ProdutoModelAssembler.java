package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.ProdutoModel;
import com.joaopedroafluz.devfood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler {

    private final ModelMapper modelMapper;

    public ProdutoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProdutoModel toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoModel.class);
    }

}
