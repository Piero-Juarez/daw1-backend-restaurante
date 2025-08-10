package com.piero.backend.chat.app.ordenes.mapper;

import com.piero.backend.chat.app.ordenes.dto.orden.OrdenRequestDTO;
import com.piero.backend.chat.app.ordenes.dto.orden.OrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.model.Mesa;
import com.piero.backend.chat.app.ordenes.model.Orden;

public interface OrdenMapper {

    Orden toEntity(OrdenRequestDTO ordenRequestDTO, Mesa mesa);
    OrdenResponseDTO toResponseDto(Orden orden);

}
