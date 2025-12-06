package com.joaopedroafluz.devfood.api.assembler;

import com.joaopedroafluz.devfood.api.model.FormaPagamentoModel;
import com.joaopedroafluz.devfood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoModelAssembler {

    private final ModelMapper modelMapper;

    public FormaPagamentoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

}
