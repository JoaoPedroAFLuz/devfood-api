package com.joaopedroluz57.devfood.domain.service;

import com.joaopedroluz57.devfood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro);

}
