package com.piero.backend.chat.app.usuarios.controller;

import com.piero.backend.chat.app.usuarios.dto.UsuarioDTORequest;
import com.piero.backend.chat.app.usuarios.dto.UsuarioDTOResponse;
import com.piero.backend.chat.app.usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "API REST para gestionar usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SimpMessagingTemplate messagingTemplate;

    // LISTADO (GET)
    @GetMapping
    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Devuelve los usuarios activos en el sistema."
    )
    public ResponseEntity<List<UsuarioDTOResponse>> obtenerUsuarios() {
        List<UsuarioDTOResponse> usuariosDtoResponse = usuarioService.obtenerUsuariosDto();
        return ResponseEntity.ok(usuariosDtoResponse);
    }

    // OBTENER UN USUARIO (GET)
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un usuario por su id",
            description = "Devuelve un usuario con sus datos completos."
    )
    public ResponseEntity<UsuarioDTOResponse> obtenerUsuarioPorId(@PathVariable Integer id) {
        UsuarioDTOResponse usuarioDtoResponse = usuarioService.obtenerUsuarioDtoPorId(id);
        return ResponseEntity.ok(usuarioDtoResponse);
    }

    // CREAR UN USUARIO (POST)
    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> crearUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.guardarUsuario(usuarioDTORequest);
        messagingTemplate.convertAndSend("/topic/usuarios", usuarioDTOResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTOResponse);
    }

    // ACTUALIZAR UN USUARIO (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.actualizarUsuario(id, usuarioDTORequest);
        messagingTemplate.convertAndSend("/topic/usuarios", usuarioDTOResponse);
        return ResponseEntity.ok(usuarioDTOResponse);
    }

    // ELIMINAR UN USUARIO (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        messagingTemplate.convertAndSend("/topic/usuarios/eliminado", id);
        return ResponseEntity.noContent().build();
    }

    // OBTENER USUARIO AUTÉNTICADO (GET)
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTOResponse> obtenerUsuarioAutenticado(Authentication authentication) {
        UsuarioDTOResponse usuarioDtoResponse = usuarioService.obtenerUsuarioDtoPorCorreo(authentication.getName());
        return ResponseEntity.ok(usuarioDtoResponse);
    }

}
