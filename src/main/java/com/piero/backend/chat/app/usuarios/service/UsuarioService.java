package com.piero.backend.chat.app.usuarios.service;

import com.piero.backend.chat.app.usuarios.dto.UsuarioDTORequest;
import com.piero.backend.chat.app.usuarios.dto.UsuarioDTOResponse;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDTOResponse> obtenerUsuariosDto();
    UsuarioDTOResponse obtenerUsuarioDtoPorCorreo(String correo);
    UsuarioDTOResponse obtenerUsuarioDtoPorId(Integer id);
    UsuarioDTOResponse guardarUsuario(UsuarioDTORequest usuarioDTORequest);
    UsuarioDTOResponse actualizarUsuario(Integer id, UsuarioDTORequest usuarioDTORequest);
    void eliminarUsuario(Integer id);

}
