package com.piero.backend.chat.app.usuarios.service;

import com.piero.backend.chat.app.usuarios.dto.UsuarioDTO;

public interface UsuarioService {

    UsuarioDTO obtenerUsuarioDtoPorCorreo(String correo);

}
