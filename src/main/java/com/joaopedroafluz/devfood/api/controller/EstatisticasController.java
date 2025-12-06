package com.joaopedroafluz.devfood.api.controller;

import com.joaopedroafluz.devfood.domain.filter.VendaDiariaFilter;
import com.joaopedroafluz.devfood.domain.model.dto.VendaDiaria;
import com.joaopedroafluz.devfood.domain.service.VendaQueryService;
import com.joaopedroafluz.devfood.domain.service.VendaReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    private final VendaQueryService vendaQueryService;
    private final VendaReportService vendaReportService;

    public EstatisticasController(VendaQueryService vendaQueryService, VendaReportService vendaReportService) {
        this.vendaQueryService = vendaQueryService;
        this.vendaReportService = vendaReportService;
    }


    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> buscarTodas(VendaDiariaFilter filtro) {
        return vendaQueryService.consultarVendasDiarias(filtro);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> buscarTodasEmPdf(VendaDiariaFilter filtro) {
        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }

}
