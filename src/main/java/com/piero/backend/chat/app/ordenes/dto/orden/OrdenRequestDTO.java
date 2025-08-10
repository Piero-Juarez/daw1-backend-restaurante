package com.piero.backend.chat.app.ordenes.dto.orden;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piero.backend.chat.app.ordenes.dto.detalleorden.DetalleOrdenRequestDTO;

import java.util.List;

public record OrdenRequestDTO(
        @JsonProperty("mesa_id") Short mesaId,
        List<DetalleOrdenRequestDTO> detalles
) { }
