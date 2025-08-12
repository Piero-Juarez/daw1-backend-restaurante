package com.piero.backend.chat.app.ordenes.service.impl;

import com.piero.backend.chat.app.exception.BusinessError;
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
        Mesa mesa = mesaRepository.findById(ordenRequestDTO.mesaId()).orElseThrow(() -> new IllegalArgumentException("Mesa con ID: " + ordenRequestDTO.mesaId() + ", no encontrada"));
        if (mesa.getEstado().equals(EstadoMesa.OCUPADA)) {
            throw new BusinessError("La mesa " + mesa.getNumero() + " ya se encuentra ocupada");
        }
        Orden nuevaOrden = ordenMapper.toEntity(ordenRequestDTO, mesa);
        mesa.setEstado(EstadoMesa.OCUPADA);
        mesaRepository.save(mesa);
        Orden ordenGuardada = ordenRepository.save(nuevaOrden);
        return ordenMapper.toResponseDto(ordenGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrdenResponseDTO> obtenerOrdenesDelDia(Pageable pageable) {
        return ordenRepository.findByFechaCreacionAndActivo(LocalDate.now(), true, pageable).map(ordenMapper::toResponseDto);
    }

    @Override
    @Transactional
    public OrdenResponseDTO actualizarOrden(Long ordenId, OrdenActualizarRequestDTO ordenActualizarRequestDTO) {
        Orden orden = ordenRepository.findById(ordenId).orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID: " + ordenId));
        if (orden.getEstado().equals(EstadoOrden.PREPARANDO) || orden.getEstado().equals(EstadoOrden.COMPLETADA) || orden.getEstado().equals(EstadoOrden.CANCELADA)) {
            throw new BusinessError("No se puede modificar una orden que ya está en preparación o ha sido completada o cancelada");
        }
        ordenMapper.actualizarDetalles(orden, ordenActualizarRequestDTO.detalles());
        Orden ordenActualizada = ordenRepository.save(orden);
        return ordenMapper.toResponseDto(ordenActualizada);
    }

    @Override
    @Transactional
    public OrdenResponseDTO cambiarEstadoOrden(Long ordenId, OrdenCambiarEstadoRequestDTO ordenCambiarEstadoRequestDTO) {
        Orden orden = ordenRepository.findById(ordenId).orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID: " + ordenId));
        Mesa mesa = mesaRepository.findById(orden.getMesa().getId()).orElseThrow(() -> new IllegalArgumentException("Mesa con ID: " + orden.getMesa().getId() + ", no encontrada"));
        if (orden.getEstado().equals(EstadoOrden.COMPLETADA)) {
            throw new BusinessError("No se puede cambiar el estado de la orden una vez completada");
        }
        orden.setEstado(EstadoOrden.valueOf(ordenCambiarEstadoRequestDTO.estadoOrden().toUpperCase()));
        if (orden.getEstado().equals(EstadoOrden.COMPLETADA)) {
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
