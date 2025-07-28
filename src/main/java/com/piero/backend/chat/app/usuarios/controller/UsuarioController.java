package com.piero.backend.chat.app.usuarios.controller;

import com.piero.backend.chat.app.usuarios.dto.UsuarioDTORequest;
import com.piero.backend.chat.app.usuarios.dto.UsuarioDTOResponse;
import com.piero.backend.chat.app.usuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // LISTADO (GET)
    @GetMapping
    public ResponseEntity<List<UsuarioDTOResponse>> obtenerUsuarios() {
        List<UsuarioDTOResponse> usuariosDtoResponse = usuarioService.obtenerUsuariosDto();
        return ResponseEntity.ok(usuariosDtoResponse);
    }

    // OBTENER UN USUARIO (GET)
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> obtenerUsuarioPorId(@PathVariable Integer id) {
        UsuarioDTOResponse usuarioDtoResponse = usuarioService.obtenerUsuarioDtoPorId(id);
        return ResponseEntity.ok(usuarioDtoResponse);
    }

    // CREAR UN USUARIO (POST)
    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> crearUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.guardarUsuario(usuarioDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTOResponse);
    }

    // ACTUALIZAR UN USUARIO (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.actualizarUsuario(id, usuarioDTORequest);
        return ResponseEntity.ok(usuarioDTOResponse);
    }

    // ELIMINAR UN USUARIO (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // OBTENER USUARIO AUTÉNTICADO (GET)
    @GetMapping("/me")
    public ResponseEntity<UsuarioDTOResponse> obtenerUsuarioAutenticado(Authentication authentication) {
        UsuarioDTOResponse usuarioDtoResponse = usuarioService.obtenerUsuarioDtoPorCorreo(authentication.getName());
        return ResponseEntity.ok(usuarioDtoResponse);
    }

}
