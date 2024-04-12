package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.PedidoResumidoModel;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumidoModelAssembler {

    private final ModelMapper modelMapper;

    public PedidoResumidoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PedidoResumidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumidoModel.class);
    }

}
