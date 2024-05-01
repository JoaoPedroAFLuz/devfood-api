package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.filter.VendaDiariaFilter;
import com.joaopedroluz57.devfood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);

}
