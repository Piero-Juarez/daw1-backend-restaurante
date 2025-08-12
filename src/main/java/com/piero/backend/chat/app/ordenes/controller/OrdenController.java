package com.piero.backend.chat.app.ordenes.controller;

import com.piero.backend.chat.app.ordenes.dto.orden.OrdenActualizarRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenCambiarEstadoRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<OrdenResponseDTO>> obtenerOrdenesDelDia(Pageable pageable) {
        Page<OrdenResponseDTO> ordenes = ordenService.obtenerOrdenesDelDia(pageable);
        return ResponseEntity.ok(ordenes);
    }

    @PostMapping
    public ResponseEntity<OrdenResponseDTO> crearOrden(@RequestBody OrdenRequestDTO ordenRequestDTO) {
        OrdenResponseDTO ordenResponseDTO = ordenService.crearOrden(ordenRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> actualizarOrden(@PathVariable Long id, @RequestBody OrdenActualizarRequestDTO ordenActualizarRequestDTO) {
        OrdenResponseDTO ordenActualizada = ordenService.actualizarOrden(id, ordenActualizarRequestDTO);
        return ResponseEntity.ok(ordenActualizada);
    }

    @PatchMapping("/cambiar-estado/{id}")
    public ResponseEntity<OrdenResponseDTO> cambiarEstado(@PathVariable Long id, @RequestBody OrdenCambiarEstadoRequestDTO ordenCambiarEstadoRequestDTO) {
        System.out.println("NOMBRE DEL ESTADO ENVIADO DESDE EL CLIENTE" + ordenCambiarEstadoRequestDTO.estadoOrden());
        OrdenResponseDTO ordenEstadoActualizado = ordenService.cambiarEstadoOrden(id, ordenCambiarEstadoRequestDTO);
        return ResponseEntity.ok(ordenEstadoActualizado);
    }

    @DeleteMapping()
    public ResponseEntity<?> desactivarOrdenes() {
        ordenService.desactivarOrdenes();
        return ResponseEntity.noContent().build();
    }

}
