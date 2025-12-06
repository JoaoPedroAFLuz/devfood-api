package com.joaopedroafluz.devfood.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class VendaDiaria {

    private Long totalVendas;
    private BigDecimal totalFaturado;
    private Date data;

}
