package com.piero.backend.chat.app.ordenes.service.impl;

import com.piero.backend.chat.app.exception.BusinessError;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.mapper.OrdenMapper;
import com.piero.backend.chat.app.ordenes.model.Mesa;
import com.piero.backend.chat.app.ordenes.model.Orden;
import com.piero.backend.chat.app.ordenes.model.enums.EstadoMesa;
import com.piero.backend.chat.app.ordenes.repository.MesaRepository;
import com.piero.backend.chat.app.ordenes.repository.OrdenRepository;
import com.piero.backend.chat.app.ordenes.service.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
            throw new BusinessError("La mesa " + mesa.getNumero() + "ya se encuentra ocupada");
        }
        Orden nuevaOrden = ordenMapper.toEntity(ordenRequestDTO, mesa);
        mesa.setEstado(EstadoMesa.OCUPADA);
        mesaRepository.save(mesa);
        Orden ordenGuardada = ordenRepository.save(nuevaOrden);
        return ordenMapper.toResponseDto(ordenGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenResponseDTO> obtenerOrdenesDelDia() {
        return ordenRepository.findByFechaCreacion(LocalDate.now()).stream().map(ordenMapper::toResponseDto).toList();
    }

}
