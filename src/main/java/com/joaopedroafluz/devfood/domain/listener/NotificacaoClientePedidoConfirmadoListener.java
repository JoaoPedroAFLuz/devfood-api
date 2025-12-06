package com.joaopedroafluz.devfood.domain.listener;

import com.joaopedroafluz.devfood.domain.event.PedidoConfirmadoEvent;
import com.joaopedroafluz.devfood.domain.model.Email;
import com.joaopedroafluz.devfood.domain.model.Pedido;
import com.joaopedroafluz.devfood.domain.service.EnvioEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificacaoClientePedidoConfirmadoListener {

    private final EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        Email email = Email.builder()
                .destinatario(pedido.getCliente().getEmail())
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .build();

        envioEmailService.enviar(email);
    }

}
