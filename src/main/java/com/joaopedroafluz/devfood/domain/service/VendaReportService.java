package com.joaopedroafluz.devfood.domain.service;

import com.joaopedroafluz.devfood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro);

}
