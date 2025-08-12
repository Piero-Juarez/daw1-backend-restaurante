package com.piero.backend.chat.app.ordenes.dto.orden;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrdenCambiarEstadoRequestDTO(
        @JsonProperty("estado_orden") String estadoOrden
) { }
