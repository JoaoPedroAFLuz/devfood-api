package com.joaopedroluz57.devfood.api.controller;

import com.joaopedroluz57.devfood.domain.filter.VendaDiariaFilter;
import com.joaopedroluz57.devfood.domain.model.dto.VendaDiaria;
import com.joaopedroluz57.devfood.domain.service.VendaQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    private final VendaQueryService vendaQueryService;

    public EstatisticasController(VendaQueryService vendaQueryService) {
        this.vendaQueryService = vendaQueryService;
    }


    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> buscarTodas(VendaDiariaFilter filtro) {
        return vendaQueryService.consultarVendasDiarias(filtro);
    }

}
