package com.piero.backend.chat.app.ordenes.mapper;

import com.piero.backend.chat.app.ordenes.dto.detalleorden.DetalleOrdenResponseDTO;
import com.piero.backend.chat.app.ordenes.model.DetalleOrden;

public interface DetalleOrdenMapper {

    DetalleOrdenResponseDTO toDetalleResponseDto(DetalleOrden detalleOrden);

}
