package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.PedidoModel;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler {

    private final ModelMapper modelMapper;

    public PedidoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PedidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

}
