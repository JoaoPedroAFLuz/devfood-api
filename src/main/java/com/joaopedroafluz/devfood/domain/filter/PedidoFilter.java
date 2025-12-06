package com.joaopedroafluz.devfood.domain.filter;

import com.joaopedroafluz.devfood.domain.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoFilter {

    private Long clienteId;
    private Long restauranteId;
    private StatusPedido status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoFim;

}
