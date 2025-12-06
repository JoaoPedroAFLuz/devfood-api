package com.joaopedroafluz.devfood.domain.service;

import com.joaopedroafluz.devfood.domain.filter.VendaDiariaFilter;
import com.joaopedroafluz.devfood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);

}
