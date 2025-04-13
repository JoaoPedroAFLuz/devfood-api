package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.PedidoResumidoModel;
import com.joaopedroluz57.devfood.api.model.RestauranteResumoModel;
import com.joaopedroluz57.devfood.api.model.UsuarioModel;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumidoModelAssembler {

    public PedidoResumidoModel toModel(Pedido pedido) {
        var restaurante = RestauranteResumoModel.builder()
                .id(pedido.getRestaurante().getId())
                .nome(pedido.getRestaurante().getNome())
                .build();

        var cliente = UsuarioModel.builder()
                .id(pedido.getCliente().getId())
                .nome(pedido.getCliente().getNome())
                .email(pedido.getCliente().getEmail())
                .build();

        return PedidoResumidoModel.builder()
                .codigo(pedido.getCodigo())
                .subTotal(pedido.getSubtotal())
                .taxaEntrega(pedido.getTaxaEntrega())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus().getDescricao())
                .dataCriacao(pedido.getDataCriacao())
                .restaurante(restaurante)
                .nomeCliente(cliente.getNome())
                .build();
    }

}
