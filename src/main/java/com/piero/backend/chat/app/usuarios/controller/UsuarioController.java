package com.piero.backend.chat.app.usuarios.controller;

import com.piero.backend.chat.app.usuarios.dto.UsuarioDTO;
import com.piero.backend.chat.app.usuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioAutenticado(Authentication authentication) {
        UsuarioDTO usuarioDto = usuarioService.obtenerUsuarioDtoPorCorreo(authentication.getName());
        return ResponseEntity.ok(usuarioDto);
    }

}
