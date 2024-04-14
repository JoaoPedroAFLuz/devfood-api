package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.enums.StatusPedido;
import com.joaopedroluz57.devfood.domain.exception.NegocioException;
import com.joaopedroluz57.devfood.domain.exception.PedidoNaoEncontradoException;
import com.joaopedroluz57.devfood.domain.model.*;
import com.joaopedroluz57.devfood.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CidadeService cidadeService;
    private final UsuarioService usuarioService;
    private final RestauranteService restauranteService;
    private final FormaPagamentoService formaPagamentoService;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository pedidoRepository,
                         CidadeService cidadeService,
                         UsuarioService usuarioService,
                         RestauranteService restauranteService,
                         FormaPagamentoService formaPagamentoService, ProdutoService produtoService) {
        this.pedidoRepository = pedidoRepository;
        this.cidadeService = cidadeService;
        this.restauranteService = restauranteService;
        this.formaPagamentoService = formaPagamentoService;
        this.usuarioService = usuarioService;
        this.produtoService = produtoService;
    }


    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarOuFalharPorId(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
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
                throw new NegocioException("Não é possível selecionar o mesmo produto mais de uma vez. Selecione o produto e aumente a sua quantidade!");
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
    public void confirmar(Long pedidoId) {
        Pedido pedido = buscarOuFalharPorId(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(
                    String.format("Status do pedido %d não pode ser alterado de %s para %s",
                            pedido.getId(), pedido.getStatus().getDescricao(), StatusPedido.CONFIRMADO.getDescricao())
            );
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }

}

