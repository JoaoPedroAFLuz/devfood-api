package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.exception.NegocioException;
import com.joaopedroluz57.devfood.domain.exception.PedidoNaoEncontradoException;
import com.joaopedroluz57.devfood.domain.filter.PedidoFilter;
import com.joaopedroluz57.devfood.domain.model.*;
import com.joaopedroluz57.devfood.domain.repository.PedidoRepository;
import com.joaopedroluz57.devfood.infrastructure.repository.spec.PedidoSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final CidadeService cidadeService;
    private final UsuarioService usuarioService;
    private final ProdutoService produtoService;
    private final PedidoRepository pedidoRepository;
    private final RestauranteService restauranteService;
    private final FormaPagamentoService formaPagamentoService;

    public Page<Pedido> buscarTodos(PedidoFilter filtro, Pageable pageable) {
        return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
    }

    public Pedido buscarOuFalharPorCodigo(UUID codigoPedido) {
        return pedidoRepository.findByCodigo(codigoPedido)
                .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaEntrega(pedido.getRestaurante().getTaxaEntrega());
        pedido.calcularValorTotal();

        return salvar(pedido);
    }

    private void validarPedido(Pedido pedido) {
        // Será utilizado o usuário autenticado
        Usuario usuario = usuarioService.buscarOuFalharPorId(1L);
        pedido.setCliente(usuario);

        Cidade cidade = cidadeService.buscarOuFalharPorId(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.buscarOuFalharPorId(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.buscarOuFalharPorId(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalharPorId(pedido.getFormaPagamento().getId());

        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
        pedido.getEnderecoEntrega().setCidade(cidade);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        Set<Long> produtoIds = new HashSet<>();

        for (ItemPedido item : pedido.getItens()) {
            if (!produtoIds.add(item.getProduto().getId())) {
                throw new NegocioException("Não é possível selecionar o mesmo produto mais de uma vez. Selecione o " +
                        "produto e aumente a sua quantidade!");
            }
        }

        pedido.getItens().forEach(item -> {
            Produto produto = produtoService
                    .buscarOuFalharPorIdERestauranteId(item.getProduto().getId(), pedido.getRestaurante().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

    @Transactional
    public void confirmar(UUID codigoPedido) {
        Pedido pedido = buscarOuFalharPorCodigo(codigoPedido);
        pedido.confirmar();

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void encaminhar(UUID codigoPedido) {
        Pedido pedido = buscarOuFalharPorCodigo(codigoPedido);

        pedido.encaminhar();
    }

    @Transactional
    public void entregar(UUID codigoPedido) {
        Pedido pedido = buscarOuFalharPorCodigo(codigoPedido);

        pedido.entregar();
    }

    @Transactional
    public void cancelar(UUID codigoPedido) {
        Pedido pedido = buscarOuFalharPorCodigo(codigoPedido);
        pedido.cancelar();

        pedidoRepository.save(pedido);
    }
}

