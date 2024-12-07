package com.joaopedroluz57.devfood.domain.event;

import com.joaopedroluz57.devfood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PedidoConfirmadoEvent {

    private Pedido pedido;

}
