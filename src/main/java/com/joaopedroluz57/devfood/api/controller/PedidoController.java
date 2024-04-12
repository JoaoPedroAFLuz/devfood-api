package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.PedidoModelAssembler;
import com.joaopedroluz57.devfood.api.assembler.PedidoResumidoModelAssembler;
import com.joaopedroluz57.devfood.api.model.PedidoModel;
import com.joaopedroluz57.devfood.api.model.PedidoResumidoModel;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import com.joaopedroluz57.devfood.domain.service.PedidoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoResumidoModelAssembler pedidoResumidoModelAssembler;

    public PedidoController(PedidoService pedidoService,
                            PedidoModelAssembler pedidoModelAssembler,
                            PedidoResumidoModelAssembler pedidoResumidoModelAssembler) {
        this.pedidoService = pedidoService;
        this.pedidoModelAssembler = pedidoModelAssembler;
        this.pedidoResumidoModelAssembler = pedidoResumidoModelAssembler;
    }


    @GetMapping
    public List<PedidoResumidoModel> buscarTodos() {
        return pedidoService.buscarTodos().stream()
                .map(pedidoResumidoModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel buscarTodos(@PathVariable Long pedidoId) {
        Pedido pedido = pedidoService.buscarOuFalharPorId(pedidoId);

        return pedidoModelAssembler.toModel(pedido);
    }

}

