package com.piero.backend.chat.app.ordenes.dto.detalleorden;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DetalleOrdenResponseDTO(
        Long id,
        @JsonProperty("nombre_item_menu") String nombreItemMenu,
        Integer cantidad,
        @JsonProperty("precio_unitario") Double precioUnitario,
        Double subtotal,
        Double total
) { }
