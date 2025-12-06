package com.joaopedroafluz.devfood.domain.event;

import com.joaopedroafluz.devfood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PedidoConfirmadoEvent {

    private Pedido pedido;

}
