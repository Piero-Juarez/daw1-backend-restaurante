package com.piero.backend.chat.app.ordenes.service;

import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;

import java.util.List;

public interface OrdenService {

    OrdenResponseDTO crearOrden(OrdenRequestDTO ordenRequestDTO);
    List<OrdenResponseDTO> obtenerOrdenesDelDia();

}
