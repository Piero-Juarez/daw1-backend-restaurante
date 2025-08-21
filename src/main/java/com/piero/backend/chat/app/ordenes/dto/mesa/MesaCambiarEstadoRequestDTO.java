package com.piero.backend.chat.app.ordenes.dto.mesa;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MesaCambiarEstadoRequestDTO(
        @JsonProperty("estado_mesa") String estadoMesa
) { }
