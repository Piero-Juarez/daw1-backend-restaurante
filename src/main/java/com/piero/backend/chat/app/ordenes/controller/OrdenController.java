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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService ordenService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping
    public ResponseEntity<Page<OrdenResponseDTO>> obtenerOrdenes(Pageable pageable) {
        Page<OrdenResponseDTO> ordenes = ordenService.obtenerOrdenes(pageable);
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/hoy")
    public ResponseEntity<Page<OrdenResponseDTO>> obtenerOrdenesDelDia(
            Pageable pageable,
            @RequestParam(required = false, name = "estado") List<String> estadosOrden
    ) {
        Page<OrdenResponseDTO> ordenes = ordenService.obtenerOrdenesDelDia(pageable, estadosOrden);
        return ResponseEntity.ok(ordenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> obtenerOrdenPorId(@PathVariable Long id) {
        OrdenResponseDTO orden = ordenService.obtenerOrdenPorId(id);
        return ResponseEntity.ok(orden);
    }

    @GetMapping("/por-mesa-pendiente/{mesaId}")
    public ResponseEntity<OrdenResponseDTO> obtenerOrdenPendientePorMesa(@PathVariable Short mesaId) {
        OrdenResponseDTO orden = ordenService.obtenerOrdenPendientePorMesa(mesaId);
        return ResponseEntity.ok(orden);
    }

    @PostMapping
    public ResponseEntity<OrdenResponseDTO> crearOrden(@RequestBody OrdenRequestDTO ordenRequestDTO) {
        OrdenResponseDTO ordenResponseDTO = ordenService.crearOrden(ordenRequestDTO);
        simpMessagingTemplate.convertAndSend("/topic/ordenes", ordenResponseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordenResponseDTO);
    }

    @PostMapping("/pagar/{id}")
    public ResponseEntity<OrdenResponseDTO> marcarOrdenComoPagada(@PathVariable Long id) {
        OrdenResponseDTO ordenPagada = ordenService.marcarComoPagada(id);
        return ResponseEntity.ok(ordenPagada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenResponseDTO> actualizarOrden(@PathVariable Long id, @RequestBody OrdenActualizarRequestDTO ordenActualizarRequestDTO) {
        OrdenResponseDTO ordenActualizada = ordenService.actualizarOrden(id, ordenActualizarRequestDTO);
        simpMessagingTemplate.convertAndSend("/topic/ordenes", ordenActualizada);
        return ResponseEntity.ok(ordenActualizada);
    }

    @PatchMapping("/cambiar-estado/{id}")
    public ResponseEntity<OrdenResponseDTO> cambiarEstadoOrden(@PathVariable Long id, @RequestBody OrdenCambiarEstadoRequestDTO ordenCambiarEstadoRequestDTO) {
        OrdenResponseDTO ordenEstadoActualizado = ordenService.cambiarEstadoOrden(id, ordenCambiarEstadoRequestDTO);
        simpMessagingTemplate.convertAndSend("/topic/ordenes/cambio-estado", ordenEstadoActualizado);
        return ResponseEntity.ok(ordenEstadoActualizado);
    }

    @DeleteMapping()
    public ResponseEntity<?> desactivarOrdenes() {
        ordenService.desactivarOrdenes();
        return ResponseEntity.noContent().build();
    }

}
