package com.joaopedroluz57.devfood.api.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.joaopedroluz57.devfood.api.assembler.PedidoInputDisassembler;
import com.joaopedroluz57.devfood.api.assembler.PedidoModelAssembler;
import com.joaopedroluz57.devfood.api.assembler.PedidoResumidoModelAssembler;
import com.joaopedroluz57.devfood.api.model.PedidoModel;
import com.joaopedroluz57.devfood.api.model.PedidoResumidoModel;
import com.joaopedroluz57.devfood.api.model.input.PedidoInput;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import com.joaopedroluz57.devfood.domain.service.PedidoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
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

//    @GetMapping
//    public List<PedidoResumidoModel> buscarTodos() {
//        return pedidoService.buscarTodos().stream()
//                .map(pedidoResumidoModelAssembler::toModel)
//                .collect(Collectors.toList());
//    }

    @GetMapping
    public MappingJacksonValue buscarTodos(@RequestParam(required = false) String campos) {
        List<PedidoResumidoModel> pedidosResumidos = pedidoService.buscarTodos().stream()
                .map(pedidoResumidoModelAssembler::toModel)
                .collect(Collectors.toList());

        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosResumidos);
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();

        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

        if (StringUtils.isNotBlank(campos)) {
            String[] camposArray = Arrays.stream(campos.split(","))
                    .map(String::strip)
                    .toArray(String[]::new);

            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter
                    .filterOutAllExcept(camposArray));
        }

        pedidosWrapper.setFilters(filterProvider);

        return pedidosWrapper;
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

