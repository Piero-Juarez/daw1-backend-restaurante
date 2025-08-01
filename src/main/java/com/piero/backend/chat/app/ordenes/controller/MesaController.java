package com.piero.backend.chat.app.ordenes.controller;

import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTORequest;
import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTOResponse;
import com.piero.backend.chat.app.ordenes.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;
    private final SimpMessagingTemplate messagingTemplate;

    // LISTADO (GET)
    @GetMapping
    public ResponseEntity<List<MesaDTOResponse>> obtenerMesas() {
        List<MesaDTOResponse> mesasDtoResponse = mesaService.listarMesas();
        return ResponseEntity.ok(mesasDtoResponse);
    }

    // OBTENER MESAS DISPONIBLES (GET)
    @GetMapping("/disponibles")
    public ResponseEntity<List<MesaDTOResponse>> obtenerMesasDisponibles() {
        List<MesaDTOResponse> mesasDtoResponse = mesaService.listarMesasDisponibles();
        return ResponseEntity.ok(mesasDtoResponse);
    }

    // OBTENER UNA MESA (GET)
    @GetMapping("/{id}")
    public ResponseEntity<MesaDTOResponse> obtenerMesaPorId(@PathVariable Short id) {
        MesaDTOResponse mesaDtoResponse = mesaService.obtenerMesaPorId(id);
        return ResponseEntity.ok(mesaDtoResponse);
    }

    // CREAR UNA MESA (POST)
    @PostMapping
    public ResponseEntity<MesaDTOResponse> crearMesa(@RequestBody MesaDTORequest mesaDTORequest) {
        MesaDTOResponse mesaDTOResponse = mesaService.guardarMesa(mesaDTORequest);
        messagingTemplate.convertAndSend("/topic/mesas", mesaDTOResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaDTOResponse);
    }

    // ACTUALIZAR UNA MESA (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<MesaDTOResponse> actualizarMesa(@PathVariable Short id, @RequestBody MesaDTORequest mesaDTORequest) {
        MesaDTOResponse mesaDTOResponse = mesaService.actualizarMesa(id, mesaDTORequest);
        messagingTemplate.convertAndSend("/topic/mesas", mesaDTOResponse);
        return ResponseEntity.ok(mesaDTOResponse);
    }

    // ELIMINAR UNA MESA (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMesa(@PathVariable Short id) {
        mesaService.eliminarMesa(id);
        messagingTemplate.convertAndSend("/topic/mesas/eliminado", id);
        return ResponseEntity.noContent().build();
    }

}
