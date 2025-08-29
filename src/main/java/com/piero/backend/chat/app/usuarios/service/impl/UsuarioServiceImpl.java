package com.piero.backend.chat.app.usuarios.service.impl;

import com.piero.backend.chat.app.exception.ErrorResponse;
import com.piero.backend.chat.app.usuarios.dto.UsuarioDTORequest;
import com.piero.backend.chat.app.usuarios.dto.UsuarioDTOResponse;
import com.piero.backend.chat.app.usuarios.mapper.UsuarioMapper;
import com.piero.backend.chat.app.usuarios.model.Usuario;
import com.piero.backend.chat.app.usuarios.model.enums.RolUsuario;
import com.piero.backend.chat.app.usuarios.repository.UsuarioRepository;
import com.piero.backend.chat.app.usuarios.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTOResponse> obtenerUsuariosDto() {
        return usuarioMapper.listToDto(usuarioRepository.findAllByActivo(true));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTOResponse obtenerUsuarioDtoPorCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            throw new  ErrorResponse("Correo no enviado o en blanco.", HttpStatus.BAD_REQUEST);
        }
        return usuarioMapper.toDto(usuarioRepository.findByCorreo(correo).orElseThrow(() -> new EntityNotFoundException("Usuario con correo: " + correo + ", no encontrado")));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTOResponse obtenerUsuarioDtoPorId(Integer id) {
        if (id == null) {
            throw new  ErrorResponse("ID no enviado.", HttpStatus.BAD_REQUEST);
        }
        return usuarioMapper.toDto(usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario con ID: " + id + ", no encontrado")));
    }

    @Override
    @Transactional
    public UsuarioDTOResponse guardarUsuario(UsuarioDTORequest usuarioDTORequest) {
        if (usuarioDTORequest == null) {
            throw new  ErrorResponse("Usuario no enviado.", HttpStatus.BAD_REQUEST);
        }
        if (usuarioRepository.existsByCorreoAndActivoTrue(usuarioDTORequest.correo())) {
            throw new  ErrorResponse("No se puede crear al usuario. El correo: " + usuarioDTORequest.correo() + " ya existe.", HttpStatus.CONFLICT);
        }
        if (usuarioDTORequest.clave().length() > 12) {
            throw new  ErrorResponse("La clave no puede ser mayor a 12 caracteres.", HttpStatus.CONFLICT);
        }
        if (!usuarioDTORequest.correo().contains("@") || !usuarioDTORequest.correo().contains(".")) {
            throw new  ErrorResponse("El correo debe tener un formato válido.", HttpStatus.CONFLICT);
        }
        Usuario usuarioCreado = usuarioRepository.save(usuarioMapper.toEntity(usuarioDTORequest));
        return usuarioMapper.toDto(usuarioCreado);
    }

    @Override
    @Transactional
    public UsuarioDTOResponse actualizarUsuario(Integer id, UsuarioDTORequest usuarioDTORequest) {
        if (id == null || usuarioDTORequest == null) {
            throw new  ErrorResponse("ID no enviado o Usuario no enviado.", HttpStatus.BAD_REQUEST);
        }
        Usuario usuarioEncontrado = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario con ID: " + id + ", no encontrado"));
        if (!usuarioDTORequest.correo().equals(usuarioEncontrado.getCorreo()) && usuarioRepository.existsByCorreoAndActivoTrue(usuarioDTORequest.correo())) {
            throw new  ErrorResponse("No se puede actualizar al usuario. El correo: " + usuarioDTORequest.correo() + " ya existe.", HttpStatus.CONFLICT);
        }
        if (usuarioDTORequest.clave().length() > 12) {
            throw new  ErrorResponse("La clave no puede ser mayor a 12 caracteres.", HttpStatus.CONFLICT);
        }
        if (!usuarioDTORequest.correo().contains("@") || !usuarioDTORequest.correo().contains(".")) {
            throw new  ErrorResponse("El correo debe tener un formato válido.", HttpStatus.CONFLICT);
        }
        usuarioEncontrado.setNombre(usuarioDTORequest.nombre());
        usuarioEncontrado.setApellido(usuarioDTORequest.apellido());
        usuarioEncontrado.setCorreo(usuarioDTORequest.correo());
        if (!usuarioDTORequest.clave().isBlank()) {
            usuarioEncontrado.setClave(passwordEncoder.encode(usuarioDTORequest.clave()));
        }
        usuarioEncontrado.setRolUsuario(RolUsuario.valueOf(usuarioDTORequest.rol()));
        return usuarioMapper.toDto(usuarioRepository.save(usuarioEncontrado));
    }

    @Override
    @Transactional
    public void eliminarUsuario(Integer id) {
        if (id == null) {
            throw new  ErrorResponse("ID no enviado.", HttpStatus.BAD_REQUEST);
        }
        usuarioRepository.eliminacionLogicaPorId(id);
    }

}
