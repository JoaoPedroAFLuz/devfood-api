package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.PedidoInputDisassembler;
import com.joaopedroluz57.devfood.api.assembler.PedidoModelAssembler;
import com.joaopedroluz57.devfood.api.assembler.PedidoResumidoModelAssembler;
import com.joaopedroluz57.devfood.api.model.PedidoModel;
import com.joaopedroluz57.devfood.api.model.PedidoResumidoModel;
import com.joaopedroluz57.devfood.api.model.input.PedidoInput;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import com.joaopedroluz57.devfood.domain.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoInputDisassembler pedidoInputDisassembler;
    private final PedidoResumidoModelAssembler pedidoResumidoModelAssembler;

    public PedidoController(PedidoService pedidoService,
                            PedidoModelAssembler pedidoModelAssembler,
                            PedidoInputDisassembler pedidoInputDisassembler,
                            PedidoResumidoModelAssembler pedidoResumidoModelAssembler) {
        this.pedidoService = pedidoService;
        this.pedidoModelAssembler = pedidoModelAssembler;
        this.pedidoInputDisassembler = pedidoInputDisassembler;
        this.pedidoResumidoModelAssembler = pedidoResumidoModelAssembler;
    }


    @GetMapping
    public List<PedidoResumidoModel> buscarTodos() {
        return pedidoService.buscarTodos().stream()
                .map(pedidoResumidoModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscarTodos(@PathVariable UUID codigoPedido) {
        Pedido pedido = pedidoService.buscarOuFalharPorCodigo(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    @PostMapping
    public PedidoModel cadastrar(@RequestBody @Valid PedidoInput pedidoInput) {
        Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

        Pedido pedidoPersistido = pedidoService.emitir(pedido);

        return pedidoModelAssembler.toModel(pedidoPersistido);
    }

    @PutMapping("/{codigoPedido}/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable UUID codigoPedido) {
        pedidoService.confirmar(codigoPedido);
    }

    @PutMapping("/{codigoPedido}/encaminho")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void encaminhar(@PathVariable UUID codigoPedido) {
        pedidoService.encaminhar(codigoPedido);
    }

    @PutMapping("/{codigoPedido}/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable UUID codigoPedido) {
        pedidoService.cancelar(codigoPedido);
    }

    @PutMapping("/{codigoPedido}/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar(@PathVariable UUID codigoPedido) {
        pedidoService.entregar(codigoPedido);
    }

}

