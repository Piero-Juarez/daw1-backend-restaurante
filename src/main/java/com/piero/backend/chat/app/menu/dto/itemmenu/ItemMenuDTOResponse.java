package com.piero.backend.chat.app.menu.dto.itemmenu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTOResponse;
import lombok.Builder;

@Builder
public record ItemMenuDTOResponse(
    Integer id,
    String nombre,
    String descripcion,
    Double precio,
    @JsonProperty("enlace_imagen")
    String enlaceImagen,
    CategoriaDTOResponse categoria,
    String estado
) { }
