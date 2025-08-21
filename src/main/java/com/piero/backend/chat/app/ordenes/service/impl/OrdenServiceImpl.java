package com.piero.backend.chat.app.ordenes.service.impl;

import com.piero.backend.chat.app.exception.ErrorResponse;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenActualizarRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenCambiarEstadoRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.mapper.OrdenMapper;
import com.piero.backend.chat.app.ordenes.model.Mesa;
import com.piero.backend.chat.app.ordenes.model.Orden;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoMesa;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoOrden;
import com.piero.backend.chat.app.ordenes.repository.MesaRepository;
import com.piero.backend.chat.app.ordenes.repository.OrdenRepository;
import com.piero.backend.chat.app.ordenes.service.OrdenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository ordenRepository;
    private final MesaRepository mesaRepository;
    private final OrdenMapper ordenMapper;

    @Override
    @Transactional
    public OrdenResponseDTO crearOrden(OrdenRequestDTO ordenRequestDTO) {
        Mesa mesa = mesaRepository.findById(ordenRequestDTO.mesaId()).orElseThrow(() -> new ErrorResponse("Mesa con ID: " + ordenRequestDTO.mesaId() + ", no encontrada", HttpStatus.NOT_FOUND));
        if (mesa.getEstado().equals(EstadoMesa.OCUPADA)) {
            throw new ErrorResponse("La mesa " + mesa.getNumero() + " ya se encuentra ocupada", HttpStatus.CONFLICT);
        }
        Orden nuevaOrden = ordenMapper.toEntity(ordenRequestDTO, mesa);
        mesa.setEstado(EstadoMesa.OCUPADA);
        mesaRepository.save(mesa);
        Orden ordenGuardada = ordenRepository.save(nuevaOrden);
        return ordenMapper.toResponseDto(ordenGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public OrdenResponseDTO obtenerOrdenPorId(Long id) {
        return ordenRepository.findById(id).map(ordenMapper::toResponseDto).orElseThrow(() -> new ErrorResponse("Orden no encontrada con ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<OrdenResponseDTO> obtenerOrdenes(Pageable pageable) {
        return ordenRepository.findAll(pageable).map(ordenMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrdenResponseDTO> obtenerOrdenesDelDia(Pageable pageable, String estadoOrden) {
        return ordenRepository.ordenesPorFechaActivoEstado(EstadoOrden.valueOf(estadoOrden.toUpperCase()), pageable).map(ordenMapper::toResponseDto);
    }

    @Override
    @Transactional
    public OrdenResponseDTO actualizarOrden(Long ordenId, OrdenActualizarRequestDTO ordenActualizarRequestDTO) {
        Orden orden = ordenRepository.findById(ordenId).orElseThrow(() -> new ErrorResponse("Orden no encontrada con ID: " + ordenId, HttpStatus.NOT_FOUND));
        if (orden.getEstado().equals(EstadoOrden.PREPARANDO) || orden.getEstado().equals(EstadoOrden.COMPLETADA) || orden.getEstado().equals(EstadoOrden.CANCELADA)) {
            throw new ErrorResponse("No se puede modificar una orden que ya está en preparación, ha sido completada o cancelada", HttpStatus.CONFLICT);
        }
        ordenMapper.actualizarDetalles(orden, ordenActualizarRequestDTO.detalles());
        Orden ordenActualizada = ordenRepository.save(orden);
        return ordenMapper.toResponseDto(ordenActualizada);
    }

    @Override
    @Transactional
    public OrdenResponseDTO cambiarEstadoOrden(Long ordenId, OrdenCambiarEstadoRequestDTO ordenCambiarEstadoRequestDTO) {
        Orden orden = ordenRepository.findById(ordenId).orElseThrow(() -> new ErrorResponse("Orden no encontrada con ID: " + ordenId, HttpStatus.NOT_FOUND));
        Mesa mesa = mesaRepository.findById(orden.getMesa().getId()).orElseThrow(() -> new ErrorResponse("Mesa con ID: " + orden.getMesa().getId() + ", no encontrada", HttpStatus.NOT_FOUND));
        if (orden.getEstado().equals(EstadoOrden.COMPLETADA) || orden.getEstado().equals(EstadoOrden.CANCELADA)) {
            throw new ErrorResponse("No se puede cambiar el estado de la orden una vez completada o cancelada", HttpStatus.CONFLICT);
        }
        orden.setEstado(EstadoOrden.valueOf(ordenCambiarEstadoRequestDTO.estadoOrden().toUpperCase()));
        if (orden.getEstado().equals(EstadoOrden.COMPLETADA) || orden.getEstado().equals(EstadoOrden.CANCELADA)) {
            mesa.setEstado(EstadoMesa.LIBRE);
            mesaRepository.save(mesa);
        }
        ordenRepository.save(orden);
        return ordenMapper.toResponseDto(orden);
    }

    @Override
    @Transactional
    public void desactivarOrdenes() {
        ordenRepository.desactivarOrdenesCanceladasCompletadas();
    }

}
