package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.api.assembler.PedidoInputDisassembler;
import com.joaopedroluz57.devfood.api.assembler.PedidoModelAssembler;
import com.joaopedroluz57.devfood.api.assembler.PedidoResumidoModelAssembler;
import com.joaopedroluz57.devfood.api.model.PedidoModel;
import com.joaopedroluz57.devfood.api.model.PedidoResumidoModel;
import com.joaopedroluz57.devfood.api.model.input.PedidoInput;
import com.joaopedroluz57.devfood.core.data.PageableTranslator;
import com.joaopedroluz57.devfood.domain.model.Pedido;
import com.joaopedroluz57.devfood.domain.filter.PedidoFilter;
import com.joaopedroluz57.devfood.domain.service.PedidoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

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
    public Page<PedidoResumidoModel> buscarTodos(PedidoFilter filtro, Pageable pageable) {
        pageable = traduzirPageable(pageable);

        return pedidoService.buscarTodos(filtro, pageable)
                .map(pedidoResumidoModelAssembler::toModel);
    }

//    @GetMapping
//    public MappingJacksonValue buscarTodos(@RequestParam(required = false) String campos) {
//        List<PedidoResumidoModel> pedidosResumidos = pedidoService.buscarTodos().stream()
//                .map(pedidoResumidoModelAssembler::toModel)
//                .collect(Collectors.toList());
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosResumidos);
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if (StringUtils.isNotBlank(campos)) {
//            String[] camposArray = Arrays.stream(campos.split(","))
//                    .map(String::strip)
//                    .toArray(String[]::new);
//
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter
//                    .filterOutAllExcept(camposArray));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//
//        return pedidosWrapper;
//    }

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

    private Pageable traduzirPageable(Pageable pageable) {
        Map<String, String> mapeamento = Map.of(
                "codigo", "codigo",
                "valorTotal", "valorTotal",
                "nomeCliente", "cliente.nome",
                "restaurante.nome", "restaurante.nome"
        );

        return PageableTranslator.translate(pageable, mapeamento);
    }

}

