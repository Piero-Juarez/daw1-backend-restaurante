package com.piero.backend.chat.app.reports.service;

import com.piero.backend.chat.app.exception.ErrorResponse;
import com.piero.backend.chat.app.ordenes.dto.detalleorden.DetalleOrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.service.OrdenService;
import com.piero.backend.chat.app.reports.dto.InformacionAdicionalBoletaRequest;
import com.piero.backend.chat.app.reports.data.DetalleInfo;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final OrdenService ordenService;

    public byte[] generarBoleta(String receipName, Long idOrden, InformacionAdicionalBoletaRequest info){

        OrdenResponseDTO orden = ordenService.obtenerOrdenPorId(idOrden);

        if (info.montoPagado() < orden.montoTotal()) {
            throw new ErrorResponse("El monto pagado no puede ser menor al monto total de la orden", HttpStatus.BAD_REQUEST);
        }

        try {
            InputStream receipStream = this.getClass().getResourceAsStream("/reports/" + receipName + ".jasper");
            InputStream logoStream = this.getClass().getResourceAsStream("/images/logo.png");

            Map<String, Object> params = new HashMap<>();
            params.put("LOGO", logoStream);
            params.put("NRO_ORDEN", orden.codigoOrden());
            params.put("NRO_MESA", orden.numeroMesa());
            params.put("FECHA", orden.fechaCreacion());
            params.put("HORA", orden.horaCreacion());
            params.put("SUB_TOTAL", orden.montoSubTotal());
            params.put("IGV", orden.detalles().stream().mapToDouble(DetalleOrdenResponseDTO::igv).sum());
            params.put("IMPORTE_TOTAL", orden.montoTotal());
            params.put("PAGO_CLIENTE", info.montoPagado());
            params.put("CAMBIO_CLIENTE", info.montoPagado() - orden.montoTotal());

            List<DetalleInfo> items = new ArrayList<>();
            orden.detalles().forEach(detalle -> items.add(new DetalleInfo(detalle.nombreItemMenu(), detalle.cantidad(), detalle.precioUnitario(), detalle.total())));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);

            JasperPrint jasperPrint = JasperFillManager.fillReport(receipStream, params, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
