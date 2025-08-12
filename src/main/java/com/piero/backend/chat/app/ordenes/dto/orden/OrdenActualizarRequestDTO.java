package com.piero.backend.chat.app.ordenes.dto.orden;

import com.piero.backend.chat.app.ordenes.dto.detalleorden.DetalleOrdenRequestDTO;

import java.util.List;

public record OrdenActualizarRequestDTO(
        List<DetalleOrdenRequestDTO> detalles
) { }
