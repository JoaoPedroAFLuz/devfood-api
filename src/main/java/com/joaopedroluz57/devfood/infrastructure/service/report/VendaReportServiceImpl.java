package com.joaopedroluz57.devfood.infrastructure.service.report;

import com.joaopedroluz57.devfood.domain.filter.VendaDiariaFilter;
import com.joaopedroluz57.devfood.domain.model.dto.VendaDiaria;
import com.joaopedroluz57.devfood.domain.service.VendaQueryService;
import com.joaopedroluz57.devfood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class VendaReportServiceImpl implements VendaReportService {

    private final VendaQueryService vendaQueryService;

    public VendaReportServiceImpl(VendaQueryService vendaQueryService) {
        this.vendaQueryService = vendaQueryService;
    }


    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro) {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            List<VendaDiaria> vendaDiarias = vendaQueryService.consultarVendasDiarias(filtro);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendaDiarias);

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir relatório de vendas", e);
        }
    }

}
