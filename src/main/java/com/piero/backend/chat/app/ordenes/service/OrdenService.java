package com.piero.backend.chat.app.ordenes.service;

import com.piero.backend.chat.app.ordenes.dto.orden.OrdenActualizarRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenCambiarEstadoRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdenService {

    OrdenResponseDTO crearOrden(OrdenRequestDTO ordenRequestDTO);
    OrdenResponseDTO obtenerOrdenPorId(Long id);
    OrdenResponseDTO obtenerOrdenPendientePorMesa(Short mesaId);
    Page<OrdenResponseDTO> obtenerOrdenes(Pageable pageable);
    Page<OrdenResponseDTO> obtenerOrdenesDelDia(Pageable pageable, List<String> estadosOrden);
    OrdenResponseDTO actualizarOrden(Long ordenId, OrdenActualizarRequestDTO ordenActualizarRequestDTO);
    OrdenResponseDTO marcarComoPagada(Long ordenId);
    OrdenResponseDTO cambiarEstadoOrden(Long ordenId, OrdenCambiarEstadoRequestDTO ordenCambiarEstadoRequestDTO);
    void desactivarOrdenes();

}
