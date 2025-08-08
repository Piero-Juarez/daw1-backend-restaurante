package com.piero.backend.chat.app.menu.dto.itemmenu;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemMenuDTORequest(
    String nombre,
    String descripcion,
    Double precio,
    @JsonProperty("enlace_imagen")
    String enlaceImagen,
    @JsonProperty("id_categoria")
    Short idCategoria,
    String estado
) { }
