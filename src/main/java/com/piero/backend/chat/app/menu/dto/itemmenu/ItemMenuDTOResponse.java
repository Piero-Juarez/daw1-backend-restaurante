package com.piero.backend.chat.app.menu.dto.itemmenu;

import com.piero.backend.chat.app.menu.dto.categoria.CategoriaDTOResponse;
import lombok.Builder;

@Builder
public record ItemMenuDTOResponse(
    Integer idItemMenu,
    String nombreItemMenu,
    String descripcionItemMenu,
    Double precioItemMenu,
    String nombreImagenItemMenu,
    CategoriaDTOResponse categoriaDTOResponse

) {
}
