package com.piero.backend.chat.app.ordenes.dto.mesa;

public record MesaDTORequest(
        String numero,
        Short capacidad,
        String estado
) { }
