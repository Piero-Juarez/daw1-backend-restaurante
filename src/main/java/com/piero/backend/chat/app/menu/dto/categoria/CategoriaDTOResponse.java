package com.piero.backend.chat.app.menu.dto.categoria;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoriaDTOResponse (
        Short id,
        String nombre,
        String descripcion,
        @JsonProperty("precio_minimo") double precioMinimo
){ }
