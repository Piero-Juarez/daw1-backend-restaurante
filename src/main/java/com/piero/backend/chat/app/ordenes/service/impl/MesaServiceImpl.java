package com.piero.backend.chat.app.ordenes.service.impl;

import com.piero.backend.chat.app.exception.ErrorResponse;
import com.piero.backend.chat.app.ordenes.dto.mesa.MesaCambiarEstadoRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTORequest;
import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTOResponse;
import com.piero.backend.chat.app.ordenes.mapper.MesaMapper;
import com.piero.backend.chat.app.ordenes.model.Mesa;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoMesa;
import com.piero.backend.chat.app.ordenes.repository.MesaRepository;
import com.piero.backend.chat.app.ordenes.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MesaServiceImpl implements MesaService {

    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

    @Override
    public List<MesaDTOResponse> listarMesas() {
        return mesaMapper.listToDto(mesaRepository.findAll());
    }

    @Override
    public List<MesaDTOResponse> listarMesasDisponibles() {
        return mesaMapper.listToDto(mesaRepository.findAllByEstado(EstadoMesa.LIBRE));
    }

    @Override
    public MesaDTOResponse guardarMesa(MesaDTORequest mesaDTORequest) {
        if (mesaDTORequest == null) { return null; }
        Mesa mesaCreada = mesaRepository.save(mesaMapper.toEntity(mesaDTORequest));
        return mesaMapper.toDto(mesaCreada);
    }

    @Override
    public MesaDTOResponse actualizarMesa(Short id, MesaDTORequest mesaDTORequest) {
        if (id == null || mesaDTORequest == null) { return null; }
        Mesa mesaEncontrada = mesaRepository.findById(id).orElseThrow(() -> new ErrorResponse("Mesa con ID: " + id + ", no encontrada", HttpStatus.NOT_FOUND));
        // mesaEncontrada.setNumero(mesaDTORequest.numero());
        mesaEncontrada.setCapacidad(mesaDTORequest.capacidad());
        if (mesaDTORequest.estado() != null && !mesaDTORequest.estado().isBlank()) {
            mesaEncontrada.setEstado(EstadoMesa.valueOf(mesaDTORequest.estado()));
        }
        return mesaMapper.toDto(mesaRepository.save(mesaEncontrada));
    }

    @Override
    public MesaDTOResponse actualizarEstadoMesa(Short id, MesaCambiarEstadoRequestDTO mesaCambiarEstadoRequestDTO) {
        if (id == null || mesaCambiarEstadoRequestDTO == null) {
            throw new ErrorResponse("ID de la mesa o estado no puede ser nulo", HttpStatus.BAD_REQUEST);
        }
        Mesa mesaEncontrada = mesaRepository.findById(id).orElseThrow(() -> new ErrorResponse("Mesa con ID: " + id + ", no encontrada", HttpStatus.NOT_FOUND));
        mesaEncontrada.setEstado(EstadoMesa.valueOf(mesaCambiarEstadoRequestDTO.estadoMesa().toUpperCase()));
        mesaRepository.save(mesaEncontrada);
        return mesaMapper.toDto(mesaEncontrada);
    }

    @Override
    public MesaDTOResponse obtenerMesaPorId(Short id) {
        if (id == null) { return null; }
        Mesa mesaEncontrada = mesaRepository.findById(id).orElseThrow(() -> new ErrorResponse("Mesa con ID: " + id + ", no encontrada", HttpStatus.NOT_FOUND));
        return mesaMapper.toDto(mesaEncontrada);
    }

    @Override
    public void eliminarMesa(Short id) {
        if (id == null) { return; }
        mesaRepository.deleteById(id);
    }

}
