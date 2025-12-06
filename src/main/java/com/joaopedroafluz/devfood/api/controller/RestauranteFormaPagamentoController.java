package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.api.assembler.FormaPagamentoModelAssembler;
import com.joaopedroafluz.devfood.api.model.FormaPagamentoModel;
import com.joaopedroafluz.devfood.domain.model.FormaPagamento;
import com.joaopedroafluz.devfood.domain.model.Restaurante;
import com.joaopedroafluz.devfood.domain.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private final RestauranteService restauranteService;
    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    public RestauranteFormaPagamentoController(RestauranteService restauranteService,
                                               FormaPagamentoModelAssembler formaPagamentoModelAssembler) {
        this.restauranteService = restauranteService;
        this.formaPagamentoModelAssembler = formaPagamentoModelAssembler;
    }

    @GetMapping
    public List<FormaPagamentoModel> buscarTodas(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalharPorId(restauranteId);

        return restaurante.getFormasPagamento().stream()
                .map(formaPagamentoModelAssembler::toModel)
                .collect(Collectors.toList());
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public FormaPagamentoModel associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

}
