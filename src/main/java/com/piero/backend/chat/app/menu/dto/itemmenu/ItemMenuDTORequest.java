package com.piero.backend.chat.app.menu.dto.itemmenu;

import java.io.File;

public record ItemMenuDTORequest(
    String nombre,
    String descripcion,
    Double precio,
    File imagen,
    String nombreImagen,
    Short idCategoria
) {
}
