package com.piero.backend.chat.app.usuarios.service.impl;

import com.piero.backend.chat.app.usuarios.dto.UsuarioDTO;
import com.piero.backend.chat.app.usuarios.mapper.UsuarioMapper;
import com.piero.backend.chat.app.usuarios.repository.UsuarioRepository;
import com.piero.backend.chat.app.usuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDTO obtenerUsuarioDtoPorCorreo(String correo) {
        if (correo == null) { return null; }
        return usuarioMapper.toDto(usuarioRepository.findByCorreo(correo).orElse(null));
    }

}
