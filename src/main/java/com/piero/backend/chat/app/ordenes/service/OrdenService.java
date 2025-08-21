package com.piero.backend.chat.app.ordenes.service;

import com.piero.backend.chat.app.ordenes.dto.orden.OrdenActualizarRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenCambiarEstadoRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrdenService {

    OrdenResponseDTO crearOrden(OrdenRequestDTO ordenRequestDTO);
    OrdenResponseDTO obtenerOrdenPorId(Long id);
    Page<OrdenResponseDTO> obtenerOrdenes(Pageable pageable);
    Page<OrdenResponseDTO> obtenerOrdenesDelDia(Pageable pageable, String estadoOrden);
    OrdenResponseDTO actualizarOrden(Long ordenId, OrdenActualizarRequestDTO ordenActualizarRequestDTO);
    OrdenResponseDTO cambiarEstadoOrden(Long ordenId, OrdenCambiarEstadoRequestDTO ordenCambiarEstadoRequestDTO);
    void desactivarOrdenes();

}
