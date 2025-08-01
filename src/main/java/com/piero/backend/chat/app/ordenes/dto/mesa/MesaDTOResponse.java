package com.piero.backend.chat.app.ordenes.dto.mesa;

import lombok.Builder;

@Builder
public record MesaDTOResponse(
        Short id,
        String numero,
        Short capacidad,
        String estado
) { }
