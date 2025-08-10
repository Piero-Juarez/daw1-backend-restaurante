package com.piero.backend.chat.app.ordenes.dto.detalleorden;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DetalleOrdenRequestDTO(
        @JsonProperty("item_menu_id") Integer itemMenuId,
        Integer cantidad
) { }
