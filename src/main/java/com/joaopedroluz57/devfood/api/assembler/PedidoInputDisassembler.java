package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.input.PedidoInput;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler {

    private final ModelMapper modelMapper;

    public PedidoInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }

}
