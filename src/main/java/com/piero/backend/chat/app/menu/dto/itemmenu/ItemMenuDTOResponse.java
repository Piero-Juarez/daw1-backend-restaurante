package com.piero.backend.chat.app.menu.dto.itemmenu;

import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTOResponse;

public record ItemMenuDTOResponse(
    Integer idItemMenu,
    String nombreItemMenu,
    String descripcionItemMenu,
    String precioItemMenu,
    String imagenItemMenu,
    CategoriaDTOResponse categoriaDTOResponse

) {
}
