package com.piero.backend.chat.app.ordenes.service.impl;

import com.piero.backend.chat.app.exception.ErrorResponse;
import com.piero.backend.chat.app.ordenes.dto.mesa.MesaDTOResponse;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenActualizarRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenCambiarEstadoRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.mapper.MesaMapper;
import com.piero.backend.chat.app.ordenes.mapper.OrdenMapper;
import com.piero.backend.chat.app.ordenes.model.Mesa;
import com.piero.backend.chat.app.ordenes.model.Orden;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoMesa;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoOrden;
import com.piero.backend.chat.app.ordenes.repository.MesaRepository;
import com.piero.backend.chat.app.ordenes.repository.OrdenRepository;
import com.piero.backend.chat.app.ordenes.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final SimpMessagingTemplate messagingTemplate;
    private final OrdenRepository ordenRepository;
    private final MesaRepository mesaRepository;
    private final OrdenMapper ordenMapper;
    private final MesaMapper mesaMapper;

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
        MesaDTOResponse mesaDTOResponse = mesaMapper.toDto(mesa);
        messagingTemplate.convertAndSend("/topic/mesas/estado", mesaDTOResponse);

        Orden ordenGuardada = ordenRepository.save(nuevaOrden);
        return ordenMapper.toResponseDto(ordenGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public OrdenResponseDTO obtenerOrdenPorId(Long id) {
        return ordenRepository.findById(id).map(ordenMapper::toResponseDto).orElseThrow(() -> new ErrorResponse("Orden no encontrada con ID: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public OrdenResponseDTO obtenerOrdenPendientePorMesa(Short mesaId) {
        Orden orden = ordenRepository.findByMesa_IdAndEstadoAndActivoTrue(mesaId, EstadoOrden.PENDIENTE).orElseThrow(() -> new ErrorResponse("No se encontró una orden pendiente para la mesa con ID: " + mesaId, HttpStatus.NOT_FOUND));
        return ordenMapper.toResponseDto(orden);
    }

    @Override
    public Page<OrdenResponseDTO> obtenerOrdenes(Pageable pageable) {
        return ordenRepository.findAll(pageable).map(ordenMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrdenResponseDTO> obtenerOrdenesDelDia(Pageable pageable, List<String> estadosOrden) {
        List<EstadoOrden> estadosEnum = estadosOrden.stream().map(s -> EstadoOrden.valueOf(s.toUpperCase())).toList();
        return ordenRepository.ordenesPorFechaActivoEstado(estadosEnum, pageable).map(ordenMapper::toResponseDto);
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
    public OrdenResponseDTO marcarComoPagada(Long ordenId) {
        Orden orden = ordenRepository.findById(ordenId).orElseThrow(() -> new ErrorResponse("Orden no encontrada con ID: " + ordenId, HttpStatus.NOT_FOUND));
        if (!orden.getEstado().equals(EstadoOrden.ENTREGADA)) {
            throw new ErrorResponse("Solo se puede pagar una orden entregada", HttpStatus.CONFLICT);
        }

        orden.setEstado(EstadoOrden.PAGADA);

        Mesa mesa = orden.getMesa();
        mesa.setEstado(EstadoMesa.LIBRE);
        Mesa mesaActualizada = mesaRepository.save(mesa);

        MesaDTOResponse mesaDTOResponse = mesaMapper.toDto(mesaActualizada);
        messagingTemplate.convertAndSend("/topic/mesas/estado", mesaDTOResponse);

        Orden ordenGuardada = ordenRepository.save(orden);

        OrdenResponseDTO ordenDto = ordenMapper.toResponseDto(ordenGuardada);
        messagingTemplate.convertAndSend("/topic/ordenes/cambio-estado", ordenDto);
        return ordenDto;
    }

    @Override
    @Transactional
    public OrdenResponseDTO cambiarEstadoOrden(Long ordenId, OrdenCambiarEstadoRequestDTO ordenCambiarEstadoRequestDTO) {
        Orden orden = ordenRepository.findById(ordenId).orElseThrow(() -> new ErrorResponse("Orden no encontrada con ID: " + ordenId, HttpStatus.NOT_FOUND));
        EstadoOrden estadoActual = orden.getEstado();
        EstadoOrden nuevoEstado = EstadoOrden.valueOf(ordenCambiarEstadoRequestDTO.estadoOrden().toUpperCase());

        // REGLA 1: No se puede cambiar el estado de una orden ya cancelada o entregada.
        if (estadoActual.equals(EstadoOrden.CANCELADA) || estadoActual.equals(EstadoOrden.ENTREGADA)) {
            throw new ErrorResponse("No se puede cambiar el estado de una orden cancelada o ya entregada.", HttpStatus.CONFLICT);
        }

        // REGLA 2: Desde 'COMPLETADA', el único camino válido es 'EN_REPARTO'.
        if (estadoActual.equals(EstadoOrden.COMPLETADA) && !nuevoEstado.equals(EstadoOrden.EN_REPARTO)) {
            throw new ErrorResponse("Una orden completada en cocina solo puede pasar a 'En Reparto'.", HttpStatus.CONFLICT);
        }

        if (estadoActual.equals(EstadoOrden.EN_REPARTO) && !nuevoEstado.equals(EstadoOrden.ENTREGADA)) {
            throw new ErrorResponse("Una orden en reparto solo puede pasar a 'Entregada'.", HttpStatus.CONFLICT);
        }

        if (estadoActual.equals(EstadoOrden.PAGADA)) {
            throw new ErrorResponse("No se puede cambiar el estado de una orden pagada.", HttpStatus.CONFLICT);
        }

        // Si pasa las validaciones, aplicamos el nuevo estado.
        orden.setEstado(nuevoEstado);

        // Lógica para liberar la mesa. Ahora se activa cuando la orden es CANCELADA.
        if (nuevoEstado.equals(EstadoOrden.CANCELADA)) {
            Mesa mesa = mesaRepository.findById(orden.getMesa().getId()).orElseThrow(() -> new ErrorResponse("Mesa no encontrada", HttpStatus.NOT_FOUND));

            mesa.setEstado(EstadoMesa.LIBRE);
            Mesa mesaActualizada = mesaRepository.save(mesa);

            MesaDTOResponse mesaDTOResponse = mesaMapper.toDto(mesaActualizada);
            messagingTemplate.convertAndSend("/topic/mesas/estado", mesaDTOResponse);
        }

        ordenRepository.save(orden);

        OrdenResponseDTO ordenDto = ordenMapper.toResponseDto(orden);
        messagingTemplate.convertAndSend("/topic/ordenes/cambio-estado", ordenDto);
        return ordenDto;
    }

    @Override
    @Transactional
    public void desactivarOrdenes() {
        ordenRepository.desactivarOrdenesCanceladasCompletadas();
    }

}
