package com.piero.backend.chat.app.ordenes.service;

import com.piero.backend.chat.app.ordenes.dto.orden.OrdenActualizarRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenCambiarEstadoRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrdenService {

    OrdenResponseDTO crearOrden(OrdenRequestDTO ordenRequestDTO);
    Page<OrdenResponseDTO> obtenerOrdenesDelDia(Pageable pageable);
    OrdenResponseDTO actualizarOrden(Long ordenId, OrdenActualizarRequestDTO ordenActualizarRequestDTO);
    OrdenResponseDTO cambiarEstadoOrden(Long ordenId, OrdenCambiarEstadoRequestDTO ordenCambiarEstadoRequestDTO);
    void desactivarOrdenes();

}
