package com.piero.backend.chat.app.menu.dto.categoria;

public record CategoriaDTOResponse (
        Short id,
        String nombre,
        String descripcion,
        double precioMinimo
){ }
