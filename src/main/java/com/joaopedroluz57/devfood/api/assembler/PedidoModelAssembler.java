package com.joaopedroluz57.devfood.api.assembler;

import com.joaopedroluz57.devfood.api.model.*;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoModelAssembler {

    public PedidoModel toModel(Pedido pedido) {
        var restaurante = RestauranteResumoModel.builder()
                .id(pedido.getRestaurante().getId())
                .nome(pedido.getRestaurante().getNome())
                .build();

        var cliente = UsuarioModel.builder()
                .id(pedido.getCliente().getId())
                .nome(pedido.getCliente().getNome())
                .email(pedido.getCliente().getEmail())
                .build();

        var formaPagamento = FormaPagamentoModel.builder()
                .id(pedido.getFormaPagamento().getId())
                .descricao(pedido.getFormaPagamento().getDescricao())
                .build();

        var cidade = CidadeModel.builder()
                .id(pedido.getEnderecoEntrega().getCidade().getId())
                .nome(pedido.getEnderecoEntrega().getCidade().getNome())
                .estado(pedido.getEnderecoEntrega().getCidade().getEstado().getNome())
                .build();

        var enderecoEntrega = EnderecoModel.builder()
                .cep(pedido.getEnderecoEntrega().getCep())
                .logradouro(pedido.getEnderecoEntrega().getLogradouro())
                .numero(pedido.getEnderecoEntrega().getNumero())
                .complemento(pedido.getEnderecoEntrega().getComplemento())
                .bairro(pedido.getEnderecoEntrega().getBairro())
                .cidade(cidade)
                .build();


        var itens = pedido.getItens().stream()
                .map(item -> ItemPedidoModel.builder()
                        .produtoId(item.getProduto().getId())
                        .produtoNome(item.getProduto().getNome())
                        .observacao(item.getObservacao())
                        .quantidade(item.getQuantidade())
                        .precoUnitario(item.getPrecoUnitario())
                        .precoTotal(item.getPrecoTotal())
                        .build())
                .collect(Collectors.toList());

        return PedidoModel.builder()
                .codigo(pedido.getCodigo())
                .subTotal(pedido.getSubtotal())
                .taxaEntrega(pedido.getTaxaEntrega())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus().getDescricao())
                .dataCriacao(pedido.getDataCriacao())
                .dataConfirmacao(pedido.getDataConfirmacao())
                .dataEntrega(pedido.getDataEntrega())
                .dataCancelamento(pedido.getDataCancelamento())
                .restaurante(restaurante)
                .cliente(cliente)
                .formaPagamento(formaPagamento)
                .enderecoEntrega(enderecoEntrega)
                .itens(itens)
                .build();
    }

}
