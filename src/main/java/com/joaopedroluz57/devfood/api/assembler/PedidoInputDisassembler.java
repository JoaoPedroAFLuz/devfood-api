package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.input.PedidoInput;
import com.joaopedroluz57.devfood.domain.enums.StatusPedido;
import com.joaopedroluz57.devfood.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class PedidoInputDisassembler {

    private final EnderecoInputDisassembler enderecoInputDisassembler;

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        var restaurante = new Restaurante();
        restaurante.setId(pedidoInput.getRestaurante().getId());

        var formaPagamento = new FormaPagamento();
        formaPagamento.setId(pedidoInput.getFormaPagamento().getId());

        var endereco = enderecoInputDisassembler.toDomainObject(pedidoInput.getEnderecoEntrega());

        var itens = new ArrayList<ItemPedido>();
        pedidoInput.getItens()
                .forEach(item -> {
                    var itemPedido = new ItemPedido();
                    var produto = new Produto();
                    produto.setId(item.getProdutoId());

                    itemPedido.setQuantidade(item.getQuantidade());
                    itemPedido.setProduto(produto);
                    itemPedido.getProduto().setId(item.getProdutoId());
                    itemPedido.setObservacao(item.getObservacao());
                    itens.add(itemPedido);
                });

        return Pedido.builder()
                .restaurante(restaurante)
                .formaPagamento(formaPagamento)
                .enderecoEntrega(endereco)
                .itens(itens)
                .status(StatusPedido.CRIADO)
                .build();
    }

}
