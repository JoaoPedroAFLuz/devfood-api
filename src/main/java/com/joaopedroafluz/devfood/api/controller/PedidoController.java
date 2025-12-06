package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.PedidoInputDisassembler;
import com.joaopedroafluz.devfood.api.assembler.PedidoModelAssembler;
import com.joaopedroafluz.devfood.api.assembler.PedidoResumidoModelAssembler;
import com.joaopedroafluz.devfood.api.model.PedidoModel;
import com.joaopedroafluz.devfood.api.model.PedidoResumidoModel;
import com.joaopedroafluz.devfood.api.model.input.PedidoInput;
import com.joaopedroafluz.devfood.api.openapi.controller.PedidoControllerOpenApi;
import com.joaopedroafluz.devfood.core.data.PageableTranslator;
import com.joaopedroafluz.devfood.domain.filter.PedidoFilter;
import com.joaopedroafluz.devfood.domain.model.Pedido;
import com.joaopedroafluz.devfood.domain.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    private final PedidoService pedidoService;
    private final PedidoModelAssembler pedidoModelAssembler;
    private final PedidoInputDisassembler pedidoInputDisassembler;
    private final PedidoResumidoModelAssembler pedidoResumidoModelAssembler;

    @GetMapping
    public Page<PedidoResumidoModel> buscarPaginada(PedidoFilter filtro, Pageable pageable) {
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
    public PedidoModel buscarPorCodigo(@PathVariable UUID codigoPedido) {
        Pedido pedido = pedidoService.buscarOuFalharPorCodigo(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    @PostMapping
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
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

