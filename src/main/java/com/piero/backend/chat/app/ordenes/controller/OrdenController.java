package com.piero.backend.chat.app.ordenes.controller;

import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;

    @GetMapping
    public ResponseEntity<List<OrdenResponseDTO>> obtenerOrdenesDelDia() {
        List<OrdenResponseDTO> ordenes = ordenService.obtenerOrdenesDelDia();
        return ResponseEntity.ok(ordenes);
    }

    @PostMapping
    public ResponseEntity<OrdenResponseDTO> crearOrden(@RequestBody OrdenRequestDTO ordenRequestDTO) {
        OrdenResponseDTO ordenResponseDTO = ordenService.crearOrden(ordenRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenResponseDTO);
    }

}
