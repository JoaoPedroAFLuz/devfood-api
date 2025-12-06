package com.joaopedroafluz.devfood.domain.listener;

import com.joaopedroafluz.devfood.domain.event.PedidoCanceladoEvent;
import com.joaopedroafluz.devfood.domain.model.Email;
import com.joaopedroafluz.devfood.domain.model.Pedido;
import com.joaopedroafluz.devfood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificacaoClientePedidoCanceladoListener {

    private final EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {
        Pedido pedido = event.getPedido();

        Email email = Email.builder()
                .destinatario(pedido.getCliente().getEmail())
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", pedido)
                .build();

        envioEmailService.enviar(email);
    }

}
