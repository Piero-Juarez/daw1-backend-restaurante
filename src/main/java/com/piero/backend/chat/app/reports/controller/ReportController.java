package com.piero.backend.chat.app.reports.controller;

import com.piero.backend.chat.app.reports.dto.InformacionAdicionalBoletaRequest;
import com.piero.backend.chat.app.reports.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/boleta/{id}")
    public ResponseEntity<byte[]> generarBoleta(
            @PathVariable Long id,
            @RequestBody InformacionAdicionalBoletaRequest info
    ) {
        byte[] report = reportService.generarBoleta("boleta_venta", id, info);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add("Content-Disposition", "inline; filename=boleta.pdf");
        return new ResponseEntity<>(report, headers, HttpStatus.OK);
    }

}
