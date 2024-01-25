package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.input.FormaPagamentoInput;
import com.joaopedroluz57.devfood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoInputDisassembler {

    private final ModelMapper modelMapper;

    public FormaPagamentoInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormaPagamento toDomainObject(FormaPagamentoInput formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoInput formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }

}
